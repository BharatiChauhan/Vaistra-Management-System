package com.vaistramanagement.vaistramanagement.repositories;


import com.vaistramanagement.vaistramanagement.entity.District;
import com.vaistramanagement.vaistramanagement.entity.SubDistrict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SubDistrictRepository extends JpaRepository<SubDistrict,Integer>
{
//
//   List<SubDistrict> findByDistrictId(int districtId);
//    List<SubDistrict> findByDistrict_districtId(int districtId);


    boolean existsBySubDistrictName(String name);
    Page<SubDistrict> findBySubDistrictNameContainingIgnoreCase(String keyword, Pageable pageable);
    Page<SubDistrict> findAllByStatus(boolean b, Pageable pageable);

//    Page<SubDistrict> findAllByState_Status(boolean b, Pageable pageable);

//
//    Page<SubDistrict> findBySubDistrictNameContainingIgnoreCase(String keyword, Pageable p);

//    List<SubDistrict> findBySubdistrict_Country_CountryId(int countryId);



}
