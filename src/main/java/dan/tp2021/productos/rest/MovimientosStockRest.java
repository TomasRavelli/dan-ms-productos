package dan.tp2021.productos.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dan.tp2021.productos.domain.Material;
import dan.tp2021.productos.domain.MovimientosStock;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/movimientostock")
@Api(value = "MovimientosStockRest", description = "Permite gestionar los movimientos de stock de la empresa")
public class MovimientosStockRest {
	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Obtener movimiento stock por su id.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Se obtuvo movimiento correctamente"),
			@ApiResponse(code = 401, message = "No autorizado"), @ApiResponse(code = 403, message = "Prohibido"),
			@ApiResponse(code = 404, message = "El ID no existe") })
	public ResponseEntity<MovimientosStock> getMovimientoStockById(@PathVariable Integer id) {

		// TODO pedir a un service el movimiento

		return ResponseEntity.badRequest().build();
	}

	@GetMapping()
	@ApiOperation(value = "Obtener todos los movimientos de stock, o por id de material")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Se obtuvieron los movimientos correctamente"),
			@ApiResponse(code = 401, message = "No autorizado"), @ApiResponse(code = 403, message = "Prohibido"),
			@ApiResponse(code = 404, message = "El ID no existe") })
	public ResponseEntity<List<MovimientosStock>> getMovimientoStock(
			@RequestParam(name = "idMaterial", required = false, defaultValue = "0") Integer idMaterial) {

		if (idMaterial > 0) {
			// TODO pedir a un service los movientos de un determinado material
		} else {
			// TODO pedir a un service todos los moviemientos
		}

		return ResponseEntity.badRequest().build();
	}

	// No se si se permite esto
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Eliminar movimiento de stock por su id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Se elimino el movimiento correctamente"),
			@ApiResponse(code = 401, message = "No autorizado"), @ApiResponse(code = 403, message = "Prohibido"),
			@ApiResponse(code = 404, message = "El ID no existe") })
	public ResponseEntity<List<MovimientosStock>> deleteMovimientoStock(@PathVariable Integer idMaterial) {

		// TODO pedir a un service que elimine el movimiento de stock por su id

		return ResponseEntity.badRequest().build();
	}

	// No se si se permite esto.
	@PutMapping()
	@ApiOperation(value = "Actualizar un movimiento de stock")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Se actualizo el movimiento correctamente"),
			@ApiResponse(code = 401, message = "No autorizado"), @ApiResponse(code = 403, message = "Prohibido"),
			@ApiResponse(code = 404, message = "El ID no existe") })
	public ResponseEntity<List<MovimientosStock>> updateMovimientoStock(@RequestBody MovimientosStock ms) {

		if (ms.getId() != null) {
			// TODO pedir a un service que actualice el movimiento
		}

		return ResponseEntity.badRequest().build();
	}

}
