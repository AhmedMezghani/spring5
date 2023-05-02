package com.example.pidevcocomarket.services;

import com.example.pidevcocomarket.entities.RoleType;
import com.example.pidevcocomarket.entities.Tender;
import com.example.pidevcocomarket.entities.User;
import com.example.pidevcocomarket.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.hibernate.jpa.AvailableSettings.PROVIDER;

@Service
public class ProviderMailService {

    private final JavaMailSender mailSender;
    private final UserRepository userRepository;
    @Autowired
    EmailServiceImpl emailService;

    @Autowired
    public ProviderMailService(JavaMailSender mailSender, UserRepository userRepository) {
        this.mailSender = mailSender;
        this.userRepository = userRepository;
    }

    public void sendTenderStartedEmail(Tender tender) {
        String subject = "New Tender Started";
        String body = "A new tender has started for " + tender.getName() + ". Please click on the link below to submit your offer:\n\n";
        String offerLink = "http://localhost:8075/swagger-ui/index.html#/offer-controller/addOffer";
        body += offerLink;
        List<User> providerUsers = userRepository.findByRole(RoleType.PROVIDER);
        String[] toEmails = providerUsers.stream()
                .map(User::getEmail)
                .toArray(String[]::new);
        for (String email:toEmails) {
            emailService.sendSimpleEmail(email, subject, body);
        }
    }
}
