package com.segunda.preentrega.mapper;

import org.springframework.stereotype.Component;

import com.segunda.preentrega.dto.DomicilioCreateDTO;
import com.segunda.preentrega.dto.DomicilioDTO;
import com.segunda.preentrega.model.Domicilio;

import java.util.stream.Collectors;

@Component
public class DomicilioMapper {

    private final ClienteMapper clienteMapper;
    private final ProductoMapper productoMapper;

    public DomicilioMapper(ClienteMapper clienteMapper, ProductoMapper productoMapper) {
        this.clienteMapper = clienteMapper;
        this.productoMapper = productoMapper;
    }

    public DomicilioDTO toDTODomicilio(Domicilio domicilio, boolean includeRelations) {
        if (domicilio == null) {
            throw new IllegalArgumentException("La entidad no puede ser nula");
        }

        DomicilioDTO.DomicilioDTOBuilder builder = DomicilioDTO.builder()
                .id(domicilio.getId())
                .nombre(domicilio.getNombre())
                .direccion(domicilio.getDireccion())
                .telefono(domicilio.getTelefono());

        if (includeRelations) {
            builder.clientes(domicilio.getClientes().stream()
                    .map(clienteMapper::toDTOCliente)
                    .collect(Collectors.toSet()));
            builder.productos(domicilio.getProductos().stream()
                    .map(productoMapper::toDTOProducto)
                    .collect(Collectors.toSet()));
        }

        return builder.build();
    }

    public Domicilio toEntity(DomicilioCreateDTO domicilioCreateDTO) {
    if (domicilioCreateDTO == null) {
        throw new IllegalArgumentException("El DomiciioCreateDTO no puede ser nulo");
    }

    Domicilio domicilio = new Domicilio();
    domicilio.setId(domicilioCreateDTO.getId());
    domicilio.setNombre(domicilioCreateDTO.getNombre());
    domicilio.setDireccion(domicilioCreateDTO.getDireccion());
    domicilio.setTelefono(domicilioCreateDTO.getTelefono());

    return domicilio;
}
}
