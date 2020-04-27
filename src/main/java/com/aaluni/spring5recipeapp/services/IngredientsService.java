package com.aaluni.spring5recipeapp.services;

import com.aaluni.spring5recipeapp.commands.IngredientCommand;

public interface IngredientsService {
	IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
}