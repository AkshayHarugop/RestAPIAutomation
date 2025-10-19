import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import files.reusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class ClientCreadentialsOAuth {

	String accessToken;
	
	@BeforeTest
	public void setup() throws IOException {
		RestAssured.baseURI = "https://rahulshettyacademy.com";

	}

	@Test(priority = 1)
	public void FetchingToken() {
		String TokenResponse = given().log().all().formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.formParam("grant_type", "client_credentials")
		.formParam("scope", "trust")
		.when().post("/oauthapi/oauth2/resourceOwner/token")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath jp1 = reusableMethods.rawToJson(TokenResponse);
		accessToken = jp1.getString("access_token");
		System.out.println(accessToken);
	}
	
	@Test(priority = 2)
	public void GetCourseDetails() {
		given().log().all().param("access_token", accessToken)
		.when().get("/oauthapi/getCourseDetails")
		.then().log().all().assertThat().statusCode(401);
	}
	
}
