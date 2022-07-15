package RestAssured_16_7_21;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import BaseTest.BaseClass;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {

	@Test(dataProvider = "getdata")
	public void addBook(String isbn, String aisle) {

		RestAssured.baseURI = "http://216.10.245.166/";
//Parameterizing the test case with dataprovider and changing argument of the payload 
		String response = given().log().all().header("Content-Type", "application/json")
				.body(payload.addBook(isbn, aisle)).when().post("Library/Addbook.php").then().log().all().assertThat()
				.statusCode(200).extract().response().asString();

		String id = BaseClass.JsonParse(response, "ID");
		System.out.println(id);

//Deleting book 
		String deleteBookresponse = given().log().all().header("Content-Type", "application/json")
				.body(payload.deleteBook(id)).when().post("/Library/DeleteBook.php").then().assertThat().statusCode(200)
				.extract().response().asString();

		System.out.println(deleteBookresponse);
		String compareResponseparameter = BaseClass.JsonParse(deleteBookresponse, "msg");
		Assert.assertEquals(compareResponseparameter, "book is successfully deleted");
	}

	@DataProvider(name = "getdata")
	public Object[][] addBooksData() {

		return new Object[][] { { "Megha98556", "985445" }, { "Megha9857856", "98455445" },
				{ "Megha98552126", "985478745" } };
	}
}
