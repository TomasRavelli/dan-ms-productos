package dan.tp2021.productos.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import dan.tp2021.productos.domain.MovimientosStock;

public interface MovimientosStockRepository extends JpaRepository<MovimientosStock, Integer> {


    List<MovimientosStock> findByMaterialId(Integer materialId);
}
