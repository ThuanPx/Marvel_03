package com.hyperion.ths.marvel_03.data.source.remote.api.service;

import com.hyperion.ths.marvel_03.data.model.CharactersList;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ths on 30/05/2017.
 */

public interface MarvelApi {
    @GET("/v1/public/characters?limit=20")
    Observable<CharactersList> getCharacters(@Query("offset") int offset,
            @Query("ts") long timestamp, @Query("apikey") String apiKey,
            @Query("hash") String hash);
}
