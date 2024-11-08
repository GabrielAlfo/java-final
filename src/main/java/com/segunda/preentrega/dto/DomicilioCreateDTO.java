package com.segunda.preentrega.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DomicilioCreateDTO {
    private Long id;
    @Schema(description = "Name of the Street", example = "lira")
    private String nombre;
    @Schema(description = "Name of the user", example = "Av 123")
    private String direccion;
    @Schema(description = "Name of the user", example = "99887766")
    private String telefono;

    public DomicilioCreateDTO() {
    }

    public DomicilioCreateDTO(Long id, String nombre, String direccion, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }
}
