package com.example.pidevcocomarket.interfaces;

import com.example.pidevcocomarket.entities.Commande;
import com.example.pidevcocomarket.entities.Livraison;
import com.example.pidevcocomarket.entities.Region;


import java.util.List;

public interface ICommandeService {
    List<Commande> afficherAllCommande();

    Commande addOrUpdateCommande(Commande c);

    void removeCommande(Integer id);

    Commande retrieveCommande(Integer id);

    public Livraison affectercamandtolivaison(Region Region, Livraison l);


}
