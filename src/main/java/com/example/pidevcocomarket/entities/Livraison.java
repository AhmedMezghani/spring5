package com.example.pidevcocomarket.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Livraison implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //  private LocalDate date;
    //private float frais;
    private LocalDate date_arrivé;

    private LocalDate date_sortie;
    // private float frais;
    private String region;
    private Boolean validation;

    @Enumerated(EnumType.STRING)
    private Etat_Livraison etat;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "Livraison_commande")
    private Set<Commande> Command_liv;


    @ManyToOne
    @JsonIgnore

    @ToString.Exclude
    User livreur;



}
