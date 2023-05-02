package com.example.pidevcocomarket.services;

import com.example.pidevcocomarket.repositories.CommandeRepository;
import com.example.pidevcocomarket.repositories.LivraisonRepository;
import com.example.pidevcocomarket.entities.Commande;

import com.example.pidevcocomarket.entities.Livraison;
import com.example.pidevcocomarket.entities.Region;
import com.example.pidevcocomarket.interfaces.ICommandeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommandeService implements ICommandeService {
    private final CommandeRepository commandeRepository;
    private final LivraisonRepository lr;


    @Override
    public List<Commande> afficherAllCommande() {
        return commandeRepository.findAll();
    }

    @Override
    public Commande addOrUpdateCommande(Commande c) {
        return commandeRepository.save(c);
    }

    @Override
    public void removeCommande(Integer id) {
        commandeRepository.deleteById(id);

    }

    @Override
    public Commande retrieveCommande(Integer id) {

        return commandeRepository.findById(id).orElse(null);
    }

    @Override
    public Livraison affectercamandtolivaison(Region Region, Livraison l) {
        List<Commande> c=commandeRepository.getnotaffectedCommand(Region);
        LocalDate d;
        float x=0,y=0;
        for (Commande i:c)
        {

            i.setAffected(true);
            i.setLivraison_commande(l);
        }

        l.setRegion(String.valueOf(Region));

        d=LocalDate.now();
        l.setDate_sortie(d);
        lr.save(l);
        return l;
    }






}





