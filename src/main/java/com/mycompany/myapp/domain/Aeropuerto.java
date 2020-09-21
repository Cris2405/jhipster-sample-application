package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Aeropuerto.
 */
@Entity
@Table(name = "aeropuerto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Aeropuerto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idaero")
    private Integer idaero;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "pais")
    private String pais;

    @Column(name = "ciudad")
    private String ciudad;

    @Column(name = "direccion")
    private String direccion;

    @OneToMany(mappedBy = "aeropuerto")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Vuelo> vuelos = new HashSet<>();

    @OneToMany(mappedBy = "aeropuerto")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Avion> avions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdaero() {
        return idaero;
    }

    public Aeropuerto idaero(Integer idaero) {
        this.idaero = idaero;
        return this;
    }

    public void setIdaero(Integer idaero) {
        this.idaero = idaero;
    }

    public String getNombre() {
        return nombre;
    }

    public Aeropuerto nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public Aeropuerto pais(String pais) {
        this.pais = pais;
        return this;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public Aeropuerto ciudad(String ciudad) {
        this.ciudad = ciudad;
        return this;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public Aeropuerto direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Set<Vuelo> getVuelos() {
        return vuelos;
    }

    public Aeropuerto vuelos(Set<Vuelo> vuelos) {
        this.vuelos = vuelos;
        return this;
    }

    public Aeropuerto addVuelo(Vuelo vuelo) {
        this.vuelos.add(vuelo);
        vuelo.setAeropuerto(this);
        return this;
    }

    public Aeropuerto removeVuelo(Vuelo vuelo) {
        this.vuelos.remove(vuelo);
        vuelo.setAeropuerto(null);
        return this;
    }

    public void setVuelos(Set<Vuelo> vuelos) {
        this.vuelos = vuelos;
    }

    public Set<Avion> getAvions() {
        return avions;
    }

    public Aeropuerto avions(Set<Avion> avions) {
        this.avions = avions;
        return this;
    }

    public Aeropuerto addAvion(Avion avion) {
        this.avions.add(avion);
        avion.setAeropuerto(this);
        return this;
    }

    public Aeropuerto removeAvion(Avion avion) {
        this.avions.remove(avion);
        avion.setAeropuerto(null);
        return this;
    }

    public void setAvions(Set<Avion> avions) {
        this.avions = avions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Aeropuerto)) {
            return false;
        }
        return id != null && id.equals(((Aeropuerto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Aeropuerto{" +
            "id=" + getId() +
            ", idaero=" + getIdaero() +
            ", nombre='" + getNombre() + "'" +
            ", pais='" + getPais() + "'" +
            ", ciudad='" + getCiudad() + "'" +
            ", direccion='" + getDireccion() + "'" +
            "}";
    }
}
