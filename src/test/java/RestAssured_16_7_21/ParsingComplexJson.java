package RestAssured_16_7_21;

import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;
import junit.framework.Assert;

public class ParsingComplexJson {

	@Test
	public void parseComplexJson() {

		JsonPath js = new JsonPath(payload.parsingComplexJson());

//1. Print No of courses returned by API
//size will give count of an array .
		System.out.println(js.getInt("courses.size()"));

//2.Print Purchase Amount, as we have the amount in integer format we need to use getInt, else we can use get(object)
		System.out.println(js.getInt("dashboard.purchaseAmount"));

//3. Print Title of the first course
//We need to extract the title from the first course which is in array that's why we need to add that in []
//if we need to extract from the array .

		System.out.println(js.getString("courses[0].title"));
		System.out.println("--------------------------");

//4. Print All course titles and their respective Prices
		for (int i = 0; i < js.getInt("courses.size()"); i++) {

			System.out.println("Course Name-->" + js.get("courses[" + i + "].title"));
			System.out.println("And price of course-->" + js.getInt("courses[" + i + "].price"));

			System.out.println("--------------------------------------------");
		}
//5. Print no of copies sold by RPA Course courses
		for (int j = 0; j < js.getInt("courses.size()"); j++) {
			if (js.get("courses[" + j + "].title").equals("RPA")) {
				System.out
						.println("Number of copies sold by RPA course is -->" + js.getInt("courses[" + j + "].copies"));
				break;
			}

		}

//6. Verify if Sum of all Course prices matches with Purchase Amount
		int price = 0;
		int copies = 0;
		int courseTotalPrice = price * copies;
		int totalFinalPrice = 0;
		for (int k = 0; k < js.getInt("courses.size()"); k++) {

			courseTotalPrice = js.getInt("courses[" + k + "].price") * js.getInt("courses[" + k + "].copies");
			totalFinalPrice = totalFinalPrice + courseTotalPrice;
		}
		Assert.assertEquals(js.getInt("dashboard.purchaseAmount"), totalFinalPrice);

	}

}
