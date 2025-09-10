import static io.restassured.RestAssured.given;

import java.util.UUID;

import io.restassured.RestAssured;

public class Basics {

	public static void main(String[] args) {
		
		/*Validate if add place API is working as expected 
        will use methods like given, when and there
        given - all input details (query parameter, header) , Body
        when -  submit the API CRUD action (resource and http method goes under when)
        then - Validate the response*/
		
		RestAssured.baseURI= "https://rahulshettyacademy.com";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json").body("{\r\n"
				+ "  \"location\": {\r\n"
				+ "    \"lat\": -38.383494,\r\n"
				+ "    \"lng\": 33.427362\r\n"
				+ "  },\r\n"
				+ "  \"accuracy\": 50,\r\n"
				+ "  \"name\": \"Frontline house\",\r\n"
				+ "  \"phone_number\": \"(+91) 983 893 3937\",\r\n"
				+ "  \"address\": \"29, side layout, cohen 09\",\r\n"
				+ "  \"types\": [\r\n"
				+ "    \"shoe park\",\r\n"
				+ "    \"shop\"\r\n"
				+ "  ],\r\n"
				+ "  \"website\": \"http://google.com\",\r\n"
				+ "  \"language\": \"French-IN\"\r\n"
				+ "}")
		.when().post("/maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200);
//		.log().all() we can utilize it in everywhere that is given(), when() and then() methods ...
		
		
		System.out.println(UUID.randomUUID().toString().substring(1, 5));
	}

}
