package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.demo.application.SpringBootDemoAppApplication;
import com.demo.application.model.Recipe;
import com.demo.application.services.RecipesService;

@SpringBootTest(classes = { SpringBootDemoAppApplication.class })
@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles("test")
class SpringBootDemoAppServiceJUnitTests {

	@Autowired
	private RecipesService service;

	@Test
	@Order(1)
	void testFindAll() {

		List<String> recipeNames = service.findAll();
		Assert.assertEquals(3, recipeNames.size());

	}

	@Test
	@Order(2)
	void testFindByName() {

		Recipe recipe = service.findByName("garlicPasta");

		List<String> ingredients = List.of("500mL water", "100g spaghetti", "25mL olive oil", "4 cloves garlic",
				"Salt");

		List<String> instructions = List.of("Heat garlic in olive oil", "Boil water in pot",
				"Add pasta to boiling water", "Remove pasta from water and mix with garlic olive oil",
				"Salt to taste and enjoy");

		Assert.assertNotNull(recipe);
		Assert.assertEquals(recipe.getIngredients(), ingredients);
		Assert.assertEquals(recipe.getInstructions(),instructions);

		recipe = service.findByName("pizza");
		Assert.assertNull(recipe);
	}

	@Test
	@Order(3)
	void testCreateRecipe() throws Exception {

		Recipe existingRecipe = service.findByName("scrambledEggs");
		String expectedErrorMessage = "Recipe already exists";
				
		Exception exception = assertThrows(Exception.class, () -> {
			service.createRecipe(existingRecipe);
		});
		
		Assert.assertEquals(expectedErrorMessage, exception.getMessage());
		
		Recipe newRecipe = new Recipe();
		
		newRecipe.setName("butteredBagel");
		newRecipe.setIngredients(List.of("1 bagel", 
			"butter"));
		newRecipe.setInstructions(List.of("cut the bagel", 
		"spread butter on bagel"));
		

		Assert.assertFalse(service.findAll().contains(newRecipe.getName()));
		service.createRecipe(newRecipe);
		Assert.assertTrue(service.findAll().contains(newRecipe.getName()));
	}

	@Test
	@Order(4)
	void editRecipe() throws Exception {
		
		Recipe newRecipe = new Recipe();
		String expectedErrorMessage = "Recipe does not exist";
		
		newRecipe.setName("Pizza");
		newRecipe.setIngredients(List.of("Tomatoes","Cheese"));
		newRecipe.setInstructions(List.of("Make pizza dough","Bake it"));
		
		Exception exception = assertThrows(Exception.class, () -> {
			service.editRecipe(newRecipe);
		});
		
		Assert.assertEquals(expectedErrorMessage, exception.getMessage());
		
		Recipe recipe = service.findByName("butteredBagel");
		List<String> oldIngredients = List.of("1 bagel", 
				"butter");
		
		Assert.assertEquals(recipe.getIngredients(), oldIngredients);
		
		
		List<String> newIngredients = List.of("1 bagel", 
				"2 tbsp butter");
		
		recipe.setIngredients(newIngredients);
				
		service.editRecipe(recipe);
		
		recipe = service.findByName("butteredBagel");
		
		Assert.assertEquals(recipe.getIngredients(), newIngredients);
		
	}
}
