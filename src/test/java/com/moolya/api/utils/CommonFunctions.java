package com.moolya.api.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class CommonFunctions {
	
	public static String generateRandomNames() {
		String randomName = "User_" + RandomStringUtils.randomAlphabetic(8);
		return randomName;
	}
	
	public static String generateRandomNumber() {
		String randomNumber = RandomStringUtils.randomNumeric(2);
		return randomNumber;
	}
	
	public static String generateRandomSalary() {
		String randomSalary = RandomStringUtils.randomNumeric(5) + ".00";
		return randomSalary;
	}

}
