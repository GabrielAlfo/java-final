package com.segunda.preentrega.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.segunda.preentrega.model.Cliente;

import jakarta.transaction.Transactional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_domicilio (user_id, domicilio_id) VALUES (?1, ?2)", nativeQuery = true)
    void insertUserDomicilio(Long userId, Long domicilioId);

    @Query(value = "SELECT domicilio_id FROM user_domicilio WHERE user_id = ?1", nativeQuery = true)
    List<Long> findDomicilioIdsByClienteId(Long userId);

    Cliente findByName(String name);

}
