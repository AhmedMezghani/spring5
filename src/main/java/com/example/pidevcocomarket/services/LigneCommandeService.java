package com.example.pidevcocomarket.services;

import com.example.pidevcocomarket.repositories.LigneCommandeRepository;
import com.example.pidevcocomarket.entities.LigneCommande;
import com.example.pidevcocomarket.entities.LigneCommandeKey;
import com.example.pidevcocomarket.interfaces.ILigneCommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class LigneCommandeService implements ILigneCommandeService {
   @Autowired
   private LigneCommandeRepository ligneCommandeRepository;


    public LigneCommande getLigneCommande(LigneCommandeKey id) {
        return ligneCommandeRepository.getOne(id);

    }

    public LigneCommande saveLigneCommandeK(LigneCommandeKey ligneCommandeKeyy) {
        LigneCommande lc = new LigneCommande();
        lc.setLigneCommandeKeyy(ligneCommandeKeyy);
        return ligneCommandeRepository.save(lc);
    }



    @Override
    public LigneCommande saveLigneCommande(LigneCommande ligneCommande) {
        return ligneCommandeRepository.save(ligneCommande);


    }


    public LigneCommande updateLigneCommande(LigneCommande ligneCommande) {
        return ligneCommandeRepository.save(ligneCommande);
    }


    public boolean deleteLigneCommande(LigneCommandeKey id) {
        LigneCommande lc = ligneCommandeRepository.getOne(id);
        if(lc==null) return false;
        ligneCommandeRepository.delete(lc);
        return true;

    }

/*
        public LigneCommande findByIdProduitAndNumeroCommande(Integer idProduit, Integer numeroCommande) {
            LigneCommandeKeyy id = new LigneCommandeKeyy(idProduit, numeroCommande);
            Optional<LigneCommande> optionalLigneCommande = ligneCommandeRepository.findById(id);
            if (optionalLigneCommande.isPresent()) {
                return optionalLigneCommande.get();
            } else {
                throw new RuntimeException("LigneCommande introuvable pour les clés primaires idProduit = " + idProduit + " et numeroCommande = " + numeroCommande);
            }
        }

    public LigneCommande getLigneCommandeByProduit(Integer numeroCommande, String name) {
        return ligneCommandeRepository.findLcByProduit(numeroCommande,name);

    }

    public LigneCommande getLigneCommandeByProduit(Integer numeroCommande, Integer id) {
        return ligneCommandeRepository.findLcByProduitt(numeroCommande,id);

    }
    */

    public Optional<LigneCommande> retrieveLigneCommandeById(Integer idProduit, Integer numeroCommande) {
        LigneCommandeKey key = new LigneCommandeKey(idProduit, numeroCommande);
        return ligneCommandeRepository.findById(key);
    }



}
