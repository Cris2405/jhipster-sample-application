package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Avion;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Avion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvionRepository extends JpaRepository<Avion, Long> {
}
