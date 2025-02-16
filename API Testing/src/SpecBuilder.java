import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.RequestBuilder;

import Files.AddPlace;
import Files.Location;

public class SpecBuilder {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// RestAssured.baseURI="https://rahulshettyacademy.com";
		AddPlace p = new AddPlace();
		p.setName("Frontline house");
		p.setAccuracy(50);
		p.setAddress("29, side layout, cohen 09");
		p.setLanguage("Sanskrit");
		p.setPhone_number("(+91) 983 893 3937");
		p.setWebsite("http://google.com");

		List<String> s = new ArrayList<String>();
		s.add("shoe park");
		s.add("shop");
		p.setTypes(s);

		Location l = new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		p.setLocation(l);

		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();
		RequestSpecification reqs = given().log().all().spec(req).body(p);

		ResponseSpecification res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON)
				.build();
		String response = reqs.when().post("/maps/api/place/add/json")

				.then().spec(res).extract().asString();
		System.out.println(response);

	}

}
