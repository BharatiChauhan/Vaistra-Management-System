package com.vaistramanagement.vaistramanagement.repositories;

import com.vaistramanagement.vaistramanagement.entity.EntityType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  EntityRepository extends JpaRepository<EntityType,Integer>
{
    boolean existsByEntityType(String entityName);

    Page<EntityType> findByEntityTypeContainingIgnoreCase(String keyword, Pageable pageable);

//   EntityType findByEntityName(String entityType);

    EntityType findByEntityType(String entityType);
}
