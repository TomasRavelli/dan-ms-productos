package dan.tp2021.productos.rest;

import java.util.ArrayList;
import java.util.List;

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

import dan.tp2021.productos.domain.Material;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/material")
@Api(value = "MaterialRest", description = "Permite gestionar los materiales de la empresa")
public class MaterialRest {

	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Obtener un material por su id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Se obtuvo correctamente"),
			@ApiResponse(code = 401, message = "No autorizado"), @ApiResponse(code = 403, message = "Prohibido"),
			@ApiResponse(code = 404, message = "El ID no existe") })
	public ResponseEntity<Material> getMaterialById(@PathVariable Integer id) {

		// TODO pedir a un service que obtenega el material
		return ResponseEntity.badRequest().build();
	}

	@GetMapping()
	@ApiOperation(value = "Obtener todos los materiales, o por nombre o descripcion")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Materiales devueltos correctamente"),
			@ApiResponse(code = 401, message = "No autorizado"), @ApiResponse(code = 403, message = "Prohibido"),
			@ApiResponse(code = 404, message = "El ID no existe") })
	public ResponseEntity<Material> getListaMateriales(
			@RequestParam(required = false, name = "nombre", defaultValue = "") String nombre,
			@RequestParam(required = false, name = "descripcion", defaultValue = "") String descripcion) {

		List<Material> resultado = new ArrayList<>();
		if (nombre.isEmpty() && descripcion.isEmpty()) {
			// TODO return todos los materiales
		} else {
			if (!nombre.isEmpty()) {
				// TODO pedir a service los materiales segun el nombre
			}
			if (!descripcion.isEmpty()) {
				// TODO pedir a services los materiales segun la descripcion
			}
		}
		return ResponseEntity.badRequest().build();
	}

	@PostMapping()
	@ApiOperation(value = "Crea un nuevo material ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Material creado correctamente"),
			@ApiResponse(code = 401, message = "No autorizado"), @ApiResponse(code = 403, message = "Prohibido"),
			@ApiResponse(code = 404, message = "El ID no existe") })
	public ResponseEntity<Material> crearMaterial(@RequestBody Material material) {

		if (material != null) {
			// TODO pedir a un service que guarde un nuevo material
		}

		return ResponseEntity.badRequest().build();
	}

	@PutMapping(path = "/{id}")
	@ApiOperation(value = "Actualiza un material por su id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Actualizado correctamente"),
			@ApiResponse(code = 401, message = "No autorizado"), @ApiResponse(code = 403, message = "Prohibido"),
			@ApiResponse(code = 404, message = "El ID no existe") })
	public ResponseEntity<Material> updateMaterial(@RequestBody Material material) {

		if (material.getId() != null) {
			// TODO pedir a un service que guarde un nuevo material
		}

		return ResponseEntity.badRequest().build();
	}

	@DeleteMapping(path = "/{id}")
	@ApiOperation(value = "Borrar un material por su id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Actualizado correctamente"),
			@ApiResponse(code = 401, message = "No autorizado"), @ApiResponse(code = 403, message = "Prohibido"),
			@ApiResponse(code = 404, message = "El ID no existe") })
	public ResponseEntity<Material> deleteMaterial(@PathVariable Integer idMaterial) {

	
			// TODO pedir a un service que elimine el material
	

		return ResponseEntity.badRequest().build();
	}
}
