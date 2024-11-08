package com.segunda.preentrega.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.segunda.preentrega.model.Cliente;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository <Cliente,Long> {

    List <Cliente> getClientsByName(Cliente cliente);

}
