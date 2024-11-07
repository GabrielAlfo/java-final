package com.segunda.preentrega.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@Table(name = "users")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String website;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Producto> productos = new ArrayList<>();

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "cliente_domicilio", joinColumns = @JoinColumn(name = "cliente_id"), inverseJoinColumns = @JoinColumn(name = "domicilio_id"))
    private Set<Domicilio> domicilios = new HashSet<>();

    public Cliente() {
    }

    public Cliente(Long id, String name, String email, String phone, String website, List<Producto> motorcycles,
            Set<Domicilio> domicilios) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.website = website;
        this.productos = productos;
        this.domicilios = domicilios;
    }

    public void addProducto(Producto producto) {
        productos.add(producto);
        // producto.setCliente(this);
    }

    public void removeProducto(Producto producto) {
        productos.remove(producto);
        // producto.setCliente(cliente:null);
    }

    // Métodos de conveniencia para manejar la relación con Domicilio
    public void addDomicilio(Domicilio domicilio) {
        if (!this.domicilios.contains(domicilio)) {
            domicilios.add(domicilio);
            // Evitar agregar el usuario de vuelta al domicilio para prevenir recursión o
            // ciclos
            if (!domicilio.getClientes().contains(this)) {
                domicilio.getClientes().add(this);
            }
        }
    }

    public void removeDomicilio(Domicilio domicilio) {
        domicilios.remove(domicilio);
        domicilio.getClientes().remove(this);
    }

    // Constructors, getters, and setters
} 