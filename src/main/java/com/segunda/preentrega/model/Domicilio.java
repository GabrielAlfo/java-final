package com.segunda.preentrega.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@Table(name = "domicilio")
public class Domicilio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String direccion;
    private String descripcion;

    @ManyToMany(mappedBy = "domicilios")
    private Set<Cliente> clientes = new HashSet<>();

    public Domicilio() {
    }

    public Domicilio(Long id, String direccion, String descripcion, Set<Cliente> clientes) {
        this.id = id;
        this.direccion = direccion;
        this.descripcion = descripcion;
        this.clientes = clientes;
    }

    public void addUser(Cliente cliente) {
        this.clientes.add(cliente);
    }

}
