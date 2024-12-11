package com.lcvl.microservices.product_service;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;

import com.lcvl.microservices.productservice.ProductServiceApplication;

import io.restassured.RestAssured;


@SpringBootTest(classes = ProductServiceApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {
	
	@ServiceConnection
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");
	
	@LocalServerPort
	private Integer port;
	
	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}
	
	static {
		
		mongoDBContainer.start();
	}

	@Test
	void shouldCreateProduct() {
		
		String requestBody = """ 

				{
				    "name": "product 1 test",
				    "description": "description 1 test",
				    "price" : 1500
				
				}
				
				""";
		
		RestAssured.given()
		.contentType("application/json")
		.body(requestBody)
		.when()
		.post("/api/product")
		.then()
		.statusCode(201)
		.body("id", Matchers.notNullValue())
		.body("name", Matchers.equalTo("product 1 test"))
		.body("description", Matchers.equalTo("description 1 test"))
		.body("price", Matchers.equalTo(1500));
		

		
	}	

}
