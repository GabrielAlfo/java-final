package com.segunda.preentrega.mapper;

import org.springframework.stereotype.Component;

import com.segunda.preentrega.dto.DomicilioDTO;
import com.segunda.preentrega.dto.ClienteDTO;
import com.segunda.preentrega.model.Domicilio;
import com.segunda.preentrega.model.Cliente;

@Component
public class ClienteMapperBuilder {

    public ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        return ClienteDTO.builder()
                .id(cliente.getId())
                .name(cliente.getName())
                .email(cliente.getEmail())
                .phone(cliente.getPhone())
                .website(cliente.getWebsite())
                .build();
    }

    public Cliente toEntity(ClienteDTO dto) {
        if (dto == null) {
            return null;
        }

        return Cliente.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .website(dto.getWebsite())
                .build();
    }

    public static DomicilioDTO toDomicilioDTO(Domicilio domicilio) {
        DomicilioDTO dto = new DomicilioDTO();
        dto.setId(domicilio.getId());
        dto.setDireccion(domicilio.getDireccion());
        dto.setDescripcion(domicilio.getDescripcion());
        return dto;
    }
}
