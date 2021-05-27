package dan.tp2021.productos.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import dan.tp2021.productos.domain.Unidad;

public interface UnidadRepository extends JpaRepository<Unidad, Integer> {

    Optional<Unidad> findByDescripcion(String descripcion);
}
