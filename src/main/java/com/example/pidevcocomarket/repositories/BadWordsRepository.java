package com.example.pidevcocomarket.repositories;

import com.example.pidevcocomarket.entities.BadWords;
import org.springframework.data.jpa.repository.JpaRepository;



public interface BadWordsRepository extends JpaRepository<BadWords,Integer> {

}
