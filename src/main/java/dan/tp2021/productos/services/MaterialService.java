package dan.tp2021.productos.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import dan.tp2021.productos.domain.Material;

@Service
public interface MaterialService {
	public ResponseEntity<List<Material>> getListaMateriales();
	public ResponseEntity<List<Material>> getMaterialesByNombre(String nombre);
	public ResponseEntity<List<Material>> getMaterialesByDescripcion(String desc);
	public ResponseEntity<Material> getMaterialById(Integer id);
	public ResponseEntity<Material> saveMaterial(Material m);
	public ResponseEntity<Material> deleteMaterialById(Integer id);
	
}
