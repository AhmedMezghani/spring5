package com.example.pidevcocomarket.repositories;

import com.example.pidevcocomarket.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
}
