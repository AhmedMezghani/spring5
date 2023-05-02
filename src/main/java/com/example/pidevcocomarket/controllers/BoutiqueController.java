package com.example.pidevcocomarket.controllers;


import com.example.pidevcocomarket.entities.Boutique;
import com.example.pidevcocomarket.interfaces.Iservice;
import com.example.pidevcocomarket.repositories.BoutiqueRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/boutique")
public class BoutiqueController {
    @Autowired
    Iservice is;
    @Autowired
    private BoutiqueRepository boutiqueRepository;




    @PostMapping("/add-boutique")
    Boutique addboutique(@RequestBody Boutique boutique) {

        return is.addboutique(boutique);}


    @PostMapping("/addboutiquePourtesterPromo")
    Boutique addBoutique(@RequestBody Boutique boutique) {

        return is.createNewBoutiquePourPromtion(boutique);
    }
    @PostMapping("/update-boutique")
    Boutique updateBoutique(@RequestBody Boutique boutique){return is.updateBoutique(boutique);}


    @GetMapping("/showall-boutiques")
    List<Boutique> retrieveAllBoutiques(){return is.retrieveAllBoutiques();}

    @GetMapping("/show-boutique/{id}")
    Boutique retrieveBoutique(@PathVariable("id") Integer id){return is.retrieveBoutique(id);}

    @DeleteMapping("/delete-boutique/{id}")
    public void removeBoutique(Integer id){is.removeBoutique(id);}

    @GetMapping("/search-by-address/{address}")
    public List<Boutique> searchByAddress(@PathVariable String address) {
        return boutiqueRepository.findByAddressContaining(address);
    }


    @GetMapping("/sort")
    public ResponseEntity<List<Boutique>> sortBoutiques() {
        List<Boutique> boutiques = boutiqueRepository.findByOrderByProductsNumberDesc();
        return ResponseEntity.ok(boutiques);
    }

}
