package com.aaluni.spring5recipeapp.services;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aaluni.spring5recipeapp.domain.Recipe;
import com.aaluni.spring5recipeapp.repositories.RecipeRepository;

/**
 * Created by jt on 7/3/17.
 */
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
	private final RecipeRepository recipeRepository;
    public ImageServiceImpl(RecipeRepository recipeRepository) {
		super();
		this.recipeRepository = recipeRepository;
	}
	
	@Override
    public void saveImageFile(Long recipeId, MultipartFile file) {
		log.debug("File received");
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		
		if (!recipeOptional.isPresent()) {
			log.debug("Recipe not found for id:" + recipeId);
			return;
		}
		Recipe recipe = recipeOptional.get();
		try {
			byte[] filecontect = file.getBytes();
			Byte[] content = new Byte[filecontect.length];
			int i = 0;
			for (byte b : filecontect)
				content[i++] = b;
			recipe.setImage(content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		recipeRepository.save(recipe);
        log.debug("Image File Saved");
    }
}
