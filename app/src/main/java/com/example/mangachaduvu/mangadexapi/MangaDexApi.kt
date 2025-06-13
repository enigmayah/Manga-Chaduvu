package com.example.mangachaduvu.mangadexapi

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.Call
import retrofit2.Response

interface MangaDexApi {

    // Get manga information by ID
    @GET("manga/{mangaId}")
    suspend fun getMangaInfo(@Path("mangaId") mangaId: String): Response<MangaInfo>

    // Search for manga
    @GET("manga")
    fun searchManga(
        @Query("title") title: String
    ): Call<MangaSearchResponse>

    @GET("/cover/{mangaOrCoverId}")
    suspend fun getCoverArt(
        @Path("mangaOrCoverId") coverId: String
    ): Response<CoverResponse>
}
