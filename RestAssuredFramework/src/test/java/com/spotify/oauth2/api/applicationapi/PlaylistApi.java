package com.spotify.oauth2.api.applicationapi;

import static com.spotify.oauth2.api.TokenManager.getToken;
import com.spotify.oauth2.api.RestResource;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.ConfigLoader;

import io.qameta.allure.Step;
import io.restassured.response.Response;	
import static com.spotify.oauth2.api.Route.*;


public class PlaylistApi {

	@Step
	public static Response post(Playlist requestPlaylist) {

		return RestResource.post(USERS+"/" + ConfigLoader.getInsastance().getUserId() 
				+ PLAYLISTS, getToken(), requestPlaylist);
	}
	@Step
	public static Response post(String token,Playlist requestPlaylist) {

		return RestResource.post(USERS + "/" + ConfigLoader.getInsastance().getUserId() 
				+ PLAYLISTS, token, requestPlaylist);
	}
	@Step
	public static Response get(String playlistId) {

		return RestResource.get(PLAYLISTS +"/" + playlistId, getToken());
	}
	@Step
	public static Response update(String playlistId,Playlist requestPlaylist) {

		return RestResource.update(PLAYLISTS + "/"+ playlistId, getToken(), requestPlaylist);
	}

}
