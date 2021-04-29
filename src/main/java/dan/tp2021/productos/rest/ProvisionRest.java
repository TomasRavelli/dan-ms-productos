package dan.tp2021.productos.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import dan.tp2021.productos.domain.Provision;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("api/provision")
@Api(value = "ProvisionRest", description = "Permite gestionar las provisiones de la empresa")

public class ProvisionRest {

	@PostMapping()
	@ApiOperation(value = "Crear una nueva provision")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Provision creada correctamente"),
			@ApiResponse(code = 401, message = "No autorizado"), @ApiResponse(code = 403, message = "Prohibido"),
			@ApiResponse(code = 404, message = "El ID no existe") })
	public ResponseEntity<Provision> crearProvision(@RequestBody Provision provision) {

		if (provision != null && provision.getDetalle() != null && provision.getDetalle().size() > 0) {
			// TODO pedir a un service que guarde la provision
		}

		return ResponseEntity.badRequest().build();
	}

	@PutMapping(path = "/{id}")
	@ApiOperation(value = "Actualiza un material por su id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Provision Actualizada correctamente"),
			@ApiResponse(code = 401, message = "No autorizado"), @ApiResponse(code = 403, message = "Prohibido"),
			@ApiResponse(code = 404, message = "El ID no existe") })
	public ResponseEntity<Provision> updateProvision(@RequestBody Provision provision) {

		if (provision.getId() != null) {
			// TODO pedir a un service que guarde la nueva provision
		}

		return ResponseEntity.badRequest().build();
	}

	@DeleteMapping(path = "/{id}")
	@ApiOperation(value = "Eliminar provision por su id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Provision eliminada correctamente"),
			@ApiResponse(code = 401, message = "No autorizado"), @ApiResponse(code = 403, message = "Prohibido"),
			@ApiResponse(code = 404, message = "El ID no existe") })
	public ResponseEntity<Provision> deleteProvision(@PathVariable Integer id) {

		// TODO pedir a un service que elimine la provision

		return ResponseEntity.badRequest().build();
	}

	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Obtener provision por su id.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Se obtuvo provision correctamente"),
			@ApiResponse(code = 401, message = "No autorizado"), @ApiResponse(code = 403, message = "Prohibido"),
			@ApiResponse(code = 404, message = "El ID no existe") })
	public ResponseEntity<Provision> getProvisionById(@PathVariable Integer id) {

		// TODO pedir a un service la provision

		return ResponseEntity.badRequest().build();
	}

	@GetMapping()
	@ApiOperation(value = "Obtener todas las provisiones.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Se obtuvieron las provisiones correctamente"),
			@ApiResponse(code = 401, message = "No autorizado"), @ApiResponse(code = 403, message = "Prohibido"),
			@ApiResponse(code = 404, message = "El ID no existe") })
	public ResponseEntity<List<Provision>> getProvisiones() {

		// TODO pedir a un service todas las provisioens

		return ResponseEntity.badRequest().build();
	}
}
