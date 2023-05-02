package com.example.pidevcocomarket.controllers;
import com.example.pidevcocomarket.utils.PagingHeaders;
import com.example.pidevcocomarket.utils.PagingResponse;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import com.example.pidevcocomarket.entities.Email;
import com.example.pidevcocomarket.entities.User;

import com.example.pidevcocomarket.services.MailBoxServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/mailbox")
@CrossOrigin(origins = "http://localhost:4200")

public class MailBoxRestController {
    @Autowired
    private MailBoxServiceImpl emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestParam String username, @RequestParam String password, @RequestParam String recipient, @RequestParam String subject, @RequestParam String body) {
        try {
            emailService.sendEmail(recipient, subject, body, username, password);
            return ResponseEntity.ok("E-mail envoyé avec succès !");
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'envoi de l'e-mail : " + e.getMessage());
        }
    }

    @GetMapping("/receive")
    public ResponseEntity<List<Email>> receiveEmails(@RequestParam String username, @RequestParam String password) {
        try {
            List<Email> emails = emailService.receiveEmails(username, password);
            return ResponseEntity.ok(emails);
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/show-all-emails")
    public List<Email> retriveAllEmails() {
        List<Email> listEmails = emailService.retrieveAllEmails();
        return listEmails;
    }

}
