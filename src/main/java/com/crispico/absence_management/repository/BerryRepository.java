package com.crispico.absence_management.repository;

import com.crispico.absence_management.domain.Berry;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Berry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BerryRepository extends JpaRepository<Berry,Long> {
    
}
