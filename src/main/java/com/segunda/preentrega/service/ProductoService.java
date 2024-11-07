package com.segunda.preentrega.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.segunda.preentrega.dto.ProductoDTO;
import com.segunda.preentrega.mapper.ProductoMapper;
import com.segunda.preentrega.model.Producto;
import com.segunda.preentrega.repository.ProductoRepository;

@Service
public class ProductoService {
    @Autowired
    private final ProductoMapper productoMapper;
    @Autowired
    private final ProductoRepository productoRepository;

    public ProductoService(ProductoMapper productoMapper, ProductoRepository productoRepository) {
        this.productoMapper = productoMapper;
        this.productoRepository = productoRepository;
    }

    public ProductoDTO createProducto(ProductoDTO productoDTO) {
        Producto producto = productoMapper.toProducto(productoDTO);
        Producto savedProducto = productoRepository.save(producto);
        return productoMapper.toProductoDTO(savedProducto);
    }

    public ProductoDTO getProductoById(Long id) {
        return productoRepository.findById(id)
                .map(productoMapper::toProductoDTO)
                .orElse(null);
    }

    public List<ProductoDTO> getAllProductos() {
        return productoRepository.findAll().stream()
                .map(productoMapper::toProductoDTO)
                .collect(Collectors.toList());
    }

    public ProductoDTO updateProducto(Long id, ProductoDTO productoDTO) {
        return productoRepository.findById(id)
                .map(producto -> {
                    producto.setName(productoDTO.getName());
                    producto.setKey(productoDTO.getKey());
                    return productoMapper.toProductoDTO(productoRepository.save(producto));
                })
                .orElse(null);
    }

    public void deleteProducto(Long id) {
        productoRepository.deleteById(id);
    }
}
