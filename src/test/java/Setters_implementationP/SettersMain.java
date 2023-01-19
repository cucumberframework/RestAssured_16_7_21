package Setters_implementationP;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import BaseTest.BaseClass;

public class SettersMain {
	
	
	public static placeAPI_Serialization placeApiSetters(Map<String, String> testData) { 
		placeAPI_Serialization place = new placeAPI_Serialization();
		
		place.setAccuracy(BaseClass.intData(testData.get("Accuracy")));
		place.setName(testData.get("Name"));
		place.setPhone_number(testData.get("PhoneNumber"));
		place.setAddress(testData.get("Address"));
		place.setWebsite(testData.get("WebSite"));
		place.setLanguage(testData.get("Language"));
		Location l = new Location();
		l.setLat(-30.22);
		l.setLng(-45.33);
		place.setLocation(l);
		List<String> type = new ArrayList<String>();
		type.add("Test One");
		type.add("test two");
		place.setType(type);
		return place;
	}
	

}
