package dan.tp2021.productos.services;

import java.util.List;

import dan.tp2021.productos.domain.Material;
import dan.tp2021.productos.exceptions.material.MaterialException;


public interface MaterialService {

	List<Material> getListaMateriales();
	List<Material> getMaterialesByNombre(String nombre);
	List<Material> getMaterialesByDescripcion(String desc);
	Material getMaterialById(Integer id) throws MaterialException;
	Material saveMaterial(Material m) throws MaterialException;
	Material deleteMaterialById(Integer id) throws MaterialException;
	List<Material> getListaMaterialesByParams(String nombre, String descripcion) throws MaterialException;
	
}
