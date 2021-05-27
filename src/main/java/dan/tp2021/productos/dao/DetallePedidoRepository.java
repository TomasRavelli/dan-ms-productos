package dan.tp2021.productos.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dan.tp2021.productos.domain.DetallePedido;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer>{

	@Query("UPDATE DetallePedido dp SET dp.material = null where material.id = :idMaterial ")
	@Modifying
	void setNullMaterialInDetalle(@Param("idMaterial") Integer idMaterial);


}
