package com.aaluni.spring5recipeapp.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import static org.mockito.ArgumentMatchers.*;

import com.aaluni.spring5recipeapp.domain.Recipe;
import com.aaluni.spring5recipeapp.repositories.RecipeRepository;
import com.aaluni.spring5recipeapp.services.RecipeService;

public class IndexControllerTest {
	
	@InjectMocks
	IndexController indexController;
	@Mock
	RecipeService recipeService;
	@Mock
	Model model;
	@Before
	public void setUp() throws Exception {
		indexController = new IndexController();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetIndexPage() {
		String viewName = indexController.getIndexPage(model);
		assertEquals("index", viewName);
		verify(recipeService, times(1)).getRecipes();
		verify(model, times(1)).addAttribute(eq("recipes"), anySet());
	}
	
	//Argument Capture
	@Test
	public void testGetIndexPageWithAttributeCapture() {
		Set<Recipe> recipes = new HashSet<>();
		recipes.add(new Recipe());
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		recipes.add(recipe);
		
		
		when(recipeService.getRecipes()).thenReturn(recipes);
		ArgumentCaptor<Set<Recipe>> argumentCaptorRecipe = ArgumentCaptor.forClass(Set.class);
		
		String viewName = indexController.getIndexPage(model);
		
		assertEquals("index", viewName);
		verify(recipeService, times(1)).getRecipes();
		verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptorRecipe.capture());
		Set<Recipe> recipeArgument = argumentCaptorRecipe.getValue();
		assertEquals(2, recipeArgument.size());
	}
	@Test
	public void testMockMVC() throws Exception{
		MockMvc mockMVC = MockMvcBuilders.standaloneSetup(indexController).build();
		mockMVC.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(view().name("index"));
		
	}
	

}
