package com.pwii.drinks.drink;

import com.pwii.drinks.config.TestConfig;
import com.pwii.drinks.controller.DrinkController;
import com.pwii.drinks.model.Drink;
import com.pwii.drinks.service.DrinkService;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(DrinkController.class)
@Import(TestConfig.class)
public class DrinkControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    DrinkService drinkService;

    @AfterEach
    void resetMocks(){
        reset(drinkService);
    }

    private List<Drink> testCreateDrinkList(){
        Drink drinkB = new Drink();
        drinkB.setId(1);
        drinkB.setName("Drink");
        drinkB.setType("Classic");
        drinkB.setVolume("100 mL");
        drinkB.setAlcoholic("Sim");
        drinkB.setPrice(10.00);
        drinkB.setManufacturer("Green Islands");

        return List.of(drinkB);
    }

    @Test
    @DisplayName("GET /product - Listar bebidas na tela index sem usuário autenticado")
    void testIndexNotAuthenticatedUser() throws Exception {
        mockMvc.perform(get("/drink"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    @DisplayName("GET /drink - Listar bebidas na tela index com usuário logado")
    void testIndexAuthenticatedUser() throws Exception {
        when(drinkService.getAllDrinks()).thenReturn(testCreateDrinkList());

        mockMvc.perform(get("/drink"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("listDrinks"))
                .andExpect(content().string(containsString("Listar Bebida")))
                .andExpect(content().string(containsString("Drink")));
    }

    @Test
    @WithMockUser(username = "aluno@iftm.edu.br", authorities = { "Admin" })
    @DisplayName("GET /drink/create - Exibe formulário de criação")
    void testCreateFormAuthorizedUser() throws Exception {
        mockMvc.perform(get("/drink/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("create"))
                .andExpect(model().attributeExists("drink"))
                .andExpect(content().string(containsString("Cadastrar Bebida")));
    }

    @Test
    @WithMockUser(username = "aluno2@iftm.edu.br", authorities = { "Manager" })
    @DisplayName("GET /drink - Verificar o link de cadastrar para um usuario não admin logado")
    void testCreateFormNotAuthorizedUser() throws Exception {
        when(drinkService.getAllDrinks()).thenReturn(testCreateDrinkList());
        mockMvc.perform(get("/drink/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("create"))
                .andExpect(model().attributeExists("drink"))
                .andExpect(content().string(not(containsString("<a class=\"dropdown-item\" href=\"/drink/create\">Cadastrar</a>"))));
    }

    @Test
    @WithMockUser
    @DisplayName("POST /drink/save - Falha na validação e retorna para o formulário")
    void testSaveDrinkValidationError() throws Exception {
        Drink drink = new Drink(); // Bebida sem nome, o que causará erro de validação

        mockMvc.perform(post("/drink/save")
                        .with(csrf())
                        .flashAttr("drink", drink))
                .andExpect(status().isOk())
                .andExpect(view().name("create"))
                .andExpect(model().attributeHasErrors("drink"));

        verify(drinkService, never()).saveDrink(any(Drink.class));
    }

    @Test
    @WithMockUser(username = "aluno@iftm.edu.br", authorities = { "Admin" })
    @DisplayName("POST /drink/save - Bebida válido é salvo com sucesso")
    void testSaveValidDrink() throws Exception {
        Drink drink = new Drink();
        drink.setName("Novo Drink");
        drink.setType("Mix");
        drink.setVolume("200 mL");
        drink.setAlcoholic("Não");
        drink.setPrice(50.00);
        drink.setManufacturer("Blue Islands");

        mockMvc.perform(post("/drink/save")
                        .with(csrf())
                        .flashAttr("drink", drink))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/drink"));

        verify(drinkService).saveDrink(any(Drink.class));
    }

}
