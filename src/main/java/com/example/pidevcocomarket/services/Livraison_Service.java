package com.example.pidevcocomarket.services;



import com.example.pidevcocomarket.entities.User;
import com.example.pidevcocomarket.interfaces.ILivraison;
import com.example.pidevcocomarket.repositories.LivraisonRepository;
import com.example.pidevcocomarket.entities.Livraison;
import com.example.pidevcocomarket.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.pidevcocomarket.entities.Etat_Livraison.Affecte_livreur;

@Service
public class Livraison_Service implements ILivraison {
@Autowired
LivraisonRepository lr;
@Autowired
    UserRepository userRepository;


    @Override
    public Livraison addLivraison(Livraison l) {
        return lr.save(l);   }

    @Override
    public Livraison updateLivraison(Livraison l) {
        return lr.save(l);
    }

    @Override
    public Livraison findbyidLivraison(Integer idliv) {
        return lr.findById(idliv).orElse(null);
    }

    @Override
    public void deleteLiv(Integer idLiv) {
        lr.deleteById(idLiv);
    }

    @Override
    public List<Livraison> retrieveAllLiv() {
        return lr.findAll();
    }

    @Override
    public List<Livraison> notaffected(String region) {
        return lr.getnotaffectedLivraison(region);
    }

    @Override
    public User affecterUsertolivaison(String Region, Integer idU) {
        List<Livraison> l=lr.getnotaffectedLivraison(Region);
        User user=userRepository.findById(idU).orElse(null);

        for (Livraison i:l)
        {
            i.setValidation(true);
            i.setEtat(Affecte_livreur);
            i.setLivreur(user);

        }

        return userRepository.save(user);
    }

    @Override

    public Livraison affecterUsertolivaison(Integer id, Integer idU) {
        Livraison l=lr.findById(id).orElse(null);
        User user=userRepository.findById(idU).orElse(null);
        //user.setLivraisons((Set<Livraison>) l);
        l.setLivreur(user);


        return lr.save(l);
    }

}
