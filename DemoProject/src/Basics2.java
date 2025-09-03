import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class Basics2 {

	public static void main(String[] args) {
		/*Validate if add place API is working as expected 
        will use methods like given, when and there
        given - all input details (query parameter, header) , Body
        when -  submit the API CRUD action (resource and http method goes under when)
        then - Validate the response*/
		
		RestAssured.baseURI= "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json").body("{\r\n"
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
		.then().log().all().assertThat().statusCode(200)
		.body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		
//		equalsTo() method belongs to static package so it won't import the code automatically so we need to add the import manually and update the package..
//		import static org.hamcrest.Matchers.*;
		System.out.println("------------------------------------");
		System.out.println(response);
		System.out.println("------------------------------------");
		
		JsonPath jp = new JsonPath(response);
		String PlaceId = jp.getString("place_id");
		String newAdd = "70 winter walk, USA";
		
		System.out.println(PlaceId);
		
		/*Validate if Get place API is working as expected 
        will use methods like given, when and there
        given - all input details (query parameter, header) , Body
        when -  submit the API CRUD action (resource and http method goes under when)
        then - Validate the response*/
		given().log().all().queryParam("key", "qaclick123").queryParam("place_id", PlaceId)
		.when().get("/maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200)
		.body("address", equalTo("29, side layout, cohen 09")).body("accuracy", equalTo("50")).header("Server", "Apache/2.4.52 (Ubuntu)");
		
		System.out.println("------------------------------------");
		
		/*Validate if Put place (Update) API is working as expected 
        will use methods like given, when and there
        given - all input details (query parameter, header) , Body
        when -  submit the API CRUD action (resource and http method goes under when)
        then - Validate the response*/
		given().log().all().queryParam("key", "qaclick123").queryParam("place_id", PlaceId).body("{\r\n"
				+ "\"place_id\":\""+PlaceId+"\",\r\n"
				+ "\"address\":\""+newAdd+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().put("/maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200)
		.body("msg", equalTo("Address successfully updated"));
		
		System.out.println("------------------------------------");
		
		/*Validate if Get place API is working as expected 
        will use methods like given, when and there
        given - all input details (query parameter, header) , Body
        when -  submit the API CRUD action (resource and http method goes under when)
        then - Validate the response*/
		given().log().all().queryParam("key", "qaclick123").queryParam("place_id", PlaceId)
		.when().get("/maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200)
		.body("address", equalTo("70 winter walk, USA")).body("accuracy", equalTo("50")).header("Server", "Apache/2.4.52 (Ubuntu)");
		
		System.out.println("------------------------------------");
		
		/*Validate if delete place API is working as expected 
        will use methods like given, when and there
        given - all input details (query parameter, header) , Body
        when -  submit the API CRUD action (resource and http method goes under when)
        then - Validate the response*/
		
		given().log().all().queryParam("key", "qaclick123").body("{\r\n"
				+ "    \"place_id\":\""+PlaceId+"\"\r\n"
				+ "}")
		.when().delete("/maps/api/place/delete/json")
		.then().log().all().assertThat().statusCode(200)
		.body("status", equalTo("OK")).header("Server", "Apache/2.4.52 (Ubuntu)");
		
		System.out.println("------------------------------------");
		
		/*Validate if Get place API is working as expected 
        will use methods like given, when and there
        given - all input details (query parameter, header) , Body
        when -  submit the API CRUD action (resource and http method goes under when)
        then - Validate the response*/
		given().log().all().queryParam("key", "qaclick123").queryParam("place_id", PlaceId)
		.when().get("/maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(404)
		.body("msg", equalTo("Get operation failed, looks like place_id  doesn't exists")).header("Server", "Apache/2.4.52 (Ubuntu)");
		
		System.out.println("------------------------------------");
		
	}

}
