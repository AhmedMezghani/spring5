package com.example.pidevcocomarket.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor /*constructeur vide*/
@AllArgsConstructor /*constructeur avec tous les attributs*/
@ToString
@Builder
public class Promotion implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private float pourcentage;
    private LocalDate PromotionDate;
    private int duree ;/*-----------------------------------------------------------------------*/
    private String nom;
    private String description;
    private Boolean autoGenerate = true ;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "promotion")
    @JsonIgnore
    @ToString.Exclude
    private Set<Stock> stocks;
    public Set<Stock> getStocks() {
        return stocks;
    }
    public void setStocks(Set<Stock> stocks) {
        this.stocks = stocks;
    }
}
