import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadingJsonFileFromOutside {

	public static void main(String[] args) throws IOException {
		/*Validate if add place API is working as expected 
        will use methods like given, when and there
        given - all input details (query parameter, header) , Body
        when -  submit the API CRUD action (resource and http method goes under when)
        then - Validate the response*/
		
		RestAssured.baseURI= "https://rahulshettyacademy.com";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(new String(Files.readAllBytes(Paths.get("E:\\Akshay_Git\\RestAPIAutomation\\AddPlace.json"))))
		.when().post("/maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200);

//		Content of File to String >>> 
//		1. Content of File can be converted into Byte >>> Files.readAllBytes(Paths.get("Actual file path with extension"))
//		2. Byte data to String >>>defining as a object >> converts from byte to String! 

	}

}
