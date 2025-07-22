package com.moolya.api.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.Test;

import com.moolya.api.models.User;
import com.moolya.api.utils.CommonFunctions;
import com.moolya.api.utils.PlayloadUtils;
import com.moolya.api.utils.PropertyOperations;

import io.restassured.RestAssured;
import io.restassured.response.Response;


public class ApiAutomation {
	//invocationCount tags
	
	@Test(description = "TC01_This method executes the get API and getting the base uri and endpoint are harcoded", groups = {"TC01", "smoke"})
	public void getListOfUsersHardCoded() {
		given().baseUri("https://reqres.in").
		when().get("/api/users?page=2").
		then().log().all().
		assertThat().statusCode(200);	
	}
	
	@Test(description = "TC02_This method executes the get API and validating the response body", groups = {"TC02", "smoke", "Regression"})
	public void getListOfUSers_body_validation() {
		given().baseUri("https://reqres.in").
		when().get("/api/users?page=2").
		then().log().all().
		assertThat().statusCode(200).
		body("page", is(equalTo(2)), "per_page", is(equalTo(6)), "data[0].id", is(equalTo(7))).
		body("data.first_name", contains("Michael", "Lindsay", "Tobias", "Byron", "George", "Rachel")).
		body("data.id", contains(7, 8, 9, 10, 11 , 12)).and().
		body("data.first_name", hasItem("Byron"));
	}
	
	@Test(description = "TC03_This method executes the get API and getting the base uri and endpoint from property fie", groups = {"TC03", "Regression"})
	public void getListOfUsers() {
		RestAssured.baseURI = PropertyOperations.getPropertyValueByKey("baseUrl");
		given().
		when().get(PropertyOperations.getPropertyValueByKey("getListOfUSerEndpoint")).
		then().log().all().assertThat().statusCode(200);		
	}
	
	@Test(description = "TC04_this method is used to create a record and using pojo class")
	public void createUser() throws Exception {
		RestAssured.baseURI = PropertyOperations.getPropertyValueByKey("dummyApiBaseUrl");
		
		// Generate test data
        String name = CommonFunctions.generateRandomNames();
        String age = CommonFunctions.generateRandomNumber();
        String salary = CommonFunctions.generateRandomSalary();
		
        User user = new User(name, salary, age);
        
        // Read and prepare request payload
        String payload = PlayloadUtils.readJsonAndReplace("src/test/resources/requestPayload/createUser.json", user);

		given()
			.contentType("application/json")
			.body(payload).
		when()
			.post(PropertyOperations.getPropertyValueByKey("dummyApiCreateEndpoint")).
		then()
			.log().all()
			.assertThat().statusCode(200)
			.body("data.name", is(equalTo(name)))
			.body("data.salary", is(equalTo(salary)))
			.body("data.age", is(equalTo(age)))
			.body("data.id", notNullValue());
    }
	
	
	
	@Test(description = "TC05_This method used to update the user salary and age details", groups = {"TC05", "smoke"})
	public void updateUserDetails() {
		RestAssured.baseURI = PropertyOperations.getPropertyValueByKey("dummyApiBaseUrl");
		
		//Generate test data
		String name = CommonFunctions.generateRandomNames();
		String salary = CommonFunctions.generateRandomSalary();
		String age = CommonFunctions.generateRandomNumber();
		
		User user = new User(name, salary, age);
		
		//Read and prepare request payload
		try {
			String payload = PlayloadUtils.readJsonAndReplace("src/test/resources/requestPayload/createUser.json", user);
			given()
			.contentType("application/json")
			.body(payload).
		when()
			.put(PropertyOperations.getPropertyValueByKey("dummyApiUpdateEndpoint")).
		then()
			.log().all()
			.assertThat().statusCode(200)
			.body("data.name", is(equalTo(name)))
			.body("data.salary", is(equalTo(salary)))
			.body("data.age", is(equalTo(age)));
			//getTheDetailsOfUseAfterUpdater(salary,age);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test(description = "TC05A_This method used to update the user salary and age details", groups = {"TC05", "smoke"})
	public void updateUserDetailsWithDisplay() {
		RestAssured.baseURI = PropertyOperations.getPropertyValueByKey("dummyApiBaseUrl");
		
		//Generate test data
		String name = CommonFunctions.generateRandomNames();
		String salary = CommonFunctions.generateRandomSalary();
		String age = CommonFunctions.generateRandomNumber();
		
		User user = new User(name, salary, age);
		
		//Read and prepare request payload
		try {
			String payload = PlayloadUtils.readJsonAndReplace("src/test/resources/requestPayload/createUser.json", user);
			Response response = RestAssured.given()
			.contentType("application/json")
			.body(payload).
		when()
			.put(PropertyOperations.getPropertyValueByKey("dummyApiUpdateEndpoint")).
		then()
			.log().all()
			.assertThat().statusCode(200)
			.body("data.name", is(equalTo(name)))
			.body("data.salary", is(equalTo(salary)))
			.body("data.age", is(equalTo(age))).extract().response();
			
			String responseBody = response.getBody().toString();
			System.out.println("the update response: " + responseBody);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test(description = "TC06_Get the details of the single user based on the id", dependsOnMethods = {"updateUserDetails"}, groups = {"TC06", "smoke", "Regression"})
	public void getTheDetailsOfUseAfterUpdater(String salary, String age) {
		RestAssured.baseURI = PropertyOperations.getPropertyValueByKey("dummyApiBaseUrl");
		given()
		.when().get(PropertyOperations.getPropertyValueByKey("dummyApiGetEndpoint"))
		.then()
		.log().all()
		.body("data.employee_salary", is(equalTo(salary)))
		.body("data.employee_age", is(equalTo(age)));
	}
	
	@Test(description = "this method is used to create a record")
	public void createUserOrder() {
		RestAssured.baseURI = "https://petstore.swagger.io";
		String str = "\"id\": 3,\r\n"
				+ "		  \"petId\": 0,\r\n"
				+ "		  \"quantity\": 0,\r\n"
				+ "		  \"shipDate\": \"2025-07-10T10:21:32.293Z\",\r\n"
				+ "		  \"status\": \"placed\",\r\n"
				+ "		  \"complete\": true";
	
		given().header("Content-Type", "application/json").headers("accept","application/json").body(str).
		when().post("/store/order").
		then().log().all().assertThat().statusCode(201);
	}
}
