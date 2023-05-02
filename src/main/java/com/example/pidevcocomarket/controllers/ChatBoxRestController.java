package com.example.pidevcocomarket.controllers;

import com.example.pidevcocomarket.configuration.JwtService;
import com.example.pidevcocomarket.entities.ChatBox;
import com.example.pidevcocomarket.entities.User;
import com.example.pidevcocomarket.interfaces.IChatBoxService;
import com.example.pidevcocomarket.repositories.ChatBoxRepository;
import com.example.pidevcocomarket.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@CrossOrigin(origins = {"*"})
@RestController
public class ChatBoxRestController {
    @Autowired
    IChatBoxService IChatBoxservice;

    private final JwtService jwtService ;
    private final UserRepository userRepository;
    private final ChatBoxRepository chatBoxRepository;

    public ChatBoxRestController(JwtService jwtService,
                                 UserRepository userRepository,
                                 ChatBoxRepository chatBoxRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.chatBoxRepository = chatBoxRepository;
    }


    @PostMapping("/addChatBox")
    public ResponseEntity<String> addChatBox(@Valid @RequestBody ChatBox chatBox) {
        ChatBox c = IChatBoxservice.addChatBox(chatBox);
        return ResponseEntity.ok("Chat Box added successfully!");
    }
    @GetMapping("/retrieveBoite/user/{token}/chat/{id2}")
    public ResponseEntity<ChatBox> retrieveBoite(@Valid @PathVariable("token") String token, @Valid @PathVariable("id2") Integer id2) {
        Claims claim =  jwtService.decodeToken(token);
        User user = userRepository.findByEmail(claim.get("sub",String.class)).get();

        ChatBox chatBox =  IChatBoxservice.retrieveBoite(user.getId(), id2);
        if (chatBox != null) {
            return ResponseEntity.ok(chatBox);
        } else {
            ChatBox chatBoxN = new ChatBox();
            chatBoxN.setId("channel-"+user.getId()+"-"+id2);
            chatBoxN.setDate(LocalDate.now());
            chatBoxN.setNombrePartcipants(2);
            chatBoxRepository.save(chatBoxN);
            return ResponseEntity.ok(chatBoxN);
        }
    }





}
