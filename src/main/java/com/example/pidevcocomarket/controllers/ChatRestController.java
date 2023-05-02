package com.example.pidevcocomarket.controllers;

import com.example.pidevcocomarket.configuration.JwtService;
import com.example.pidevcocomarket.entities.Chat;
import com.example.pidevcocomarket.entities.ChatBox;
import com.example.pidevcocomarket.entities.User;
import com.example.pidevcocomarket.interfaces.IChatBoxService;
import com.example.pidevcocomarket.repositories.ChatBoxRepository;
import com.example.pidevcocomarket.repositories.ChatRepository;
import com.example.pidevcocomarket.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
public class ChatRestController {
    @Autowired
    ChatBoxRepository chatBoxrepo;
    @Autowired
    IChatBoxService iChatBoxService;
    @Autowired
    ChatRepository chatRepository;
    @Autowired
    UserRepository userRepository;
    private final JwtService jwtService ;

    public ChatRestController(JwtService jwtService,
                              UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }
    @Transactional
    @PostMapping("/chat.sendMessage/{message}/{userId}")
    public Chat sendMessage(@PathVariable("message") String message,@PathVariable("userId") int userId,@RequestBody ChatBox chatbox) {
        LocalDateTime now = LocalDateTime.now();
        Chat chat = new Chat();
        chat.setDate(now);
        chat.setChatBox(chatbox);
        chat.setContent(message);
        if (userId==chatbox.getIdUser1()){
            chat.setFromm(chatbox.getIdUser1());
            chat.setToo(chatbox.getIdUser2());
        }
        else {
            chat.setToo(chatbox.getIdUser1());
            chat.setFromm(chatbox.getIdUser2());
        }
        List<Chat> chats = chatbox.getChats();
        chats.add(chat);
        chatbox.setChats(chats);
        chatRepository.save(chat);
        return chat;
    }

    @GetMapping("/public/retrieveAllMsg")
    public ResponseEntity<List<Chat>> retrieveAllMsg() {
        ChatBox chatBox = chatBoxrepo.findById("public15976").get();



        if (chatBox.getChats().size()==0) {
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.ok(chatBox.getChats().stream().sorted(Comparator.comparing(Chat::getDate))
                    .collect(Collectors.toList()));
        }
    }
    ///app/channel/"+{chatBoxId}+"/Sender/"+{token}+"/to/"+{id2}
    @Transactional
    @MessageMapping("/channel/{chatBoxId}/Sender/{token}/to/{id2}")
    @SendTo("/topic/channel/{chatBoxId}")
    public Chat sendMessageUsers(@Payload Chat chat, @DestinationVariable("chatBoxId") String chatBoxId, @DestinationVariable("token") String token, @DestinationVariable("id2") String id2) {
        /*Claims claim =  jwtService.decodeToken(token);
        User user = userRepository.findByEmail(claim.get("sub",String.class)).get();
        LocalDateTime now = LocalDateTime.now();
        chat.setDate(now);
        ChatBox chatBox = chatBoxrepo.findById(chatBoxId).get();
        chat.setChatBox(chatBox);
        chat.setFromm(id2);
        chat.setType(Chat.MessageType.CHAT);
        List<Chat> chats = chatBox.getChats();
        chats.add(chat);
        chatBox.setChats(chats);
        chatRepository.save(chat);
        return chat;*/
        return null;
    }

    @MessageMapping("/chat.connect")
    @SendTo("/queue/reply")
    public Chat connectToChat(@Payload Chat chat, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        /*String currentUserToken = headerAccessor.getUser().getName(); // get current user's token
        Claims claim =  jwtService.decodeToken(chat.getSender());
        User currentUser = userRepository.findByEmail(claim.get("sub",String.class)).get();
        User otherUser = userRepository.findById(Integer.parseInt(chat.getFromm())).get(); // get other user's object from database
        String chatId = "user-" + currentUser.getEmail() + "-chat-" + otherUser.getEmail();
        chat.setSender(currentUser.getEmail());
        chat.setSender(otherUser.getEmail());*/
        /*Chat existingChat = iChatBoxService.getChatById(chatId);
        if (existingChat == null) {
            chat.setUsers(Arrays.asList(currentUser, otherUser));
            chatService.addChat(chat); // add chat to database if it doesn't exist
        }
        List<Chat> chats = iChatBoxService.getChatsByUserId(currentUser.getId());
        return new Chat(chats); // return all chats for the current user*/
        return null;
    }




    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public Chat addUser(@Payload Chat chat,
                        SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        //headerAccessor.getSessionAttributes().put("username", chat.getSender());
        return chat;
    }
    public ResponseEntity<String> addChat(@Valid @RequestBody Chat chat) {
        chatRepository.save(chat);
        return ResponseEntity.ok("Chat added successfully!");
    }
    @MessageMapping("/chat.createRoom")
    public void createOrGetRoom(Chat message) throws Exception {
        /*String sender = message.getSender();
        String to = message.getFromm();
        String roomId = sender + "-" + to;
        String channelName = "/user/" + sender + "/chat/" + to;*/
/*
        User user1 = userRepository.findById(Integer.parseInt(sender)).get();
        User user2 = userRepository.findById(Integer.parseInt(to)).get();
        List<User> users = new ArrayList<>();
        users.add(user1);users.add(user2);
        List<ChatBox> chatBoxes = chatBoxrepo.findAll();
        ChatBox boite= chatBoxes.stream()
                .filter(chatBox -> chatBox.getUsers().containsAll(users))
                .findFirst()
                .orElse(null);

        // Check if the room already exists
        if (boite!=null) {
            List<Chat> chats = boite.getChats();
            chats.add(message);boite.setChats(chats);
            chatBoxrepo.save(boite);
        } else {
            // If the room does not exist, create a new chat room and add the message
            ChatBox chatRoom = new ChatBox();
            List<Chat> chats = chatRoom.getChats();
            chats.add(message);chatRoom.setChats(chats);
            chatRoom.setUsers(users);
            List<ChatBox> chatBoxes1 = user1.getChatBoxes();
            chatBoxes1.add(chatRoom);
            user1.setChatBoxes(chatBoxes1);
            user2.setChatBoxes(chatBoxes1);

            chatBoxrepo.save(boite);
            chatRepository.save(message);
        }
        */
/*
        // Notify the sender and receiver that the message has been added to the room
        simpMessagingTemplate.convertAndSendToUser(sender, "/queue/chat.message", message);
        simpMessagingTemplate.convertAndSendToUser(to, "/queue/chat.message", message);*/
    }
}
