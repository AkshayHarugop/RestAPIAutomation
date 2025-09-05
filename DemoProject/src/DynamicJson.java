import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.payload;
import files.reusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {

	@Test(dataProvider = "BooksData")
	public void addBook(String isbn, String aisle) {
		RestAssured.baseURI = "http://216.10.245.166";
		String addBookResponse = given().log().all().header("Content-Type", "application/json").body(payload.addBookApiJson(isbn, aisle))
		.when().post("/Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath jp1 = reusableMethods.rawToJson(addBookResponse);
		String msg = jp1.getString("Msg");
		String id = jp1.getString("ID");
		Assert.assertEquals("successfully added", msg);

	}

	@DataProvider(name = "BooksData")
	public Object[][] getData() {
		return new Object[][] {{"abc", "9111"},{"def", "9112"},{"ghi", "9113"},{"jkl", "9114"},{"mno", "9115"}};
	}
	
}
