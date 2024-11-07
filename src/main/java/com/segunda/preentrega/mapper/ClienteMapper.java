package com.segunda.preentrega.mapper;
import org.springframework.stereotype.Component;

import com.segunda.preentrega.dto.ClienteDTO;
import com.segunda.preentrega.model.Cliente;

@Component
public class ClienteMapper {

    public ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setName(cliente.getName());
        return dto;
    }

    public Cliente toEntity(ClienteDTO dto) {
        if (dto == null) {
            return null;
        }

        Cliente cliente = new Cliente();
        cliente.setId(dto.getId());
        cliente.setName(dto.getName());
        cliente.setEmail(dto.getEmail());
        cliente.setPhone(dto.getPhone());
        cliente.setWebsite(dto.getWebsite());
        return cliente;
    }
}
