package com.vaistramanagement.vaistramanagement.repositories;

import com.vaistramanagement.vaistramanagement.entity.SubDistrict;
import com.vaistramanagement.vaistramanagement.entity.Village;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VillageRepository extends JpaRepository<Village,Integer> {
    boolean existsByVillageName(String villageName);

    Page<Village> findByVillageNameContainingIgnoreCase(String keyword, Pageable pageable);
}
