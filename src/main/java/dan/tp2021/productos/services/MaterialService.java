
package dan.tp2021.productos.services;

import java.util.List;

import dan.tp2021.productos.domain.Material;
import dan.tp2021.productos.exeptions.material.MaterialException;
import dan.tp2021.productos.exeptions.material.UnidadInvalidaException;


public interface MaterialService {

	List<Material> getListaMateriales(Integer stockMinimo, Integer stockMaximo, Double precioMin, Double precioMax);
	List<Material> getMaterialesByNombre(String nombre, Integer stockMinimo, Integer stockMaximo, Double precioMin, Double precioMax);
	List<Material> getMaterialesByDescripcion(String desc, Integer stockMinimo, Integer stockMaximo, Double precioMin, Double precioMax);
	Material getMaterialById(Integer id) throws MaterialException;
	Material saveMaterial(Material m) throws MaterialException, UnidadInvalidaException;
	Material deleteMaterialById(Integer id) throws MaterialException;
	List<Material> getListaMaterialesByParams(String nombre, String descripcion, Integer stockMinimo, Integer stockMaximo, Double precioMin, Double precioMax) throws MaterialException;
	
}
