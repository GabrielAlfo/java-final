package com.segunda.preentrega.dto;

import lombok.Builder;
import lombok.Data;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
public class DomicilioDTO {
    @Schema(description = "Id of the Cliente", example = "1")
    private Long id;
    @Schema(description = "Name ", example = "sds")
    private String nombre;
    @Schema(description = "Address ", example = "AV 123")
    private String direccion;
    @Schema(description = "Phone ", example = "99887766")
    private String telefono;
    private Set<ClienteDTO> clientes;
    private Set<ProductoDTO> productos;

    public DomicilioDTO() {
    }

    public DomicilioDTO(Long id, String nombre, String direccion, String telefono,Set<ClienteDTO> clientes, Set<ProductoDTO> productos) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.clientes = clientes;
        this.productos = productos;
    }

}