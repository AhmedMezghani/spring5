package com.example.pidevcocomarket.services;
import com.example.pidevcocomarket.entities.Boutique;
import com.example.pidevcocomarket.repositories.BoutiqueRepository;
import com.example.pidevcocomarket.repositories.StockRepository;
import com.example.pidevcocomarket.entities.Stock;

import com.example.pidevcocomarket.interfaces.IStockService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor
public class StockService implements IStockService {
    StockRepository stockRepository;
    BoutiqueRepository boutiqueRepository;
    @Override
    public Stock ajouterStock(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public Stock modifierStock(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public List<Stock> afficherListeStock() {
        return stockRepository.findAll();
    }

    @Override
    public void deleteStock(int id) {
        stockRepository.deleteById(id);

    }

    @Override
    public Stock retrieveStock(int id) {
        return stockRepository.findById(id).orElse(null);
    }

                       /*mohamed*/
    @Override
    public void StockAffectBoutique(Integer idStock, Integer idBoutique) {
        Stock stock =stockRepository.findById(idStock).orElse(null);
        Boutique boutique=boutiqueRepository.findById(idBoutique).orElse(null);
        stock.setBoutique(boutique);
        Set<Stock> stocks = boutique.getStocks();
        stocks.add(stock);
        boutique.setStocks(stocks);
        stockRepository.save(stock);
        boutiqueRepository.save(boutique);

    }
                  /*end mohamed*/




}
