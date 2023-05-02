package com.example.pidevcocomarket.repositories;

import com.example.pidevcocomarket.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock,Integer> {
}
