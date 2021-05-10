package dan.tp2021.productos.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dan.tp2021.productos.dao.MaterialInMemoryRepository;
import dan.tp2021.productos.domain.Material;

@Service
public class MaterialServiceImpl implements MaterialService {

	@Autowired
	MaterialInMemoryRepository inMemoryRepository;

	@Override
	public List<Material> getListaMateriales() {

		List<Material> resultado = new ArrayList<>();
		inMemoryRepository.findAll().forEach(m -> resultado.add(m));
		return resultado;
	}

	@Override
	public List<Material> getMaterialesByNombre(String nombre) {

		List<Material> resultado = new ArrayList<>();
		inMemoryRepository.findAll().forEach(m -> {
			if (m.getNombre().contains(nombre))
				resultado.add(m);
		});
		return resultado;
	}

	@Override
	public List<Material> getMaterialesByDescripcion(String desc) {
		List<Material> resultado = new ArrayList<>();
		inMemoryRepository.findAll().forEach(m -> {
			if (m.getDescripcion().contains(desc))
				resultado.add(m);
		});
		return resultado;

	}

	@Override
	public Material getMaterialById(Integer id) throws MaterialException {

		Optional<Material> respuesta = inMemoryRepository.findById(id);
		if(respuesta.isPresent()) {
			return respuesta.get();
		}

		throw new MaterialNotFoundException("No se encontró el material con id: " + id);

	}

	@Override
	public Material saveMaterial(Material m) throws MaterialException {

		if (m.getId() != null && !inMemoryRepository.existsById(m.getId())) {
			// Por si busca actualizar un material que no existe.
			throw new MaterialNotFoundException("No se encontró el material con id: " + m.getId());

		}

		return inMemoryRepository.save(m);

	}

	@Override
	public Material deleteMaterialById(Integer id) throws MaterialException {

		Optional<Material> find = inMemoryRepository.findById(id);
		if(find.isEmpty()){
			throw new MaterialNotFoundException("No se encontró el material con id: " + id);
		}
		try {
			inMemoryRepository.deleteById(id);
		} catch (Exception e) {
			throw new MaterialException("");
		}
		return find.get();
	}

	@Override
	public List<Material> getListaMaterialesByParams(String nombre, String descripcion) throws MaterialException {
		List<Material> resultado = new ArrayList<>();
		try {
			inMemoryRepository.findAll().forEach(m -> {
				if (m.getDescripcion().contains(descripcion) || m.getNombre().contains(nombre))
					resultado.add(m);
			});
		} catch (Exception e) {
			throw new MaterialException("");
		}
		return resultado;
	}

}
