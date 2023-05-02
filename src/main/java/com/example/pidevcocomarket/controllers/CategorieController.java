package com.example.pidevcocomarket.controllers;


import com.example.pidevcocomarket.interfaces.ICategorieService;
import com.example.pidevcocomarket.entities.Categorie;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorie")
@AllArgsConstructor
public class CategorieController {
    ICategorieService categorieService;

    @PostMapping("/ajouter-Categorie")
    Categorie ajoutercategorie(@RequestBody Categorie c) {
        return categorieService.ajouterCategorie(c);
    }

  /*  @PostMapping("/addCategoryWithSubcategories")
    Categorie ajoutercat_subcat(@RequestBody Categorie c){return  categorieService.add}*/

    @PutMapping("/modifier-Categorie")
    Categorie modifiercategorie(@RequestBody Categorie c) {
        return categorieService.modifierCategorie(c);
    }

    @GetMapping("/afficher-Categorie")
    List<Categorie> affichercategorie() {
        return categorieService.afficherListeCategories();
    }

    @DeleteMapping("/supprimer-Categorie/{id}")
    void supprimercategorie(@PathVariable int id) {
        categorieService.deleteCategorie(id);
    }

    @GetMapping("afficher-Categorie/{id}")
    Categorie retrivecategorie(@PathVariable int id) {
        return categorieService.retrieveCategorie(id);
    }
    @PostMapping
    public void addCategoryWithSubCategories(@RequestBody Categorie category) {
        categorieService.addCategoryWithSubCategories(category);
    }



}

