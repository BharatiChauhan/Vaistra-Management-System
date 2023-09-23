package com.vaistramanagement.vaistramanagement.repositories;

import com.vaistramanagement.vaistramanagement.entity.Equipment;
import com.vaistramanagement.vaistramanagement.entity.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle,Integer>
{

   Vehicle findByVehicleName(String vehicleName);

    boolean existsByVehicleName(String vehicleName);

    Page<Vehicle> findByVehicleNameContainingIgnoreCase(String keyword, Pageable pageable);

}
