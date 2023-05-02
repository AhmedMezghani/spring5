package com.example.pidevcocomarket.schedulers;

import com.example.pidevcocomarket.entities.Produit;
import com.example.pidevcocomarket.entities.Stock;
import com.example.pidevcocomarket.repositories.ProduitRepository;
import com.example.pidevcocomarket.services.VendorMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class CheckLowQuantityProductsScheduler {

    private final ProduitRepository produitRepository;
    private final VendorMailService vendorMailService;

    @Autowired
    public CheckLowQuantityProductsScheduler(ProduitRepository produitRepository, VendorMailService vendorMailService) {
        this.produitRepository = produitRepository;
        this.vendorMailService = vendorMailService;
    }

    @Scheduled(cron = "0 */1 * * * *") // run every 1 minute
    public void checkLowQuantityProducts() {
        System.out.println("checkLowQuantityProducts");
        List<Produit> produits = produitRepository.findAll();
        for (Produit produit : produits) {
            System.out.println(produit.toString());
            Stock stock = produit.getStock();
            if (stock.getQuantity()<50){
                    String vendorEmail = produit.getVendorMail();
                    if (vendorEmail != null) {
                        vendorMailService.sendQuantityLowEmail(vendorEmail);
                    }

            }
        }
    }
}