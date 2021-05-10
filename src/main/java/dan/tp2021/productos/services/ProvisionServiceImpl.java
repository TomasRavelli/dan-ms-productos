package dan.tp2021.productos.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dan.tp2021.productos.dao.ProvisionInMemoryRepository;
import dan.tp2021.productos.domain.Provision;

@Service
public class ProvisionServiceImpl implements ProvisionService {

	@Autowired
	ProvisionInMemoryRepository inMemoryRepository;
	
	@Override
	public Provision getProvisionById(Integer id) throws ProvisionException {

		Optional<Provision> find = inMemoryRepository.findById(id);

		if(find.isEmpty()){
			throw new ProvisionNotFoundException("No se pudo encontrar la provisión con id: " + id);
		}
		
		return find.get();
	}

	@Override
	public List<Provision> getListaProvisiones() {
		List<Provision> resultado = new ArrayList<>();
		inMemoryRepository.findAll().forEach(p -> resultado.add(p));
		return resultado;
	}

	@Override
	public Provision saveProvision(Provision p) throws ProvisionException {
		if(p.getId() != null && !inMemoryRepository.existsById(p.getId())) {
			throw new ProvisionNotFoundException("");
		}
		return inMemoryRepository.save(p);
	}

	@Override
	public Provision deleteProvisionById(Integer id) throws ProvisionException {

		Optional<Provision> find = inMemoryRepository.findById(id);

		if(find.isEmpty()){
			throw new ProvisionNotFoundException("No se pudo encontrar la provisión con id: " + id);
		}

		inMemoryRepository.deleteById(id);
		return find.get();
	
	}

}
