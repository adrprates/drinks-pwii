package com.pwii.drinks.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "drinks")
public class Drink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "volume", nullable = false)
    private String volume;

    @Column(name = "alcoholic", nullable = false)
    private String alcoholic;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "manufacturer", nullable = false)
    private String manufacturer;
}
