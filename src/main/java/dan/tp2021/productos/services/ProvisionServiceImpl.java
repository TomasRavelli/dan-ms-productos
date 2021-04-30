package dan.tp2021.productos.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import dan.tp2021.productos.dao.ProvisionInMemoryRepository;
import dan.tp2021.productos.domain.Provision;
import frsf.isi.dan.InMemoryRepository;

@Service
public class ProvisionServiceImpl implements ProvisionService {

	@Autowired
	ProvisionInMemoryRepository inMemoryRepository;
	
	@Override
	public ResponseEntity<Provision> getProvisionById(Integer id) {
		
		return ResponseEntity.of(inMemoryRepository.findById(id));
	}

	@Override
	public ResponseEntity<List<Provision>> getListaProvisiones() {
		List<Provision> resultado = new ArrayList<>();
		inMemoryRepository.findAll().forEach(p -> resultado.add(p));
		return ResponseEntity.ok(resultado);
	}

	@Override
	public ResponseEntity<Provision> saveProvision(Provision p) {
		if(p.getId() != null && !inMemoryRepository.existsById(p.getId())) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(inMemoryRepository.save(p));
	}

	@Override
	public ResponseEntity<Provision> deleteProvisionById(Integer id) {
		try {
			inMemoryRepository.deleteById(id);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			//Que pasa si no encuentra el id?
			return ResponseEntity.notFound().build();
		}
	
	}

}
