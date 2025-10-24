import static io.restassured.RestAssured.given;

import java.io.File;

import org.testng.Assert;

import files.reusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class E2E_eCommerce_API_Testing_normal {

	public static void main(String[] args) {
		
		RestAssured.baseURI="https://rahulshettyacademy.com";

//		login
		String loginResponse = given().log().all().header("Content-Type", "application/json").body("{\r\n"
				+ "    \"userEmail\": \"akshayharugop@gmail.com\",\r\n"
				+ "    \"userPassword\": \"Test@123\"\r\n"
				+ "}   ")
		.when().post("/api/ecom/auth/login")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath jp1 = reusableMethods.rawToJson(loginResponse);
		String Authorization = jp1.getString("token");
		String userId = jp1.getString("userId");
		Assert.assertTrue(jp1.getString("message").equals("Login Successfully"));
		
		System.out.println(Authorization);
		System.out.println(userId);
		
//		Create Product
		String crProdResp = given().log().all().header("Authorization", Authorization)
		.param("productName", "Experctorant")
		.param("productAddedBy", userId)
		.param("productCategory", "Health")
		.param("productSubCategory", "Cough")
		.param("productPrice", "161")
		.param("productDescription", "By_Dr.Asiwarya S")
		.param("productFor", "All age above 18")
		.multiPart("productImage", new File("C:\\Users\\aha5\\Downloads\\Gemini_Generated_Image_iu2shniu2shniu2s.png"))
		.when().post("/api/ecom/product/add-product")
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		JsonPath jp2 = reusableMethods.rawToJson(crProdResp);
		String productOrderedId = jp2.getString("productId");
		Assert.assertTrue(jp2.getString("message").equals("Product Added Successfully"));
		
//		Create Order
		String crtOrderResponse = given().log().all().header("Content-Type", "application/json").header("Authorization", Authorization).body("{\r\n"
				+ "    \"orders\": [\r\n"
				+ "        {\r\n"
				+ "            \"country\": \"India\",\r\n"
				+ "            \"productOrderedId\": \""+productOrderedId+"\"\r\n"
				+ "        }\r\n"
				+ "    ]\r\n"
				+ "}")
		.when().post("/api/ecom/order/create-order")
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		JsonPath jp3 = reusableMethods.rawToJson(crtOrderResponse);
		String orderId = jp3.getString("orders[0]");
		Assert.assertTrue(jp3.getString("message").equals("Order Placed Successfully"));
		
//		Get Order Details
		String getOrderDetilsResponse = given().log().all().queryParam("id", orderId).header("Authorization", Authorization)
		.when().log().all().get("/api/ecom/order/get-orders-details")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath jp4 =reusableMethods.rawToJson(getOrderDetilsResponse);
		Assert.assertTrue(jp4.getString("message").equals("Orders fetched for customer Successfully"));
		
//		Delete Product
		String delProdResponse = given().log().all().header("Authorization",Authorization)
		.when().log().all().delete("api/ecom/product/delete-product/"+productOrderedId+"")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath jp5 = reusableMethods.rawToJson(delProdResponse);
		Assert.assertTrue(jp5.getString("message").equals("Product Deleted Successfully"));
	}

}
