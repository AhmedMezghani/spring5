package com.example.pidevcocomarket.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor /*constructeur vide*/
@AllArgsConstructor /*constructeur avec tous les attributs*/
@ToString
@Builder
public class Boutique implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nom;
    private String address;
    private String description;
    private String logo;
    private Integer productsNumber;
    private String IBAN;
    private String BIC;
    @ManyToOne
    @ToString.Exclude
            @JsonIgnore
    User user;
    @OneToMany(cascade = CascadeType.ALL,mappedBy="boutique")
    @JsonIgnore
    @ToString.Exclude
    private Set<Stock> stocks;
    @OneToMany(cascade = CascadeType.ALL,mappedBy="boutique")
    @JsonIgnore
    @ToString.Exclude
    private Set<Produit> produits;
}
