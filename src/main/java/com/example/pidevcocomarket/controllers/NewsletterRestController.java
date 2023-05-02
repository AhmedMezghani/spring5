package com.example.pidevcocomarket.controllers;

import com.example.pidevcocomarket.entities.User;
import com.example.pidevcocomarket.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/newsletter")
public class NewsletterRestController {
    @Autowired
    UserRepository userRepository;

    @PostMapping("/subscribe/{email}")
    public ResponseEntity<String> subscribe(@PathVariable String email) {
        User existingSubscriber = userRepository.findByEmail(email).get();
            existingSubscriber.setSubscribed(true);
            userRepository.save(existingSubscriber);
            return ResponseEntity.ok("Subscribed successfully");
    }

    @PutMapping("/unsubscribe/{email}")
    public ResponseEntity<String> unsubscribe(@PathVariable String email) {
        User existingSubscriber = userRepository.findByEmail(email).get();
        if (existingSubscriber == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
        } else {
            existingSubscriber.setSubscribed(false);
            userRepository.save(existingSubscriber);
            return ResponseEntity.ok("Unsubscribed successfully");
        }
    }
}
