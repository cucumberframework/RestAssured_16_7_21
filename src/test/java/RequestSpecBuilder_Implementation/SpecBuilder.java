package RequestSpecBuilder_Implementation;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import Setters_implementationP.Location;
import Setters_implementationP.placeAPI_Serialization;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilder {

	public static void main(String[] args) {
		/*
		 * GET_PLACE RestAssured.baseURI="XXXX";
		 * 
		 * Response res=given().queryParam("key",
		 * "qaclick123").header("Content-Type","application/json") when ().
		 * 
		 * get("/maps/api/place/get/json").
		 * then().assertThat().statusCode(200).contentType("application/json").extract()
		 * .response();
		 * 
		 * DELETE_PLACE RestAssured.baseURI="XXXX";
		 * 
		 * Response res=given().queryParam("key",
		 * "qaclick123").header("Content-Type","application/json")
		 * 
		 * .body(“delete_Place_json”) .when().post("/maps/api/place/delete/json").
		 * then().assertThat().statusCode(200).
		 * contentType("application/json").extract().response();
		 * 
		 */

		placeAPI_Serialization place = new placeAPI_Serialization();

		place.setAccuracy(50);
		place.setName("Om Villa");
		place.setPhone_number("9892338563");
		place.setAddress("Bhilgaon Address");
		place.setWebsite("www.automationsea.com");
		place.setLanguage("English");
		Location l = new Location();
		l.setLat(-30.22);
		l.setLng(-45.33);
		place.setLocation(l);
		List<String> type = new ArrayList<String>();
		type.add("Test One");
		type.add("test two");
		place.setType(type);

		// In RequestSpec builder we keep all the reusable content of the request in
		// request spec builder so that we can reuse it every time without writing the
		// same piece of code every time
		// like Content type , headers , Base URI ,Query parameter which is common for
		// all the type of methods like get ,post , put is kept in request specification
		RequestSpecification req = new RequestSpecBuilder().setContentType(ContentType.JSON)
				.setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123").build();
		

		// We will be putting the reusable response and compare it , for that we will be
		// creating response spec builder so that we dont have to write the same code
		// every time , and if it response changes we dont need to make changes in the
		// every test case , change in one test case is enough to make it reflect in
		// every test case
		ResponseSpecification responseBuilder = new ResponseSpecBuilder().expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();

		// RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().spec(req).body(place).when().post("/maps/api/place/add/json").then()
				.spec(responseBuilder).extract().response().asString();

		System.out.println(response);
	}
}
