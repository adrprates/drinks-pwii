package com.pwii.drinks.service;

import com.pwii.drinks.model.Drink;
import java.util.List;

public interface DrinkService {
    List<Drink> getAllDrinks();
    void saveDrink(Drink drink);
    Drink getDrinkById(Long id);
    void deleteDrinkById(Long id);
}
