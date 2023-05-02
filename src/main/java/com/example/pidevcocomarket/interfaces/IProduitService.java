package com.example.pidevcocomarket.interfaces;

import com.example.pidevcocomarket.entities.Produit;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IProduitService {
    public Produit ajouterProduit(Produit produit);
    public Produit modifierProduit(Produit produit);
    public List<Produit> afficherListeProduit();
    public void deleteProduit(int id);
    public Produit retrieveProduit(int id);

                               /*mohamed*/
    void ProduitAffectBoutiqueCategorieStock(Integer idProduit, Integer idBoutique, Integer idCategorie, Integer idStock);

                               /*end mohamed*/


    public List<String> getBasicColors();
    public List<Produit> findProductsByName(String name);
    public List<Produit> findProductsByDescription(String tags);
    public List<Produit> getSimilarProducts(Produit product);


}
