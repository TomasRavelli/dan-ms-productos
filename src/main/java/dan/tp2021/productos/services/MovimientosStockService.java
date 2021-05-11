package dan.tp2021.productos.services;

import java.util.List;

import dan.tp2021.productos.domain.MovimientosStock;
import dan.tp2021.productos.exceptions.movimientosstock.MovimientosStockException;


public interface MovimientosStockService {

	
	MovimientosStock getMovimientoStockById(Integer id) throws MovimientosStockException;
	List<MovimientosStock> getListaMovimientos(Integer materialId);
	List<MovimientosStock> getMovimientosByMaterial(Integer materialId);
	MovimientosStock saveMovimientoStock(MovimientosStock ms) throws MovimientosStockException;
	MovimientosStock deleteMovimientoStockById(Integer id) throws MovimientosStockException;
}
