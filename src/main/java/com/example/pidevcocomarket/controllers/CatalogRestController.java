package com.example.pidevcocomarket.controllers;

import com.example.pidevcocomarket.entities.Catalog;
import com.example.pidevcocomarket.services.ICatalogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"*"})
@RestController
public class CatalogRestController {
    @Autowired
    ICatalogServiceImpl Catalogueservice ;

    @GetMapping("/afficherCatalogueAllProduct")
    Catalog afficherCatalogueAllProduct() {
        return Catalogueservice.GenerationCatalogueAll();
    }

    @GetMapping("afficherCatalogueBoutiquePromotion/{id}")
    Catalog retriveCatalogueBoutiquePromotion(@PathVariable("id") int id) {
        return Catalogueservice.GenerateCatalogueBoutiquePromotion(id);
    }

    @GetMapping("afficherCatalogueBoutiqueSansPromotion/{id}")
    Catalog retriveCatalogueBoutiqueSansPromotion(@PathVariable("id") int id) {
        return Catalogueservice.GenerateCatalogueBoutiqueSansPromotion(id);
    }

    @GetMapping("/afficherCataloguePromotion")
    Catalog afficherCataloguePromotion() {
        return Catalogueservice.GenerateCataloguePromotion();
    }

    @GetMapping("/afficherAllCatalogue")
    List<Catalog> afficherAllCataloque() {
        return Catalogueservice.GetAllCatalogue();
    }

    @DeleteMapping("/DeleteCatalogue/{Id}")
    Integer DeleteCatalogue (@PathVariable ("Id") Integer Id){
        return Catalogueservice.DeleteCatalogue(Id) ;
    }

}
