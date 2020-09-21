package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Operacion.
 */
@Entity
@Table(name = "operacion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Operacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idop")
    private Integer idop;

    @Column(name = "despegue")
    private String despegue;

    @Column(name = "aterrizaje")
    private String aterrizaje;

    @Column(name = "fecha")
    private String fecha;

    @Column(name = "idavion")
    private Integer idavion;

    @ManyToOne
    @JsonIgnoreProperties(value = "operacions", allowSetters = true)
    private Avion avion;

    @ManyToOne
    @JsonIgnoreProperties(value = "operacions", allowSetters = true)
    private Vuelo vuelo;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdop() {
        return idop;
    }

    public Operacion idop(Integer idop) {
        this.idop = idop;
        return this;
    }

    public void setIdop(Integer idop) {
        this.idop = idop;
    }

    public String getDespegue() {
        return despegue;
    }

    public Operacion despegue(String despegue) {
        this.despegue = despegue;
        return this;
    }

    public void setDespegue(String despegue) {
        this.despegue = despegue;
    }

    public String getAterrizaje() {
        return aterrizaje;
    }

    public Operacion aterrizaje(String aterrizaje) {
        this.aterrizaje = aterrizaje;
        return this;
    }

    public void setAterrizaje(String aterrizaje) {
        this.aterrizaje = aterrizaje;
    }

    public String getFecha() {
        return fecha;
    }

    public Operacion fecha(String fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getIdavion() {
        return idavion;
    }

    public Operacion idavion(Integer idavion) {
        this.idavion = idavion;
        return this;
    }

    public void setIdavion(Integer idavion) {
        this.idavion = idavion;
    }

    public Avion getAvion() {
        return avion;
    }

    public Operacion avion(Avion avion) {
        this.avion = avion;
        return this;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }

    public Vuelo getVuelo() {
        return vuelo;
    }

    public Operacion vuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
        return this;
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Operacion)) {
            return false;
        }
        return id != null && id.equals(((Operacion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Operacion{" +
            "id=" + getId() +
            ", idop=" + getIdop() +
            ", despegue='" + getDespegue() + "'" +
            ", aterrizaje='" + getAterrizaje() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", idavion=" + getIdavion() +
            "}";
    }
}
