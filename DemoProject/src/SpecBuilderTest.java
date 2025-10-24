import java.util.ArrayList;
import java.util.List;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import static io.restassured.RestAssured.*;

public class SpecBuilderTest {
	public static void main(String[] args) {

		/*
		 * Validate if add place API is working as expected will use methods like given,
		 * when and there given - all input details (query parameter, header) , Body
		 * when - submit the API CRUD action (resource and http method goes under when)
		 * then - Validate the response
		 */
		
		AddPlace ad = new AddPlace();
		ad.setAccuracy(50);
		ad.setName("Frontline house");
		ad.setPhone_number("(+91) 983 893 3937");
		ad.setAddress("29, side layout, cohen 09");
		ad.setWebsite("http://google.com");
		ad.setLanguage("French-IN");

		List<String> MyList = new ArrayList<String>();
		MyList.add("shoe park");
		MyList.add("shop");
		ad.setTypes(MyList);
		
		Location l =new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		ad.setLocation(l);
		
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();
		ResponseSpecification res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
//		RestAssured.baseURI = "https://rahulshettyacademy.com";
//		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
//				.body(ad)
//				.when().post("/maps/api/place/add/json").then().log().all().assertThat().statusCode(200);
//		.log().all() we can utilize it in everywhere that is given(), when() and then() methods ...
		
		String responseString = given().spec(req).body(ad)
		.when().post("/maps/api/place/add/json")
		.then().spec(res).extract().response().asString();
		
		System.out.println(responseString);
		
	}
}
