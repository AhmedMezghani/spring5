package com.example.pidevcocomarket.entities;


import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor /*constructeur vide*/
@AllArgsConstructor /*constructeur avec tous les attributs*/
@ToString
@Builder
public class Role implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
    //@ManyToMany(mappedBy="roles",cascade =CascadeType.ALL)
    //@JsonIgnore
    //@ToString.Exclude
    //private Set<User> users;
}
