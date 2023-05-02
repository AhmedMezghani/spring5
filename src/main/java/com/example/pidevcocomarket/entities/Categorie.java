package com.example.pidevcocomarket.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
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
public class Categorie implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String type;

    public Categorie(String type){
        this.type=type;
        subCategories=new ArrayList<>();
    }
    public List<Categorie> getSubCategories() {
        return subCategories;
    }
    public void addSubCategory(Categorie category) {
        subCategories.add(category);
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private List<Categorie>subCategories = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL,mappedBy="categorie")
    @JsonIgnore
    @ToString.Exclude
    private Set<Produit>produits;
}
