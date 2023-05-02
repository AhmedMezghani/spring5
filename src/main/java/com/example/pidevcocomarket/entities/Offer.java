package com.example.pidevcocomarket.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor /*constructeur vide*/
@AllArgsConstructor /*constructeur avec tous les attributs*/
@ToString
@Builder
public class Offer implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double price;
    private Integer quantity;
    private LocalDateTime deliveryTime;
    @Enumerated(EnumType.STRING)
    private OfferStatus status;
    @ManyToOne
    @JsonIgnore
    private Tender tender;
    @ManyToOne
    @JsonIgnore
    private User provider;
}
