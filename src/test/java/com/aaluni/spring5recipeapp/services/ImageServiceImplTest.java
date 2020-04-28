package com.aaluni.spring5recipeapp.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.aaluni.spring5recipeapp.domain.Recipe;
import com.aaluni.spring5recipeapp.repositories.RecipeRepository;

class ImageServiceImplTest {
	@Mock
	RecipeRepository recipeRepo;
	
	ImageService imageService;
	String STRING_CONTENT = "Some String contect";
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		imageService = new ImageServiceImpl(recipeRepo);
	}

	@Test
	void testSaveImageFile() {
		byte[] fileContent = STRING_CONTENT.getBytes();
		MultipartFile multipartFile = new MockMultipartFile("imagefile", "filename.txt", 
				"text/plain", fileContent);	
		
		Byte[] content = new Byte[fileContent.length];
		int i = 0;
		for (byte b : fileContent)
			content[i++] = b;
		
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		
		when(recipeRepo.findById(anyLong())).thenReturn(Optional.of(recipe));
		ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);
		
		imageService.saveImageFile(recipe.getId(), multipartFile);
		verify(recipeRepo, times(1)).save(argumentCaptor.capture());
		Recipe savedRecipe = argumentCaptor.getValue();
		System.out.println(content.equals(recipe.getImage()));
		assert(Arrays.equals(content, recipe.getImage()));
				
	}

}
