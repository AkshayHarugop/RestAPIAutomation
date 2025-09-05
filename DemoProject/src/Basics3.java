import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class Basics3 {
	
	/*    will use methods like given, when and there
    given - all input details (query parameter, header) , Body
    when -  submit the API CRUD action (resource and http method goes under when)
    then - Validate the response*/

	public static void main(String[] args) {
		RestAssured.baseURI= "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json").body(payload.AddPlace())
		.when().post("/maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200)
		.body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)")
		.extract().response().asString();
		
		JsonPath jp = new JsonPath(response);
		String PlaceID = jp.getString("place_id");
		
		
		System.out.println("Done!!");

	}

}
