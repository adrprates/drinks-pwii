package com.pwii.drinks.controller;

import com.pwii.drinks.model.Drink;
import com.pwii.drinks.service.DrinkService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/drink/create")
    public String create(Model model){
        model.addAttribute("drink", new Drink());
        return "drink/create";
    }

    @PostMapping("/drink/save")
    public String save(@ModelAttribute @Valid Drink drink, BindingResult result){
        if(result.hasErrors()){
            return "drink/create";
        }
        drinkService.saveDrink(drink);
        return "redirect:/drink";
    }

    @GetMapping("/drink/delete/{id}")
    public String delete(@PathVariable(value = "id") Long id){
        drinkService.deleteDrinkById(id);
        return "redirect:/drink";
    }


    @GetMapping("/drink/edit/{id}")
    public String edit(@PathVariable(value = "id") Long id, Model model){
        Drink drink = drinkService.getDrinkById(id);
        model.addAttribute("drink", drink);
        return "drink/edit";
    }


}
