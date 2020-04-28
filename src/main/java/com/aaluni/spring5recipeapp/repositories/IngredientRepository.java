package com.aaluni.spring5recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;

import com.aaluni.spring5recipeapp.domain.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, Long>{

}
