package OAuth2_0;

import static io.restassured.RestAssured.given;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Pojo.GetCourses;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

public class Deserialize_And_OAuth {

	public static void main(String[] args) throws InterruptedException {

		/*Deserialization concept
		 * This URL is to be constructed in postman and then hit the same on the
		 * browser, enter username and password and then extract the URL and use this in
		 * the below string,this is done becuase google authentication has been removed
		 * from the third party softwares through automation and currently there is NO
		 * workaround for the same
		 */

		String url = "https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=verifytest";
		String getCurrentURL = "https://rahulshettyacademy.com/getCourse.php?state=verifytest&code=4%2F0AX4XfWhmv2G329yukq_rnR_sceDcIgWwKICyJlFympfesoqcAyiffivezTFNlmW9Ih60fw&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none#";
		String authCode1 = getCurrentURL.split("&code=")[1];
		String FinalAuthCode = authCode1.split("&scope=")[0];
		System.out.println(FinalAuthCode);

		// URLencoding has to be disabled becuase it converts the specia; charecters to
		// other format
		String accessTokenRaw = given().log().all().urlEncodingEnabled(false).queryParams("state", "verifytest")
				.queryParams("code", FinalAuthCode)
				.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W").queryParams("response_type", "code")
				.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParams("grant_type", "authorization_code").post("https://www.googleapis.com/oauth2/v4/token")
				.asString();

		JsonPath js = new JsonPath(accessTokenRaw);
		String finalAccessToken = js.getString("access_token");

		/*
		 * String finalResponse = given().log().all().queryParam("access_token",
		 * finalAccessToken)
		 * .get("https://rahulshettyacademy.com/getCourse.php").asString();
		 */
//Deserialization concept .as(GetCourses.class);
		GetCourses finalResponse = given().queryParam("access_token", finalAccessToken).expect()
				.defaultParser(Parser.JSON).when().get("https://rahulshettyacademy.com/getCourse.php")
				.as(GetCourses.class);
		System.out.println(finalResponse.getLinkedIn());
		System.out.println(finalResponse.getInstructor());
		System.out.println(finalResponse.getCourses().getApi().get(1).getCourseTitle());
		
		
		

}
}
