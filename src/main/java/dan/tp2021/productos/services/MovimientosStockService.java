package dan.tp2021.productos.services;

import java.util.List;

import dan.tp2021.productos.domain.MovimientosStock;


public interface MovimientosStockService {

	class MovimientosStockException extends Exception { MovimientosStockException(String message){super(message);}}
	class MovimientosStockNotFoundException extends MovimientosStockException {MovimientosStockNotFoundException(String message){super(message);}}
	
	public MovimientosStock getMovimientoStockById(Integer id) throws MovimientosStockException;
	public List<MovimientosStock> getListaMovimientos();
	public List<MovimientosStock> getMovimientosByMaterial(Integer materialId);
	public MovimientosStock saveMovimientoStock(MovimientosStock ms) throws MovimientosStockException;
	public MovimientosStock deleteMovimientoStockById(Integer id) throws MovimientosStockException;
}
