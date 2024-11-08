package com.segunda.preentrega.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.segunda.preentrega.dto.DomicilioCreateDTO;
import com.segunda.preentrega.dto.DomicilioDTO;
import com.segunda.preentrega.service.DomicilioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.segunda.preentrega.utils.ApiResponseMsg;


@RestController
@RequestMapping("/api/domicilios")
public class DomicilioController {

    @Autowired
    private final DomicilioService domicilioService;

    public DomicilioController(DomicilioService domicilioService) {
        this.domicilioService = domicilioService;
    }

    @GetMapping("/all")
    @Operation(summary = "Obtener todas los domicilios", description = "Retorna todos los domicilios")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = DomicilioDTO.class))),
            @ApiResponse(responseCode = "404", description = "Adress not found", content = @Content(schema = @Schema(implementation = ApiResponse.class), examples = {
                    @ExampleObject(name = "DireccionNotFound", value = "{\"message\": \"Adress not found\"}", description = "Direccion no encontradas")
            }))
    })
    public ResponseEntity<List<DomicilioDTO>> getAllDomicilios(@RequestParam(value = "includeRelations", defaultValue = "false") boolean includeRelations) {
        return ResponseEntity.ok(domicilioService.getAllDomicilios(true));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un domicilio por su id", description = "Retorna el domicilio asociada al id y sus relaciones")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = DomicilioDTO.class))),
        @ApiResponse(responseCode = "404", description = "Adress not found", content = @Content(schema = @Schema(implementation = ApiResponse.class), examples = {
                @ExampleObject(name = "DireccionNotFound", value = "{\"message\": \"Adress not found\"}", description = "Direccion no encontrada")
        }))
    })
    public ResponseEntity<?> getPDomicilioById(@PathVariable("id") Long id){
        try {
            Optional<DomicilioDTO> domicilio = domicilioService.getDomicilioById(id, false);
            return ResponseEntity.ok(domicilio);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponseMsg("usuario no encontrado", e.getMessage()));
        }
    }

    @PostMapping("/createDomicilio")
    @Operation(summary = "Crear un domicilio", description = "Retorna el domicilio")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = DomicilioCreateDTO.class))),
        @ApiResponse(responseCode = "404", description = "Adress not created", content = @Content(schema = @Schema(implementation = ApiResponse.class), examples = {
                @ExampleObject(name = "DomicilioNotCreated", value = "{\"message\": \"Adress not created\"}", description = "Domicilio no se pudo crear")
        }))
    })
    public ResponseEntity<DomicilioDTO> createDomicilio(@RequestBody DomicilioCreateDTO domicilioCreateDTO) {
        DomicilioDTO createdDomicilio = domicilioService.saveDomicilio(domicilioCreateDTO);
        return ResponseEntity.ok(createdDomicilio);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Borra un domicilio por su id asociado", description = "Retorna mensaje domicilio eliminada")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = DomicilioDTO.class))),
        @ApiResponse(responseCode = "404", description = "Adress could not be deleted", content = @Content(schema = @Schema(implementation = ApiResponse.class), examples = {
                @ExampleObject(name = "DomicilioNotDeleted", value = "{\"message\": \"Adress not deleted\"}", description = "Domicilio no borrada")
        }))
    })
    public ResponseEntity<?> deleteClient(@PathVariable("id") Long id) {
        try {
            domicilioService.deleteDomicilio(id);
            return ResponseEntity.ok().body(new ApiResponseMsg("Domicilio eliminado", id));
            } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponseMsg("Error: No se pudo eliminar el domicilio", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifica los campos  por su id", description = "Retorna modificado.")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = DomicilioDTO.class))),
        @ApiResponse(responseCode = "404", description = "Adress not found", content = @Content(schema = @Schema(implementation = ApiResponse.class), examples = {
                @ExampleObject(name = "DomicilioNotFound", value = "{\"message\": \"Adress not found\"}", description = "Domicilio no encontrada ni modificada")
        }))
    })
    public ResponseEntity<DomicilioDTO> updateDomicilio(@PathVariable Long id, @RequestBody DomicilioCreateDTO domicilioCreateDTO) {
        DomicilioDTO updatedDomicilio = domicilioService.updateDomicilio(id, domicilioCreateDTO);
        return ResponseEntity.ok(updatedDomicilio);
    }
}