package com.vaistramanagement.vaistramanagement.repositories;


import com.vaistramanagement.vaistramanagement.entity.Bank;
import com.vaistramanagement.vaistramanagement.entity.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank,Integer>
{
    Bank findByBankName(String name);


    boolean existsByBankName(String name);


    Page<Bank> findByBankNameContainingIgnoreCase(String keyword, Pageable p);
}
