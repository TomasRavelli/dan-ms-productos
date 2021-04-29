package dan.tp2021.productos.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import dan.tp2021.productos.domain.Material;

@Service
public interface MaterialService {
	ResponseEntity<List<Material>> getListaMateriales();
	ResponseEntity<List<Material>> getMaterialesByNombre(String nombre);
	ResponseEntity<List<Material>> getMaterialesByDescripcion(String desc);
	ResponseEntity<Material> getMaterialById(Integer id);
	ResponseEntity<Material> saveMaterial(Material m);
	ResponseEntity<Material> deleteMaterialById(Integer id);
	
}
