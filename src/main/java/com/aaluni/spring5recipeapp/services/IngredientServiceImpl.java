package com.aaluni.spring5recipeapp.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.aaluni.spring5recipeapp.commands.IngredientCommand;
import com.aaluni.spring5recipeapp.converters.IngredientCommandToIngredient;
import com.aaluni.spring5recipeapp.converters.IngredientToIngredientCommand;
import com.aaluni.spring5recipeapp.domain.Ingredient;
import com.aaluni.spring5recipeapp.domain.Recipe;
import com.aaluni.spring5recipeapp.repositories.RecipeRepository;
import com.aaluni.spring5recipeapp.repositories.UnitOfMeasureRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientsService {
	private final RecipeRepository recipeRepository;
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	private final UnitOfMeasureRepository uomRepository;
	
	public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, 
			IngredientCommandToIngredient ingredientCommandToIngredient,
			RecipeRepository recipeRepository, UnitOfMeasureRepository uomRepository) {
		this.recipeRepository = recipeRepository;
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
		this.uomRepository = uomRepository;
	}

	
	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
		Optional<Recipe> recipe = recipeRepository.findById(recipeId);
		if (!recipe.isPresent()) {
			//TODO implement error handling
		}
		
		Optional<IngredientCommand> ingredientCommandOptional = recipe.get().getIngredients().stream()
			.filter(ingredient -> ingredientId.equals(ingredient.getId()))
			.map(ingr -> ingredientToIngredientCommand.convert(ingr))
			.findFirst();
		
		
		if (!ingredientCommandOptional.isPresent()) {
			//TODO implement error handling
		}
		ingredientCommandOptional.get().setRecipeId(recipeId);
		return ingredientCommandOptional.get();
	}

	@Transactional
	@Override
	public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
		
		if (ingredientCommand == null) {
			log.debug("Null Entity cannot be saved");
			throw new RuntimeException("Null entity 'Ingredient'");
		}
		Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());
		if (!recipeOptional.isPresent()) {
			//TODO implement error handling
			log.error("Recipe not found for id: " + ingredientCommand.getRecipeId());
            return new IngredientCommand();
		}
		Recipe savedRecipe = recipeOptional.get();
		Optional<Ingredient> ingrToUpdateOptional = savedRecipe.getIngredients().stream()
			.filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
			.findFirst();
		
		if (ingrToUpdateOptional.isPresent()) {
			//update the ingredient
			Ingredient ingredientFound = ingrToUpdateOptional.get();
			ingredientFound.setDescription(ingredientCommand.getDescription());
            ingredientFound.setAmount(ingredientCommand.getAmount());
            ingredientFound.setUom(uomRepository
                    .findById(ingredientCommand.getUom().getId())
                    .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //TODO address this
			
		} else {
			// Save new ingredient
			Ingredient newIngredient = ingredientCommandToIngredient.convert(ingredientCommand);
			newIngredient.setRecipe(savedRecipe);
			savedRecipe.addIngredient(newIngredient);
		}
		
		Recipe recipe = recipeRepository.save(savedRecipe);
		
		Optional<Ingredient> savedIngredientOptional = recipe.getIngredients().stream()
                .filter(recipeIngredients -> recipeIngredients.getId().equals(ingredientCommand.getId()))
                .findFirst();
                
		//If new Ingredient, search by description and other fields
		if (!savedIngredientOptional.isPresent()) {
            savedIngredientOptional = recipe.getIngredients().stream()
                    .filter(recipeIngredient -> recipeIngredient.getDescription().equals(ingredientCommand.getDescription()))
                    .filter(recipeIngredient -> recipeIngredient.getAmount().equals(ingredientCommand.getAmount()))
                    .filter(recipeIngredient -> recipeIngredient.getUom().getId().equals(ingredientCommand.getUom().getId()))
                    .findFirst();
		}
                
		
		return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
	}

}
