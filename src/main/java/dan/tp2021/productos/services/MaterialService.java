package dan.tp2021.productos.services;

import java.util.List;

import dan.tp2021.productos.domain.Material;


public interface MaterialService {

	class MaterialException extends Exception { MaterialException(String message){super(message);}}
	class MaterialNotFoundException extends MaterialException {MaterialNotFoundException(String message){super(message);}}

	public List<Material> getListaMateriales();
	public List<Material> getMaterialesByNombre(String nombre);
	public List<Material> getMaterialesByDescripcion(String desc);
	public Material getMaterialById(Integer id) throws MaterialException;
	public Material saveMaterial(Material m) throws MaterialException;
	public Material deleteMaterialById(Integer id) throws MaterialException;
	public List<Material> getListaMaterialesByParams(String nombre, String descripcion) throws MaterialException;
	
}
