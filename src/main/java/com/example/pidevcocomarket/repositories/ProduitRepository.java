package com.example.pidevcocomarket.repositories;

import com.example.pidevcocomarket.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduitRepository extends JpaRepository<Produit,Integer> {

   // List<Produit> search(String query);
}
