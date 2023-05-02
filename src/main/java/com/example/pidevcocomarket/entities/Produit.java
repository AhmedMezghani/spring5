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
public class Produit implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String image;
    private String description;
    private float price;
    private String color;
    private String imagePath;
    private String vendorMail;
    @Enumerated(EnumType.STRING)
    private ProductStatus status;
    private boolean promotion = false ;
    @ManyToOne
    @ToString.Exclude
    Categorie categorie;
    @ManyToOne
    @ToString.Exclude
    Stock stock;
    @ManyToOne
    @ToString.Exclude
    Boutique boutique;
    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    Catalog catalog;

    @JsonIgnore
    public Set<Stock> getStocks() {
        return null;
    }

}
