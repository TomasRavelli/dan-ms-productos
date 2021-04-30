package dan.tp2021.productos.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import dan.tp2021.productos.domain.MovimientosStock;

@Service
public interface MovimientosStockService {
	
	public ResponseEntity<MovimientosStock> getMovimientoStockById(Integer id);
	public ResponseEntity<List<MovimientosStock>> getListaMovimientos();
	public ResponseEntity<List<MovimientosStock>> getMovimientosByMaterial(Integer materialId);
	public ResponseEntity<MovimientosStock> saveMovimientoStock(MovimientosStock ms);
	public ResponseEntity<MovimientosStock> deleteMovimientoStockById(Integer id);
}
