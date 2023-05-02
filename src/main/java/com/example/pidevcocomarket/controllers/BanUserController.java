package com.example.pidevcocomarket.controllers;

import com.example.pidevcocomarket.entities.BanUser;
import com.example.pidevcocomarket.entities.StatusUser;
import com.example.pidevcocomarket.entities.User;
import com.example.pidevcocomarket.interfaces.IBanUserService;
import com.example.pidevcocomarket.repositories.UserRepository;
import com.example.pidevcocomarket.services.EmailServiceImpl;
import com.example.pidevcocomarket.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/ban-user")
public class BanUserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailServiceImpl emailService;
    @Autowired
    private UserServiceImpl userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{email}/ban")
    public ResponseEntity<?> banUser(@PathVariable String email) {
        User user = userRepository.findByEmail(email).get();
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setStatusUser(StatusUser.BANNED);
        userRepository.save(user);
        emailService.sendSimpleEmail(email, "Account Suspension Notification", "Dear User,\n" +
                "\n" +
                "We regret to inform you that your account has been suspended for violating our terms of service. This decision was made after a thorough evaluation of your activities on our platform.\n" +
                "\n" +
                "Please note that the suspension of your account is either temporary or permanent, depending on the severity of the violation. If you believe this action is unwarranted, you may contact us at noreply.pidev@gmail.com. \n Sincerely,\n" +
                "\n" +
                "Cocomarket Team");
        return ResponseEntity.ok("Account Banned Succefully");
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{email}/unban")
    public ResponseEntity<?> unbanUser(@PathVariable String email) {
        User user = userRepository.findByEmail(email).get();
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setStatusUser(StatusUser.ACTIVE);
        userRepository.save(user);
        return ResponseEntity.ok("Account Unbanned Succefully");
    }

    @GetMapping("/show-all-users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getUtilisateursBannis() {
        return userRepository.findByStatusUser(StatusUser.BANNED);
    }

}
