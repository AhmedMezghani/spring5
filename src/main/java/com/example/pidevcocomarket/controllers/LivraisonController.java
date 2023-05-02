package com.example.pidevcocomarket.controllers;


import com.example.pidevcocomarket.entities.User;
import com.example.pidevcocomarket.interfaces.ILivraison;
import com.example.pidevcocomarket.entities.Livraison;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.pidevcocomarket.entities.Etat_Livraison.Affecte_livreur;

@RestController
@RequestMapping("/livraison")
public class LivraisonController {
    @Autowired
    ILivraison il;
    @PostMapping("/add-Livraison")
    public Livraison addLivraison(@RequestBody Livraison l) {
        Livraison li = il.addLivraison(l);
        return li;
    }
    @PutMapping("/update-Livraison")
    public Livraison updatelivration(@RequestBody Livraison l) {
        Livraison li = il.updateLivraison(l);
        return li;
    }
    @DeleteMapping("/remove-livraison/{liv-id}")
    public void removeLiv(@PathVariable("liv-id") Integer livId) {

        il.deleteLiv(livId);
    }
    @GetMapping("/retrieve-all-Liv")
    public List<Livraison> getlivs() {
        List<Livraison> listLiv = il.retrieveAllLiv();
        return listLiv;
    }
    @GetMapping("/retrieve-Liv/{liv-id}")
    public Livraison getliv(@PathVariable("liv-id") Integer idliv) {
        Livraison Liv = il.findbyidLivraison(idliv);
        return Liv;
    }

    @GetMapping("/retrieve-Liv-notaffected/{region}")
    public  List<Livraison> getlivnotaffected(@PathVariable("region") String region) {
        List <Livraison> Liv = il.notaffected(region);
        return Liv;
    }

    @PutMapping("/assignLivreurToLivr/{idL}/{idU}")
    public Livraison affecterLivUser(
            @PathVariable("idL") Integer idL,
            @PathVariable("idU") Integer id) {
        il.affecterUsertolivaison(idL, id);
        Livraison l= il.findbyidLivraison(idL);
        l.setEtat(Affecte_livreur);
        l.setValidation(true);
        il.updateLivraison(l);

        return null;


    }
    @PutMapping("/add-assign-liv/{region}/{id}")
    @ResponseBody
    public User addLivreurToLiv(
            @PathVariable("region") String region,
            @PathVariable("id") Integer id){
        User user=il.affecterUsertolivaison(region,id);
        return user;

    }
}
