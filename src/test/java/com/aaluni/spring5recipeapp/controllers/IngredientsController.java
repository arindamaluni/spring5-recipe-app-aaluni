package com.aaluni.spring5recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aaluni.spring5recipeapp.services.IngredientsService;
import com.aaluni.spring5recipeapp.services.RecipeService;

@Controller
public class IngredientsController {

	private final RecipeService recipeService;
	private final IngredientsService ingredientService;
	public IngredientsController(RecipeService recipeService, IngredientsService ingredientService) {
		super();
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
	}
	
	@RequestMapping(path = "/recipe/{id}/ingredients")
	public String showIngredients(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findRecipeCommandById(Long.valueOf(id)));
		return "/recipe/ingredient/list";
	}
	
	@RequestMapping("recipe/{recipeId}/ingredient/{id}/show")
	public String viewIngredient(@PathVariable String recipeId, 
			@PathVariable String id, Model model) {
		model.addAttribute("ingredient", 
				ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
        return "recipe/ingredient/show";
	}
	
	

}
