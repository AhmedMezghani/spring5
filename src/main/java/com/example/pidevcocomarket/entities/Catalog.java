package com.example.pidevcocomarket.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor /*constructeur vide*/
@AllArgsConstructor /*constructeur avec tous les attributs*/
@ToString
@Builder
public class Catalog implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate dateCreation;
    private String name ;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "catalog")
    @ToString.Exclude
    private Set<Produit> produits;
}
