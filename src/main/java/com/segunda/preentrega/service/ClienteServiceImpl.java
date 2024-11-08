package com.segunda.preentrega.service;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.segunda.preentrega.dto.ClienteDTO;
import com.segunda.preentrega.mapper.ClienteMapper;
import com.segunda.preentrega.model.Cliente;
import com.segunda.preentrega.model.Domicilio;
import com.segunda.preentrega.repository.ClienteRepository;
import com.segunda.preentrega.repository.DomicilioRepository;
import jakarta.transaction.Transactional;

@Service
public class ClienteServiceImpl {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/users";

    @Autowired
    private final ClienteRepository clienteRepository;
    @Autowired
    private final ClienteMapper clienteMapper;
    @Autowired
    private RestTemplate restTemplate;

    public ClienteServiceImpl(ClienteRepository clienteRepository, DomicilioRepository domicilioRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    @Autowired
    private DomicilioRepository domicilioRepository;

    public List<ClienteDTO> getAllClients() {
        List<ClienteDTO> clientesDB = clienteRepository.findAll().stream()
                .map(clienteMapper::toDTOCliente)
                .collect(Collectors.toList());

        Cliente[] clientesAPI = restTemplate.getForObject(BASE_URL, Cliente[].class);

        if (clientesAPI != null) {
            for (Cliente cliente : clientesAPI) {
                clientesDB.add(clienteMapper.toDTOCliente(cliente));
            }
        }

        return clientesDB;
    }

    public ClienteDTO getClientById(Long id) {
        Optional<Cliente> optionalClient = clienteRepository.findById(id);

        if (optionalClient.isPresent()) {
            return clienteMapper.toDTOCliente(optionalClient.get());
        } else {
                ClienteDTO clienteDTO = restTemplate.getForObject(BASE_URL + "/{id}", ClienteDTO.class, id);
            if (clienteDTO != null) {
                return clienteDTO;
            } else {
                throw new RuntimeException("Cliente no encontrado ni en la base de datos ni en la API externa");
            }
        }
    }

    @Transactional
    public ClienteDTO saveClientFromApi(Long id) {
        ClienteDTO clienteDTO = restTemplate.getForObject(BASE_URL + "/{id}", ClienteDTO.class, id);

        if (clienteDTO != null) {
            Cliente cliente = clienteMapper.toEntity(clienteDTO);
            Cliente savedCliente = clienteRepository.save(cliente);
            return clienteMapper.toDTOCliente(savedCliente);
        } else {
            throw new RuntimeException("Cliente no encontrado en la API con ID: " + id);
        }
    }
    public ClienteDTO saveClienteDTO(ClienteDTO clienteDTO) {
        // Crear un nuevo cliente a partir del DTO
        Cliente cliente = clienteMapper.toEntity(clienteDTO);

        
        if (clienteDTO.getDomicilioIds() != null && !clienteDTO.getDomicilioIds().isEmpty()) {
            Set<Domicilio> domicilios = new HashSet<>();

            for (Long domicilioId : clienteDTO.getDomicilioIds()) {
               
                Optional<Domicilio> optionalDomicilio = domicilioRepository.findById(domicilioId);

               
                optionalDomicilio.ifPresent(domicilios::add);
            }

            
            cliente.setDomicilios(domicilios);
        }

        
        Cliente savedCliente = clienteRepository.save(cliente);

       
        return clienteMapper.toDTOCliente(savedCliente);
    }

    
    public void deleteClient(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);

          
            restTemplate.delete(BASE_URL + "/{id}", id);
        } else {
            throw new RuntimeException("Cliente no encontrado con ID: " + id);
        }
    }

    public ClienteDTO updaClienteDTO(Long id, ClienteDTO clienteDTO){
        return clienteRepository.findById(id)
        .map(cliente -> {
            cliente.setName(clienteDTO.getName());
            cliente.setEmail(clienteDTO.getEmail());
            cliente.setPhone(clienteDTO.getPhone());
            return clienteMapper.toDTOCliente(clienteRepository.save(cliente));
        })
        .orElse(null);
    }

}
