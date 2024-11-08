package com.segunda.preentrega.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.segunda.preentrega.dto.DomicilioCreateDTO;
import com.segunda.preentrega.dto.DomicilioDTO;
import com.segunda.preentrega.mapper.DomicilioMapper;
import com.segunda.preentrega.model.Domicilio;
import com.segunda.preentrega.repository.DomicilioRepository;

@Service
public class DomicilioService {
    @Autowired
    private final DomicilioRepository domicilioRepository;
    @Autowired
    private final DomicilioMapper domicilioMapper;

    public DomicilioService(DomicilioMapper domicilioMapper, DomicilioRepository domicilioRepository) {
        this.domicilioMapper = domicilioMapper;
        this.domicilioRepository = domicilioRepository;
    }

    public List<DomicilioDTO> getAllDomicilios(boolean includeRelations) {
        
        return domicilioRepository.findAll().stream()
                .map(domicilio -> domicilioMapper.toDTODomicilio(domicilio, includeRelations))
                .collect(Collectors.toList());
    }

    public Optional<DomicilioDTO> getDomicilioById(Long id, boolean includeRelations) {
        return domicilioRepository.findById(id)
                .map(domicilio -> domicilioMapper.toDTODomicilio(domicilio, includeRelations));
    }

    public DomicilioDTO saveDomicilio(DomicilioCreateDTO domicilioCreateDTO) {
    Domicilio domicilio = domicilioMapper.toEntity(domicilioCreateDTO);
    Domicilio savedDomicilio = domicilioRepository.save(domicilio);
    return domicilioMapper.toDTODomicilio(savedDomicilio, false);
}

public DomicilioDTO updateDomicilio(Long id, DomicilioCreateDTO domicilioCreateDTO) {
    return domicilioRepository.findById(id)
        .map(domicilio -> {
            domicilio.setNombre(domicilioCreateDTO.getNombre());
            domicilio.setDireccion(domicilioCreateDTO.getDireccion());
            domicilio.setTelefono(domicilioCreateDTO.getTelefono());
            return domicilioMapper.toDTOPanaderia(domicilioRepository.save(domicilio), false);
        })
        .orElse(null);
}

    public void deleteDomicilio(Long id) {
        if (domicilioRepository.existsById(id)) {
            domicilioRepository.deleteById(id);
        } else {
            System.out.println(" -item no existe");
        }
    }
}
