package com.example.pidevcocomarket.repositories;

import com.example.pidevcocomarket.entities.ChatBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface ChatBoxRepository extends JpaRepository<ChatBox, String/*mohamed*/> {
}