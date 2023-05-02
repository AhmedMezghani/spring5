package com.example.pidevcocomarket.repositories;

import com.example.pidevcocomarket.entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie,Integer> {
}
