package dan.tp2021.productos.services;

import java.util.*;
import java.util.logging.Logger;

import dan.tp2021.productos.domain.DetalleProvision;
import dan.tp2021.productos.domain.Material;
import dan.tp2021.productos.domain.Provision;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import dan.tp2021.productos.dao.MovimientosStockInMemoryRepository;
import dan.tp2021.productos.domain.MovimientosStock;

import javax.jms.Message;

@Service
public class MovimientoStockServiceImpl implements MovimientosStockService {

	@Autowired
	MovimientosStockInMemoryRepository inMemoryRepository;

	@Autowired
	MaterialService materialServiceImpl;

	@Autowired
	ProvisionService provisionServiceImpl;

	@Override
	public MovimientosStock getMovimientoStockById(Integer id) throws MovimientosStockException {

		Optional<MovimientosStock> find = inMemoryRepository.findById(id);

		if (find.isEmpty()) {
			throw new MovimientosStockNotFoundException("No se encontró el MovimientoStockl con id: " + id);
		}

		return find.get();
	}

	@Override
	public List<MovimientosStock> getListaMovimientos(Integer idMaterial) {

		List<MovimientosStock> resultado = new ArrayList<>();
		
		if (idMaterial > 0) {
			resultado.addAll(getMovimientosByMaterial(idMaterial));
			return resultado;
		}
		inMemoryRepository.findAll().forEach(ms -> resultado.add(ms));
		return resultado;
	}

	@Override
	public List<MovimientosStock> getMovimientosByMaterial(Integer materialId) {

		List<MovimientosStock> resultado = new ArrayList<>();

		inMemoryRepository.findAll().forEach(ms -> {
			if (ms.getMaterial().getId().equals(materialId))
				resultado.add(ms);
		});

		return resultado;

	}

	@Override
	public MovimientosStock saveMovimientoStock(MovimientosStock ms) throws MovimientosStockException {

		if (ms.getId() != null && !inMemoryRepository.existsById(ms.getId())) {

			throw new MovimientosStockNotFoundException("");
		}

		return inMemoryRepository.save(ms);
	}

	@Override
	public MovimientosStock deleteMovimientoStockById(Integer id) throws MovimientosStockException {

		Optional<MovimientosStock> find = inMemoryRepository.findById(id);

		if (find.isEmpty()) {
			throw new MovimientosStockNotFoundException("No se encontró el MovimientoStockl con id: " + id);
		}

		inMemoryRepository.deleteById(id);

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
