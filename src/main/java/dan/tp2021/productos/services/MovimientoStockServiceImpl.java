package dan.tp2021.productos.services;

import ch.qos.logback.core.encoder.EchoEncoder;
import dan.tp2021.productos.dao.MovimientosStockRepository;
import dan.tp2021.productos.domain.*;
import dan.tp2021.productos.exeptions.material.MaterialException;
import dan.tp2021.productos.exeptions.material.UnidadInvalidaException;
import dan.tp2021.productos.exeptions.movimientoStock.MovimientosStockException;
import dan.tp2021.productos.exeptions.movimientoStock.MovimientosStockNotFoundException;
import dan.tp2021.productos.exeptions.provision.ProvisionException;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

@Service
public class MovimientoStockServiceImpl implements MovimientosStockService {

    private static final Logger logger = LoggerFactory.getLogger(MovimientoStockServiceImpl.class);

    @Autowired
    MovimientosStockRepository movimientosStockRepository;

    @Autowired
    MaterialService materialServiceImpl;

    @Autowired
    ProvisionService provisionServiceImpl;

    @Autowired
    DetallePedidoService detallePedidoServiceImpl;
    
    @Autowired
	MeterRegistry meterRegistry;
	
	private Counter cantMovStock;
	
	@PostConstruct
	private void inicializarContadores() {
		cantMovStock = Counter.builder("Movimientos_Stock").tag("Cantidad", "Total").description("Total de movimientos stock realizados.").register(meterRegistry);
	}
	

    @Override
    public MovimientosStock getMovimientoStockById(Integer id) throws MovimientosStockException {

        Optional<MovimientosStock> find = movimientosStockRepository.findById(id);

        if (find.isEmpty()) {
            logger.debug("getMovimientoStockById(): No se pudo encontrar un MovimientosStock con el id \"" + id + "\"");
            throw new MovimientosStockNotFoundException("No se encontró el MovimientosStock con id: " + id);
        }
        logger.debug("getMovimientoStockById(): Se encontró el MovimientosStock con id \"" + id + "\": " + find.get());
        return find.get();
    }

    @Override
    public List<MovimientosStock> getListaMovimientos(Integer materialId) {

        List<MovimientosStock> resultado;

        if (materialId != null && materialId > 0) {
            resultado = movimientosStockRepository.findByMaterialId(materialId);
            logger.debug("getListaMovimientos(): Lista de MovimientosStock encontrados con el id de material \"" + materialId + "\": " + resultado);
            return resultado;
        }
        resultado = movimientosStockRepository.findAll();
        logger.debug("getListaMovimientos(): No se recibó id de Material, retornando lista completa: " + resultado);
        return resultado;
    }

    @Override
    public MovimientosStock saveMovimientoStock(MovimientosStock ms) throws MovimientosStockException {

        if (ms.getId() != null && !movimientosStockRepository.existsById(ms.getId())) {
            logger.debug("saveMovimientoStock(): Se recibió un Material con id no existente.");
            throw new MovimientosStockNotFoundException("");
        }
        logger.debug("saveMovimientoStock(): Guardando el MovimientosStock: " + ms);
        //Este método se supone que actualiza solo los valores que no son null (hace merge) si la entidad existe en la BD, pero no me estaba andando.
        
        MovimientosStock movStock = movimientosStockRepository.save(ms);
        cantMovStock.increment();
        return movStock;
    }

    @Override
    public MovimientosStock deleteMovimientoStockById(Integer id) throws MovimientosStockException {

        Optional<MovimientosStock> find = movimientosStockRepository.findById(id);

        if (find.isEmpty()) {
            logger.debug("deleteMovimientoStockById(): No se encontró el MovimientosStock con id \"" + id + "\" para eliminar. Lanzando excepción.");
            throw new MovimientosStockNotFoundException("No se encontró el MovimientosStock con id: " + id);
        }

        logger.debug("deleteMovimientoStockById(): Eliminado el MovimientosStock con id \"" + id + "\": " + find.get());
        movimientosStockRepository.deleteById(id);

        return find.get();
    }

    //Recibo mensajes del microservicio de Pedidos
    //Supongo que la descripcion en del producto en el microservicio de Pedidos equivale al nombre
    //en este microservicio
    @JmsListener(destination = "COLA_PEDIDOS")
    public void handle(ArrayList<Integer> msg) {
        logger.trace("LLEGO EL MENSAJE CON " + msg.size() + " DETALLES");
        //TODO falta que le llegue el id del detalle pedido para setearlo en el movimiento stock.
        for (Integer id : msg) {
            logger.debug("Detalle con id: " + id);
            try {
                DetallePedido detallePedido = detallePedidoServiceImpl.findById(id);
                registrarMovStock(detallePedido);
            } catch (Exception e) {
                logger.error("No existe el detalle pedido", e);
            }
        }
    }

    private void registrarMovStock(DetallePedido detallePedido) throws UnidadInvalidaException, MaterialException, ProvisionException, MovimientosStockException {
        MovimientosStock movStockNuevo = new MovimientosStock();
        Material material = detallePedido.getMaterial();
        Integer cantidad = detallePedido.getCantidad();
        //Actualizo stock actual del material
        material.setStockActual(material.getStockActual() - cantidad);
        material = materialServiceImpl.saveMaterial(material);
        movStockNuevo.setMaterial(material);
        movStockNuevo.setCantidadSalida(cantidad);
        movStockNuevo.setDetallePedido(detallePedido);
        movStockNuevo.setFecha(Instant.now());
        this.saveMovimientoStock(movStockNuevo);
        //Si el nuevo stock es menor al minimo creo una provision
        if (material.getStockActual() < material.getStockMinimo()) {
            Provision provision = new Provision();
            DetalleProvision detalleProvision = new DetalleProvision();
            detalleProvision.setMaterial(material);
            //Compro lo justo para quedar en el minimo, esto se podria variar segun necesidad del negocio
            detalleProvision.setCantidad(material.getStockMinimo() - material.getStockActual());
            provision.getDetalle().add(detalleProvision);
            provision.setFechaProvision(Instant.now());
            detalleProvision.setProvision(provision);
            provisionServiceImpl.saveProvision(provision);
            MovimientosStock movStockProvision = new MovimientosStock();
            movStockProvision.setMaterial(material);
            movStockProvision.setCantidadEntrada(detalleProvision.getCantidad());
            movStockProvision.setDetalleProvision(detalleProvision);
            movStockProvision.setFecha(Instant.now());
            this.saveMovimientoStock(movStockProvision);
        }
    }
	/*
	private void registrarMovStock(String idString, Integer cantidad) throws MaterialException, UnidadInvalidaException, ProvisionException, MovimientosStockException {
		Integer id = Integer.valueOf(idString);
		MovimientosStock movStockNuevo = new MovimientosStock();
		Material material = materialServiceImpl.getMaterialById(id);
		//Actualizo stock actual del material
		material.setStockActual(material.getStockActual()-cantidad);
		materialServiceImpl.saveMaterial(material);
		movStockNuevo.setMaterial(material);
		movStockNuevo.setCantidadSalida(cantidad);
		//Si el nuevo stock es menor al minimo creo una provision
		if(material.getStockActual()<material.getStockMinimo()){
			Provision provision = new Provision();
			DetalleProvision detalleProvision = new DetalleProvision();
			detalleProvision.setMaterial(material);
			//Compro lo justo para quedar en el minimo, esto se podria variar segun necesidad del negocio
			detalleProvision.setCantidad(material.getStockMinimo()-material.getStockActual());
			provision.getDetalle().add(detalleProvision);
			provision.setFechaProvision(Instant.now());
			detalleProvision.setProvision(provision);
			provisionServiceImpl.saveProvision(provision);
			MovimientosStock movStockProvision = new MovimientosStock();
			movStockProvision.setMaterial(material);
			movStockProvision.setCantidadEntrada(detalleProvision.getCantidad());
			movStockProvision.setDetalleProvision(detalleProvision);
			movStockProvision.setFecha(Instant.now());
			this.saveMovimientoStock(movStockProvision);
		}
		movStockNuevo.setFecha(Instant.now());
		this.saveMovimientoStock(movStockNuevo);
	}*/

}
