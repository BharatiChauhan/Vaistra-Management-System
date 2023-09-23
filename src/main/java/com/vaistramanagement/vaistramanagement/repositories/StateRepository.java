package com.vaistramanagement.vaistramanagement.repositories;

import com.vaistramanagement.vaistramanagement.entity.Country;
import com.vaistramanagement.vaistramanagement.entity.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {
    Page<State> findByCountry(Country country, Pageable p);
    State findByStateName(String name);
    boolean existsByStateName(String name);
    Page<State> findAllByCountry_Status(boolean b, Pageable p);
    Page<State> findByStateNameContainingIgnoreCase(String keyword, Pageable p);

}

