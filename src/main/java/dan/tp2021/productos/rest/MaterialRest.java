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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dan.tp2021.productos.domain.Material;
import dan.tp2021.productos.exeptions.material.MaterialNotFoundException;
import dan.tp2021.productos.exeptions.material.UnidadInvalidaException;
import dan.tp2021.productos.services.MaterialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/material")
@Api(value = "MaterialRest", description = "Permite gestionar los materiales de la empresa")
public class MaterialRest {

	private static final Logger logger = LoggerFactory.getLogger(MaterialRest.class);

	@Autowired
	MaterialService materialServiceImpl;

	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Obtener un material por su id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Se obtuvo correctamente"),
			@ApiResponse(code = 401, message = "No autorizado"), @ApiResponse(code = 403, message = "Prohibido"),
			@ApiResponse(code = 404, message = "El ID no existe") })
	public ResponseEntity<Material> getMaterialById(@PathVariable Integer id) {

		// TODO pedir a un service que obtenega el material
		try {
			Material result = materialServiceImpl.getMaterialById(id);
			return ResponseEntity.ok(result);
		} catch (MaterialNotFoundException e){
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@GetMapping()
	@ApiOperation(value = "Obtener todos los materiales, o por nombre o descripcion")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Materiales devueltos correctamente"),
			@ApiResponse(code = 401, message = "No autorizado"), @ApiResponse(code = 403, message = "Prohibido"),
			@ApiResponse(code = 404, message = "El ID no existe") })
	public ResponseEntity<List<Material>> getListaMateriales(
			@RequestParam(required = false, name = "nombre", defaultValue = "") String nombre,
			@RequestParam(required = false, name = "descripcion", defaultValue = "") String descripcion) {


		try {
			logger.debug("getListaMateriales(): Se recibieron parámetros nombre: " + nombre + " y descripción: " + descripcion);
			List<Material> lista = materialServiceImpl.getListaMaterialesByParams(nombre, descripcion);
			return ResponseEntity.ok(lista);
		} catch (Exception e) {
			logger.error("getListaMateriales(): Error al obtener la lista de materiales: " + e.getMessage(), e);
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping()
	@ApiOperation(value = "Crea un nuevo material ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Material creado correctamente"),
			@ApiResponse(code = 401, message = "No autorizado"), @ApiResponse(code = 403, message = "Prohibido"),
			@ApiResponse(code = 404, message = "El ID no existe") })
	public ResponseEntity<Material> crearMaterial(@RequestBody Material material) {

		if (material != null) {
			try {
				logger.debug( "El material no es null y se va aguardar: "+material);
				Material resultado = materialServiceImpl.saveMaterial(material);
				return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
			} catch (MaterialNotFoundException e){
				logger.warn("crearMaterial(): Se intentó guardar un material con un ID no existente: " + material, e);
				return ResponseEntity.notFound().build();
			} catch (UnidadInvalidaException e){
				logger.warn("crearMaterial(): La unidad recibida no se pudo validar: " + material, e);
				return ResponseEntity.unprocessableEntity().build();
			} catch (Exception e) {
				logger.error("crearMaterial(): Error desconocido: " + e.getMessage(), e);
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		}

		return ResponseEntity.badRequest().build();
	}

	@PutMapping()
	@ApiOperation(value = "Actualiza un material por su id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Actualizado correctamente"),
			@ApiResponse(code = 401, message = "No autorizado"), @ApiResponse(code = 403, message = "Prohibido"),
			@ApiResponse(code = 404, message = "El ID no existe") })
	public ResponseEntity<Material> updateMaterial(@RequestBody Material material) {

		if (material.getId() != null) {
			try {
				Material resultado = materialServiceImpl.saveMaterial(material);
				return ResponseEntity.ok(resultado);
			} catch (MaterialNotFoundException e){
				logger.warn("updateMaterial(): No se encontó el material: " + material, e);
				return ResponseEntity.notFound().build();
			} catch (UnidadInvalidaException e){
				logger.warn("updateMaterial(): La unidad recibida con el material no es válida. " + material, e);
				return ResponseEntity.unprocessableEntity().build();
			} catch (Exception e) {
				logger.error("updateMaterial(): Ocurrió un error al actualizar el material: " + e.getMessage(), e);
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		}

		return ResponseEntity.badRequest().build();
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Borrar un material por su id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Actualizado correctamente"),
			@ApiResponse(code = 401, message = "No autorizado"), @ApiResponse(code = 403, message = "Prohibido"),
			@ApiResponse(code = 404, message = "El ID no existe") })
	public ResponseEntity<Material> deleteMaterial(@PathVariable Integer id) {

		try {
			logger.debug("deleteMaterial(): La API recibió el id material: " + id);
			Material resultado =  materialServiceImpl.deleteMaterialById(id);
			return ResponseEntity.ok(resultado);
		} catch (MaterialNotFoundException e){
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
}
