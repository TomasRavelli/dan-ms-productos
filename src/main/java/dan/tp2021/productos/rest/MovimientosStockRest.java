package dan.tp2021.productos.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dan.tp2021.productos.domain.MovimientosStock;
import dan.tp2021.productos.services.MovimientosStockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/movimientostock")
@Api(value = "MovimientosStockRest", description = "Permite gestionar los movimientos de stock de la empresa")
public class MovimientosStockRest {

	private static final Logger logger = LoggerFactory.getLogger(MovimientosStockRest.class);

	@Autowired
	MovimientosStockService movimientosStockServiceImpl;

	@PostMapping
	@ApiOperation(value = "Crear un nuevo movimiento stock.")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Movimiento creado correctamente"),
			@ApiResponse(code = 401, message = "No autorizado"), 
			@ApiResponse(code = 403, message = "Prohibido"),
			@ApiResponse(code = 404, message = "El ID no existe") })
	public ResponseEntity<MovimientosStock> saveMovStock(@RequestBody MovimientosStock ms) {

		try {
			MovimientosStock resultado = movimientosStockServiceImpl.saveMovimientoStock(ms);
			return ResponseEntity.ok(resultado);
		} catch (MovimientosStockService.MovimientosStockNotFoundException e){
			logger.warn("saveMovStock(): No se encontró el MovimientosStock: " + ms);
			return ResponseEntity.notFound().build();
		} catch (DataIntegrityViolationException e){
			//Esta excepción se lanza cuando se viola una restricción SQL, como una foreign Key. Nose como saber excatamente que pasó así que retorno un mensage general.
			// Respondo 422 Unprocessable Entity, porque es un error en los datos que me mandaron y el cliente tiene que cambiar algo para que no falle: https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/422
			logger.error("saveMovStock(): Se produjo un error al insertar el MovimientosStock en la base de datos. MovimientosStock: " + ms + "\n Error recibido: " + e.getMessage());
			return ResponseEntity.unprocessableEntity().build();
		} catch (Exception e) {
			logger.error("saveMovStock(): Ocurrió un error inesperado: " + e.getMessage());
			logger.debug("saveMovStock(): Tipo de la excepción: " + e.getClass().getName());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Obtener movimiento stock por su id.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Se obtuvo movimiento correctamente"),
			@ApiResponse(code = 401, message = "No autorizado"), @ApiResponse(code = 403, message = "Prohibido"),
			@ApiResponse(code = 404, message = "El ID no existe") })
	public ResponseEntity<MovimientosStock> getMovimientoStockById(@PathVariable Integer id) {

		try {
			MovimientosStock resultado = movimientosStockServiceImpl.getMovimientoStockById(id);
			return ResponseEntity.ok(resultado);
		} catch (MovimientosStockService.MovimientosStockNotFoundException e){
			logger.warn("getMovimientoStockById(): No se encontró el MovimientosStock con id: " + id);
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			logger.error("saveMovStock(): Ocurrió un error inesperado: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@GetMapping()
	@ApiOperation(value = "Obtener todos los movimientos de stock, o por id de material")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Se obtuvieron los movimientos correctamente"),
			@ApiResponse(code = 401, message = "No autorizado"), @ApiResponse(code = 403, message = "Prohibido"),
			@ApiResponse(code = 404, message = "El ID no existe") })
	public ResponseEntity<List<MovimientosStock>> getMovimientosStock(
			@RequestParam(name = "idMaterial", required = false, defaultValue = "0") Integer idMaterial) {

		List<MovimientosStock> resultado;
		try {
			resultado = movimientosStockServiceImpl.getListaMovimientos(idMaterial);
			logger.debug("saveMovStock(): Devolviendo la lista: " + resultado);
			return ResponseEntity.ok(resultado);
		} catch (Exception e) {
			logger.error("saveMovStock(): Ocurrió un error inesperado: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	// No se si se permite esto
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Eliminar movimiento de stock por su id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Se elimino el movimiento correctamente"),
			@ApiResponse(code = 401, message = "No autorizado"), @ApiResponse(code = 403, message = "Prohibido"),
			@ApiResponse(code = 404, message = "El ID no existe") })
	public ResponseEntity<MovimientosStock> deleteMovimientoStock(@PathVariable Integer id) {

		try {
			MovimientosStock resultado = movimientosStockServiceImpl.deleteMovimientoStockById(id);
			logger.debug("deleteMovimientoStock(): retornando: " + resultado);
			return ResponseEntity.ok(resultado);
		} catch (MovimientosStockService.MovimientosStockNotFoundException e){
			logger.warn("deleteMovimientoStock(): No se encontró el MovimientosStock con id: " + id);
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			logger.error("deleteMovimientoStock(): Ocurrió un error inesperado: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	// No se si se permite esto.
	@PutMapping()
	@ApiOperation(value = "Actualizar un movimiento de stock")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Se actualizo el movimiento correctamente"),
			@ApiResponse(code = 401, message = "No autorizado"), @ApiResponse(code = 403, message = "Prohibido"),
			@ApiResponse(code = 404, message = "El ID no existe") })
	public ResponseEntity<MovimientosStock> updateMovimientoStock(@RequestBody MovimientosStock ms) {

		if (ms.getId() != null) {
			try {
				MovimientosStock resultado = movimientosStockServiceImpl.saveMovimientoStock(ms);
				logger.debug("updateMovimientoStock(): retornando: " + resultado);
				return ResponseEntity.ok(resultado);
			} catch (MovimientosStockService.MovimientosStockNotFoundException e){
				logger.warn("deleteMovimientoStock(): No se encontró el MovimientosStock con: " + ms);
				return ResponseEntity.notFound().build();
			} catch (Exception e) {
				logger.error("deleteMovimientoStock(): Ocurrió un error inesperado: " + e.getMessage());
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		}
		logger.warn("updateMovimientoStock() Se recibió un MovimientosStock sin id: " + ms);
		return ResponseEntity.badRequest().build();
	}

}
