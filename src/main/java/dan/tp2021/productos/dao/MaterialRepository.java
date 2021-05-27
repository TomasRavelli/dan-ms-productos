package dan.tp2021.productos.dao;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

import dan.tp2021.productos.domain.Material;

public interface MaterialRepository extends JpaRepository<Material, Integer> {

    List<Material> findByNombreContains(String nombre);

    List<Material> findByDescripcionContains(String descripcion);

    List<Material> findByNombreContainsOrDescripcionContains(String nombre, String descripcion);

}
