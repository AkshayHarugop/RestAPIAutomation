import static io.restassured.RestAssured.given;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.RestAssured;

public class Jira {
	
	@BeforeTest
	public void setup() {
		RestAssured.baseURI= "https://akshayharugop.atlassian.net";
	}
	
	@Test
	public void createBug() {
		String createBugResponse = given().log().all().header("Accept","application/json").header("Content-Type","application/json").header("Authorization","Basic YWtzaGF5aGFydWdvcEBnbWFpbC5jb206QVRBVFQzeEZmR0YwS093V3Jlc3g4bmRaSGxGYnNqQkZpczhEUUJDZVlKbk1aR1E0eXJlYXZ2VEZJUHUxRC1jOHpZLUFBek84dm5iNG5XWXhqdTF3dUlmRkxIdU9oU1M4WUJCWTlQWWxTMHk5ODR4NlFzTzhIN0kzZnp5WnNVWlF6VkhDeDlURHoyNUFaQllSOFBVel8yZ2owcFpZS083TWNiRGNCbHg5cktsZzF2enlPakN1dXdzPTJCODUyQUFD")
		.body(payload.jiraCreateDefect())
		.when().post("/rest/api/2/issue")
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
	}
	
	
}
