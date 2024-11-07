package com.segunda.preentrega.mapper;

import org.springframework.stereotype.Component;

import com.segunda.preentrega.dto.ProductoDTO;
import com.segunda.preentrega.model.Producto;

@Component
public class ProductoMapper {
    public ProductoDTO toProductoDTO(Producto producto) {
        if (producto == null) {
            return null;
        }

        return ProductoDTO.builder()
                .id(producto.getId())
                .name(producto.getName())
                .key(producto.getKey())
                .userId(producto.getUser() != null ? producto.getUser().getId() : null)
                .build();
    }

    public Producto toProducto(ProductoDTO productoDTO) {
        if (productoDTO == null) {
            return null;
        }

        Producto producto = new Producto();
        producto.setId(productoDTO.getId());
        producto.setModel(productoDTO.getName());
        producto.setBrand(productoDTO.getKey());
        // Note: We're not setting the User here, as it should be handled separately
        return producto;
    }
}
