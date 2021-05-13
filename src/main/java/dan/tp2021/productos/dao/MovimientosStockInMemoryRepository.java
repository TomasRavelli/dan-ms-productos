package dan.tp2021.productos.dao;

import org.springframework.stereotype.Repository;

import dan.tp2021.productos.domain.MovimientosStock;
import frsf.isi.dan.InMemoryRepository;

@Deprecated
@Repository
public class MovimientosStockInMemoryRepository extends InMemoryRepository<MovimientosStock> {

	@Override
	public Integer getId(MovimientosStock entity) {
		
		return entity.getId();
	}

	@Override
	public void setId(MovimientosStock entity, Integer id) {
		entity.setId(id);
		
	}

}
