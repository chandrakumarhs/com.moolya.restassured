package com.moolya.api.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyOperations {
	
	static Properties prop;
	
	public static String getPropertyValueByKey(String key) {
		FileInputStream fis = null;
		try {
			if(prop == null) {
				prop = new Properties();
				fis = new FileInputStream("src/test/resources/config.properties");
				
				//load a properties file
				prop.load(fis);
			}
			return prop.getProperty(key);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

}
