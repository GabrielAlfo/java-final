package com.segunda.preentrega.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.segunda.preentrega.model.Domicilio;

@Repository
public interface DomicilioRepository extends JpaRepository <Domicilio, Long> {

    Optional<Long> getById(Domicilio domicilioId);
    
}
