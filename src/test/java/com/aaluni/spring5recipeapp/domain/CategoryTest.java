package com.aaluni.spring5recipeapp.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CategoryTest {
	Category category;
	@Before
	public void setUp() throws Exception {
		category = new Category();
	}	

	@Test
	public void testGetId() {
		Long id = 4L;	
		category.setId(id);
		assertEquals(id, category.getId());
		
	}

}
