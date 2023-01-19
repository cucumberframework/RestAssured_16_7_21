package BaseTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeSuite;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.restassured.path.json.JsonPath;

public class BaseClass {
	public static Map<String,String> testData;
	
	  public static Properties prop; 
	private FileInputStream fis; 

	public static String JsonParse(String response, String key) {

		JsonPath path = new JsonPath(response);
		String returnStringValue = path.getString(key);
		return returnStringValue;
	}

	public static Map<String, String> getTestData(String fileName) throws IOException, ParseException {
		String testCaseName;

		StackTraceElement[] methodName = Thread.currentThread().getStackTrace();
		for (int i = 0; i < methodName.length; i++) {
			testData= new HashMap<String,String> ();

			if (methodName[i].toString().contains("RequestSpecBuilder_Implementation")
					|| methodName[i].toString().contains("RestAssured_16_7_21")) {
				testCaseName = methodName[i].toString();
				System.out.println(testCaseName);
				String[] actualTestCaseName = testCaseName.split("\\.");
				String[] finalTestCaseName = actualTestCaseName[2].split("\\(");
				testCaseName = finalTestCaseName[0].toString().trim();
				System.out.println(testCaseName);
				FileReader readFile = new FileReader(fileName); 
				JsonElement jsonElement=(new JsonParser()).parse(readFile);
				JsonObject jsonObject = jsonElement.getAsJsonObject();
				JsonElement nodeName=jsonObject.get(testCaseName);
				
				for(String allElements :nodeName.getAsJsonObject().keySet()) {
						testData.put(allElements, nodeName.getAsJsonObject().get(allElements).getAsString());
					}
				}else {
					continue;
				}
				break;
			}
		return testData;

	}
	
	public static int intData(String value) {
		
		int number=Integer.parseInt(value);
		return number;
	}
	
	
	public  Properties getProperties() throws IOException { 
		prop= new Properties (); 
		fis = new FileInputStream(".\\properties\\Main.properties"); 
		prop.load(fis);
		if(prop.getProperty("environment").equalsIgnoreCase("qa")) { 
			prop.clear();
			fis=new FileInputStream(".\\properties\\qa.properties"); 
			prop.load(fis);
			this.prop=prop;
		}
		
		return prop;
	}
	
	
	@BeforeSuite
	public void beforeSuite() throws IOException { 
		
		prop=getProperties(); 
		
		
	}
}
