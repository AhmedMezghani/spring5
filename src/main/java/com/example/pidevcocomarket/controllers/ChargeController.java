package com.example.pidevcocomarket.controllers;



import com.example.pidevcocomarket.repositories.CommandeRepository;
import com.example.pidevcocomarket.interfaces.ICommandeService;
import com.example.pidevcocomarket.payement.ChargeRequest;
import com.example.pidevcocomarket.services.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
/*
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;

import javax.naming.AuthenticationException;
import javax.smartcardio.CardException;
*/

@Log
@Controller
public class ChargeController {

    @Autowired
    StripeService paymentsService;

    @Autowired
    CommandeRepository commandeRepository;
    @Autowired
    ICommandeService commandeService;
    @Autowired
    com.example.pidevcocomarket.services.EmailServiceImpl emailService;

 /* @PostMapping("/charge")
    public String charge(ChargeRequest chargeRequest, Model model) throws StripeException, AuthenticationException, CardException {
        chargeRequest.setDescription("Example charge");
        chargeRequest.setCurrency(ChargeRequest.Currency.EUR);
        Charge charge = paymentsService.charge(chargeRequest);
        model.addAttribute("id", charge.getId());
        model.addAttribute("status", charge.getStatus());
        model.addAttribute("chargeId", charge.getId());
        model.addAttribute("balance_transaction", charge.getBalanceTransaction());
        emailService.sendSimpleEmail("benali.dania@esprit.tn","paiemment réussi", String.valueOf(charge.getAmount()/100));

      return "result";
    }
*/

  /*  @PostMapping("/charge")

    public String charge(ChargeRequest chargeRequest, Model model) throws StripeException, AuthenticationException, CardException {

        chargeRequest.setDescription("Example charge");
        chargeRequest.setCurrency(ChargeRequest.Currency.EUR);

        // retrieve the order by its ID
        Integer orderId = chargeRequest.getOrderId();

        Commande order = commandeRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        //Commande order = commandeService.retrieveCommande(orderId).orElseThrow(() -> new RuntimeException("Order not found"));;
        Charge charge = paymentsService.charge(chargeRequest);
        model.addAttribute("id", charge.getId());
        model.addAttribute("status", charge.getStatus());
        model.addAttribute("chargeId", charge.getId());
        model.addAttribute("balance_transaction", charge.getBalanceTransaction());

        // set payment status and date
        order.setPay_confirmed(true);
        order.setDatePay(LocalDate.now());

        // save the updated order to the database
        commandeRepository.save(order);

        return "result";
    } */


 /*   @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "result";
    }*/
}