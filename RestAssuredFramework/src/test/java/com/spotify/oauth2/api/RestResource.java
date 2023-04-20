package com.spotify.oauth2.api;

import static com.spotify.oauth2.api.SpecBuilder.*;
import static io.restassured.RestAssured.given;
import static com.spotify.oauth2.api.Route.*;

import java.util.HashMap;

import io.restassured.response.Response;

public class RestResource {

	public static Response post(String path,String token,Object requestPlaylist) {

		return given(getRequestSpec()).
						body(requestPlaylist).
						//auth().oauth2(token).
						header("Authorization","Bearer "+ token).
				when().
						post(path).
				then().
						spec(getResponseSpec()).
						extract().response();
	}
	
	public static Response postAccount(HashMap<String, String> formParams) {
		return given(getAccountRequestSpec()).
					formParams(formParams).
				when().
					post(API + TOKEN).
				then().
					spec(getResponseSpec()).extract().response();
	}

	public static Response get(String path,String token) {

		return given(getRequestSpec()).
					//auth().oauth2(token).
					header("Authorization","Bearer "+ token).
				when().
					get(path).
				then().
					spec(getResponseSpec()).
					extract().response();
	}

	public static Response update(String path,String token,Object requestPlaylist) {
		return given(getRequestSpec()).
					body(requestPlaylist).
					auth().oauth2(token).
					//header("Authorization","Bearer "+ token).
				when().
					put(path).
				then().
					spec(getResponseSpec()).
					extract().
					response();

	}

}
