package com.spotify.oauth2.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import org.testng.annotations.Test;

import com.spotify.oauth2.api.StatusCode;
import com.spotify.oauth2.api.applicationapi.PlaylistApi;
import com.spotify.oauth2.pojo.ErrorRoot;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.DataLoader;
import static com.spotify.oauth2.utils.FakerUtils.*;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.restassured.response.Response;


@Epic("Spotify.oauth2.0")
@Feature("Playlist Api")
public class PlaylistTests {
	@Step
	public Playlist playlistBuilder(String name,String description,boolean _public) {
		return Playlist.builder().
				name(name).
				description(description).
				_public(_public).build();
	}
	@Step
	public void assertPlaylistEqual(Playlist responsePlaylist,Playlist requestPlaylist ) {
		assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
		assertThat(responsePlaylist.getDescription(),equalTo(requestPlaylist.getDescription()));
		assertThat(responsePlaylist.get_public(),equalTo(requestPlaylist.get_public()));
	}
	@Step
	public void assertStatuscode(int actualStatuscode, int expectedStatuscode) {
		assertThat(actualStatuscode,equalTo(expectedStatuscode));
	}
	@Step
	public void assertionError(ErrorRoot responseError,int expectedStatusCode,String expectedMsg) {
		assertThat(responseError.getError().getStatus(), equalTo(expectedStatusCode));
		assertThat(responseError.getError().getMessage(),equalTo(expectedMsg));
	}
	@Story("Create a playlist")
	@Description("This test is to verify whether user is able to create a Playlist")
	@Test(priority=1,description="Should be able to create a Playlist")
	public void shouldBeAbleToCreatePlaylist() {

		Playlist requestPlaylist=playlistBuilder(generateName(), generateDescription(), false);
		Response response = PlaylistApi.post(requestPlaylist);
		assertStatuscode(response.statusCode(), StatusCode.CODE_201.getCode());
		Playlist responsePlaylist=response.as(Playlist.class);
		assertPlaylistEqual(responsePlaylist, requestPlaylist);
	}
	@Story("Get a playlist")
	@Description("This test is to verify whether user is able to get a Playlist")
	@Test(priority=2,description="Should be able to Get a Playlist")
	public void shouldBeAbleToGetPlaylist() {

		Playlist requestPlaylist=playlistBuilder("New Playlist", "Sanjeev playlist description", false);
		Response response=PlaylistApi.get(DataLoader.getInsastance().getGetPlayListId());
		assertStatuscode(response.statusCode(), StatusCode.CODE_200.getCode());
		Playlist responsePlaylist=response.as(Playlist.class);
		assertPlaylistEqual(responsePlaylist, requestPlaylist);
	}
	@Story("Update a playlist")
	@Description("This test is to verify whether user is able to update a Playlist")
	@Test(priority=3,description="Should be able to Update a Playlist")
	public void shouldBeAbleToUpdatePlaylist() {

		Playlist requestPlaylist=playlistBuilder(generateName(), generateDescription(), false);
		Response response=PlaylistApi.update(DataLoader.getInsastance().getUpdatePlayListId(), requestPlaylist);
		assertStatuscode(response.statusCode(), StatusCode.CODE_200.getCode());
	}
	@Story("Create a playlist")
	@Description("This test is to verify user Should not be able to create a Playlist without Name")
	@Test(priority=4,description="Should not be able to create a Playlist without Name")
	public void shouldNotBeAbleToCreatePlaylistWithoutName() {

		Playlist requestPlaylist=playlistBuilder("", generateDescription(), false);
		Response response=PlaylistApi.post(requestPlaylist);
		assertStatuscode(response.statusCode(), StatusCode.CODE_400.getCode());
		assertionError(response.as(ErrorRoot.class), StatusCode.CODE_400.getCode(),StatusCode.CODE_400.getMsg());
	}
	@Story("Create a playlist")
	@Description("This test is to verify user Should not be able to create a Playlist with expired token")
	@Test(priority=5,description="Should not be able to create a Playlist with Expired token")
	public void shouldNotBeAbleToCreatePlaylistWithExpiredToken() {

		String invalid_token="12345";
		Playlist requestPlaylist=playlistBuilder(generateName(), generateDescription(), false);
		Response response=PlaylistApi.post(invalid_token,requestPlaylist);
		assertStatuscode(response.statusCode(), StatusCode.CODE_401.getCode());
		assertionError(response.as(ErrorRoot.class), StatusCode.CODE_401.getCode(),StatusCode.CODE_401.getMsg());
	}


}
