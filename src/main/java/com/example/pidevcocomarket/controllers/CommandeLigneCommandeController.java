package com.example.pidevcocomarket.controllers;

import com.example.pidevcocomarket.entities.*;
import com.example.pidevcocomarket.interfaces.ICommandeService;
import com.example.pidevcocomarket.interfaces.ILigneCommandeService;
import com.example.pidevcocomarket.interfaces.IProduitService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/commande-lignecommande")
public class CommandeLigneCommandeController {

    private final ICommandeService iCommandeService;

    private final IProduitService metierProduit;
    private final ILigneCommandeService iLigneCommande;


    @Autowired
    private HttpSession session;




    //////////////////////////COMMANDE/////////////

    @PostMapping("/add-order")
    Commande addCommande(@RequestBody Commande c) {
        c.setAmount(7);

        return iCommandeService.addOrUpdateCommande(c);
    }

    @PostMapping("/update-order")
    Commande updateComm(@RequestBody Commande c) {
        return iCommandeService.addOrUpdateCommande(c);
    }

    @GetMapping("/get-order/{id}")
    Commande getorder(@PathVariable("id") Integer id) {
        return iCommandeService.retrieveCommande(id);
    }

    @GetMapping("/all-orders")
    List<Commande> getallOrders() {
        return iCommandeService.afficherAllCommande();
    }

    @DeleteMapping("/delete-order/{id}")
    void deleteOrder(@PathVariable("id") Integer id) {
        iCommandeService.removeCommande(id);
    }

    @PutMapping("/add-assign-liv/{region}")
    @ResponseBody
    public Livraison addLivwithcommand(@RequestBody Livraison l,
                                       @PathVariable("region") Region region)
    {
        Livraison liv = iCommandeService.affectercamandtolivaison(region,l);
        return liv;
    }





///////////////////////////////////LIGNE_Commande///////////////////

    @PostMapping("/add-ligne-commande/{idproduit}/{idcommande}/{q}")
    public LigneCommande addlignecommande(@RequestBody LigneCommande lc,
                                          @PathVariable("idproduit") Integer id,
                                          @PathVariable("idcommande") Integer idc ,
                                          @PathVariable("q") Integer q) {
        lc.setCommande(iCommandeService.retrieveCommande(idc));
        lc.setProduit(metierProduit.retrieveProduit(id));
        lc.setQuantite(q);
        // lc.setPrixtot((int) (lc.getQuantite() * lc.getProduit().getPrice()));

        //  lc.setTotal( lc.getQte() * lc.getProduit().getPrix() );
        //  lc.setTtc( lc.getTotal() + (lc.getTotal()*lc.getProduit().getTva().getTaux())/100.0);


        /// update lel prix fel commande
        Commande order = iCommandeService.retrieveCommande(idc);
        order.setAmount(lc.getCommande().getAmount()+ lc.getQuantite() * lc.getProduit().getPrice());

        iCommandeService.addOrUpdateCommande(order);

        return iLigneCommande.saveLigneCommande(lc);
    }


    @PutMapping("/Update-ligne-commande/{idpp}/{idC}/{q}")
    public LigneCommande aupdatelignecommande(
            @PathVariable("idpp") Integer idProduit,
            @PathVariable("idC") Integer numeroCommande,
            @PathVariable("q") Integer q) {

        LigneCommandeKey id = new LigneCommandeKey((idProduit), (numeroCommande));
        LigneCommande ligneCommande = iLigneCommande.getLigneCommande(id);


        ligneCommande.setQuantite(q);
        // ligneCommande.setPrixtot((int) (ligneCommande.getQuantite() * ligneCommande.getProduit().getPrice()));
        ligneCommande.setCommande(iCommandeService.retrieveCommande(numeroCommande));
        ligneCommande.setProduit(metierProduit.retrieveProduit(idProduit));

        /// update lel prix fel commande
        Commande order = iCommandeService.retrieveCommande(numeroCommande);
        order.setAmount(7+ ligneCommande.getQuantite() * ligneCommande.getProduit().getPrice());
        return iLigneCommande.updateLigneCommande(ligneCommande);

    }




    @DeleteMapping("/delete-ligne-commande/{p}/{c}")
    void deleteLigneCommande( @PathVariable("p") Integer idProduit,
                              @PathVariable("c") Integer numeroCommande){

        LigneCommandeKey id = new LigneCommandeKey((idProduit), (numeroCommande));
        LigneCommande ligneCommande = iLigneCommande.getLigneCommande(id);

        iLigneCommande.deleteLigneCommande(id);


    }
}














