package com.example.pidevcocomarket.services;

import com.example.pidevcocomarket.entities.Categorie;
import com.example.pidevcocomarket.repositories.BoutiqueRepository;
import com.example.pidevcocomarket.repositories.CategorieRepository;
import com.example.pidevcocomarket.repositories.ProduitRepository;
import com.example.pidevcocomarket.repositories.StockRepository;
import com.example.pidevcocomarket.entities.Boutique;
import com.example.pidevcocomarket.entities.Produit;

import com.example.pidevcocomarket.entities.Stock;
import com.example.pidevcocomarket.interfaces.IProduitService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor

public class ProduitService implements IProduitService {
    ProduitRepository produitRepository;
    BoutiqueRepository boutiqueRepository;

    StockRepository stockRepository;
    CategorieRepository categorieRepository;

    @Override
    public Produit ajouterProduit(Produit produit)  {

        return produitRepository.save(produit);
    }

    @Override
    public Produit modifierProduit(Produit produit) {
        return produitRepository.save(produit);
    }

    @Override
    public List<Produit> afficherListeProduit() {
        return produitRepository.findAll();
    }

    @Override
    public void deleteProduit(int id) {
        produitRepository.deleteById(id);

    }

    @Override
    public Produit retrieveProduit(int id) {
        return produitRepository.findById(id).orElse(null);
    }


    public void ProduitAffectBoutique(Integer idProduit, Integer idBoutique) {
        Produit produit =produitRepository.findById(idProduit).orElse(null);
        Boutique boutique=boutiqueRepository.findById(idBoutique).orElse(null);
       // produit.setBoutiques((Set<Boutique>) boutique);
        produitRepository.save(produit);

    }




    public void ProduitAffectBoutiqueAndStock(Integer idProduit, Integer idBoutique,Integer idStock) {

        Produit produit =produitRepository.findById(idProduit).orElse(null);
        Stock stock = stockRepository.findById(idStock).orElse(null);
        Boutique boutique = boutiqueRepository.findById(idBoutique).orElse(null);

       // produit.setStocks((Set<Stock>) stock);
        //produit.setBoutiques((Set<Boutique>) boutique);

        produitRepository.save(produit);


    }

  //////////////////////////////// mohamed/////////////////////////////////////////////////////////////////////////
     @Override
     public void ProduitAffectBoutiqueCategorieStock(Integer idProduit, Integer idBoutique, Integer idCategorie, Integer idStock) {
         Produit produit = produitRepository.findById(idProduit).orElse(null);
         Boutique boutique = boutiqueRepository.findById(idBoutique).orElse(null);
         Categorie categorie = categorieRepository.findById(idCategorie).orElse(null);
         Stock stock = stockRepository.findById(idStock).orElse(null);

         if (produit != null && boutique != null && categorie != null && stock != null) {
             produit.setBoutique(boutique);
             produit.setCategorie(categorie);
             produit.setStock(stock);

             Set<Produit> produitsBoutique = boutique.getProduits();
             produitsBoutique.add(produit);
             boutique.setProduits(produitsBoutique);

             Set<Produit> produitsCategorie = categorie.getProduits();
             produitsCategorie.add(produit);
             categorie.setProduits(produitsCategorie);

             Set<Produit> produitsStock = stock.getProduits();
             produitsStock.add(produit);
             stock.setProduits(produitsStock);

             produitRepository.save(produit);
             boutiqueRepository.save(boutique);
             categorieRepository.save(categorie);
             stockRepository.save(stock);
         }
     }

////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public List<String> getBasicColors(){
        List<String> basicColors=new ArrayList<>();
        basicColors.add("#FF0000");//rouge
        basicColors.add("#00FF00");//vert
        basicColors.add("#0000FF");//bleu
        basicColors.add("#FFFF00");//jaune
        basicColors.add("#FFA500");//orange
        basicColors.add("#800080");//violet
        return basicColors;


    }

    @Override
    public List<Produit> findProductsByName(String name) {
        return null;
    }

    @Override
    public List<Produit> findProductsByDescription(String tags) {
        return null;
    }

    @Override
    public List<Produit> getSimilarProducts(Produit product) {
        return null;
    }

    private List<Produit> productList = new ArrayList<Produit>();
    // Recherche de produits par nom
    public List<Produit> searchProducts(String query) {
        List<Produit> matchingProducts = new ArrayList<Produit>();
        for (Produit product : productList) {
            if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                matchingProducts.add(product);
            }
        }
        return matchingProducts;
    }
    // Récupération de la liste complète des produits

    public List<Produit> getAllProducts() {
        return productList;
    }



}
