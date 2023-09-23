package com.vaistramanagement.vaistramanagement.repositories;

import com.vaistramanagement.vaistramanagement.entity.Designation;
import com.vaistramanagement.vaistramanagement.entity.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment,Integer>
{
//    boolean existsByEquipmentName(String equipmentName);

//    Page<Equipment> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

//   EntityType findByName(String equipmentName);

  Equipment findByEquipmentName(String equipmentName);

    boolean existsByEquipmentName(String equipmentName);

    Page<Equipment> findByEquipmentNameContainingIgnoreCase(String keyword, Pageable pageable);
}
