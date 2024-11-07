package com.segunda.preentrega.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.segunda.preentrega.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
