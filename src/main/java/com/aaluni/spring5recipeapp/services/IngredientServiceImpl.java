package com.aaluni.spring5recipeapp.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.aaluni.spring5recipeapp.commands.IngredientCommand;
import com.aaluni.spring5recipeapp.converters.IngredientToIngredientCommand;
import com.aaluni.spring5recipeapp.domain.Recipe;
import com.aaluni.spring5recipeapp.repositories.RecipeRepository;

@Service
public class IngredientServiceImpl implements IngredientsService {
	private final RecipeRepository recipeRepository;
	private final IngredientToIngredientCommand ingredientCommand;
	
	public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
		this.ingredientCommand = ingredientToIngredientCommand;
	}

	
	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
		Optional<Recipe> recipe = recipeRepository.findById(recipeId);
		if (!recipe.isPresent()) {
			//TODO implement error handling
		}
		
		Optional<IngredientCommand> ingredientCommandOptional = recipe.get().getIngredients().stream()
			.filter(ingredient -> ingredientId.equals(ingredient.getId()))
			.map(ingr -> ingredientCommand.convert(ingr))
			.findFirst();
		
		
		if (!ingredientCommandOptional.isPresent()) {
			//TODO implement error handling
		}
		ingredientCommandOptional.get().setRecipeId(recipeId);
		return ingredientCommandOptional.get();
	}

}
