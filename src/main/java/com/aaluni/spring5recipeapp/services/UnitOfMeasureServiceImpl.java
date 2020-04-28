package com.aaluni.spring5recipeapp.services;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.aaluni.spring5recipeapp.commands.UnitOfMeasureCommand;
import com.aaluni.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.aaluni.spring5recipeapp.repositories.UnitOfMeasureRepository;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {
	
	private final UnitOfMeasureRepository uomRepo;
	private final UnitOfMeasureToUnitOfMeasureCommand uomToUomCommandService;
	
	public UnitOfMeasureServiceImpl(UnitOfMeasureRepository uomRepo,
			UnitOfMeasureToUnitOfMeasureCommand uomToUomCommandService) {
		this.uomRepo = uomRepo;
		this.uomToUomCommandService = uomToUomCommandService;
	}	

	@Override
	public Set<UnitOfMeasureCommand> listAllUoms() {
		return StreamSupport.stream(uomRepo.findAll().spliterator(), false)
			.map(uomToUomCommandService::convert)
			.collect(Collectors.toSet());
	}

}
