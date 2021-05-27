package dan.tp2021.productos.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import dan.tp2021.productos.exeptions.provision.ProvisionNotFoundException;
import dan.tp2021.productos.services.ProvisionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("api/provision")
@Api(value = "ProvisionRest", description = "Permite gestionar las provisiones de la empresa")

public class ProvisionRest {

	private static final Logger logger = LoggerFactory.getLogger(ProvisionRest.class);

	@Autowired
	ProvisionService provisionServiceImpl;

	@PostMapping()
	@ApiOperation(value = "Crear una nueva provision")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Provision creada correctamente"),
			@ApiResponse(code = 401, message = "No autorizado"), @ApiResponse(code = 403, message = "Prohibido"),
			@ApiResponse(code = 404, message = "El ID no existe") })
	public ResponseEntity<Provision> crearProvision(@RequestBody Provision provision) {

		if (provision != null && provision.getDetalle() != null && provision.getDetalle().size() > 0) {
			try {
				Provision resultado =  provisionServiceImpl.saveProvision(provision);
				return ResponseEntity.ok(resultado);
			} catch (ProvisionNotFoundException e){
				logger.warn("crearProvision(): No se la provisión: " + provision, e);
				return ResponseEntity.notFound().build();
			} catch (Exception e) {
				logger.error("crearProvision(): Error al crear la provisión: " + provision + " Exepcion: " + e.getClass().getName() + ":: " +e.getMessage(), e);
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		}

		return ResponseEntity.badRequest().build();
	}

	@PutMapping()
	@ApiOperation(value = "Actualiza un material por su id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Provision Actualizada correctamente"),
			@ApiResponse(code = 401, message = "No autorizado"), @ApiResponse(code = 403, message = "Prohibido"),
			@ApiResponse(code = 404, message = "El ID no existe") })
	public ResponseEntity<Provision> updateProvision(@RequestBody Provision provision) {

		if (provision.getId() != null) {
			try {
				Provision resultado =  provisionServiceImpl.saveProvision(provision);
				return ResponseEntity.ok(resultado);
			} catch (ProvisionNotFoundException e){
				logger.warn("Provision no encontrada. Id: " +provision.getId() + ". Mensaje de error: " + e.getMessage(), e);
				return ResponseEntity.notFound().build();
			} catch (Exception e) {
				logger.error("Error desconocido. Mensaje de error: " + e.getMessage(), e);
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		}

		return ResponseEntity.badRequest().build();
	}

	@DeleteMapping(path = "/{id}")
	@ApiOperation(value = "Eliminar provision por su id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Provision eliminada correctamente"),
			@ApiResponse(code = 401, message = "No autorizado"), @ApiResponse(code = 403, message = "Prohibido"),
			@ApiResponse(code = 404, message = "El ID no existe") })
	public ResponseEntity<Provision> crearProvision(@PathVariable Integer id) {

		try {
			Provision resultado = provisionServiceImpl.deleteProvisionById(id);
			return ResponseEntity.ok(resultado);
		} catch (ProvisionNotFoundException e){
			logger.warn("crearProvision(): No se encontró la provisión a eliminar por id: " + id, e);
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			logger.error("crearProvision(): Error al eliminar la provisión: " + e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Obtener provision por su id.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Se obtuvo provision correctamente"),
			@ApiResponse(code = 401, message = "No autorizado"), @ApiResponse(code = 403, message = "Prohibido"),
			@ApiResponse(code = 404, message = "El ID no existe") })
	public ResponseEntity<Provision> getProvisionById(@PathVariable Integer id) {

		try {
			Provision resultado = provisionServiceImpl.getProvisionById(id);
			return ResponseEntity.ok(resultado);
		} catch (ProvisionNotFoundException e){
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@GetMapping()
	@ApiOperation(value = "Obtener todas las provisiones.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Se obtuvieron las provisiones correctamente"),
			@ApiResponse(code = 401, message = "No autorizado"), @ApiResponse(code = 403, message = "Prohibido"),
			@ApiResponse(code = 404, message = "El ID no existe") })
	public ResponseEntity<List<Provision>> getProvisiones() {

		try {
			List<Provision> resultado = provisionServiceImpl.getListaProvisiones();
			return ResponseEntity.ok(resultado);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
}
