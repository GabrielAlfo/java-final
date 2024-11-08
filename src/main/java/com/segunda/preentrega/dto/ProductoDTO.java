package com.segunda.preentrega.dto;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductoDTO {
    @Schema(description = "Id of the product", example = "0")
    private Long id;
    @Schema(description = "Name of the product", example = "sdasdasd")
    private String nombre;
    @Schema(description = "Price of the product", example = "3.9")
    private Double precio;
    @Schema(description = "Stock of the product", example = "10000")
    private int stock;
    @Schema(description = "Category of the product", example = "sadasdas")
    private String categoria;
    private Set<Long> panaderiaIds;

    ProductoDTO(){

    }

    public ProductoDTO(Long id, String nombre, Double precio, int stock, String categoria, Set<Long> panaderiaIds) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
        this.panaderiaIds = panaderiaIds;
    }

    public Set<Long> getDomicilioIds() {
        return DomicilioIds;
    }

    public void setDomicilioIds(Set<Long> domicilioIds) {
        // this.domicilioIds = domicilioIds;
    }
}
