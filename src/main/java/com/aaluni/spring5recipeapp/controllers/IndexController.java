package com.aaluni.spring5recipeapp.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aaluni.spring5recipeapp.domain.Category;
import com.aaluni.spring5recipeapp.domain.UnitOfMeasure;
import com.aaluni.spring5recipeapp.repositories.CategoryRepository;
import com.aaluni.spring5recipeapp.repositories.UnitOfMeasureRepository;
import com.aaluni.spring5recipeapp.services.RecipeService;

@Controller
public class IndexController {
	@Autowired
	RecipeService recipeService;
	
	@RequestMapping({"", "/", "/index"})
	public String getIndexPage(Model model) {
		
		model.addAttribute("recipes", recipeService.getRecipes());
		return "index";
		
	}

}
