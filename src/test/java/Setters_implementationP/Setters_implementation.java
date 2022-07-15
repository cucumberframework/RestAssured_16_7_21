package Setters_implementationP;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import files.payload;
import io.restassured.RestAssured;

public class Setters_implementation {

	public static void main (String[] args) { 
		
		placeAPI_Serialization place= new placeAPI_Serialization(); 
		
		place.setAccuracy(50);
		place.setName("Om Villa");
		place.setPhone_number("9892338563");
		place.setAddress("Bhilgaon Address");
		place.setWebsite("www.automationsea.com");
		place.setLanguage("English");
		Location l=new Location();
		l.setLat(-30.22);
		l.setLng(-45.33);
		place.setLocation(l);
		List<String> type= new ArrayList<String>(); 
		type.add("Test One"); 
		type.add("test two" );
		place.setType(type);
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").body(place).when()
				.post("/maps/api/place/add/json").then().assertThat().statusCode(200).extract().response().asString();

		System.out.println(response);
	}
}
