package com.moolya.api.utils;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.moolya.api.models.User;

public class PlayloadUtils {

	public static String readJsonAndReplace(String filePath, User user) throws Exception {
		String content = new String(Files.readAllBytes(Paths.get(filePath)));
		return content.replace("${name}", user.getName()).replace("${salary}", user.getSalary()).replace("${age}",
				user.getAge());
	}
}
