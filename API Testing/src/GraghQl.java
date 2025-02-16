import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;

public class GraghQl {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String response=given().log().all().header("content-type","application/json")
		.body("{\"query\":\"query($characterId:Int!){\\n  character(characterId: $characterId) {\\n    name\\n    type\\n  }\\n  locations(filters: {name: \\\"Krishna\\\", type: \\\"Supreme God\\\", dimension: \\\"Both\\\"}) {\\n    info {\\n      count\\n      pages\\n      next\\n      prev\\n    }\\n  }\\n  location(locationId: 18346) {\\n    id\\n    name\\n    type\\n    dimension\\n  }\\n}\\n\",\"variables\":{\"characterId\":12522}}")
		.when().post("https://rahulshettyacademy.com/gq/graphql")
		.then().log().all().extract().response().asString();
		
		JsonPath js=new JsonPath(response);
		String n=js.get("data.character.name");
		System.out.println(n);
		System.out.println("------------------------------");
		System.out.println("**************************************88");
	}

}
