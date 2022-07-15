package BaseTest;

import io.restassured.path.json.JsonPath;

public class BaseClass {

	public static String JsonParse(String response,String key) { 
		
		JsonPath path=new JsonPath(response);
		String returnStringValue=path.getString(key);
		return returnStringValue; 
	}
}
