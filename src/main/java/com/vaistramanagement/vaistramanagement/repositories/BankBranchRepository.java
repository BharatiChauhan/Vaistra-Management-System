package com.vaistramanagement.vaistramanagement.repositories;

import com.vaistramanagement.vaistramanagement.entity.Bank;
import com.vaistramanagement.vaistramanagement.entity.BankBranch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BankBranchRepository extends JpaRepository<BankBranch,Integer>
{
    BankBranch findByBranchName(String name);


    boolean existsByBranchName(String name);


    @Transactional
    Page<BankBranch> findByBranchNameContainingIgnoreCase(String keyword, Pageable p);
}
