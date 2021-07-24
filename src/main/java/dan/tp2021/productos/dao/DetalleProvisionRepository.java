package dan.tp2021.productos.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import dan.tp2021.productos.domain.DetalleProvision;

public interface DetalleProvisionRepository extends JpaRepository<DetalleProvision, Integer> {

    List<DetalleProvision> findByMaterialId(Integer materialId);

}
