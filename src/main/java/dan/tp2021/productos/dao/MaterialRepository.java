package dan.tp2021.productos.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import dan.tp2021.productos.domain.Material;

public interface MaterialRepository extends JpaRepository<Material, Integer> {

    List<Material> findByNombreContainsAndStockActualBetween(String nombre, Integer stockMin, Integer stockMax);

    List<Material> findByDescripcionContainsAndStockActualBetween(String descripcion, Integer stockMin, Integer stockMax);

    @Query("SELECT m FROM Material m WHERE (m.nombre LIKE %:nombre% OR m.descripcion LIKE %:descripcion%) AND m.stockActual between :stockMin and :stockMax")
    List<Material> findByNombreContainsOrDescripcionContainsAndStockActualBetween(String nombre, String descripcion, Integer stockMin, Integer stockMax);

	List<Material> findAllByStockActualBetween(Integer stockMaximo, Integer stockMaximo2);
    

}
