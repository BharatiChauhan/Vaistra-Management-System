package com.vaistramanagement.vaistramanagement.repositories;

import com.vaistramanagement.vaistramanagement.entity.Designation;
import com.vaistramanagement.vaistramanagement.entity.EntityType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesignationRepository extends JpaRepository<Designation,Integer>
{
    boolean existsByName(String name);

    Page<Designation> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

//   EntityType findByName(String name);

 Designation findByName(String name);
}
