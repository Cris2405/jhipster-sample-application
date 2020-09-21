package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Pasajero.
 */
@Entity
@Table(name = "pasajero")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Pasajero implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idpas")
    private Integer idpas;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "identificacion")
    private String identificacion;

    @Column(name = "idt")
    private Integer idt;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "pasajero_idpas",
               joinColumns = @JoinColumn(name = "pasajero_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "idpas_id", referencedColumnName = "id"))
    private Set<Vuelo> idpas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "pasajeros", allowSetters = true)
    private Tiquete tiquete;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdpas() {
        return idpas;
    }

    public Pasajero idpas(Integer idpas) {
        this.idpas = idpas;
        return this;
    }

    public void setIdpas(Integer idpas) {
        this.idpas = idpas;
    }

    public String getNombre() {
        return nombre;
    }

    public Pasajero nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public Pasajero identificacion(String identificacion) {
        this.identificacion = identificacion;
        return this;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public Integer getIdt() {
        return idt;
    }

    public Pasajero idt(Integer idt) {
        this.idt = idt;
        return this;
    }

    public void setIdt(Integer idt) {
        this.idt = idt;
    }

    public Set<Vuelo> getIdpas() {
        return idpas;
    }

    public Pasajero idpas(Set<Vuelo> vuelos) {
        this.idpas = vuelos;
        return this;
    }

    public Pasajero addIdpas(Vuelo vuelo) {
        this.idpas.add(vuelo);
        vuelo.getIdvuelos().add(this);
        return this;
    }

    public Pasajero removeIdpas(Vuelo vuelo) {
        this.idpas.remove(vuelo);
        vuelo.getIdvuelos().remove(this);
        return this;
    }

    public void setIdpas(Set<Vuelo> vuelos) {
        this.idpas = vuelos;
    }

    public Tiquete getTiquete() {
        return tiquete;
    }

    public Pasajero tiquete(Tiquete tiquete) {
        this.tiquete = tiquete;
        return this;
    }

    public void setTiquete(Tiquete tiquete) {
        this.tiquete = tiquete;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pasajero)) {
            return false;
        }
        return id != null && id.equals(((Pasajero) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pasajero{" +
            "id=" + getId() +
            ", idpas=" + getIdpas() +
            ", nombre='" + getNombre() + "'" +
            ", identificacion='" + getIdentificacion() + "'" +
            ", idt=" + getIdt() +
            "}";
    }
}
