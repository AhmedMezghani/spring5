package com.example.pidevcocomarket.repositories;

import com.example.pidevcocomarket.entities.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
   /* List<Promotion> findPromotionByAutoGenerateFalse() ;
    List<Promotion> findPromotionByAutoGenerateTrue();*/
}
