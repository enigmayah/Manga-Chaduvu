package com.example.mangachaduvu.mangadexapi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class MangaViewModel : ViewModel() {

    val client = OkHttpClient()

    // Define the function to fetch manga info
    fun getMangaInfo(mangaId: String, onSuccess: (String, String, String?) -> Unit) {
        // Call your API to fetch the manga details
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getMangaInfo(mangaId)
                if (response.isSuccessful) {
                    val mangaResponse = response.body()  // Assuming you get the response body as a data class

                    // Extract title, description, and cover art id
                    val title = mangaResponse?.data?.attributes?.title?.get("en") ?: "No Title"
                    val description = mangaResponse?.data?.attributes?.description?.get("en") ?: "No Description"

                    // Check relationships for cover art ID
                    val coverArtId = mangaResponse?.data?.relationships?.find { it.type == "cover_art" }?.id
                    if (coverArtId != null) {
                        fetchCoverArt(coverArtId,mangaId)
                    } else {
                        _mangaCoverUrl.postValue("")  // Fallback if no cover art
                    }

                    // Pass data back to the composable using onSuccess
                    onSuccess(title, description, coverArtId)
                } else {
                    _errorMessage.postValue("Error fetching manga details")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("An error occurred: ${e.message}")
            }
        }
    }

    // LiveData to hold the cover URL, so it can be observed in the UI
    private val _mangaCoverUrl = MutableLiveData<String>()
    val mangaCoverUrl: LiveData<String> get() = _mangaCoverUrl

    // LiveData to hold any error messages
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    // Function to fetch the manga cover


    private val _coverData = MutableLiveData<CoverData>()
    val coverData: LiveData<CoverData> get() = _coverData

    private val _coverResponse = MutableLiveData<CoverResponse>()
    val coverResponse: LiveData<CoverResponse> get() = _coverResponse


    fun fetchCoverArt(coverId: String, mangaId: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getCoverArt(coverId)
                if (response.isSuccessful) {
                    val fileName = response.body()?.data?.attributes?.fileName ?: ""
                    if (fileName.isNotEmpty()) {
                        val coverUrl = "https://uploads.mangadex.org/covers/${mangaId}/${fileName}"
                        _mangaCoverUrl.postValue(coverUrl)  // Update the cover URL
                    } else {
                        Log.e("MangaViewModel", "Cover image fileName is empty")
                    }
                } else {
                    Log.e("MangaViewModel", "Error fetching cover art: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("MangaViewModel", "Network error: $e")
            }
        }
    }







//    fun searchManga(title: String, onSuccess: (List<Pair<String, String>>) -> Unit) {
//        viewModelScope.launch {
//            RetrofitClient.api.searchManga(title).enqueue(object : Callback<MangaSearchResponse> {
//                override fun onResponse(call: Call<MangaSearchResponse>, response: Response<MangaSearchResponse>) {
//                    if (response.isSuccessful) {
//                        val mangaSearchResults = response.body()?.data
//                        // Do something with the search results
//                        val mangaList = response.body()?.data?.map {
//                            val mangaTitle = it.attributes.title["en"] ?: "No Title"
//                            val mangaCoverUrl = it.attributes.coverArt["original"] ?: ""
//                            Pair(mangaTitle, mangaCoverUrl)  // Pair of title and cover URL
//                        }
//                        onSuccess(mangaList ?: emptyList())  // Send list of title and cover URL pairs
//
//                    } else{
//                        Log.e("respns","failed response not suc")
//                    }
//                }
//
//                override fun onFailure(call: Call<MangaSearchResponse>, t: Throwable) {
//                    // Handle failure
//                }
//            })
//        }
//    }
}
