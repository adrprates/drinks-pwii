package com.pwii.drinks.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotBlank
    @Size(min = 3, max = 100)
    private String name;

    @NotBlank
    @Column(name = "type", nullable = false)
    private String type;

    @NotBlank
    @Column(name = "volume", nullable = false)
    private String volume;

    @NotBlank
    @Column(name = "alcoholic", nullable = false)
    private String alcoholic;

    @NotNull
    @Column(name = "price", nullable = false)
    private double price;

    @NotBlank
    @Size(min = 3, max = 100)
    @Column(name = "manufacturer", nullable = false)
    private String manufacturer;
}
