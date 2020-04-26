package com.aaluni.spring5recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aaluni.spring5recipeapp.services.RecipeService;

@Controller
public class IngredientsController {

	RecipeService recipeService;
	public IngredientsController(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}
	
	@RequestMapping(path = "/recipe/{id}/ingredients")
	public String showIngredients(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findRecipeCommandById(Long.valueOf(id)));
		return "/recipe/ingredient/list";
	}
	

}
