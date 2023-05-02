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
public class Stock implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer quantity;
    private LocalDate dateMise;
    private LocalDate dateFin;
    private LocalDate dateEnvoi;
    @OneToMany(cascade = CascadeType.ALL,mappedBy="stock")
    @JsonIgnore
    @ToString.Exclude
    private Set<Produit> produits;

    @ManyToOne
    @ToString.Exclude
    Boutique boutique;

    @ManyToOne
    @ToString.Exclude
    Promotion promotion;
}
