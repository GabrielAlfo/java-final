package com.segunda.preentrega.model;



import java.util.Set;
import jakarta.persistence.*;
import jakarta.persistence.ManyToMany;
import lombok.Builder;
import lombok.Data;


@Entity
@Data
@Builder
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Double precio;
    private int stock;
    private String categoria;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    name = "producto_domicilio",
    joinColumns = @JoinColumn(name = "producto_id"),
    inverseJoinColumns = @JoinColumn(name = "domicilio_id")
)
    private Set<Domicilio> domicilios;

    public Producto(){

    }

    public Producto(Long id, String nombre, Double precio, int stock, String categoria, Set<Domicilio> domicilios){
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
        this.domicilios = domicilios;
    }

    public Set<Domicilio> getDomicilios() {
        return domicilios; //
    }

    public void setDomicilios(Set<Domicilio> domicilios) {
        this.domicilios = domicilios;
    }

    

}
