import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

public class JiraBug {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI="https://akashkr930487-1736604491444.atlassian.net/";
		String bugId=given().header("Content-Type","application/json")
		.header("Authorization","Basic YWthc2hrcjkzMDQ4N0BnbWFpbC5jb206QVRBVFQzeEZmR0YwSWNIRldPTExkWGd0Uy1KWjNwNGNMT3hNdC12OU04V0lpQUZVYlV3N1haM0szV1dTckN5VldQX0tGc01MX2RUQVJyOEtrdjFxUlhicElQU1NEOFNKaExUM1BuX2hpcWJSbmJkUG5NbFNRdHYzM0kxbTI4c1h3OFIxQ1FXU1hRa0p1cDMwTC03NURiRS1iNWRRMmpDQ25Gbm81a2xraDlSb0g0MVBqWDM5eXpBPTVBM0ExQkYx")
		.body("{\r\n"
				+ "    \"fields\": {\r\n"
				+ "       \"project\":\r\n"
				+ "       {\r\n"
				+ "          \"key\": \"SCRUM\"\r\n"
				+ "       },\r\n"
				+ "       \"summary\": \"RestAssured links is not working\",\r\n"
				+ "       \"description\": \"Creating of an issue using project keys and issue type names using the REST API\",\r\n"
				+ "       \"issuetype\": {\r\n"
				+ "          \"name\": \"Bug\"\r\n"
				+ "       }\r\n"
				+ "   }\r\n"
				+ "}\r\n"
				+ "").
		when().post("rest/api/2/issue").
		then().assertThat().statusCode(201).extract().asString();
		
		JsonPath js=new JsonPath(bugId);
		String issueId=js.getString("id");
		System.out.println(issueId);
		
		// Attachment
		given().pathParam("key", issueId)
		.header("X-Atlassian-Token","no-check")
		.header("Authorization","Basic YWthc2hrcjkzMDQ4N0BnbWFpbC5jb206QVRBVFQzeEZmR0YwSWNIRldPTExkWGd0Uy1KWjNwNGNMT3hNdC12OU04V0lpQUZVYlV3N1haM0szV1dTckN5VldQX0tGc01MX2RUQVJyOEtrdjFxUlhicElQU1NEOFNKaExUM1BuX2hpcWJSbmJkUG5NbFNRdHYzM0kxbTI4c1h3OFIxQ1FXU1hRa0p1cDMwTC03NURiRS1iNWRRMmpDQ25Gbm81a2xraDlSb0g0MVBqWDM5eXpBPTVBM0ExQkYx")
		.multiPart("file",new File("C:\\Users\\akash\\OneDrive\\Desktop\\flower.jpg"))
		.when().post("rest/api/2/issue/{key}/attachments").
		then().assertThat().statusCode(200);

	}

}
