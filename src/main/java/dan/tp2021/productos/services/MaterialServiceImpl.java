package dan.tp2021.productos.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import dan.tp2021.productos.domain.Material;
import frsf.isi.dan.InMemoryRepository;

public class MaterialServiceImpl implements MaterialService {

	@Autowired
	InMemoryRepository<Material> inMemoryRepository;

	@Override
	public ResponseEntity<List<Material>> getListaMateriales() {

		List<Material> resultado = new ArrayList<Material>();
		inMemoryRepository.findAll().forEach(m -> resultado.add(m));
		return ResponseEntity.ok(resultado);
	}

	@Override
	public ResponseEntity<List<Material>> getMaterialesByNombre(String nombre) {

		List<Material> resultado = new ArrayList<Material>();
		inMemoryRepository.findAll().forEach(m -> {
			if (m.getNombre().contains(nombre))
				resultado.add(m);
		});
		return ResponseEntity.ok(resultado);
	}

	@Override
	public ResponseEntity<List<Material>> getMaterialesByDescripcion(String desc) {
		List<Material> resultado = new ArrayList<Material>();
		inMemoryRepository.findAll().forEach(m -> {
			if (m.getDescripcion().contains(desc))
				resultado.add(m);
		});
		return ResponseEntity.ok(resultado);

	}

	@Override
	public ResponseEntity<Material> getMaterialById(Integer id) {

		return ResponseEntity.of(inMemoryRepository.findById(id));
	}

	@Override
	public ResponseEntity<Material> saveMaterial(Material m) {

		if (m.getId() != null && !inMemoryRepository.existsById(m.getId())) {
			// Por si busca actualizar un material que no existe.
			return ResponseEntity.badRequest().build();

		}

		return ResponseEntity.ok(inMemoryRepository.save(m));

	}

	@Override
	public ResponseEntity<Material> deleteMaterialById(Integer id) {
		try {
			inMemoryRepository.deleteById(id);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
