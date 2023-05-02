package com.example.pidevcocomarket.repositories;

import com.example.pidevcocomarket.entities.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface CatalogRepository extends JpaRepository<Catalog, Integer> {
}