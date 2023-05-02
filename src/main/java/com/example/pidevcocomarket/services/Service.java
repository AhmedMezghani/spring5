package com.example.pidevcocomarket.services;


import com.example.pidevcocomarket.entities.*;
import com.example.pidevcocomarket.interfaces.Iservice;
import com.example.pidevcocomarket.repositories.*;

import org.apache.commons.validator.routines.IBANValidator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service

public class Service implements Iservice {


    @Autowired
    ContratRepository contratRepository;
    @Autowired
    BoutiqueRepository boutiqueRepository;
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    BadWordsRepository badWordsRepository;
    @Autowired
    ProduitRepository produitRepository;

    @Override
    public Contract addContract(Contract c) {
        return contratRepository.save(c);
    }

    @Override
    public Contract updateContract(Contract c) {
        return contratRepository.save(c);
    }

    @Override
    public void removeContract(Long idContract) {
        contratRepository.deleteById(idContract);
    }

    @Override
    public List<Contract> retrieveAllContract() {
        return contratRepository.findAll();
    }

    @Override
    public Contract retrieveContract(Long idContract) {
        return contratRepository.findById(idContract).orElse(null);
    }


    //////////////////////////boutique/////////////////////////
    public static String repeatString(String str, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }



    private boolean isValidBIC(String bicStr) {
        // Le format d'un BIC est 8 caractères alphanumériques suivis de 3 caractères optionnels alphanumériques
        String bicRegex = "^[A-Za-z0-9]{8}([A-Za-z0-9]{3})?$";
        return bicStr.matches(bicRegex);
    }


    private boolean isValidAddress(String address) {
        // Vérifier si l'adresse est valide
        return address.matches("^\\d+\\s[A-z]+\\s[A-z]+");
    }


    public List<Boutique> findByAddressContaining(String address) {
        return boutiqueRepository.findByAddressContaining(address);
    }

    public List<Boutique> findByOrderByProductsNumberDesc() {
        return boutiqueRepository.findByOrderByProductsNumberDesc();
    }


    @Override
    public Boutique addboutique(Boutique b) {

        IBANValidator validator = IBANValidator.getInstance();


        if (!validator.isValid(b.getIBAN())) {
            throw new IllegalArgumentException("IBAN invalide");
        } else if (!isValidBIC(b.getBIC())) {
            throw new IllegalArgumentException("BIC invalide");


        } else if (!isValidAddress(b.getAddress())) {
            throw new IllegalArgumentException("Adresse invalide");

        } else {
            Boutique savedBoutique = boutiqueRepository.save(b);
            List<BadWords> badWords = badWordsRepository.findAll();
            String description = savedBoutique.getDescription();
            for (BadWords word : badWords) {
                if (description.contains(word.getMot())) {
                    String asterisks = repeatString("*", word.getMot().length());
                    description = description.replaceAll(word.getMot(), asterisks);
                }
            }

            savedBoutique.setDescription(description);
            return boutiqueRepository.save(savedBoutique);
        }
    }


    @Override
    public Boutique updateBoutique(Boutique b) {
        return boutiqueRepository.save(b);
    }

    @Override
    public void removeBoutique(Integer idBoutique) {
        boutiqueRepository.deleteById(idBoutique);
    }

    @Override
    public List<Boutique> retrieveAllBoutiques() {
        return boutiqueRepository.findAll();
    }

    @Override
    public Boutique retrieveBoutique(Integer idBoutique) {
        return boutiqueRepository.findById(idBoutique).orElse(null);
    }

//////////////////////notifications//////////////////////

    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }

     public void setNotificationRepository(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification addnotifications(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

//////////////////////BadWords//////////////////////////////////////

    public BadWords addword (BadWords b){ return badWordsRepository.save(b);}

////////////////////////////////////////////////////mohamed///////////////
    @Override
    public Boutique createNewBoutiquePourPromtion (Boutique boutique ){
        return  boutiqueRepository.save(boutique) ;
    }


    public List<Boutique> GetAllBoutiquePourPromtion (){
        return boutiqueRepository.findAll() ;
    }

    public List<Produit> GetProduitByBoutiquePourPromtion(Integer ID) {
        return produitRepository.findAll().stream().filter(produit ->produit.getBoutique().getId()==ID).collect(Collectors.toList());
    }
}