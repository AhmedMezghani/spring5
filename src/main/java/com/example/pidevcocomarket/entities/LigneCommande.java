package com.example.pidevcocomarket.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor /*constructeur vide*/
@AllArgsConstructor /*constructeur avec tous les attributs*/
@ToString
@Builder
public class LigneCommande implements java.io.Serializable{
    @EmbeddedId
    private LigneCommandeKey ligneCommandeKeyy;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="commande_id",referencedColumnName = "id",insertable = false,updatable = false)
    @MapsId("commandeId")
    private Commande commande;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="produit_id",referencedColumnName = "id",insertable = false,updatable = false)
    @MapsId("produitId")
    private Produit produit;
    private int quantite;
   // private int prixtot;
}
