package com.example.pidevcocomarket.controllers;

import com.example.pidevcocomarket.interfaces.ICommandeService;
import com.example.pidevcocomarket.entities.Commande;
import com.example.pidevcocomarket.payement.ChargeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CheckoutController {

    @Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;

    @Autowired
    ICommandeService iCommandeService;



    @RequestMapping("/checkout")
    public String checkout(Model model) {
        model.addAttribute("amount", 50 * 100); // in cents

        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("currency", ChargeRequest.Currency.EUR);

        return "checkout";
    }

    @RequestMapping("/checkoutt")
    public String checkout(Model model, @RequestParam("id") Integer commandeId) {


        Commande commande= iCommandeService.retrieveCommande(commandeId);

        int amount = (int) commande.getAmount();

        // create the ChargeRequest object and set the order ID
        ChargeRequest chargeRequest = new ChargeRequest();
        chargeRequest.setAmount(amount);
        chargeRequest.setCurrency(ChargeRequest.Currency.EUR);
        chargeRequest.setDescription("Example charge");


        model.addAttribute("amount", amount * 100);
        model.addAttribute("stripePublicKey", stripePublicKey);

        model.addAttribute("chargeRequest", chargeRequest);


        return "checkout";
    }
}