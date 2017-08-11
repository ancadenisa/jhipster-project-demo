package com.crispico.absence_management.repository;

import com.crispico.absence_management.domain.Cherry;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Cherry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CherryRepository extends JpaRepository<Cherry,Long> {
    
}
