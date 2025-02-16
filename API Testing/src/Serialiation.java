import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import Files.AddPlace;
import Files.Location;
public class Serialiation {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestAssured.baseURI="https://rahulshettyacademy.com";
		AddPlace p=new AddPlace();
		p.setName("Frontline house");
		p.setAccuracy(50);
		p.setAddress("29, side layout, cohen 09");
		p.setLanguage("Sanskrit");
		p.setPhone_number("(+91) 983 893 3937");
		p.setWebsite("http://google.com");
		
		List<String> s=new ArrayList<String>();		
		s.add("shoe park");
		s.add("shop");
		p.setTypes(s);
		
		Location l=new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		p.setLocation(l);
		
		String response=given().log().all().queryParam("key","qaclick123")
		
		.body(p)
		.when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).extract().asString();
		System.out.println(response);

	}

}
