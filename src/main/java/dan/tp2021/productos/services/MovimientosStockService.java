package dan.tp2021.productos.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import dan.tp2021.productos.domain.MovimientosStock;

@Service
public interface MovimientosStockService {
	
	ResponseEntity<MovimientosStock> getMovimientoStockById(Integer id);
	ResponseEntity<List<MovimientosStock>> getListaMovimientos();
	ResponseEntity<List<MovimientosStock>> getMovimientosByMaterial(Integer materialId);
	ResponseEntity<MovimientosStock> saveMovimientoStock(MovimientosStock ms);
	ResponseEntity<MovimientosStock> deleteMovimientoStockById(Integer id);
}
