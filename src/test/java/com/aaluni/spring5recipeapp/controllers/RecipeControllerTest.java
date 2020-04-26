package com.aaluni.spring5recipeapp.controllers;

import static org.hamcrest.CoreMatchers.isA;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.aaluni.spring5recipeapp.commands.RecipeCommand;
import com.aaluni.spring5recipeapp.domain.Recipe;
import com.aaluni.spring5recipeapp.services.RecipeService;

public class RecipeControllerTest {


    @Mock
    RecipeService recipeService;

    RecipeController controller;
    
    MockMvc mockMvc;
    

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetRecipe() throws Exception {

        Recipe recipe = new Recipe();
        recipe.setId(1L);

        when(recipeService.findById(anyLong())).thenReturn(recipe);

        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"));
    }
    
    @Test
    public void testNewRecipe() throws Exception{
    	mockMvc.perform(get("/recipe/new"))
    		.andExpect(model().attribute("recipe", isA(RecipeCommand.class)))
    		.andExpect(status().isOk())
    		.andExpect(view().name("recipe/recipeform"));
     }
    
    @Test
    public void testPostNewRecipeForm() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

        when(recipeService.saveRecipeCommand(any())).thenReturn(command);

        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/show"));
    }
    
//    @Test
//    public void testGetUpdateView() throws Exception {
//        RecipeCommand command = new RecipeCommand();
//        command.setId(2L);
//
//        when(recipeService.findCommandById(anyLong())).thenReturn(command);
//
//        mockMvc.perform(get("/recipe/1/update"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("recipe/recipeform"))
//                .andExpect(model().attributeExists("recipe"));
//    }
    
}