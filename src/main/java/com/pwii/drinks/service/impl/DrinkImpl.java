package com.pwii.drinks.service.impl;

import com.pwii.drinks.model.Drink;
import com.pwii.drinks.repository.DrinkRepository;
import com.pwii.drinks.service.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DrinkImpl implements DrinkService {

    @Autowired
    private DrinkRepository drinkRepository;

    @Override
    public List<Drink> getAllDrinks() {
        return drinkRepository.findAll();
    }

    @Override
    public void saveDrink(Drink drink) {
        drinkRepository.save(drink);
    }

    @Override
    public Drink getDrinkById(Long id) {
        Optional<Drink> optional = drinkRepository.findById(id);
        Drink drink = null;
        if(optional.isPresent()){
            drink = optional.get();
        } else{
            throw new RuntimeException("Bebida não encontrada para o id " + id);
        }
        return drink;
    }

    @Override
    public void deleteDrinkById(Long id) {
        drinkRepository.deleteById(id);
    }

}
