package com.example.pidevcocomarket.controllers;


import com.example.pidevcocomarket.entities.Stock;
import com.example.pidevcocomarket.interfaces.IStockService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock")
@AllArgsConstructor
@CrossOrigin(origins = {"*"})

public class StockController {
    IStockService stockService;
    @PostMapping("/ajouter-stock")
    Stock ajouterstock(@RequestBody Stock s) {
        return stockService.ajouterStock(s);
    }

    @PutMapping("/modifier-stock")
    Stock modifierstock(@RequestBody Stock s) {
        return stockService.modifierStock(s);
    }

    @GetMapping("/afficher-stock")
    List<Stock> afficherstock() {
        return stockService.afficherListeStock();
    }

    @DeleteMapping("/supprimer-stock/{id}")
    void supprimerstock(@PathVariable int id) {
        stockService.deleteStock(id);
    }

    @GetMapping("afficher-stock/{id}")
    Stock retrivestock(@PathVariable int id) {
        return stockService.retrieveStock(id);
    }

                                 /*mohamed*/
  @PostMapping ("/affectStockToBoutique/Pour testerPromo/{idStock}/{idBoutique}")
    void affecterStockToBoutique(@PathVariable("idStock") Integer idStock,@PathVariable("idBoutique") Integer idBoutique)
    {
        stockService.StockAffectBoutique(idStock, idBoutique);
    }


}

