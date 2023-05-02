package com.example.pidevcocomarket.algorithms;

import com.example.pidevcocomarket.entities.Offer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class BestOfferAlgorithm {

    public Offer getBestOffer(List<Offer> offers) {
        if (offers.isEmpty()) {
            return null;
        }

        // sort by price ascending, quantity descending, and delivery date ascending
        offers.sort(Comparator.comparing(Offer::getPrice)
                .thenComparing(Offer::getQuantity, Comparator.reverseOrder())
                .thenComparing(Offer::getDeliveryTime));

        // find the first offer that has the lowest price
        Double lowestPrice = offers.get(0).getPrice();
        List<Offer> lowestPriceOffers = offers.stream()
                .filter(o -> o.getPrice().compareTo(lowestPrice) == 0)
                .collect(Collectors.toList());

        if (lowestPriceOffers.size() == 1) {
            return lowestPriceOffers.get(0);
        }

        // find the offer with the highest quantity among the lowest price offers
        Integer highestQuantity = lowestPriceOffers.stream()
                .mapToInt(Offer::getQuantity)
                .max()
                .orElseThrow(RuntimeException::new);
        List<Offer> highestQuantityOffers = lowestPriceOffers.stream()
                .filter(o -> o.getQuantity().equals(highestQuantity))
                .collect(Collectors.toList());

        if (highestQuantityOffers.size() == 1) {
            return highestQuantityOffers.get(0);
        }

        // find the offer with the lowest delivery date among the highest quantity offers
        LocalDateTime earliestDeliveryTime = highestQuantityOffers.get(0).getDeliveryTime();
        List<Offer> earliestDeliveryDateOffers = highestQuantityOffers.stream()
                .filter(o -> o.getDeliveryTime().isEqual(earliestDeliveryTime))
                .collect(Collectors.toList());

        if (earliestDeliveryDateOffers.size() == 1) {
            return earliestDeliveryDateOffers.get(0);
        }

        // if all else fails, choose a random offer among the earliest delivery date offers
        return earliestDeliveryDateOffers.get(new Random().nextInt(earliestDeliveryDateOffers.size()));
    }
}
