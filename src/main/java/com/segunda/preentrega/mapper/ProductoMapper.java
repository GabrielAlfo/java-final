package com.segunda.preentrega.mapper;
import java.util.Set;
import org.springframework.stereotype.Component;
import com.segunda.preentrega.dto.ProductoDTO;
import com.segunda.preentrega.model.Domicilio;
import com.segunda.preentrega.model.Producto;
import java.util.stream.Collectors;

@Component
public class ProductoMapper {

    public ProductoDTO toDTOProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("La entidad no puede ser nula");
        }

        Set<Long> domicilioIds = producto.getDomicilios().stream()
                .map(Domicilio::getId)
                .collect(Collectors.toSet());

        return ProductoDTO.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .precio(producto.getPrecio())
                .stock(producto.getStock())
                .categoria(producto.getCategoria())
                .build();
    }

    public Producto toEntity(ProductoDTO productoDTO) {
        if (productoDTO == null) {
            throw new IllegalArgumentException("El productoDTO no puede ser nulo");
        }

        Producto producto = new Producto();
        producto.setId(productoDTO.getId());
        producto.setNombre(productoDTO.getNombre());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setStock(productoDTO.getStock());
        producto.setCategoria(productoDTO.getCategoria());
        return producto;
    }
}
