package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * not an ignored comment
 */
@ApiModel(description = "not an ignored comment")
@Entity
@Table(name = "vuelo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Vuelo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idvuelo")
    private Integer idvuelo;

    @Column(name = "origen")
    private String origen;

    @Column(name = "destino")
    private String destino;

    @Column(name = "idpas")
    private Integer idpas;

    @OneToMany(mappedBy = "vuelo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Operacion> operacions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "vuelos", allowSetters = true)
    private Avion avion;

    @ManyToOne
    @JsonIgnoreProperties(value = "vuelos", allowSetters = true)
    private Aeropuerto aeropuerto;

    @ManyToMany(mappedBy = "idpas")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Pasajero> idvuelos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdvuelo() {
        return idvuelo;
    }

    public Vuelo idvuelo(Integer idvuelo) {
        this.idvuelo = idvuelo;
        return this;
    }

    public void setIdvuelo(Integer idvuelo) {
        this.idvuelo = idvuelo;
    }

    public String getOrigen() {
        return origen;
    }

    public Vuelo origen(String origen) {
        this.origen = origen;
        return this;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public Vuelo destino(String destino) {
        this.destino = destino;
        return this;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Integer getIdpas() {
        return idpas;
    }

    public Vuelo idpas(Integer idpas) {
        this.idpas = idpas;
        return this;
    }

    public void setIdpas(Integer idpas) {
        this.idpas = idpas;
    }

    public Set<Operacion> getOperacions() {
        return operacions;
    }

    public Vuelo operacions(Set<Operacion> operacions) {
        this.operacions = operacions;
        return this;
    }

    public Vuelo addOperacion(Operacion operacion) {
        this.operacions.add(operacion);
        operacion.setVuelo(this);
        return this;
    }

    public Vuelo removeOperacion(Operacion operacion) {
        this.operacions.remove(operacion);
        operacion.setVuelo(null);
        return this;
    }

    public void setOperacions(Set<Operacion> operacions) {
        this.operacions = operacions;
    }

    public Avion getAvion() {
        return avion;
    }

    public Vuelo avion(Avion avion) {
        this.avion = avion;
        return this;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }

    public Aeropuerto getAeropuerto() {
        return aeropuerto;
    }

    public Vuelo aeropuerto(Aeropuerto aeropuerto) {
        this.aeropuerto = aeropuerto;
        return this;
    }

    public void setAeropuerto(Aeropuerto aeropuerto) {
        this.aeropuerto = aeropuerto;
    }

    public Set<Pasajero> getIdvuelos() {
        return idvuelos;
    }

    public Vuelo idvuelos(Set<Pasajero> pasajeros) {
        this.idvuelos = pasajeros;
        return this;
    }

    public Vuelo addIdvuelo(Pasajero pasajero) {
        this.idvuelos.add(pasajero);
        pasajero.getIdpas().add(this);
        return this;
    }

    public Vuelo removeIdvuelo(Pasajero pasajero) {
        this.idvuelos.remove(pasajero);
        pasajero.getIdpas().remove(this);
        return this;
    }

    public void setIdvuelos(Set<Pasajero> pasajeros) {
        this.idvuelos = pasajeros;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vuelo)) {
            return false;
        }
        return id != null && id.equals(((Vuelo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vuelo{" +
            "id=" + getId() +
            ", idvuelo=" + getIdvuelo() +
            ", origen='" + getOrigen() + "'" +
            ", destino='" + getDestino() + "'" +
            ", idpas=" + getIdpas() +
            "}";
    }
}
