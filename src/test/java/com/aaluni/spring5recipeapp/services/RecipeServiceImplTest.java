package com.aaluni.spring5recipeapp.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.aaluni.spring5recipeapp.domain.Recipe;
import com.aaluni.spring5recipeapp.repositories.RecipeRepository;

public class RecipeServiceImplTest {
	@InjectMocks
	RecipeServiceImpl recipeService;
	@Mock
	RecipeRepository recipeRepo;
	@Before
	public void setUp() throws Exception {
		recipeService = new RecipeServiceImpl();;
		MockitoAnnotations.initMocks(this);
	}
	@Test
	public void testGetRecipes() {
		Recipe recipe = new Recipe();
		Set<Recipe> mockRecipes = new HashSet<>();
		mockRecipes.add(recipe);
		when(recipeRepo.findAll()).thenReturn(mockRecipes);	
		Set<Recipe> recipes = recipeService.getRecipes();
		assertEquals(recipes.size(), 1);
		verify(recipeRepo, times(1)).findAll();
	}

}
