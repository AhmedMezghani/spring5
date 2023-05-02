package com.example.pidevcocomarket.interfaces;

import com.example.pidevcocomarket.entities.Offer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IOfferService {
    Optional<Offer> getOfferById(int id);

    List<Offer> getAllOffers();

    Offer addOffer(Offer offer);

    Offer updateOffer(Offer offer);

    void deleteOffer(int id);

    Offer getBestOffer();

    void saveOffer(Offer offer);

    void setDeliveryTime(Offer offer, LocalDateTime deliveryTime);

}