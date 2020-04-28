package com.aaluni.spring5recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aaluni.spring5recipeapp.commands.RecipeCommand;
import com.aaluni.spring5recipeapp.services.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
        return "recipe/show";
    }
    
    @GetMapping(path = "/recipe/new")
    public String newRecipe(Model model) {
    	
    	model.addAttribute("recipe", new RecipeCommand());
    	return "recipe/recipeform";
    }
    @PostMapping(path = "/recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand recipe) {
    	RecipeCommand savedRecipe = recipeService.saveRecipeCommand(recipe);
    	return "redirect:/recipe/" + savedRecipe.getId() + "/show";
    }
    
    @GetMapping("/recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findRecipeCommandById(Long.valueOf(id)));
        return "recipe/recipeform";
    }
    
    @GetMapping("/recipe/{id}/delete")
    public String deleteRecipe(@PathVariable String id, Model model){
    	log.debug("Deleting Recipe: {}", id);
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }
    
    
}
