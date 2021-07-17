package dan.tp2021.productos.services;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dan.tp2021.productos.dao.MaterialRepository;
import dan.tp2021.productos.dao.UnidadRepository;
import dan.tp2021.productos.domain.DetallePedido;
import dan.tp2021.productos.domain.DetalleProvision;
import dan.tp2021.productos.domain.Material;
import dan.tp2021.productos.domain.MovimientosStock;
import dan.tp2021.productos.domain.Unidad;
import dan.tp2021.productos.exeptions.material.MaterialException;
import dan.tp2021.productos.exeptions.material.MaterialNotFoundException;
import dan.tp2021.productos.exeptions.material.UnidadInvalidaException;

@Service
public class MaterialServiceImpl implements MaterialService {

	private static final Logger logger = LoggerFactory.getLogger(MaterialServiceImpl.class);

	@Autowired
	DetallePedidoService detallePedidoService;

	@Autowired
	MaterialRepository materialRepository;

	@Autowired
	UnidadRepository unidadRepository;

	@Autowired
	MovimientosStockService movimientosStockService;

	@Autowired
	DetalleProvisionService detalleProvisionService;

	@Override
	public List<Material> getListaMateriales(Integer stockMinimo, Integer stockMaximo, Double precioMin, Double precioMax) {

		List<Material> resultado = materialRepository.findAllByStockActualBetweenAndPrecioBetweenAndFechaBajaNull(stockMinimo, stockMaximo, precioMin, precioMax);

		logger.debug("getListaMateriales(): Retorna: " + resultado);
		return resultado;
	}

	@Override
	public List<Material> getMaterialesByNombre(String nombre, Integer stockMinimo, Integer stockMaximo, Double precioMin, Double precioMax) {
		List<Material> resultado = materialRepository.findByNombreContainsAndStockActualBetweenAndPrecioBetweenAndFechaBajaNull(nombre, stockMinimo, stockMaximo, precioMin, precioMax);
		logger.debug("getMaterialesByNombre(): Materiales encontrados con el nombre \"" + nombre + "\": " + resultado);
		return resultado;
	}

	@Override
	public List<Material> getMaterialesByDescripcion(String descripcion, Integer stockMinimo, Integer stockMaximo, Double precioMin, Double precioMax) {
		List<Material> resultado = materialRepository.findByDescripcionContainsAndStockActualBetweenAndPrecioBetweenAndFechaBajaNull(descripcion, stockMinimo, stockMaximo, precioMin, precioMax);
		logger.debug("getMaterialesByNombre(): Materiales encontrados con el la descripción \"" + descripcion + "\": " + resultado);
		return resultado;
	}

	@Override
	public Material getMaterialById(Integer id) throws MaterialException {

		Optional<Material> respuesta = materialRepository.findById(id);
		if (respuesta.isPresent()) {
			return respuesta.get();
		}

		throw new MaterialNotFoundException("No se encontró el material con id: " + id);

	}

	@Override
	public Material saveMaterial(Material m) throws MaterialException, UnidadInvalidaException {

		if (m.getId() != null && !materialRepository.existsById(m.getId())) {
			// Por si busca actualizar un material que no existe.
			throw new MaterialNotFoundException("No se encontró el material con id: " + m.getId());

		}
		logger.debug("saveMaterial(): Validando la unidad en el material a guardar." + m);
		validarUnidad(m);
		logger.debug("saveMaterial(): Voy a guardar el Material: " + m);
		Material ret = materialRepository.save(m);
		logger.debug("saveMaterial(): Retrono el material:" + ret);
		return ret;

	}

	public void validarUnidad(Material material) throws UnidadInvalidaException {
		Unidad unidadRecibida = material.getUnidad();
		boolean unidadValida = false;

		logger.debug("validarUnidad(): Validando unidad: " + unidadRecibida);

		if(unidadRecibida == null){
			logger.debug("validarUnidad(): La unidad es null, no es válida.");
			throw new UnidadInvalidaException("El material recibido no tiene una unidad seteada.");
		}

		if(unidadRecibida.getId() != null && unidadRecibida.getId() > 0){
			//La unidad tiene id, capaz qeu existe en la BD.
			logger.trace("validarUnidad(): La unidad tiene un id válido se va a buscar a la base de datos.");
			Optional<Unidad> unidadById = unidadRepository.findById(unidadRecibida.getId());
			if(unidadById.isPresent()){
				logger.trace("validarUnidad(): La unidad existe en la base de datos");
				if(unidadRecibida.getDescripcion() != null && !unidadRecibida.getDescripcion().isBlank() && !unidadById.get().getDescripcion().equals(unidadRecibida.getDescripcion())){
					//Error, se recibió una unidad con un id existente pero con otra descripción.
					logger.debug("validarUnidad(): La unidad recibida tiene un id existente pero una descripción diferente a la de la base de datos.");
					throw new UnidadInvalidaException("La unidad recibida tiene un id válido existente con una descripción distinta a la recibida");
				}
				//La unidad es válida
				material.setUnidad(unidadById.get());
				unidadValida = true;
			}
		}
		if(!unidadValida){
			//La unidad no se pudo validar con el id, probamos con la descripción.
			logger.debug("validarUnidad(): La unidad no tiene id o tiene un id inexistente.");
			if(unidadRecibida.getDescripcion() == null || unidadRecibida.getDescripcion().isBlank()){
				//Descripción recibida no es válida.
				logger.debug("validarUnidad(): No se recibió descripción para la unidad sin id.");
				throw new UnidadInvalidaException("Se recibió una unidad sin id o con un id inexistente pero con una descripción inválida.");
			}
			//La descripción es válida
			logger.trace("validarUnidad(): Se recibió la descripción \"" + unidadRecibida.getDescripcion() + "\". Buscando la unidad en la base de datos.");
			Optional<Unidad> unidadByDescripcion = unidadRepository.findByDescripcion(unidadRecibida.getDescripcion());
			if(unidadByDescripcion.isPresent()){
				//La descripción existe en la base de datos, usamos esa unidad.
				logger.debug("validarUnidad(): Se encontró una unidad con la descripción recibida: " + unidadByDescripcion.get());
				material.setUnidad(unidadByDescripcion.get());
			} else {
				//La descripción no existe pero es válida, seteamos el id de la unidad en null para asegurar que se cree una nueva.
				logger.debug("validarUnidad(): No se encontró una unidad con la descripción \"" + unidadRecibida.getDescripcion() + "\". Seteando el id en null para crear la nueva unidad.");
				material.getUnidad().setId(null);
				
			}
		}
	}

	@Override
	public Material deleteMaterialById(Integer id) throws MaterialException {

		Optional<Material> find = materialRepository.findById(id);

		if (find.isEmpty()) {
			throw new MaterialNotFoundException("No se encontró el material con id: " + id);
		}
		try {
			logger.debug("deleteMaterialById(): Voy a eliminar el material con id: " + id);
			//TODO Tira error porque tiene relaciones con otras entidades, por ejemplo detalle_pedido, en Pedidos.
//			detallePedidoService.setNullMateriales(id);
			Material material = find.get();
			if(this.sePuedeEliminarMaterial(material)) {
				//El material no tiene relaciones con otras entidades, podemos eliminarlo.
				materialRepository.deleteById(id);
			} else {
				//El material tiene relación con alguna otra entidad, hay que marcarlo como dado de baja
				material.setFechaBaja(Instant.now());
				materialRepository.save(material);
			}
		} catch (Exception e) {
			logger.error("deleteMaterialById(): Excepción eliminando el material: " + e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
			throw new MaterialException("");
		}
		return find.get();
	}

	private boolean sePuedeEliminarMaterial(Material material) {

		List<DetallePedido> detallesPedido = detallePedidoService.findByMaterialId(material.getId());
		logger.debug("sePuedeEliminarMaterial(): Lista de detalles pedido: " + detallesPedido);
		if(detallesPedido.isEmpty()){
			//hay que ver los detalles provisiones y movimientos stock.
			List<MovimientosStock> movimientosStock = movimientosStockService.getListaMovimientos(material.getId());
			logger.debug("sePuedeEliminarMaterial(): Lista de movimientos de stock: " + movimientosStock);
			if(movimientosStock.isEmpty()){
				//último cheque, detalles de provisión.
				List<DetalleProvision> detallesProvision = detalleProvisionService.findByMaterialId(material.getId());
				logger.debug("sePuedeEliminarMaterial(): Lista de detalles provisión: " + detallesProvision);
				//Si la lista esta vacía se puede eliminar el material, sino no.
				return detallesProvision.isEmpty();
			}
		}
		logger.debug("sePuedeEliminarMaterial(): No se puede eliminar el material. Retorno false");
		//Si llego hasta acá significa que el material está relacionado con detalles pedido o movimientos de stock.
		return false;

	}

	@Override
	public List<Material> getListaMaterialesByParams(String nombre, String descripcion, Integer stockMinimo, Integer stockMaximo, Double precioMin, Double precioMax){
		
		List<Material> resultado;

		if (!nombre.isBlank() && !descripcion.isBlank()) {
			resultado = materialRepository.findByNombreContainsOrDescripcionContainsAndStockActualBetweenAndPrecioBetweenAndFechaBajaNull(nombre, descripcion, stockMinimo, stockMaximo, precioMin, precioMax);
			logger.debug("getListaMaterialesByParams(): Materiales encontrados con nombre \"" + nombre + "\" y/o descripción \"" + descripcion + "\" : "+resultado);
			return resultado;
		}
		
		if (!nombre.isBlank()) {
			resultado = this.getMaterialesByNombre(nombre, stockMinimo, stockMaximo, precioMin, precioMax);
			return resultado;
		}

		if (!descripcion.isBlank()) {
			resultado = this.getMaterialesByDescripcion(descripcion, stockMinimo, stockMaximo, precioMin, precioMax);
			return resultado;
		}

		resultado = this.getListaMateriales(stockMinimo, stockMaximo, precioMin, precioMax);
		return resultado;

	}

}