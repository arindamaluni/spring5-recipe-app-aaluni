package com.aaluni.spring5recipeapp.services;

import java.util.Set;

import com.aaluni.spring5recipeapp.domain.Recipe;

public interface RecipeService {
		Set<Recipe> getRecipes();	
}
