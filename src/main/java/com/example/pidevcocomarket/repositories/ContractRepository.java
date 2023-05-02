package com.example.pidevcocomarket.repositories;

import com.example.pidevcocomarket.entities.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {
}
