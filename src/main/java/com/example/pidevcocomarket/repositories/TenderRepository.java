package com.example.pidevcocomarket.repositories;

import com.example.pidevcocomarket.entities.Tender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenderRepository extends JpaRepository<Tender, Integer> {
}