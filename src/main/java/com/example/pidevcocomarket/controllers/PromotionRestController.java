package com.example.pidevcocomarket.controllers;

import com.example.pidevcocomarket.entities.Promotion;
import com.example.pidevcocomarket.services.IPromotionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/promotions")
public class PromotionRestController {
    @Autowired
    IPromotionServiceImpl Promotionservice ;

    /*@PostMapping(value="/addPromotion", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Promotion addPromotion(@RequestBody Promotion promotion){
        return Promotionservice.AddRealisationPromotion(promotion);
    }*/

    /*@PutMapping(value="/updatePromotion/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Promotion UpdatePromotion(@RequestBody Promotion NewPromotion , @PathVariable("id") Integer Id ){
        return  Promotionservice.UpdatePromotion(NewPromotion, Id);
    }*/
    @PostMapping(value="/addPromotionToStock/stockid/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Promotion addPromotionToStock(@RequestBody Promotion promotion,@PathVariable("id") int id){
        return Promotionservice.addPromotionToStock(promotion,id);
    }
    @DeleteMapping("/removePromotion/{Id}")
    public Integer removePromotion(@PathVariable("Id") Integer Id){
        return Promotionservice.deletePromotion(Id);
    }



    @GetMapping("/AllPromotion")
    public List<Promotion> getPromotion(){
        return Promotionservice.getAllPromotion();
    }

}
