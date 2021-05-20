package dan.tp2021.productos.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dan.tp2021.productos.dao.ProvisionInMemoryRepository;
import dan.tp2021.productos.dao.ProvisionRepository;
import dan.tp2021.productos.domain.DetalleProvision;
import dan.tp2021.productos.domain.Provision;

@Service
public class ProvisionServiceImpl implements ProvisionService {

	private static final Logger logger = LoggerFactory.getLogger(ProvisionServiceImpl.class);

	@Autowired
	ProvisionInMemoryRepository inMemoryRepository;

	@Autowired
	ProvisionRepository provisionRepository;
	
	@Override
	public Provision getProvisionById(Integer id) throws ProvisionException {

		Optional<Provision> find = provisionRepository.findById(id);

		if(find.isEmpty()){
			throw new ProvisionNotFoundException("No se pudo encontrar la provisión con id: " + id);
		}
		
		return find.get();
	}

	@Override
	public List<Provision> getListaProvisiones() {
		List<Provision> resultado = provisionRepository.findAll();
		logger.debug("getListaProvisiones(): retornando: " + resultado);
		return resultado;
	}

	@Override
	public Provision saveProvision(Provision p) throws ProvisionException {
		if(p.getId() != null && !provisionRepository.existsById(p.getId())) {
			logger.debug("saveProvision(): Se recibió una provisión con id pero que no está en la base de datos: " + p);
			throw new ProvisionNotFoundException("");
		}
		for (DetalleProvision det: p.getDetalle()) {
			det.setProvision(p);
		}
		if(p.getId() == null){
			p.setFechaProvision(Instant.now());
		}
		logger.debug("saveProvision(): Guardando la provisión: " + p);
		return provisionRepository.save(p);
	}

	@Override
	public Provision deleteProvisionById(Integer id) throws ProvisionException {

		Optional<Provision> find = provisionRepository.findById(id);

		if(find.isEmpty()){
			logger.debug("deleteProvisionById(): No se encontró la provisión con id " + id + " para eliminar.");
			throw new ProvisionNotFoundException("No se pudo encontrar la provisión con id: " + id);
		}
		logger.debug("deleteProvisionById(): Eliminando la provisión: " + find.get());
		provisionRepository.deleteById(id);
		return find.get();
	
	}

}
