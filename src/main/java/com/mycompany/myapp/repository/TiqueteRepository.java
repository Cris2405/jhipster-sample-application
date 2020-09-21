package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Tiquete;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Tiquete entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TiqueteRepository extends JpaRepository<Tiquete, Long> {
}
