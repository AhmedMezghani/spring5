package com.example.pidevcocomarket.controllers;

import com.example.pidevcocomarket.services.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/mail")
public class MailRestController {
    @Autowired
    private EmailServiceImpl emailService;

   @PostMapping("/send-from-cocomarket")
    public void sendMail(@RequestParam("to") String to,
                         @RequestParam("subject") String subject, @RequestParam("body") String body) {

       emailService.sendSimpleEmail(to, subject, body);

   }
}
