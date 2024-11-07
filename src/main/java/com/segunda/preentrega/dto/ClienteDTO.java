package com.segunda.preentrega.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.segunda.preentrega.mapper.ClienteMapperBuilder;
import com.segunda.preentrega.model.Cliente;
import com.segunda.preentrega.repository.ClienteRepository;

import io.swagger.v3.oas.annotations.media.Schema;
@Data
@Builder
public class ClienteDTO {
    @Schema(description = "Unique identifier of the user", example = "1")
    private Long id;
    @Schema(description = "Name of the user", example = "John Doe")
    private String name;
    @Schema(description = "Email address of the user", example = "john.doe@example.com")
    private String email;
    @Schema(description = "Phone number of the user", example = "1234567890")
    private String phone;
    @Schema(description = "Website of the user", example = "www.johndoe.com")
    private String website;

    @Schema(description = "List of Products associated with the user")
    private Set<Long> domicilioIds;

    @Autowired
    private ClienteRepository clienteRepository;

    public ClienteDTO() {
    }

    public ClienteDTO(Long id, String name, String email, String phone, String website, Set<Long> domicilioIds,
            ClienteRepository ClienteRepository) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.website = website;
        this.domicilioIds = domicilioIds;
        this.clienteRepository = clienteRepository;
    }

    public Set<DomicilioDTO> getDomiciliosByClienteId(Long clienteId) {
        // Buscar el usuario por ID
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Convertir los domicilios del User a DomicilioDTOs
        return cliente.getDomicilios()
                .stream()
                .map(ClienteMapperBuilder::toDomicilioDTO)
                .collect(Collectors.toSet());
    }

    // Constructors, getters, and setters
}