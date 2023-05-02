package com.example.pidevcocomarket.services;

import com.example.pidevcocomarket.entities.Catalog;
import com.example.pidevcocomarket.entities.Produit;
import com.example.pidevcocomarket.entities.Promotion;
import com.example.pidevcocomarket.entities.Stock;
import com.example.pidevcocomarket.interfaces.IPromotionService;
import com.example.pidevcocomarket.repositories.CatalogRepository;
import com.example.pidevcocomarket.repositories.ProduitRepository;
import com.example.pidevcocomarket.repositories.PromotionRepository;
import com.example.pidevcocomarket.repositories.StockRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.json.*;
import javax.transaction.Transactional;
import java.io.FileReader;
import java.io.IOException;

import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IPromotionServiceImpl implements IPromotionService {
    @Autowired
    PromotionRepository promotionrepo;
    @Autowired
    StockRepository stockRepository ;
    @Autowired
    ProduitRepository produitRepository ;
    @Autowired
    CatalogRepository catalogueRepository;

/*
    public Promotion AddRealisationPromotion (Promotion promotion){
        return promotionrepo.save(promotion) ;
    }
*/
/* public Promotion UpdatePromotion (Promotion newPromotion , Integer id){
        Promotion promotionToUpdate = promotionrepo.findById(id).orElse(null);
        promotionToUpdate.setPromotionDate(newPromotion.getPromotionDate());
        promotionToUpdate.setNom(newPromotion.getNom());
        promotionToUpdate.setDescription(newPromotion.getDescription());
        promotionToUpdate.setPourcentage(newPromotion.getPourcentage());
        return promotionrepo.save(promotionToUpdate);
    }*/

    public Promotion addPromotionToStock(Promotion promotion, int id) {
        Stock stock = stockRepository.findById(id).get();
        stock.setPromotion(promotion);
        promotion.setAutoGenerate(Boolean.FALSE);
        return promotionrepo.save(promotion) ;
    }


    public List<Promotion> getAllPromotion (){
        return promotionrepo.findAll();
    }



    public Integer deletePromotion (Integer id){
        promotionrepo.deleteById(id);
        return id;
    }


   // realisation de promotion tout se fait automatiquement selon des dates speciales//

@Transactional
@Scheduled(cron = "0 0 8 * * ?")
    public void RealisationPromotion() {
        System.out.println("RealisationPromotion");

        List<Integer> idsSt50 = new ArrayList<>() ;
        List<Integer> idsStDate90 = new ArrayList<>() ;
        List<Integer> idsStDate90120 = new ArrayList<>() ;
        List<Integer> idsStDate95120 = new ArrayList<>() ;
        List<Integer> idsStDate30 = new ArrayList<>() ;
        Year currentYear = Year.now();
        int year = currentYear.getValue();
        Catalog newCatalog = new Catalog();
        LocalDate now = LocalDate.now();
        String filePath = "D:\\Users\\hama\\Downloads\\PidevCocomarket13Finale\\PidevCocomarket\\src\\main\\resources\\Promotion.json";


        try (JsonReader reader = Json.createReader(new FileReader(filePath))) {
            // Read the JSON file content as a JsonObject
            JsonObject json = reader.readObject();
            JsonArray promotions = json.getJsonArray("promotions");
            for (JsonValue promotion : promotions) {
                if (now.equals(LocalDate.parse((((JsonObject)promotion).getString("Date"))))){
                    Promotion p = new Promotion();
                    p.setNom("promotion");
                    p.setPromotionDate(LocalDate.parse((((JsonObject)promotion).getString("Date"))));
                    p.setPourcentage((((JsonObject)promotion).getInt("pourcentage")));
                    p.setDuree((((JsonObject)promotion).getInt("duration")));
                    List<Produit> produits = produitRepository.findAll().stream()
                            .filter(pr-> ((JsonObject)promotion).getJsonArray("category").contains(Json.createValue(pr.getCategorie().getType())))
                            .map(pr->{
                                Set<Stock> stocks = new HashSet<Stock>();
                                stocks.add(pr.getStock());
                                p.setStocks(stocks);
                                pr.setPrice((float) (pr.getPrice()*((float)(100-((JsonObject) promotion).getInt("pourcentage"))/100))) ;
                                pr.setPromotion(true);
                                pr.getStock().setPromotion(p);
                                promotionrepo.save(p);
                                produitRepository.save(pr);
                                stockRepository.save(pr.getStock());
                                return pr;
                            })
                            .collect(Collectors.toList());
                }

            }
            // Do something with the JsonObject
            // For example, print its content
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Annlation des promotion automatique
    @Transactional
    @Scheduled(cron = "0 0 8 * * ?")
    public void AnnulationPromotion() {
        System.out.println("AnnulationPromotion");

        Year currentYear = Year.now();
        int year = currentYear.getValue();
        Catalog newCatalog = new Catalog();
        LocalDate now = LocalDate.now();
        List<Promotion> promotions = promotionrepo.findAll().stream()
                .filter(pr-> now.equals(pr.getPromotionDate().plusDays(pr.getDuree())))
                .map(pr->{
                    pr.getStocks().forEach(s->
                                               {s.setPromotion(null);s.getProduits().forEach(p->
                                                        {p.setPromotion(false);p.setPrice(p.getPrice()*100/(100-pr.getPourcentage()));});
                        });

                    return pr;
                }).collect(Collectors.toList());
    }



}
