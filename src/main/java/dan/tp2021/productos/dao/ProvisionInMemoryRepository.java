package dan.tp2021.productos.dao;

import org.springframework.stereotype.Repository;

import dan.tp2021.productos.domain.Provision;
import frsf.isi.dan.InMemoryRepository;

@Repository
public class ProvisionInMemoryRepository extends InMemoryRepository<Provision> {

	@Override
	public Integer getId(Provision entity) {
		
		return entity.getId();
	}

	@Override
	public void setId(Provision entity, Integer id) {
		entity.setId(id);
		
	}

}
