package dan.tp2021.productos.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dan.tp2021.productos.dao.MovimientosStockInMemoryRepository;
import dan.tp2021.productos.domain.MovimientosStock;

@Service
public class MovimientoStockServiceImpl implements MovimientosStockService {

	@Autowired
	MovimientosStockInMemoryRepository inMemoryRepository;

	@Override
	public MovimientosStock getMovimientoStockById(Integer id) throws MovimientosStockException {

		Optional<MovimientosStock> find = inMemoryRepository.findById(id);

		if(!find.isPresent()){
			throw new MovimientosStockNotFoundException("No se encontró el MovimientoStockl con id: " + id);
		}

		return find.get();
	}

	@Override
	public List<MovimientosStock> getListaMovimientos() {

		List<MovimientosStock> resultado = new ArrayList<>();

		inMemoryRepository.findAll().forEach(ms -> resultado.add(ms));

		return resultado;
	}

	@Override
	public List<MovimientosStock> getMovimientosByMaterial(Integer materialId) {

		List<MovimientosStock> resultado = new ArrayList<>();

		inMemoryRepository.findAll().forEach(ms -> {
			if (ms.getMaterial().getId().equals(materialId))
				resultado.add(ms);
		});

		return resultado;
	
	}

	@Override
	public MovimientosStock saveMovimientoStock(MovimientosStock ms) throws MovimientosStockException {
		
		
		if(ms.getId() != null && !inMemoryRepository.existsById(ms.getId())) {
			
			throw new MovimientosStockNotFoundException("");
		}
	
		return inMemoryRepository.save(ms);
	}

	@Override
	public MovimientosStock deleteMovimientoStockById(Integer id) throws MovimientosStockException {

		Optional<MovimientosStock> find = inMemoryRepository.findById(id);

		if(!find.isPresent()){
			throw new MovimientosStockNotFoundException("No se encontró el MovimientoStockl con id: " + id);
		}

		inMemoryRepository.deleteById(id);
		
		return find.get();
	}

}
