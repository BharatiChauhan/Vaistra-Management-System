package com.vaistramanagement.vaistramanagement.repositories;

import com.vaistramanagement.vaistramanagement.dto.CountryDto;
import com.vaistramanagement.vaistramanagement.entity.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    Country findByCountryName(String name);
    Page<Country> findAllByStatus(Boolean b, Pageable p);

    boolean existsByCountryName(String name);


//    Page<Country> findByCountryNameContainingIgnoreCase(String keyword, Pageable p);



    Page<Country> findByCountryNameContainingIgnoreCaseOrCountryIdOrStatus(Pageable pageable, String keyword, Integer keyword2, Boolean keyword3);
}