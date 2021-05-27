package dan.tp2021.productos.services;

import java.util.*;
import java.util.logging.Logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dan.tp2021.productos.domain.DetalleProvision;
import dan.tp2021.productos.domain.Material;
import dan.tp2021.productos.domain.Provision;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import dan.tp2021.productos.dao.MovimientosStockRepository;
import dan.tp2021.productos.dao.MovimientosStockInMemoryRepository;
import dan.tp2021.productos.domain.MovimientosStock;
import dan.tp2021.productos.exeptions.movimientoStock.MovimientosStockException;
import dan.tp2021.productos.exeptions.movimientoStock.MovimientosStockNotFoundException;

import javax.jms.Message;

@Service
public class MovimientoStockServiceImpl implements MovimientosStockService {

	private static final Logger logger = LoggerFactory.getLogger(MovimientoStockServiceImpl.class);

	@Autowired
	MovimientosStockRepository movimientosStockRepository;

	@Autowired
	MaterialService materialServiceImpl;

	@Autowired
	ProvisionService provisionServiceImpl;

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
		//TODO hay que guardar el Material también? Si no hay material o no está en la BD se laza una excepción y el controller responde 422 Unprocessable Entity.
		//Este método se supone que actualiza solo los valores que no son null (hace merge) si la entidad existe en la BD, pero no me estaba andando.
		return movimientosStockRepository.save(ms);
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
	public void handle(HashMap msg) throws JmsException{
		msg.forEach((k,v)-> {
			try {
				registrarMovStock(k.toString(),(Integer) v);
			} catch (MaterialService.MaterialException | ProvisionService.ProvisionException | MovimientosStockException e) {
				e.printStackTrace();
			}
		});
	}

	private void registrarMovStock(String idString, Integer cantidad) throws MaterialService.MaterialException, ProvisionService.ProvisionException, MovimientosStockException {
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
			provisionServiceImpl.saveProvision(provision);
			movStockNuevo.setDetalleProvision(detalleProvision);
		}
		this.saveMovimientoStock(movStockNuevo);
	}

}
