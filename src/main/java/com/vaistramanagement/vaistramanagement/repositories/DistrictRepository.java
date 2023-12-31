package com.vaistramanagement.vaistramanagement.repositories;

import com.vaistramanagement.vaistramanagement.entity.District;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface DistrictRepository extends JpaRepository<District, Integer> {
    List<District> findByState_StateId(int stateId);
    List<District> findByState_Country_CountryId(int countryId);

    boolean existsByDistrictName(String name);
    Page<District> findAllByState_Status(Boolean b, Pageable p);

    Page<District> findByDistrictNameContainingIgnoreCase(String keyword, Pageable p);

    District findByDistrictName(String districtName);
}

