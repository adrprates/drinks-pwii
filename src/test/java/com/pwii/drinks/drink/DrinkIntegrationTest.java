package com.pwii.drinks.drink;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pwii.drinks.model.Drink;
import com.pwii.drinks.repository.DrinkRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class DrinkIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DrinkRepository drinkRepository;

    @Test
    @WithMockUser(authorities = { "Admin" })
    void testSaveDrinkIntegration() throws Exception {

        Drink drinkA = new Drink();
        drinkA.setName("Drink A");
        drinkA.setType("Tipo A");
        drinkA.setVolume("800 mL");
        drinkA.setAlcoholic("Sim");
        drinkA.setPrice(8.00);
        drinkA.setManufacturer("Los Angeles");


        mockMvc.perform(post("/drink/save")
                        .with(csrf())
                        .flashAttr("drink", drinkA))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/drink"));

        // Verifica no banco se foi salvo
        assertTrue(drinkRepository.findAll()
                .stream()
                .anyMatch(d -> "Drink A".equals(d.getName())));

    }
}
