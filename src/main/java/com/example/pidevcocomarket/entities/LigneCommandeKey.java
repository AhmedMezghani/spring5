package com.example.pidevcocomarket.entities;


import javax.persistence.Column;
import javax.persistence.Embeddable;



@Embeddable
public class LigneCommandeKey implements java.io.Serializable{
    @Column(name = "commande_id")
    private Integer commandeId;
    @Column(name = "produit_id")
    private Integer produitId;

    public LigneCommandeKey(Integer idProduit, Integer numeroCommande) {
        this.produitId = idProduit;
        this.commandeId = numeroCommande;
    }

    public LigneCommandeKey() {

    }
}
