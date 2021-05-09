package dan.tp2021.productos.dao;

import org.springframework.stereotype.Repository;

import dan.tp2021.productos.domain.Material;
import frsf.isi.dan.InMemoryRepository;

@Repository
public class MaterialInMemoryRepository extends InMemoryRepository<Material> {

	@Override
	public Integer getId(Material entity) {
		
		return entity.getId();
	}

	@Override
	public void setId(Material entity, Integer id) {
		entity.setId(id);		
	}

}
