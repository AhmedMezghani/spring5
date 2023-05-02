package com.example.pidevcocomarket.repositories;

import com.example.pidevcocomarket.entities.Commande;

import com.example.pidevcocomarket.entities.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Integer> {

  /*  @Query("SELECT c FROM Commande c where c.region = :region and c.Affected=false")
    public List<Commande> getnotaffectedCommand(@Param("region") String region); */
  @Query("SELECT c FROM Commande c where c.region = :region and c.Affected=false")
  public List<Commande> getnotaffectedCommand(@Param("region") Region region);
}