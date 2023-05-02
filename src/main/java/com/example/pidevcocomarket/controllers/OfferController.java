package com.example.pidevcocomarket.controllers;

import com.example.pidevcocomarket.entities.Offer;
import com.example.pidevcocomarket.interfaces.IOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/offer")
public class OfferController {

    @Autowired
    private IOfferService offerService;


    @Autowired
    public OfferController(IOfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/{id}")
    public Optional<Offer> getOfferById(@PathVariable int id) {
        return offerService.getOfferById(id);
    }

    @GetMapping("/all-offers")
    public List<Offer> getAllOffers() {
        return offerService.getAllOffers();
    }

    @PostMapping("/add-offer")
    public Offer addOffer(@RequestBody Offer offer) {
        return offerService.addOffer(offer);
    }

    @PutMapping("/update-offer")
    public Offer updateOffer(@RequestBody Offer offer) {
        return offerService.updateOffer(offer);
    }

    @DeleteMapping("/delete-offer/{id}")
    public void deleteOffer(@PathVariable int id) {
        offerService.deleteOffer(id);
    }


    @GetMapping("/best-offer")
    public ResponseEntity<Offer> getBestOffer() {
        Offer bestOffer = offerService.getBestOffer();
        if (bestOffer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(bestOffer, HttpStatus.OK);
        }
    }

}
