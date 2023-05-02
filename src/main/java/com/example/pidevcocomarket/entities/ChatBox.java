package com.example.pidevcocomarket.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor /*constructeur vide*/
@AllArgsConstructor /*constructeur avec tous les attributs*/
@ToString
@Builder
public class ChatBox implements java.io.Serializable{
    @Id
    private String id;   /*mohamed*/
    private Integer nombrePartcipants;
    private LocalDate date;
    private int idUser1;
    private int idUser2;
    @OneToMany(cascade = CascadeType.ALL,mappedBy="chatBox")
    @JsonIgnore
    @ToString.Exclude
    private List<Chat> chats =new ArrayList<Chat>(); /*mohamed*/
    @ManyToMany
    @JsonIgnore
    @ToString.Exclude
    private List<User> users;
}
