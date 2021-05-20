package dan.tp2021.productos.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dan.tp2021.productos.dao.MaterialInMemoryRepository;
import dan.tp2021.productos.dao.MaterialRepository;
import dan.tp2021.productos.domain.Material;

@Service
public class MaterialServiceImpl implements MaterialService {

	private static final Logger logger = LoggerFactory.getLogger(MaterialServiceImpl.class);

	@Autowired
	MaterialInMemoryRepository inMemoryRepository;

	@Autowired
	MaterialRepository materialRepository;

	@Override
	public List<Material> getListaMateriales() {

		List<Material> resultado = materialRepository.findAll();

		logger.debug("getListaMateriales(): Retorna: " + resultado);
		return resultado;
	}

	@Override
	public List<Material> getMaterialesByNombre(String nombre) {
		List<Material> resultado = materialRepository.findByNombreContains(nombre);
		logger.debug("getMaterialesByNombre(): Materiales encontrados con el nombre \"" + nombre + "\": " + resultado);
		return resultado;
	}

	@Override
	public List<Material> getMaterialesByDescripcion(String descripcion) {
		List<Material> resultado = materialRepository.findByDescripcionContains(descripcion);
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
	public Material saveMaterial(Material m) throws MaterialException {

		if (m.getId() != null && !materialRepository.existsById(m.getId())) {
			// Por si busca actualizar un material que no existe.
			throw new MaterialNotFoundException("No se encontró el material con id: " + m.getId());

		}
		logger.debug("saveMaterial(): Voy a guardar el Material: " + m);
		Material ret = materialRepository.save(m);
		logger.debug("saveMaterial(): Retrono el material:" + ret);
		return ret;

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
			materialRepository.deleteById(id);
		} catch (Exception e) {
			throw new MaterialException("");
		}
		return find.get();
	}

	@Override
	public List<Material> getListaMaterialesByParams(String nombre, String descripcion) throws MaterialException {
		
		List<Material> resultado;

		if (!nombre.isBlank() && !descripcion.isBlank()) {
			resultado = materialRepository.findByNombreContainsOrDescripcionContains(nombre, descripcion);
			logger.debug("getListaMaterialesByParams(): Materiales encontrados con nombre \"" + nombre + "\" y/o descripción \"" + descripcion + "\" : "+resultado);
			return resultado;
		}
		
		if (!nombre.isBlank()) {
			resultado = this.getMaterialesByNombre(nombre);
			return resultado;
		}

		if (!descripcion.isBlank()) {
			resultado = this.getMaterialesByDescripcion(descripcion);
			return resultado;
		}

		resultado = this.getListaMateriales();
		return resultado;

	}

}
