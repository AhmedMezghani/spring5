package com.example.pidevcocomarket.controllers;

import com.example.pidevcocomarket.authentication.AuthenticationRequest;
import com.example.pidevcocomarket.configuration.JwtService;
import com.example.pidevcocomarket.entities.Chat;
import com.example.pidevcocomarket.entities.ChatBox;
import com.example.pidevcocomarket.entities.User;
import com.example.pidevcocomarket.interfaces.IUserService;
import com.example.pidevcocomarket.repositories.ChatBoxRepository;
import com.example.pidevcocomarket.repositories.ChatRepository;
import com.example.pidevcocomarket.repositories.UserRepository;
import com.example.pidevcocomarket.utils.PagingHeaders;
import com.example.pidevcocomarket.utils.PagingResponse;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/user")
@Slf4j
@SecurityRequirement(name = "javainuseapi")
@Validated
public class UserRestController {
    @Autowired
    IUserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatBoxRepository cbr;
    @Autowired
    private ChatRepository cr;
                       /*mohamed*/
    private final JwtService jwtService ;
    public UserRestController(JwtService jwtService,
                              UserRepository userRepository,
                              ChatBoxRepository chatBoxRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }
                       /*end mohamed*/

    ///////////////// CRUD/////////////////////////

    @GetMapping("/show-all-users")
    public List<User> retriveAllUsers() {
        List<User> listUsers = userService.retrieveAllUsers();
        return listUsers;
    }
    @GetMapping("/channelsByUserId")
    public List<User> channelsByUserId(@Valid @PathVariable("id") Integer id) {
        List<User> listUsers = userService.channelsByUserId(id);
        return listUsers;
    }
                    /*mohamed*/
@GetMapping("/getNomPrenomUser/{id}")
    public String getNomPrenomUser(@PathVariable("id")  int id) {
        User user = userRepository.findById(id).get();
        return user.getEmail();
    }
    @GetMapping("/getChats/{chatBoxId}")
    public List<Chat> getChats(@PathVariable("chatBoxId")  String chatBoxId) {
    List<Chat> chatss = cr.findAll();
        List<Chat> chats = chatss.stream().filter(c->c.getChatBox().getId().equals(chatBoxId)).collect(Collectors.toList());
        return chats;
    }

    @GetMapping("/usersByName/{entry}/{id}")
    public List<User> usersByName(@PathVariable("entry")  String entry,@PathVariable("id") int id) {
        List<User> users = userRepository.findAll().stream()
                .filter(u -> u.getFirstname().startsWith(entry) || u.getLastname().startsWith(entry))
                .collect(Collectors.toList());
        for (User u : users){
            ChatBox chatBox = cbr.findAll().stream()
                    .filter(cb -> (cb.getIdUser1() == u.getId() && cb.getIdUser2() == id) || (cb.getIdUser1() == id && cb.getIdUser2() == u.getId()))
                    .findFirst()
                    .orElse(null);
            if (chatBox==null){
                u.setChatBoxes(null);
            }
            else {
                u.getChatBoxes().clear();
                u.getChatBoxes().add(chatBox);
            }
        }
        return users;
    }
    @GetMapping("/show-all-users2/{token}")
    public List<User> retriveAllUsers2(@PathVariable("token")  String token) {
        Claims claim =  jwtService.decodeToken(token);
        User user = userRepository.findByEmail(claim.get("sub",String.class)).get();
        List<User> listUsers = userService.retrieveAllUsers().stream().filter(u -> u.getId() != user.getId()).collect(Collectors.toList());
        return listUsers;
    }
                    /*end mohamed*/
    @GetMapping("/show-user/{id}")
    public User retrieveUser(@Valid @PathVariable("id") Integer id) {
        return userService.retrieveUser(id);
    }


    @PostMapping("/add-user")
    public ResponseEntity<String> addUser(@Valid @RequestBody User u) {
        User user = userService.addUser(u);
        return ResponseEntity.ok("User added successfully!");
    }

    @DeleteMapping("/remove-user/{id}")
    public ResponseEntity<String> removeUser(@PathVariable("id") Integer id) {
        userService.removeUser(id);
        return ResponseEntity.ok("User deleted successfully!");
    }

    @PutMapping("/update-user")
    public ResponseEntity<String> updateUser(@Valid @RequestBody User u) {
        User user = userService.updateUser(u);
        return ResponseEntity.ok("User updated successfully!");
    }


    ///////////////////TRI FILTRE PAGINATION////////////
    @Transactional
    @GetMapping(value = "/recherche-avancee", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<User>> get(
            @And({
                    @Spec(path = "firstname", params = "firstname", spec = Like.class),
                    @Spec(path = "lastname", params = "lastname", spec = Like.class),
                    @Spec(path = "email", params = "email", spec = Like.class),
                    @Spec(path = "company", params = "company", spec = Like.class),
                    @Spec(path = "address", params = "address", spec = Like.class),
                    @Spec(path = "fidelity", params = "fidelity", spec = Like.class),
                    @Spec(path = "role", params = "role", spec = Like.class)
            }) Specification<User> spec, Sort sort, @RequestHeader HttpHeaders headers) {
        final PagingResponse response = userService.get3(spec, headers, sort);
        return new ResponseEntity<>(response.getElements(), returnHttpHeaders(response), HttpStatus.OK);

    }

    public HttpHeaders returnHttpHeaders(PagingResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(PagingHeaders.COUNT.getName(), String.valueOf(response.getCount()));
        headers.set(PagingHeaders.PAGE_SIZE.getName(), String.valueOf(response.getPageSize()));
        headers.set(PagingHeaders.PAGE_OFFSET.getName(), String.valueOf(response.getPageOffset()));
        headers.set(PagingHeaders.PAGE_NUMBER.getName(), String.valueOf(response.getPageNumber()));
        headers.set(PagingHeaders.PAGE_TOTAL.getName(), String.valueOf(response.getPageTotal()));
        return headers;
    }

    /////////////Image Upload/////////////
    @PostMapping(value = "/uploadLogo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadLogo(@RequestParam("file") MultipartFile file, AuthenticationRequest authentication) {
        /*User user = userRepository.findByEmail(authentication.getEmail()).get();

        try {
            user.setLogo(file.getBytes());
            userRepository.save(user);
            return ResponseEntity.ok("Logo uploaded successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload logo");
        }*/
        return null;
    }

}
