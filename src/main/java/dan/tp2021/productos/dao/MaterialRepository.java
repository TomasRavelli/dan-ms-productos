package dan.tp2021.productos.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import dan.tp2021.productos.domain.Material;

public interface MaterialRepository extends JpaRepository<Material, Integer> {

    List<Material> findByNombreContainsAndStockActualBetweenAndPrecioBetweenAndFechaBajaNull(String nombre, Integer stockMin, Integer stockMax, Double precioMin, Double precioMax);

    List<Material> findByDescripcionContainsAndStockActualBetweenAndPrecioBetweenAndFechaBajaNull(String descripcion, Integer stockMin, Integer stockMax, Double precioMin, Double precioMax);

    @Query("SELECT m FROM Material m WHERE (m.nombre LIKE %:nombre% OR m.descripcion LIKE %:descripcion%) AND (m.stockActual between :stockMin and :stockMax AND m.precio between :precioMin and :precioMax) AND m.fechaBaja is null")
    List<Material> findByNombreContainsOrDescripcionContainsAndStockActualBetweenAndPrecioBetweenAndFechaBajaNull(String nombre, String descripcion, Integer stockMin, Integer stockMax, Double precioMin, Double precioMax);

	List<Material> findAllByStockActualBetweenAndPrecioBetweenAndFechaBajaNull(Integer stockMaximo, Integer stockMaximo2, Double precioMin, Double precioMax);
    

}
