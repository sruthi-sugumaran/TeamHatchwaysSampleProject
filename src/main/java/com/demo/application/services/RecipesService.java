package com.demo.application.services;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.demo.application.model.Recipe;
import com.demo.application.model.Recipes;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RecipesService {

	private static Recipes recipes = new Recipes();

	public RecipesService() throws StreamReadException, DatabindException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		RecipesService.recipes = mapper.readValue(Paths.get("src/main/resources/data.json").toFile(), Recipes.class);
	}

	public List<String> findAll() {
		if (recipes == null)
			return null;

		List<String> recipeNames = new ArrayList<>();

		for (Recipe recipe : recipes.getRecipes()) {
			recipeNames.add(recipe.getName());
		}

		return recipeNames;
	}

	public Recipe findByName(String recipeName) {
		for (Recipe recipe : recipes.getRecipes()) {
			if (recipe.getName().equals(recipeName)) {
				return recipe;
			}
		}

		return null;
	}

	public void createRecipe(Recipe recipe) throws Exception {
		for (Recipe item : recipes.getRecipes()) {
			if (item.getName().equals(recipe.getName())) {
				throw new Exception("Recipe already exists");
			}
		}

		recipes.getRecipes().add(recipe);
	}

	public void editRecipe(Recipe recipe) throws Exception {
		for (Recipe item : recipes.getRecipes()) {
			if (item.getName().equals(recipe.getName())) {
				recipes.getRecipes().remove(item);
				recipes.getRecipes().add(recipe);
				return;
			}
		}

		throw new Exception("Recipe does not exist");
	}
}
