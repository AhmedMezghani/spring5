package com.example.pidevcocomarket.services;

import com.example.pidevcocomarket.repositories.CategorieRepository;
import com.example.pidevcocomarket.entities.Categorie;

import com.example.pidevcocomarket.interfaces.ICategorieService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CategorieService implements ICategorieService {
    CategorieRepository categorieRepository;

    @Override
    public Categorie ajouterCategorie(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    @Override
    public Categorie modifierCategorie(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    @Override
    public List<Categorie> afficherListeCategories() {
        return categorieRepository.findAll();
      /*  List<Categorie> categoryList = categorieRepository.findAll();
       // List<Categorie> categoryList = categorieRepository.findByPParentIsNull();

        for (Categorie categorie : categoryList) {
            recursiveTree(categorie);
        }
        return categoryList;*/
        // return categorieRepository.findByParentIsNull();

    }

    @Override
    public void deleteCategorie(int id) {
        categorieRepository.deleteById(id);

    }

    @Override
    public Categorie retrieveCategorie(int id) {
        return categorieRepository.findById(id).orElse(null);
    }


    //  List<Categorie> categoryList = categorieRepository.findAll();

    /*  for (Categorie categorie : categoryList) {
           recursiveTree(categorie);
       } */


 /*  @Transactional
    public void recursiveTree(Categorie categorie) {

            System.out.println(categorie.getType());
            if (categorie.getChildren().size() > 0) {
                for (Categorie c : categorie.getChildren()) {
                    recursiveTree(c);
                }
            }

        }*/

    /*  @Transactional
      public void addCategoryWithSubcategories(Categorie categorie) {
          if (categorie.getChildren() != null && !categorie.getChildren().isEmpty()) {
              for (Categorie subcategory : categorie.getChildren()) {
                  subcategory.setParent(categorie);
                  addCategoryWithSubcategories(subcategory);
              }
          }
          categorieRepository.save(categorie);
      }*/
    @Override
    public void addCategoryWithSubCategories(Categorie category) {
        addSubCategories(category);
        categorieRepository.save(category);
    }

    private void addSubCategories(Categorie category) {
        for (Categorie subCategory : category.getSubCategories()) {
            addSubCategories(subCategory);
            categorieRepository.save(subCategory);
        }
    }
}

