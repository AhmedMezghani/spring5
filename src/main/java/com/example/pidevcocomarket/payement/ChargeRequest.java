package com.example.pidevcocomarket.payement;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChargeRequest {

    public enum Currency {
        EUR, USD, TND;
    }
    private String description;
    private int amount; // cents
    private Currency currency;
    private String stripeEmail;
    private String stripeToken;





}