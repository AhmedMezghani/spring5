package com.example.pidevcocomarket.services;

import com.example.pidevcocomarket.algorithms.BestOfferAlgorithm;
import com.example.pidevcocomarket.entities.Offer;
import com.example.pidevcocomarket.interfaces.IOfferService;
import com.example.pidevcocomarket.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OfferServiceImpl implements IOfferService {
    @Autowired
    private OfferRepository offerRepository;

    @Override
    public Optional<Offer> getOfferById(int id) {
        return offerRepository.findById(id);
    }

    @Override
    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    @Override
    public Offer addOffer(Offer offer) {
        return offerRepository.save(offer);
    }

    @Override
    public Offer updateOffer(Offer offer) {
        return offerRepository.save(offer);
    }

    @Override
    public void deleteOffer(int id) {
        offerRepository.deleteById(id);
    }

    @Override
    public Offer getBestOffer() {
        List<Offer> allOffers = getAllOffers();
        BestOfferAlgorithm algorithm = new BestOfferAlgorithm();
        return algorithm.getBestOffer(allOffers);
    }

    @Override
    public void saveOffer(Offer offer) {
        offerRepository.save(offer);
    }


    @Override
    public void setDeliveryTime(Offer offer, LocalDateTime deliveryTime) {
        offer.setDeliveryTime(deliveryTime);
        offerRepository.save(offer);
    }


}
