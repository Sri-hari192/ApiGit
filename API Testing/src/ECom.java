import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import Files.LoginCredentials;
import Files.LoginResponse;
import Files.Order;
import Files.OrderDetails;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class ECom {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Login
		LoginCredentials lc=new LoginCredentials();
		lc.setUserEmail("RadhaKrishna108@gmail.com");
		lc.setUserPassword("Radha@108");
		
		RequestSpecification req=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON)
		.build();
		ResponseSpecification res=new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		RequestSpecification reqLogin=given().relaxedHTTPSValidation().log().all().spec(req).body(lc);
		
		LoginResponse loginResponse=reqLogin.when().post("/api/ecom/auth/login").
		then().spec(res).log().all().extract().response().as(LoginResponse.class);
		  
		String token=loginResponse.getToken();
		String userId=loginResponse.getUserId();

		
		// Create Product
		
		RequestSpecification reqCreate=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token)
				.build();
		RequestSpecification reqCreated=given().log().all().spec(reqCreate)
		.param("productName", "Hare Krishna")
		.param("productAddedBy", userId)
		.param("productCategory","HolyName")
		.param("productSubCategory", "Material diseases")
		.param("productPrice", "108")
		.param("productDescription", "Goloka Prema dhan")
		.param("productFor", "Prema")
		.multiPart("productImage", new File("C:\\Users\\akash\\OneDrive\\Desktop\\flower.jpg"));
		
		String productCreate=reqCreated.when().post("/api/ecom/product/add-product").
		then().log().all().extract().response().asString();
		
		JsonPath js =new JsonPath(productCreate);
		String productId=js.get("productId");
		String message=js.getString("message");
		
		
		// Create Order
		
		RequestSpecification reqOrder=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token)
				.build();
		
		OrderDetails od=new OrderDetails();
		od.setCountry("India");
		od.setProductOrderedId(productId);
		
		List<OrderDetails> orders=new ArrayList<OrderDetails>();
		orders.add(od);
		
		Order or=new Order();
		or.setOrders(orders);
		
		RequestSpecification createOrder=given().log().all().spec(reqOrder).body(or);
		
		String createOrders=createOrder.when().post("/api/ecom/order/create-order")
		.then().log().all().extract().response().asString();
		
		System.out.println(createOrders);
		
//		JsonPath jst=new JsonPath(createOrders);
//		String orderNumber=jst.getString("orders");
//		
		
		// Delete order
		
		RequestSpecification reqDelete=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token)
				.build();
		
		RequestSpecification deleteOrder=given().log().all().spec(reqDelete).pathParam("productId", productId);
		
		String deleteProduct=deleteOrder.when().delete("/api/ecom/product/delete-product/{productId}")
				.then().log().all().extract().response().asString();
		
		JsonPath jsp=new JsonPath(deleteProduct);
		Assert.assertEquals("Product Deleted Successfully", jsp.getString("message"));
		
		
	}

}
