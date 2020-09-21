package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Tiquete.
 */
@Entity
@Table(name = "tiquete")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Tiquete implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idt")
    private Integer idt;

    @Column(name = "asiento")
    private String asiento;

    @Column(name = "fecha")
    private String fecha;

    @OneToMany(mappedBy = "tiquete")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Pasajero> pasajeros = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdt() {
        return idt;
    }

    public Tiquete idt(Integer idt) {
        this.idt = idt;
        return this;
    }

    public void setIdt(Integer idt) {
        this.idt = idt;
    }

    public String getAsiento() {
        return asiento;
    }

    public Tiquete asiento(String asiento) {
        this.asiento = asiento;
        return this;
    }

    public void setAsiento(String asiento) {
        this.asiento = asiento;
    }

    public String getFecha() {
        return fecha;
    }

    public Tiquete fecha(String fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Set<Pasajero> getPasajeros() {
        return pasajeros;
    }

    public Tiquete pasajeros(Set<Pasajero> pasajeros) {
        this.pasajeros = pasajeros;
        return this;
    }

    public Tiquete addPasajero(Pasajero pasajero) {
        this.pasajeros.add(pasajero);
        pasajero.setTiquete(this);
        return this;
    }

    public Tiquete removePasajero(Pasajero pasajero) {
        this.pasajeros.remove(pasajero);
        pasajero.setTiquete(null);
        return this;
    }

    public void setPasajeros(Set<Pasajero> pasajeros) {
        this.pasajeros = pasajeros;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tiquete)) {
            return false;
        }
        return id != null && id.equals(((Tiquete) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tiquete{" +
            "id=" + getId() +
            ", idt=" + getIdt() +
            ", asiento='" + getAsiento() + "'" +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
