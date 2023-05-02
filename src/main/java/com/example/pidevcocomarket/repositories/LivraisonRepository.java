package com.example.pidevcocomarket.repositories;

import com.example.pidevcocomarket.entities.Livraison;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LivraisonRepository extends JpaRepository<Livraison, Integer> {
    @Query("SELECT l FROM Livraison l where l.region = :region and l.etat='En_attente' ")
    public List<Livraison> getnotaffectedLivraison(@Param("region") String region);
}