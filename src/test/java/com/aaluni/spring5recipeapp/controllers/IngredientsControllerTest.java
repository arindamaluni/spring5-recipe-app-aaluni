package com.aaluni.spring5recipeapp.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import com.aaluni.spring5recipeapp.commands.IngredientCommand;
import com.aaluni.spring5recipeapp.commands.RecipeCommand;

import com.aaluni.spring5recipeapp.services.IngredientsService;
import com.aaluni.spring5recipeapp.services.RecipeService;
import com.aaluni.spring5recipeapp.services.UnitOfMeasureService;


class IngredientsControllerTest {
	@InjectMocks
	IngredientsController controller;
	
	@Mock
	RecipeService recipeService;
	@Mock
	IngredientsService ingredientService;
	@Mock
    UnitOfMeasureService unitOfMeasureService;
	
	MockMvc mockMvc;
	
	RecipeCommand recipe;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		recipe = new RecipeCommand(); 
		recipe.setId(1L);
		recipe.setDescription("Test Recipe");
		controller = new IngredientsController(recipeService, ingredientService, unitOfMeasureService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	void testListIngredients() throws Exception{
		
		when(recipeService.findRecipeCommandById(anyLong())).thenReturn(recipe);
		mockMvc.perform(get("/recipe/1/ingredients"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("recipe"))
			.andExpect(model().attribute("recipe", recipe))
			.andExpect(view().name("/recipe/ingredient/list"));
		verify(recipeService, times(1)).findRecipeCommandById(anyLong());
		
	}
	
	@Test
	void testShowIngredient() throws Exception {
		IngredientCommand ingr = new IngredientCommand();
		when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingr);
		
		mockMvc.perform(get("/recipe/1/ingredient/2/show"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("ingredient"))
			.andExpect(view().name("recipe/ingredient/show"));
		verify(ingredientService, times(1)).findByRecipeIdAndIngredientId(anyLong(), anyLong());
	}
	
	   @Test
	    public void testUpdateIngredientForm() throws Exception {
	        //given
	        IngredientCommand ingredientCommand = new IngredientCommand();

	        //when
	        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);
	        when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());

	        //then
	        mockMvc.perform(get("/recipe/1/ingredient/2/update"))
	                .andExpect(status().isOk())
	                .andExpect(view().name("recipe/ingredient/ingredientform"))
	                .andExpect(model().attributeExists("ingredient"))
	                .andExpect(model().attributeExists("uomList"));
	    }

	    @Test
	    public void testSaveOrUpdate() throws Exception {
	        //given
	        IngredientCommand command = new IngredientCommand();
	        command.setId(3L);
	        command.setRecipeId(2L);

	        //when
	        when(ingredientService.saveIngredientCommand(any())).thenReturn(command);

	        //then
	        mockMvc.perform(post("/recipe/2/ingredient")
	                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
	                .param("id", "")
	                .param("description", "some string")
	        )
	                .andExpect(status().is3xxRedirection())
	                .andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));
	    }

}
