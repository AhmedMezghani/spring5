package com.example.pidevcocomarket.interfaces;

import com.example.pidevcocomarket.entities.Stock;

import java.util.List;

public interface IStockService {
    public Stock ajouterStock(Stock stock);
    public Stock modifierStock(Stock stock);
    public List<Stock> afficherListeStock();
    public void deleteStock(int id);
    public Stock retrieveStock(int id);

                              /*mohamed*/
    void StockAffectBoutique(Integer idStock, Integer idBoutique);
}
