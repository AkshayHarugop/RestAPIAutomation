import org.testng.Assert;

import files.payload;
import files.reusableMethods;
import io.restassured.path.json.JsonPath;

public class JsonReadingAndExploration {

	public static void main(String[] args) {
		JsonPath jp = reusableMethods.rawToJson(payload.ComplexJson());

//		1. Print No of courses returned by API
		int coursesCount = jp.getInt("courses.size()");
		System.out.println(coursesCount);
		System.out.println("------------------------------------");

//		2.Print Purchase Amount
		int purchaseAmount = jp.getInt("dashboard.purchaseAmount");
		System.out.println(jp.getInt("dashboard.purchaseAmount"));
		System.out.println("------------------------------------");

//		3. Print Title of the first course
		System.out.println(jp.getString("courses[0].title"));
		System.out.println("------------------------------------");

//		4. Print All course titles and their respective Prices
		for (int i = 0; i < coursesCount; i++) {
			System.out.println(jp.getString("courses[" + i + "].title"));
			System.out.println(jp.getString("courses[" + i + "].price"));
		}
		System.out.println("------------------------------------");

//		5. Print no of copies sold by RPA Course
		for (int i = 0; i < coursesCount; i++) {
			String courseName = jp.getString("courses[" + i + "].title");
			if (courseName.equals("RPA")) {
				System.out.println("Course of the RPA price is = " + jp.getString("courses[" + i + "].price"));
			}
		}
		System.out.println("------------------------------------");

//		6. Verify if Sum of all Course prices matches with Purchase Amount
		int actualCount = 0;
		for (int i = 0; i < coursesCount; i++) {
			int copies = jp.getInt("courses[" + i + "].copies");
			int count = jp.getInt("courses[" + i + "].price")*copies;
			actualCount = actualCount + count;
		}
		Assert.assertEquals(purchaseAmount, actualCount);
		System.out.println("------------------------------------");

	}

}
