import org.testng.Assert;
import org.testng.annotations.Test;

import files.payload;
import files.reusableMethods;
import io.restassured.path.json.JsonPath;

public class SumValidation {
	
	@Test
	/*For @Test to run we need to import the testNg jar file in the java build path for class level*/
	public void sumOfCourses() {
		JsonPath jp = reusableMethods.rawToJson(payload.ComplexJson());
		int actualCount = 0;
		int coursesCount = jp.getInt("courses.size()");
		int purchaseAmount = jp.getInt("dashboard.purchaseAmount");
		for (int i = 0; i < coursesCount; i++) {
			int copies = jp.getInt("courses[" + i + "].copies");
			int count = jp.getInt("courses[" + i + "].price")*copies;
			actualCount = actualCount + count;
		}
		Assert.assertEquals(purchaseAmount, actualCount);
	}
	
}
