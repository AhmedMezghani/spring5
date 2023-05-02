package com.example.pidevcocomarket.repositories;

import com.example.pidevcocomarket.entities.BanUser;
import com.example.pidevcocomarket.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BanUserRepository extends JpaRepository<BanUser, Integer> {
    List<BanUser> findByUser(User user);
}

