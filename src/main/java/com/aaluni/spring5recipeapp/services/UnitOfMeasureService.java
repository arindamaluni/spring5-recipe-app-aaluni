package com.aaluni.spring5recipeapp.services;

import java.util.Set;

import com.aaluni.spring5recipeapp.commands.UnitOfMeasureCommand;

public interface UnitOfMeasureService {
	Set<UnitOfMeasureCommand> listAllUoms();
}
