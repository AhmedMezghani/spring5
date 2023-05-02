package com.example.pidevcocomarket.interfaces;

import com.example.pidevcocomarket.entities.LigneCommande;
import com.example.pidevcocomarket.entities.LigneCommandeKey;


public interface ILigneCommandeService {
    public LigneCommande getLigneCommande(LigneCommandeKey id );
   // public LigneCommande getLigneCommandeByProduit( Integer numeroCommande, String name );

    //public LigneCommande getLigneCommandeByProduit( Integer numeroCommande, Integer id );
    public LigneCommande saveLigneCommande(LigneCommande ligneCommande);
    public LigneCommande updateLigneCommande(LigneCommande ligneCommande);
    public LigneCommande saveLigneCommandeK(LigneCommandeKey ligneCommandeKeyy);
    public boolean deleteLigneCommande(LigneCommandeKey id);


   // LigneCommande findByIdProduitAndNumeroCommande(Integer idProduit, Integer numeroCommande);
}
