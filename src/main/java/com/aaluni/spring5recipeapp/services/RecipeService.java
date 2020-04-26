package com.aaluni.spring5recipeapp.services;

import java.util.Set;

import com.aaluni.spring5recipeapp.commands.RecipeCommand;
import com.aaluni.spring5recipeapp.domain.Recipe;

public interface RecipeService {
		Set<Recipe> getRecipes();	
		Recipe findById(Long l);
		RecipeCommand saveRecipeCommand(RecipeCommand command);
		RecipeCommand findRecipeCommandById(Long id);
}
