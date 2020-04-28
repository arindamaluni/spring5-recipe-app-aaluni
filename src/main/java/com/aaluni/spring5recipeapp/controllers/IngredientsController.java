package com.aaluni.spring5recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aaluni.spring5recipeapp.commands.IngredientCommand;
import com.aaluni.spring5recipeapp.commands.RecipeCommand;
import com.aaluni.spring5recipeapp.commands.UnitOfMeasureCommand;
import com.aaluni.spring5recipeapp.services.IngredientsService;
import com.aaluni.spring5recipeapp.services.RecipeService;
import com.aaluni.spring5recipeapp.services.UnitOfMeasureService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IngredientsController {

	private final RecipeService recipeService;
	private final IngredientsService ingredientService;
	private final UnitOfMeasureService uomService;
	public IngredientsController(RecipeService recipeService, IngredientsService ingredientService, UnitOfMeasureService uomService) {
		super();
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
		this.uomService = uomService;
	}
	
	@GetMapping
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

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable String recipeId, Model model){

        //make sure we have a good id value
        RecipeCommand recipeCommand = recipeService.findRecipeCommandById(Long.valueOf(recipeId));
        //todo raise exception if null

        //need to return back parent id for hidden form property
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(Long.valueOf(recipeId));
        model.addAttribute("ingredient", ingredientCommand);

        //init uom
        ingredientCommand.setUom(new UnitOfMeasureCommand());

        model.addAttribute("uomList",  uomService.listAllUoms());

        return "recipe/ingredient/ingredientform";
    }
    
	@RequestMapping("recipe/{recipeId}/ingredient/{id}/update")
	public String updateIngredient(@PathVariable String recipeId, 
			@PathVariable String id, Model model) {
		model.addAttribute("ingredient", 
				ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
		model.addAttribute("uomList", uomService.listAllUoms());
        return "recipe/ingredient/ingredientform";
	}
	
   @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command){
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        log.debug("saved receipe id:" + savedCommand.getRecipeId());
        log.debug("saved ingredient id:" + savedCommand.getId());

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }
   
   @GetMapping
   @RequestMapping("recipe/{recipeId}/ingredient/{id}/delete")
   public String deleteIngredient(@PathVariable String recipeId,
                                  @PathVariable String id){

       log.debug("deleting ingredient id:" + id);
       ingredientService.deleteById(Long.valueOf(recipeId), Long.valueOf(id));

       return "redirect:/recipe/" + recipeId + "/ingredients";
   }
	
	

}
