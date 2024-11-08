package com.segunda.preentrega.service;


import java.util.List;

import com.segunda.preentrega.dto.ClienteDTO;

public interface ClienteService {
    ClienteDTO getClientById(Long id);

    List<ClienteDTO> getAllClients();

    ClienteDTO saveClienteDTO(ClienteDTO clienteDTO);

    ClienteDTO saveClientFromApi(ClienteDTO clienteDTO);

    void deleteClient(Long id);
}
