package com.example.pidevcocomarket.interfaces;



import com.example.pidevcocomarket.entities.Livraison;
import com.example.pidevcocomarket.entities.User;

import java.util.List;

public interface ILivraison {
    public Livraison addLivraison (Livraison l);
    public Livraison updateLivraison (Livraison l);
    public Livraison findbyidLivraison (Integer idliv);
    void deleteLiv(Integer idLiv);
    public List<Livraison> retrieveAllLiv();

    public List<Livraison> notaffected(String region);

    public User affecterUsertolivaison(String Region, Integer idU);


    // public Livraison affecterUsertolivaison(String Region, Integer idU) {
    // List<Livraison> l=lr.getnotaffectedLivraison(Region);
    Livraison affecterUsertolivaison(Integer id, Integer idU);

}
