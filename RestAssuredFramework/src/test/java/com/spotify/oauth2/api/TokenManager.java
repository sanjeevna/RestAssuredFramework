package com.spotify.oauth2.api;

import java.time.Instant;
import java.util.HashMap;

import com.spotify.oauth2.utils.ConfigLoader;

import io.restassured.response.Response;;

public class TokenManager {
	private static String access_token;
	private static Instant expiry_time;
	
	public static synchronized String getToken() {
			
		try {
			if(access_token==null ||Instant.now().isAfter(expiry_time)) {
				System.out.println("Renewing token...");
				Response response=renewToken();
				access_token=response.path("access_token");
				int expiryDurationInSeconds=response.path("expires_in");
				expiry_time=Instant.now().plusSeconds(expiryDurationInSeconds-300);
			}else {
				System.out.println("Token is good to use");
			}
			
		}
			catch (Exception e) {
		throw new RuntimeException("ABORT!!! failed to get token");
			}
		return access_token;
	}
	
	private static Response renewToken() {
		
		HashMap<String,String> formParams=new HashMap<String, String>();
		formParams.put("grant_type", ConfigLoader.getInsastance().getGrantType());
		formParams.put("client_id", ConfigLoader.getInsastance().getClientId());
		formParams.put("refresh_token", ConfigLoader.getInsastance().getRefreshToken());
		formParams.put("client_secret", ConfigLoader.getInsastance().getClientSecret());
		
		Response response= RestResource.postAccount(formParams);
		
		if(response.statusCode()!=200) {
			throw new RuntimeException("ABORT: renew token failed");
			
		}
								
		return response;
		
	}

}
