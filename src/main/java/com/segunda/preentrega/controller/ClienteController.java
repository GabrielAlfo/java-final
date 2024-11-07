package com.segunda.preentrega.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.segunda.preentrega.dto.DomicilioDTO;
import com.segunda.preentrega.dto.ProductoDTO;
import com.segunda.preentrega.dto.ClienteDTO;
import com.segunda.preentrega.model.Cliente;
import com.segunda.preentrega.service.ClienteServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
// import io.swagger.v3.oas.models.responses.ApiResponses;

import org.springframework.web.bind.annotation.GetMapping;
import com.segunda.preentrega.utils.ApiResponseMsg;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "API Test Swagger")
public class ClienteController {

    @Autowired
    private ClienteServiceImpl clienteService;

    // @GetMapping("/{id}")
    // public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
    // UserDTO user = userService.getUserById(id);
    // return ResponseEntity.ok(user);
    // }
    @PostMapping("/users/{userId}/motorcycles")
    public ResponseEntity<ClienteDTO> addProductoToCliente(@PathVariable Long clienteId,
            @RequestBody ProductoDTO productoDTO) {
        ClienteDTO updatedCliente = clienteService.addProductoToCliente(clienteId, productoDTO);
        return ResponseEntity.ok(updatedCliente);
    }

    @PostMapping("/{userId}/domicilios/{domicilioId}")
    public ResponseEntity<ClienteDTO> addDomicilioToCliente(@PathVariable Long clienteId, @PathVariable Long domicilioId) {
        ClienteDTO updatedCliente = clienteService.addDomicilioToCliente(clienteId, domicilioId);
        return ResponseEntity.ok(updatedCliente);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Retorna el cliente por su ID")
    @ApiResponse(responseCode = "200", description = "Successful!", content = @Content(schema = @Schema(implementation = ClienteDTO.class)))
    @ApiResponse(responseCode = "404", description = "Client not found!", content = @Content(schema = @Schema(implementation = ClienteDTO.class)))
    public ResponseEntity<?> encontrarClientePorId(@PathVariable Long id) {
        try {
            ClienteDTO cliente = clienteService.findById(id);
            return ResponseEntity.ok(cliente);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponseMsg("Clinets not found", e.getMessage()));
        }
    }

    @GetMapping(path = "/all")
    @Operation(summary = "Obtener todos los clientes", description = "Retorna todos los clientes")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = ClienteDTO.class))),
            @ApiResponse(responseCode = "404", description = "Clients not found", content = @Content(schema = @Schema(implementation = ApiResponseMsg.class), examples = {
                    @ExampleObject(name = "ClientNotFoundExample", value = "{\"message\": \"User not found\"}", description = "Clientes no encontrados")
            }))
    })
    public ResponseEntity<?> getAllClientes() {
        try {
            Cliente[] cliente = clienteService.findAll();
            return ResponseEntity.ok().body(new ApiResponseMsg("Clientes encontrados ", cliente));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponseMsg("Clientes no encontrados ", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> createCliente(@RequestBody ClienteDTO clienteDTO) {
        ClienteDTO createdCliente = clienteService.createCliente(clienteDTO);
        return new ResponseEntity<>(createdCliente, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCliente(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        clienteService.updateCliente(id, clienteDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borrar un cliente de la DB", description = "Borra un cliente por su ID")
    @ApiResponse(responseCode = "204", description = "Client deleted")
    @ApiResponse(responseCode = "404", description = "Client not found")
    public ResponseEntity<?> deleteCliente(
            @Parameter(description = "ID of the client to be deleted") @PathVariable Long id) {
        try {
            clienteService.deleteCliente(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponseMsg("Cliente Delete", e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Borrar un cliente ", description = "Borra un cliente por su ID")
    @ApiResponse(responseCode = "204", description = "Client deleted")
    @ApiResponse(responseCode = "404", description = "Client not found")
    public ResponseEntity<?> deleteById(
            @Parameter(description = "ID of the client to be deleted") @PathVariable Long id) {
        try {
            clienteService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponseMsg("Client Delete error: ", e.getMessage()));
        }
    }

    @PostMapping(value = "/users", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> postCliente(@RequestBody String cliente) {

        String clienteGuardado = clienteService.guardarCliente(cliente);


        return ResponseEntity.created(null).body(clienteGuardado);
    }

    @GetMapping("/{userId}/domicilios")
    public ResponseEntity<List<DomicilioDTO>> getDomiciliosByClienteId(@PathVariable Long clienteId) {

        List<DomicilioDTO> domicilios = clienteService.getDomiciliosByClienteId(clienteId);
        return ResponseEntity.ok(domicilios);
    }
}