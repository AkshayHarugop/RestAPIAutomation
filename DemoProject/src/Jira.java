import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.IOException;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import files.payload;
import files.reusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class Jira {

	String createBugID;
	String attachmentId;

	@BeforeTest
	public void setup() throws IOException {
		RestAssured.baseURI = "https://akshayharugop.atlassian.net";

	}

	@Test(priority = 1)
	public void createBug() {
		String createBugResponse = given().log().all().header("Accept", "application/json")
				.header("Content-Type", "application/json")
				.header("Authorization",
						"Basic YWtzaGF5aGFydWdvcEBnbWFpbC5jb206QVRBVFQzeEZmR0YwS093V3Jlc3g4bmRaSGxGYnNqQkZpczhEUUJDZVlKbk1aR1E0eXJlYXZ2VEZJUHUxRC1jOHpZLUFBek84dm5iNG5XWXhqdTF3dUlmRkxIdU9oU1M4WUJCWTlQWWxTMHk5ODR4NlFzTzhIN0kzZnp5WnNVWlF6VkhDeDlURHoyNUFaQllSOFBVel8yZ2owcFpZS083TWNiRGNCbHg5cktsZzF2enlPakN1dXdzPTJCODUyQUFD")
				.body(payload.jiraCreateDefect()).when().post("/rest/api/2/issue").then().log().all().assertThat()
				.statusCode(201).extract().response().asString();
		JsonPath jp1 = reusableMethods.rawToJson(createBugResponse);
		createBugID = jp1.getString("id");
	}

	@Test(priority = 2)
	public void addAttachementToBug() throws IOException {
		String addAttachementResponse = given().log().all().header("X-Atlassian-Token", "no-check").header(
				"Authorization",
				"Basic YWtzaGF5aGFydWdvcEBnbWFpbC5jb206QVRBVFQzeEZmR0YwS093V3Jlc3g4bmRaSGxGYnNqQkZpczhEUUJDZVlKbk1aR1E0eXJlYXZ2VEZJUHUxRC1jOHpZLUFBek84dm5iNG5XWXhqdTF3dUlmRkxIdU9oU1M4WUJCWTlQWWxTMHk5ODR4NlFzTzhIN0kzZnp5WnNVWlF6VkhDeDlURHoyNUFaQllSOFBVel8yZ2owcFpZS083TWNiRGNCbHg5cktsZzF2enlPakN1dXdzPTJCODUyQUFD")
				.multiPart("file", new File("C:\\Users\\aha5\\Downloads\\Gemini_Generated_Image_furm4pfurm4pfurm.png"))
				.log().all().when().post("/rest/api/2/issue/" + createBugID + "/attachments").then().log().all()
				.assertThat().statusCode(200).extract().response().asString();
		JsonPath jp2 = reusableMethods.rawToJson(addAttachementResponse);
		attachmentId = jp2.getString("id");
	}

	@Test(priority = 3)
	public void removeAddedAttachmentToBug() {
		System.out.println(attachmentId);
		given().log().all().header("Accept", "application/json").header("Content-Type", "application/json").header(
				"Authorization",
				"Basic YWtzaGF5aGFydWdvcEBnbWFpbC5jb206QVRBVFQzeEZmR0YwS093V3Jlc3g4bmRaSGxGYnNqQkZpczhEUUJDZVlKbk1aR1E0eXJlYXZ2VEZJUHUxRC1jOHpZLUFBek84dm5iNG5XWXhqdTF3dUlmRkxIdU9oU1M4WUJCWTlQWWxTMHk5ODR4NlFzTzhIN0kzZnp5WnNVWlF6VkhDeDlURHoyNUFaQllSOFBVel8yZ2owcFpZS083TWNiRGNCbHg5cktsZzF2enlPakN1dXdzPTJCODUyQUFD")
				.when().delete("/rest/api/3/attachment/" + attachmentId).then().log().all().assertThat()
				.statusCode(204);
	}

//	@Test(priority = 4)
//	public void deleteBug() {
//		given().log().all().header("Accept", "application/json").header("Content-Type", "application/json").header(
//				"Authorization",
//				"Basic YWtzaGF5aGFydWdvcEBnbWFpbC5jb206QVRBVFQzeEZmR0YwS093V3Jlc3g4bmRaSGxGYnNqQkZpczhEUUJDZVlKbk1aR1E0eXJlYXZ2VEZJUHUxRC1jOHpZLUFBek84dm5iNG5XWXhqdTF3dUlmRkxIdU9oU1M4WUJCWTlQWWxTMHk5ODR4NlFzTzhIN0kzZnp5WnNVWlF6VkhDeDlURHoyNUFaQllSOFBVel8yZ2owcFpZS083TWNiRGNCbHg5cktsZzF2enlPakN1dXdzPTJCODUyQUFD")
//				.when().delete("rest/api/2/issue/" + createBugID + "").then().log().all().assertThat().statusCode(204);
//
//	}

}
