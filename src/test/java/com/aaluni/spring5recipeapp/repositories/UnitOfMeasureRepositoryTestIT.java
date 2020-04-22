package com.aaluni.spring5recipeapp.repositories;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.aaluni.spring5recipeapp.domain.UnitOfMeasure;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryTestIT {
	@Autowired
	UnitOfMeasureRepository uomRepo;
	@Before
	public void setUp() throws Exception {
	}

	@Test
	@DirtiesContext //This is to reload the spring context if the method changes the environment
	public void testFindByDescription() {
		Optional<UnitOfMeasure> uom = uomRepo.findByDescription("Teaspoon");
		assertEquals("Teaspoon", uom.get().getDescription());
	}
	
	@Test
	public void testFindByDescriptionCup() {
		Optional<UnitOfMeasure> uom = uomRepo.findByDescription("Cup");
		assertEquals("Cup", uom.get().getDescription());
	}

}
