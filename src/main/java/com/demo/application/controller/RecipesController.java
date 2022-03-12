package com.demo.application.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.application.model.Recipe;
import com.demo.application.services.RecipesService;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

@RestController
@RequestMapping("/recipes")
public class RecipesController {
	
	private RecipesService service;
	
	public RecipesController(RecipesService service) throws StreamReadException, DatabindException, IOException
	{
		this.service = service;
	}
	
	@GetMapping
	public ResponseEntity<?> getAllRecipeNames()  
	{	
		try
		{
			List<String> recipeNames = service.findAll();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("recipeNames", recipeNames);
			
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/details/{recipeName}")
	public ResponseEntity<?> getDetails(@PathVariable("recipeName") String recipeName)
	{	
		try
		{
			Recipe recipe = service.findByName(recipeName);
			
			if(recipe == null)
				return new ResponseEntity<>(Collections.emptyMap(), HttpStatus.OK);
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("ingredients", recipe.getIngredients());
			map.put("numSteps", recipe.getInstructions().size());
			
			Map<String, Object> details = new HashMap<String, Object>();
			details.put("details", map);
			
			return new ResponseEntity<>(details, HttpStatus.OK);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping
	public ResponseEntity<?> createRecipe(@RequestBody Recipe recipe)
	{
		try
		{			
			service.createRecipe(recipe);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		catch(Exception e)
		{
			Map<String, String> errors = new HashMap<String, String>();
			errors.put("error", e.getMessage());
			
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping
	public ResponseEntity<?> editRecipe(@RequestBody Recipe recipe)
	{
		try
		{
			service.editRecipe(recipe);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch(Exception e)
		{
			Map<String, String> errors = new HashMap<String, String>();
			errors.put("error", e.getMessage());
			
			return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
		}
	}
}