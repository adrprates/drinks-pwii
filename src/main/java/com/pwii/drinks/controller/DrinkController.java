package com.pwii.drinks.controller;

import com.pwii.drinks.model.Drink;
import com.pwii.drinks.service.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DrinkController {

    @Autowired
    private DrinkService drinkService;

    @GetMapping("/")
    public String viewHomePage(Model model){
        model.addAttribute("listDrinks", drinkService.getAllDrinks());
        return "index";
    }

    @GetMapping("/drink")
    public String index(Model model){
        model.addAttribute("listDrinks", drinkService.getAllDrinks());
        return "drink/index";
    }

    @GetMapping("/save-drink")
    public String saveDrink(@ModelAttribute("drink") Drink drink){
        drinkService.saveDrink(drink);
        return "redirect:/";
    }

    @GetMapping("/deleteDrink/{id}")
    public String deleteDrink(@PathVariable(value = "id") long id){
        drinkService.deleteDrinkById(id);
        return "redirect:/";
    }


}
