package com.spotify.oauth2.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

public class PropertyUtils {

	public static Properties propertyLoader(String filepath) {
		Properties properties=new Properties();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(filepath));
			try {
				properties.load(reader);
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("falied to load properties file"+ filepath);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("properties file not found in "+ filepath);

		}
		return properties;

	}

}
