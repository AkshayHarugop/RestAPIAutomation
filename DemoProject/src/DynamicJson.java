import static io.restassured.RestAssured.given;

import java.util.UUID;

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
		String addBookResponse = given().log().all().header("Content-Type", "application/json")
				.body(payload.addBookApiJson(isbn, aisle, "Test@123")).when().post("/Library/Addbook.php").then().log()
				.all().assertThat().statusCode(200).extract().response().asString();

		JsonPath jp1 = reusableMethods.rawToJson(addBookResponse);
		String msg = jp1.getString("Msg");
		id = jp1.getString("ID");
		Assert.assertEquals("successfully added", msg);

	}

	@Test
	public void getBookWithAuthorName() {
		given().log().all().queryParam("AuthorName", "Test@123").when().get("/Library/GetBook.php").then().log().all()
				.assertThat().statusCode(200);

	}

	@Test(dataProvider = "BooksData")
	public void GetBookWithID(String isbn, String aisle) {
		given().log().all().queryParam("ID", isbn + aisle).when().get("Library/GetBook.php").then().log().all()
				.assertThat().statusCode(200);
	}

	@Test
	public void DeleteBookByID(String isbn, String aisle) {
		given().log().all().header("Content-Type", "application/json").body(payload.deleteBookApiJson(isbn + aisle))
				.when().post("Library/DeleteBook.php").then().log().all().assertThat().statusCode(200);

	}

	@DataProvider(name = "BooksData")
	public Object[][] getData() {
		return new Object[][] { { "abc", UUID.randomUUID().toString().substring(1, 5) },
				{ "def", UUID.randomUUID().toString().substring(1, 5) },
				{ "ghi", UUID.randomUUID().toString().substring(1, 5) },
				{ "jkl", UUID.randomUUID().toString().substring(1, 5) },
				{ "mno", UUID.randomUUID().toString().substring(1, 5) } };
	}

}
