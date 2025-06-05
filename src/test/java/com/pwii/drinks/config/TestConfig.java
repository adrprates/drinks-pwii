package com.pwii.drinks.config;


import com.pwii.drinks.service.DrinkService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public DrinkService drinkService() {
        return Mockito.mock(DrinkService.class);
    }
}
