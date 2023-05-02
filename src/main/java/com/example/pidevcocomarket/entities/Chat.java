package com.example.pidevcocomarket.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor /*constructeur vide*/
@AllArgsConstructor /*constructeur avec tous les attributs*/
@ToString
@Builder
public class Chat implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String content;
    private String image ;
    /*mohamed*/
    private int fromm;
    private int too;
    private LocalDateTime date;
    /*end mohamed*/

    @ManyToOne
    @ToString.Exclude
    @JsonIgnore
    private  ChatBox chatBox;
}
