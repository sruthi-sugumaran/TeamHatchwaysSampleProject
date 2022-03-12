package com.example.demo;

import org.junit.Assert;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.demo.application.SpringBootDemoAppApplication;

@SpringBootTest(classes = { SpringBootDemoAppApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles("test")
class SpringBootDemoAppControllerJUnitTests {

	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	void testGetAllRecipeNames() throws Exception {
		
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/recipes", String.class);
		
		Assert.assertEquals(response.getStatusCodeValue(), 200);
		Assert.assertEquals("{\"recipeNames\":[\"scrambledEggs\",\"garlicPasta\",\"chai\"]}", response.getBody());
	}
}
