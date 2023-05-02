package com.example.pidevcocomarket.authentication;
import com.example.pidevcocomarket.entities.StatusUser;

import com.example.pidevcocomarket.entities.User;
import com.example.pidevcocomarket.interfaces.IUserService;
import com.example.pidevcocomarket.repositories.UserRepository;
import com.example.pidevcocomarket.services.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;
import java.util.Random;


@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")

public class AuthenticationController {
    private final AuthenticationService service;
    @Autowired
    private ForgotPasswordService pwdservice;
    @Autowired
    private IUserService userService;
    @Autowired
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private EmailServiceImpl emailService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        emailService.sendSimpleEmail(request.getEmail(),
                "Welcome Mail",
                "Welcome To Cocomarket"+
                "\n" +
                "Bienvenue à COCOMARKET,\n" +
                "\n" +
                "Merci d’avoir rejoint Cocomarket.\n" +
                "\n" +
                "Nous aimerions vous confirmer que votre compte a été créé avec succès.\n "+
                "\n" +
                "Si vous rencontrez des difficultés pour vous connecter à votre compte, contactez-nous à noreply.pidev@gmail.com.\n" +
                "\n" +
                "Cordialement,\n" +
                "COCOMARKET");
        System.out.print("Account successfully created");
        return ResponseEntity.ok(service.register(request));
    }
                 /* ameni*/
/*
   @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
           @RequestBody AuthenticationRequest request) {
       return ResponseEntity.ok(service.authenticate(request));
   }
*/
                 /* mohamed version 1*/
   /* @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        AuthenticationResponse auth = service.authenticate(request);
        if(auth==null)
            return null;
        else
            return ResponseEntity.ok(service.authenticate(request));
    }*/

    /* mohamed version 2*/

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        User user = userRepository.findByEmail(request.getEmail()).get();
        if(user.getStatusUser()==StatusUser.BANNED){
            System.out.print("YOUR ACCOUNT IS BANNED");
            return ResponseEntity.badRequest().build();
        }
        if(user.getStatusUser()==null){
            System.out.print("YOUR ACCOUNT IS NOT VERIFIED");
            return ResponseEntity.badRequest().build();
        }



        AuthenticationResponse auth = service.authenticate(request);
        if(auth==null)
            return null;
        else
            return ResponseEntity.ok(auth);


    }


    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        try {
            String newPassword = pwdservice.generateNewPassword();
            pwdservice.sendPasswordResetEmail(email, newPassword);

            User user = userRepository.findByEmail(email).get();
            user.setPassword(passwordEncoder.encode(newPassword));
            userService.updateUser(user);

            return ResponseEntity.ok("Password reset email sent");
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    @PostMapping("/update-password")
    public ResponseEntity<?>  updatePassword(@RequestParam String email, @RequestParam String oldPassword, @RequestParam String newPassword) {
        User user = userRepository.findByEmail(email).get();
        boolean isMatch = passwordEncoder.matches(oldPassword, user.getPassword());
        if (isMatch) {
            String encodedNewPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedNewPassword);
            userRepository.save(user);
            return ResponseEntity.ok("Password updated successfully!");
        } else {
            return ResponseEntity.ok(" Please enter the right password");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return ResponseEntity.ok("User logged out successfully.");
    }

    @PostMapping("/send-code")
    public void sendCode(@RequestBody String email) {
        User user = userRepository.findByEmail(email).get();
        // générez le code ici
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        user.setCode(code);
        userRepository.save(user);
        emailService.sendSimpleEmail(email, "Cocomarket Verification Code", String.valueOf(code));
        System.out.println(code);
    }

   /* @PostMapping("/verify-code")
    public ResponseEntity<?> verifyCode(@RequestParam String email,@RequestParam int code) {
        User user = userRepository.findByEmail(email).get();
        // vérifiez le code ici
       if(code==user.getCode()){
           return ResponseEntity.ok("We are pleased to inform you that your account has been successfully verified");
       }
        return ResponseEntity.ok("Unfortunately, we cannot verify your account at this time, " +
                "as we have encountered issues with the information provided");
    }*/
   @PostMapping("/verify-code")
   //version adéquate Angular
   public void verifyCode(@RequestBody Map<String, Object> requestBody) {
       String email = (String) requestBody.get("email");
       int code = (int) requestBody.get("code");

       User user = userRepository.findByEmail(email).get();

       if (code == user.getCode()) {
           user.setStatusUser(StatusUser.ACTIVE);
           userRepository.save(user);
       }
   }


}
