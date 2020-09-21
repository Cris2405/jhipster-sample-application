package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Pasajero;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Pasajero entity.
 */
@Repository
public interface PasajeroRepository extends JpaRepository<Pasajero, Long> {

    @Query(value = "select distinct pasajero from Pasajero pasajero left join fetch pasajero.idpas",
        countQuery = "select count(distinct pasajero) from Pasajero pasajero")
    Page<Pasajero> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct pasajero from Pasajero pasajero left join fetch pasajero.idpas")
    List<Pasajero> findAllWithEagerRelationships();

    @Query("select pasajero from Pasajero pasajero left join fetch pasajero.idpas where pasajero.id =:id")
    Optional<Pasajero> findOneWithEagerRelationships(@Param("id") Long id);
}
