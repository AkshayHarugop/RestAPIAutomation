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
	int[][] matrix;

	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "http://216.10.245.166"; // set once for all tests
	}

	@Test(priority = 1, dataProvider = "BooksData")
	public void addBook(String isbn, String aisle) {
		String addBookResponse = given().log().all().header("Content-Type", "application/json")
				.body(payload.addBookApiJson(isbn, aisle, "Test1234")).when().post("/Library/Addbook.php").then().log()
				.all().assertThat().statusCode(200).extract().response().asString();

		JsonPath jp1 = reusableMethods.rawToJson(addBookResponse);
		String msg = jp1.getString("Msg");
		id = jp1.getString("ID");
		Assert.assertEquals("successfully added", msg);

	}

	@Test(priority = 2)
	public void getBookWithAuthorName() {
		given().log().all().queryParam("AuthorName", "Test1234").when().get("/Library/GetBook.php").then().log().all()
				.assertThat().statusCode(200);

	}

	@Test(priority = 3, dataProvider = "BooksData")
	public void GetBookWithID(String isbn, String aisle) {
		System.out.println(isbn + aisle);
		given().log().all().queryParam("ID", isbn + aisle).when().get("Library/GetBook.php").then().log().all()
				.assertThat().statusCode(200);
	}

	@Test(priority = 4, dependsOnMethods = {"GetBookWithID"})
	public void DeleteBookByID(String isbn, String aisle) {
		given().log().all().header("Content-Type", "application/json").body(payload.deleteBookApiJson(isbn + aisle))
				.when().post("Library/DeleteBook.php").then().log().all().assertThat().statusCode(200);

	}

	@DataProvider(name = "BooksData")
	public Object[][] getData() {
		return new Object[][] { { "abc", "123" },
				{ "def", "456" },
				{ "ghi", "789" },
				{ "jkl", "123" },
				{ "mno", "456" } };
	}

}
