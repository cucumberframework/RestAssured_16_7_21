package RestAssured_16_7_21;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;
import org.testng.annotations.Test;

import BaseTest.BaseClass;
import files.payload;
import io.restassured.path.json.JsonPath;

public class Basics {

	@Test
	public void basicMethod() {

//This will add the place with the help of POST method of API and will then Assert to check the status 
//code of 200 and then will check the response has the scope as APP which it uses the Hamcrest static package
// thenit will check if the headers aof the response is Apache/2.4.18 (Ubuntu)
//everything after Assertthat() method will be the assertion.
//extract().response().asString();--> usedto extract response as string; 

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").body(payload.Addplace()).when()
				.post("/maps/api/place/add/json").then().assertThat().statusCode(200).body("scope", equalTo("APP"))
				.extract().response().asString();

		System.out.println(response);

//PUT request 
//To extract the place_id from the response
		// for parsing json
		String placeID = BaseClass.JsonParse(response, "place_id");
		System.out.println(placeID);
		String updatedPlace = "Bhilgaon Place Updated";
		given().log().all().queryParam("key", "qaclick123").body(payload.getPlace(placeID, updatedPlace)).when()
				.put("/maps/api/place/update/json").then().log().all().assertThat().statusCode(200)
				.body("msg", equalTo("Address successfully updated")).header("Server", "Apache/2.4.18 (Ubuntu)");
		// System.out.println(putResponse);

//GET API-->To check if the address which was updated is displayed correctly or NOT 
		String getResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeID).when()
				.get("/maps/api/place/get/json").then().log().all().assertThat().statusCode(200).extract().response()
				.asString();
		System.out.println(getResponse);
		String updatedAddressReponse = BaseClass.JsonParse(getResponse, "address");
		System.out.println(updatedAddressReponse);
		Assert.assertEquals(updatedPlace, updatedAddressReponse);
	}
}
