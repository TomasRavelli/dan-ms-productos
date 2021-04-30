package dan.tp2021.productos.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import dan.tp2021.productos.domain.MovimientosStock;
import frsf.isi.dan.InMemoryRepository;

public class MovimientoStockServiceImpl implements MovimientosStockService {

	@Autowired
	InMemoryRepository<MovimientosStock> inMemoryRepository;

	@Override
	public ResponseEntity<MovimientosStock> getMovimientoStockById(Integer id) {

		return ResponseEntity.of(inMemoryRepository.findById(id));
	}

	@Override
	public ResponseEntity<List<MovimientosStock>> getListaMovimientos() {

		List<MovimientosStock> resultado = new ArrayList<>();

		inMemoryRepository.findAll().forEach(ms -> resultado.add(ms));

		return ResponseEntity.ok(resultado);
	}

	@Override
	public ResponseEntity<List<MovimientosStock>> getMovimientosByMaterial(Integer materialId) {

		List<MovimientosStock> resultado = new ArrayList<>();

		inMemoryRepository.findAll().forEach(ms -> {
			if (ms.getMaterial().getId().equals(materialId))
				resultado.add(ms);
		});

		return ResponseEntity.ok(resultado);
	
	}

	@Override
	public ResponseEntity<MovimientosStock> saveMovimientoStock(MovimientosStock ms) {
		
		if(ms.getId() != null && !inMemoryRepository.existsById(ms.getId())) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(inMemoryRepository.save(ms));
	}

	@Override
	public ResponseEntity<MovimientosStock> deleteMovimientoStockById(Integer id) {
		
		try{
			inMemoryRepository.deleteById(id);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} 
		
		return ResponseEntity.ok().build();
	}

}
