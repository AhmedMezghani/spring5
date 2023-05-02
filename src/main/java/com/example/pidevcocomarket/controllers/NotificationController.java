package com.example.pidevcocomarket.controllers;

import com.example.pidevcocomarket.entities.Notification;
import com.example.pidevcocomarket.services.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@AllArgsConstructor
@Slf4j
@Controller
@RequestMapping("/notification")

public class NotificationController {
    @Autowired
    private Service notificationService;

    //@Autowired
    //private SimpMessagingTemplate messagingTemplate;


/*
    @PostMapping("/sendNotifications")
    @SendTo("/topic/notifications")
    public ResponseEntity<Notification> sendNotification(@RequestBody Notification notification) {
        System.err.println(notification.getDescription());
        Notification notification1 = new Notification();
        notification1.setDate(LocalDateTime.now());
        notification1.setDescription(notification.getDescription());
        notification1.setEtat(notification.getEtat());
        notificationService.saveNotification(notification1);
        notificationService.addnotifications(notification1);
        messagingTemplate.convertAndSend("/topic/notification", notification);
        return ResponseEntity.ok(notification1);
    }*/

@PostMapping("/send-notification")
public ResponseEntity<Notification> sendNotification1(@RequestBody Notification notification) {
    // Ajouter la date actuelle à la notification
    notification.setDate(LocalDateTime.now());

    // Enregistrer la notification en base de données
    notificationService.saveNotification(notification);

    // Envoyer la notification à la destination "/topic/notification"
    //messagingTemplate.convertAndSend("/topic/notification", notification);

    return ResponseEntity.ok(notification);
}


    @GetMapping("/notifications")
    public ResponseEntity<List<Notification>> getNotifications() {
        List<Notification> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }




}
