package com.segunda.preentrega.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.segunda.preentrega.dto.DomicilioDTO;
import com.segunda.preentrega.dto.ProductoDTO;
import com.segunda.preentrega.dto.ClienteDTO;
import com.segunda.preentrega.mapper.ProductoMapper;
import com.segunda.preentrega.mapper.ClienteMapper;
import com.segunda.preentrega.mapper.ClienteMapperBuilder;
import com.segunda.preentrega.model.Domicilio;
import com.segunda.preentrega.model.Producto;
import com.segunda.preentrega.model.Cliente;
import com.segunda.preentrega.repository.DomicilioRepository;

import com.segunda.preentrega.repository.ClienteRepository;

import jakarta.transaction.Transactional;

@Service
public class ClienteServiceImpl implements ClienteService {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/users";

    @Autowired
    private final ClienteRepository clienteRepository;
    @Autowired
    private final ClienteMapper clienteMapper;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private final ProductoMapper productoMapper;
    @Autowired
    private DomicilioRepository domicilioRepository;
    @Autowired
    private ClienteMapperBuilder clienteMapperBuilder;

    public ClienteServiceImpl(ClienteRepository clienteRepository, ClienteMapper clienteMapper, RestTemplate restTemplate,
            ProductoMapper productoMapper,
            DomicilioRepository domicilioRepository, ClienteMapperBuilder clienteMapperBuilder) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
        this.restTemplate = restTemplate;

        this.productoMapper = productoMapper;
        this.domicilioRepository = domicilioRepository;
        this.clienteMapperBuilder = clienteMapperBuilder;
    }

    @Override
    public ClienteDTO getClienteById(Long id) {
        return clienteRepository.findById(id)
                .map(clienteMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    public List<DomicilioDTO> getDomiciliosByClienteId(Long clienteId) {
        // Paso 1: Obtener los ids de los domicilios del usuario
        List<Long> domicilioIds = clienteRepository.findDomicilioIdsByClienteId(clienteId);

        // Paso 2: Buscar todos los domicilios por sus ids
        List<Domicilio> domicilios = domicilioRepository.findAllById(domicilioIds);

        // Paso 3: Mapear los Domicilio a DomicilioDTO
        return domicilios.stream()
                .map(ClienteMapperBuilder::toDomicilioDTO) // Convertir cada Domicilio en DomicilioDTO
                .collect(Collectors.toList());
    }

    @Override
    public List<ClienteDTO> getAllClientes() {
        return clienteRepository.findAll().stream()
                .map(clienteMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ClienteDTO findById(Long id) {
        Optional<Cliente> existingClienteOptional = clienteRepository.findById(id);

        if (existingClienteOptional.isPresent()) {
            throw new RuntimeException("El cliente ya existe en la base de datos");
        }

        ClienteDTO clienteDTO = restTemplate.getForObject(BASE_URL + "/{id}", ClienteDTO.class, id);

        // Cliente clienteWithSameName = clienteRepository.findByName(clienteDTO.getName());
        // if (clienteWithSameName != null) {
        //     throw new RuntimeException("Ya existe un cliente con el mismo nombre en la base de datos");
        // }

        Cliente newCliente = clienteMapperBuilder.toEntity(clienteDTO);
        Cliente savedCliente = clienteRepository.save(newCliente);
        return clienteMapperBuilder.toDTO(savedCliente);
    }

    public Cliente[] findAll() {
        // for (User u : restTemplate.getForObject(BASE_URL, User[].class)) {
        // System.out.println(u.getName());
        // }
        return restTemplate.getForObject(BASE_URL, Cliente[].class);
    }
    // public List<User> findAll() {
    // return restTemplate.getForObject(BASE_URL, List.class);
    // }

    // public Optional<PanaderiaDTO> getPanaderiaById(Long id) {
    // return panaderiaRepository.findById(id).map(panaderiaMapper::toDTOPanaderia);
    // }

    @Override
    public ClienteDTO createCliente(ClienteDTO clienteDTO) {
        // User user = userMapper.toEntity(userDTO);
        Cliente cliente = clienteMapperBuilder.toEntity(clienteDTO);
        Cliente savedCliente = clienteRepository.save(cliente);
        return clienteMapper.toDTO(savedCliente);
    }

    @Override
    public void updateCliente(Long id, ClienteDTO clienteDTO) {
        Cliente cliente = clienteMapperBuilder.toEntity(clienteDTO);
        cliente.setId(id);
        clienteRepository.save(cliente);
    }

    @Override
    public void deleteCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    public void deleteById(Long id) {
        restTemplate.delete(BASE_URL + "/{id}", id);
    }

    public String guardarCliente(String cliente) {

        final String url = "https://jsonplaceholder.org/users";


        return restTemplate.postForObject(url, cliente, String.class);
    }

    @Transactional
    public ClienteDTO addProductoToCliente(Long clienteId, ProductoDTO productoDTO) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente not found"));

        // Producto producto = productoMapper.toProducto(productoDTO);
        // producto.setCliente(cliente);
        // cliente.getProducto().add(producto);

        Cliente savedCliente = clienteRepository.save(cliente);
        return clienteMapper.toDTO(savedCliente);
    }

    public ClienteDTO addDomicilioToCliente(Long clienteId, Long domicilioId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente not found"));

        Domicilio domicilio = domicilioRepository.findById(domicilioId)
                .orElseThrow(() -> new RuntimeException("Domicilio not found"));

        clienteRepository.insertUserDomicilio(cliente.getId(), domicilio.getId());


        return this.clienteMapperBuilder.toDTO(cliente);
    }

    public ClienteDTO getClienteDTO(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente not found"));
        return this.clienteMapperBuilder.toDTO(cliente);
    }



}
