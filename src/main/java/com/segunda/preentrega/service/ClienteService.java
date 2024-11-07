package com.segunda.preentrega.service;


import java.util.List;

import com.segunda.preentrega.dto.ProductoDTO;
import com.segunda.preentrega.dto.ClienteDTO;

public interface ClienteService {
    ClienteDTO getClienteById(Long id);

    List<ClienteDTO> getAllClientes();

    ClienteDTO createCliente(ClienteDTO clienteDTO);

    void updateCliente(Long id, ClienteDTO clienteDTO);

    void deleteCliente(Long id);

    ClienteDTO addProductoToCliente(Long userId, ProductoDTO productoDTO);

    ClienteDTO addDomicilioToCliente(Long userId, Long domicilioId);
}
