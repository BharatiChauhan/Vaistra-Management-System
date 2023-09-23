package com.vaistramanagement.vaistramanagement.repositories;

import com.vaistramanagement.vaistramanagement.entity.Mineral;
import com.vaistramanagement.vaistramanagement.entity.SubDistrict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MineralRepository extends JpaRepository<Mineral,Integer> {

    boolean existsByMineralName(String mineralName);

    Page<Mineral> findByMineralNameContainingIgnoreCase(String keyword, Pageable pageable);

    Mineral findByMineralName(String mineralName);
}
