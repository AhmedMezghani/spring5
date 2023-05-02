package com.example.pidevcocomarket.utils;

import com.example.pidevcocomarket.entities.User;
import com.example.pidevcocomarket.repositories.UserRepository;
import com.example.pidevcocomarket.services.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
public class NewsletterScheduler {
    @Autowired
    EmailServiceImpl emailService;
    @Autowired
    private UserRepository userRepository;

    //chaque lundi midi
    //@Scheduled(cron = "0 0 12 ? * MON")
    //@Scheduled(cron = "0 * * * * ?")
    public void sendNewsletter() {
        List<User> subscribers = userRepository.findAllByIsSubscribed(true);
        for (User subscriber : subscribers) {
            emailService.sendSimpleEmail(subscriber.getEmail(), "Newsletter", "Here's your newsletter! " +
                    "https://za.pinterest.com/aqeelasasman/fashion-newsletters/");
        }
    }

}
