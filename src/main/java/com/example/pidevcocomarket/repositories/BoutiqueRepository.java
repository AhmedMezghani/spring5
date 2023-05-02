package com.example.pidevcocomarket.repositories;

import com.example.pidevcocomarket.entities.Boutique;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoutiqueRepository extends JpaRepository<Boutique,Integer> {
    public List<Boutique> findByAddressContaining(String address);
    public List<Boutique> findByOrderByProductsNumberDesc() ;
}
