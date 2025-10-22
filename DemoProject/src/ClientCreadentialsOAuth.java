import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import files.reusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import pojo.GetCourse;
import pojo.WebAutomation;

public class ClientCreadentialsOAuth {

	String accessToken;

	String[] Expected = { "Selenium Webdriver Java", "Cypress", "Protractor" };

	List<String> expectedList = Arrays.asList(Expected);

	@BeforeTest
	public void setup() throws IOException {
		RestAssured.baseURI = "https://rahulshettyacademy.com";

	}

	@Test(priority = 1)
	public void FetchingToken() {
		String TokenResponse = given().log().all()
				.formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W").formParam("grant_type", "client_credentials")
				.formParam("scope", "trust").when().post("/oauthapi/oauth2/resourceOwner/token").then().log().all()
				.assertThat().statusCode(200).extract().response().asString();

		JsonPath jp1 = reusableMethods.rawToJson(TokenResponse);
		this.accessToken = jp1.getString("access_token");
		System.out.println(accessToken);
	}

	@Test(priority = 2)
	public void GetCourseDetails() {
		GetCourse gc = given().log().all().queryParam("access_token", accessToken).when()
				.get("/oauthapi/getCourseDetails").as(GetCourse.class);
//		.then().log().all().assertThat().statusCode(401);

		System.out.println(gc.getInstructor());
		System.out.println(gc.getLinkedIn());
//		System.out.println(gc.getCourses().getApi().size());
//		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle().equals("SoapUI Webservices testing"));

		for (int i = 0; i < gc.getCourses().getApi().size(); i++) {
			if (gc.getCourses().getApi().get(i).getCourseTitle().equals("SoapUI Webservices testing")) {
				System.out.println(gc.getCourses().getApi().get(i).getPrice());
			}
		}

//		System.out.println(gc.getCourses().getApi().get(0).getCourseTitle());

//		Get the details of the course names of the webAutomation

		List<WebAutomation> webAutomation = gc.getCourses().getWebAutomation();
//		System.out.println(webAutomation.get(0).getCourseTitle());
//		System.out.println(webAutomation.size());
//		System.out.println("Below are the course details of the courses belongs to webAutomation : ");
		ArrayList<String> actualCourse = new ArrayList<String>();
		for (int i = 0; i < webAutomation.size(); i++) {
			actualCourse.add(webAutomation.get(i).getCourseTitle());
		}
		
		Assert.assertTrue(actualCourse.equals(expectedList));
		

	}

}
