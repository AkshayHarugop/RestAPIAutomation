import static io.restassured.RestAssured.given;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import files.payload;
import files.reusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class E2E_eCommerce_API_Testing_TestNG {

	String Authorization;
	String userId;
	String productOrderedId;
	String orderId;

	@BeforeTest
	public void setup() {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
	}

	@Test(priority = 1)
	public void login() {
		String loginResponse = given().log().all().header("Content-Type", "application/json").body(payload.e2Elogin())
				.when().post("/api/ecom/auth/login").then().log().all().assertThat().statusCode(200).extract()
				.response().asString();

		JsonPath jp1 = reusableMethods.rawToJson(loginResponse);
		Authorization = jp1.getString("token");
		userId = jp1.getString("userId");
		Assert.assertTrue(jp1.getString("message").equals("Login Successfully"));

		System.out.println(Authorization);
		System.out.println(userId);
	}

	@Test(priority = 2)
	public void createProduct() {
		String crProdResp = given().log().all().header("Authorization", Authorization)
				.param("productName", "Experctorant").param("productAddedBy", userId).param("productCategory", "Health")
				.param("productSubCategory", "Cough").param("productPrice", "161")
				.param("productDescription", "By_Dr.Asiwarya S").param("productFor", "All age above 18")
				.multiPart("productImage",
						new File("C:\\Users\\aha5\\Downloads\\Gemini_Generated_Image_iu2shniu2shniu2s.png"))
				.when().post("/api/ecom/product/add-product").then().log().all().assertThat().statusCode(201).extract()
				.response().asString();

		JsonPath jp2 = reusableMethods.rawToJson(crProdResp);
		productOrderedId = jp2.getString("productId");
		Assert.assertTrue(jp2.getString("message").equals("Product Added Successfully"));
	}

	@Test(priority = 3)
	public void createOrder() {
		String crtOrderResponse = given().log().all().header("Content-Type", "application/json")
				.header("Authorization", Authorization).body(payload.e2EcreateOrder(productOrderedId)).when()
				.post("/api/ecom/order/create-order").then().log().all().assertThat().statusCode(201).extract()
				.response().asString();

		JsonPath jp3 = reusableMethods.rawToJson(crtOrderResponse);
		orderId = jp3.getString("orders[0]");
		Assert.assertTrue(jp3.getString("message").equals("Order Placed Successfully"));
	}

	@Test(priority = 4)
	public void getOrderDetails() {
		String getOrderDetilsResponse = given().log().all().queryParam("id", orderId)
				.header("Authorization", Authorization).when().log().all().get("/api/ecom/order/get-orders-details")
				.then().log().all().assertThat().statusCode(200).extract().response().asString();

		JsonPath jp4 = reusableMethods.rawToJson(getOrderDetilsResponse);
		Assert.assertTrue(jp4.getString("message").equals("Orders fetched for customer Successfully"));
	}

	@Test(priority = 5)
	public void deleteProduct() {
		String delProdResponse = given().log().all().header("Authorization", Authorization).when().log().all()
				.delete("api/ecom/product/delete-product/" + productOrderedId + "").then().log().all().assertThat()
				.statusCode(200).extract().response().asString();

		JsonPath jp5 = reusableMethods.rawToJson(delProdResponse);
		Assert.assertTrue(jp5.getString("message").equals("Product Deleted Successfully"));

	}

}
