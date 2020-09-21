package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Avion.
 */
@Entity
@Table(name = "avion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Avion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idavion")
    private Integer idavion;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "capacidad")
    private Integer capacidad;

    @Column(name = "idaero")
    private String idaero;

    @OneToMany(mappedBy = "avion")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Vuelo> vuelos = new HashSet<>();

    @OneToMany(mappedBy = "avion")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Operacion> operacions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "avions", allowSetters = true)
    private Aeropuerto aeropuerto;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdavion() {
        return idavion;
    }

    public Avion idavion(Integer idavion) {
        this.idavion = idavion;
        return this;
    }

    public void setIdavion(Integer idavion) {
        this.idavion = idavion;
    }

    public String getModelo() {
        return modelo;
    }

    public Avion modelo(String modelo) {
        this.modelo = modelo;
        return this;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public Avion capacidad(Integer capacidad) {
        this.capacidad = capacidad;
        return this;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public String getIdaero() {
        return idaero;
    }

    public Avion idaero(String idaero) {
        this.idaero = idaero;
        return this;
    }

    public void setIdaero(String idaero) {
        this.idaero = idaero;
    }

    public Set<Vuelo> getVuelos() {
        return vuelos;
    }

    public Avion vuelos(Set<Vuelo> vuelos) {
        this.vuelos = vuelos;
        return this;
    }

    public Avion addVuelo(Vuelo vuelo) {
        this.vuelos.add(vuelo);
        vuelo.setAvion(this);
        return this;
    }

    public Avion removeVuelo(Vuelo vuelo) {
        this.vuelos.remove(vuelo);
        vuelo.setAvion(null);
        return this;
    }

    public void setVuelos(Set<Vuelo> vuelos) {
        this.vuelos = vuelos;
    }

    public Set<Operacion> getOperacions() {
        return operacions;
    }

    public Avion operacions(Set<Operacion> operacions) {
        this.operacions = operacions;
        return this;
    }

    public Avion addOperacion(Operacion operacion) {
        this.operacions.add(operacion);
        operacion.setAvion(this);
        return this;
    }

    public Avion removeOperacion(Operacion operacion) {
        this.operacions.remove(operacion);
        operacion.setAvion(null);
        return this;
    }

    public void setOperacions(Set<Operacion> operacions) {
        this.operacions = operacions;
    }

    public Aeropuerto getAeropuerto() {
        return aeropuerto;
    }

    public Avion aeropuerto(Aeropuerto aeropuerto) {
        this.aeropuerto = aeropuerto;
        return this;
    }

    public void setAeropuerto(Aeropuerto aeropuerto) {
        this.aeropuerto = aeropuerto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Avion)) {
            return false;
        }
        return id != null && id.equals(((Avion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Avion{" +
            "id=" + getId() +
            ", idavion=" + getIdavion() +
            ", modelo='" + getModelo() + "'" +
            ", capacidad=" + getCapacidad() +
            ", idaero='" + getIdaero() + "'" +
            "}";
    }
}
