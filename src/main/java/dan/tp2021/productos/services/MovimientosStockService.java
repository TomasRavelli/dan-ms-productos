package dan.tp2021.productos.services;

import java.util.List;

import dan.tp2021.productos.domain.MovimientosStock;


public interface MovimientosStockService {

	class MovimientosStockException extends Exception { MovimientosStockException(String message){super(message);}}
	class MovimientosStockNotFoundException extends MovimientosStockException { public MovimientosStockNotFoundException(String message){super(message);}}
	
	MovimientosStock getMovimientoStockById(Integer id) throws MovimientosStockException;
	List<MovimientosStock> getListaMovimientos(Integer materialId);
	MovimientosStock saveMovimientoStock(MovimientosStock ms) throws MovimientosStockException;
	MovimientosStock deleteMovimientoStockById(Integer id) throws MovimientosStockException;
}
