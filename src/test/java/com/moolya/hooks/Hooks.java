package com.moolya.hooks;

import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;

public class Hooks {
	
	@BeforeClass
	public void setup() {
		//RestAssured.filters(new AllureRestAssured());  // Add Allure filter
	}

}
