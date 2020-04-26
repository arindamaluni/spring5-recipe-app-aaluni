package com.aaluni.spring5recipeapp.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.mockito.ArgumentMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.aaluni.spring5recipeapp.commands.RecipeCommand;
import com.aaluni.spring5recipeapp.domain.Recipe;
import com.aaluni.spring5recipeapp.services.RecipeService;

class IngredientsControllerTest {
	@InjectMocks
	IngredientsController controller;
	
	@Mock
	RecipeService recipeService;
	MockMvc mockMvc;
	
	RecipeCommand recipe;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		recipe = new RecipeCommand(); 
		recipe.setId(1L);
		recipe.setDescription("Test Recipe");
		controller = new IngredientsController(recipeService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	void testShowAllIngredients() throws Exception{
		
		when(recipeService.findRecipeCommandById(anyLong())).thenReturn(recipe);
		mockMvc.perform(get("/recipe/1/ingredients"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("recipe"))
			.andExpect(model().attribute("recipe", recipe))
			.andExpect(view().name("/recipe/ingredient/list"));
		verify(recipeService, times(1)).findRecipeCommandById(anyLong());
	}

}
