import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.payload;
import files.reusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {
	String id;
	
	@BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://216.10.245.166"; // set once for all tests
    }

	@Test(dataProvider = "BooksData")
	public void addBook(String isbn, String aisle) {
		String addBookResponse = given().log().all().header("Content-Type", "application/json").body(payload.addBookApiJson(isbn, aisle, "Test@123"))
		.when().post("/Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath jp1 = reusableMethods.rawToJson(addBookResponse);
		String msg = jp1.getString("Msg");
		String id = jp1.getString("ID");
		Assert.assertEquals("successfully added", msg);

	}
	
	@Test
	public void getBookWithAuthorName() {
		given().log().all().queryParam("AuthorName", "Test@123")
		.when().get("/Library/GetBook.php")
		.then().log().all().assertThat().statusCode(200);
		
	}

	@DataProvider(name = "BooksData")
	public Object[][] getData() {
		return new Object[][] {{"abc", "9111"},{"def", "9112"},{"ghi", "9113"},{"jkl", "9114"},{"mno", "9115"}};
	}
	
}
