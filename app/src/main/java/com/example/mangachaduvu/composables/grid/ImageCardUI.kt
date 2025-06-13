package com.example.mangachaduvu.composables.grid

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toIntSize
import coil3.compose.SubcomposeAsyncImage
import com.example.mangachaduvu.mangadexapi.MangaAttributes
import com.example.mangachaduvu.mangadexapi.MangaData
import com.example.mangachaduvu.mangadexapi.MangaInfo
import com.example.mangachaduvu.mangadexapi.MangaViewModel

@Composable
fun ImageCard(
    imageUrl: String,
    onDownloadClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,  // Reduced elevation for subtler effect
                shape = RoundedCornerShape(8.dp)  // Smaller corner radius
            )
            .clip(RoundedCornerShape(8.dp))  // Matching corner radius
            .clickable { onDownloadClick(imageUrl) },  // Moved clickable here
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Box {
            var imageSize by remember { mutableStateOf(IntSize.Zero) }

            SubcomposeAsyncImage(
                model = imageUrl,
                contentDescription = "Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(
                        ratio = if (imageSize != IntSize.Zero) {
                            imageSize.width.toFloat() / imageSize.height.toFloat()
                        } else {
                            1f
                        },
                        matchHeightConstraintsFirst = false
                    ),
                contentScale = ContentScale.Fit,
                loading = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),  // Smaller loading indicator
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                error = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.errorContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Warning,
                            contentDescription = "Error",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(24.dp)  // Smaller error icon
                        )
                    }
                },
                success = { state ->
                    imageSize = state.painter.intrinsicSize.toIntSize()
                    Image(
                        painter = state.painter,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                },
                onError = {
                    Log.e("ImageCard", "Error loading image: ${it.result.throwable.message}")
                }
            )
        }
    }
}

@Composable
fun SimpleMangaCardOld(
    mangaId: String,  // Unique manga ID
    viewModel: MangaViewModel,  // Assuming ViewModel is passed in
    onClick: (String) -> Unit  // Click listener to handle manga selection
) {
    // States for manga info
    var mangaInfo by remember { mutableStateOf<MangaInfo?>(null) }

    // Fetch manga information when the composable is launched
    LaunchedEffect(mangaId) {
        Log.d("SimpleMangaCard", "Requesting manga info for mangaId: $mangaId")

        viewModel.getMangaInfo(mangaId) { title, description, coverUrl ->
            Log.d("SimpleMangaCard", "Manga info received: Title: $title, Cover URL: $coverUrl")

            // Update manga info when fetched
            mangaInfo = MangaInfo(data = MangaData(
                id = mangaId,
                type = "manga",
                attributes = MangaAttributes(
                    title = mapOf("en" to title),
                    description = mapOf("desc" to "") // You can update with the description if needed
                ),
                relationships = emptyList()  // Empty for simplicity
            ))
        }
    }

    // Card layout
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(200.dp)
            .clickable {
                mangaInfo?.data?.id?.let {
                    Log.d("SimpleMangaCard", "Manga clicked: $it")
                    onClick(it)
                }
            },  // Handle on click
        shape = RoundedCornerShape(8.dp),  // Rounded corners for the card
        elevation = CardDefaults.cardElevation(4.dp) // Small elevation for shadow effect
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Manga Cover Image
            val coverUrl = mangaInfo?.data?.attributes?.description?.get("original")
            coverUrl?.let {
                SubcomposeAsyncImage(
                    model = coverUrl,
                    contentDescription = "Manga Cover",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    loading = {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = MaterialTheme.colorScheme.primary
                        )
                    },
                    error = {
                        Icon(
                            imageVector = Icons.Rounded.Warning,
                            contentDescription = "Error loading image",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                )
            }

            // Title Text
            val title = mangaInfo?.data?.attributes?.title?.get("en") ?: "No Title"
            Log.d("SimpleMangaCard", "Displaying title: $title")

            Text(
                text = title,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp),
                fontWeight = FontWeight.Bold,
                maxLines = 2
            )
        }
    }
}


@Composable
fun SimpleMangaCard(
    mangaId: String,  // Unique manga ID
    viewModel: MangaViewModel,
    onClick: (String) -> Unit
) {
    // States for manga info and cover URL
    var mangaInfo by remember { mutableStateOf<MangaInfo?>(null) }

    // Observe the cover URL from the ViewModel
    val coverUrl by viewModel.mangaCoverUrl.observeAsState("")
    val errorMessage by viewModel.errorMessage.observeAsState("")


    val coverData by viewModel.coverData.observeAsState()
    val coverResponse by viewModel.coverResponse.observeAsState()

    // Fetch manga information and cover URL when the composable is launched
    LaunchedEffect(mangaId) {
        Log.d("SimpleMangaCard", "Requesting manga info for mangaId: $mangaId")

        // Fetch Manga Information
        viewModel.getMangaInfo(mangaId) { title, description, coverArtId ->
            Log.d("SimpleMangaCard", "Fetched Title: $title, Description: $description")

            // Update manga info with title, description, and cover URL
            mangaInfo = MangaInfo(
                data = MangaData(
                    id = mangaId,
                    type = "manga",
                    attributes = MangaAttributes(
                        title = mapOf("en" to title),  // Keep "en" for the title
                        description = mapOf("en" to description)  // Keep "en" for the description
                    ),
                    relationships = emptyList() // You can modify this if you want to handle relationships
                )
            )


            // Fetch the cover using the coverArtId if available
            if (coverArtId != null) {
                Log.d("SimpleMangaCard", "Fetching cover art for coverId: $coverArtId")
                viewModel.fetchCoverArt(coverArtId,mangaId)
            }
        }
    }


    LaunchedEffect(Unit){
        viewModel.fetchCoverArt(coverUrl,mangaId)
    }

    // Log the current cover URL and error message
    Log.d("SimpleMangaCard", "Current coverUrl: $coverUrl")
    Log.d("SimpleMangaCard", "Error message: $errorMessage")

    // Card layout
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(200.dp)
            .clickable { mangaInfo?.data?.id?.let { onClick(it) } },
        shape = RoundedCornerShape(8.dp),  // Rounded corners for the card
        elevation = CardDefaults.cardElevation(4.dp) // Small elevation for shadow effect
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Manga Cover Image or Placeholder
            if (coverUrl.isNotEmpty()) {
                Log.d("SimpleMangaCard", "Displaying manga cover with URL: $coverUrl")
             //  val coverUrlFinal = "https://uploads.mangadex.org/covers/${mangaId}/${coverUrl}"

                Log.e("urll",coverUrl)
                MangaCoverCard(
                    coverId = coverUrl,  // Use the cover URL or cover ID dynamically
                    mangaId = mangaId,
                    viewModel = viewModel,
                    onClick = { /* Handle click here */ }
                )
            } else {
                // Fallback or placeholder when cover is missing
                Log.d("SimpleMangaCard", "No cover available, showing fallback.")
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    Text(
                        text = "No Cover Available",
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            // Title Text
            val title = mangaInfo?.data?.attributes?.title?.get("en") ?: "No Title"
            Log.d("SimpleMangaCard", "Displaying manga title: $title")
            Text(
                text = title,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp),
                fontWeight = FontWeight.Bold,
                maxLines = 2
            )
        }
    }
}


@Composable
fun MangaCoverCard(
    coverId: String,
    mangaId: String,
    viewModel: MangaViewModel,
    onClick: (String) -> Unit
) {


    // Card layout
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onClick(coverId) },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Display cover or a placeholder
            SubcomposeAsyncImage(
                model = coverId,
                contentDescription = "Manga Cover",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                loading = {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                },
                error = {
                    Icon(
                        imageVector = Icons.Rounded.Warning,
                        contentDescription = "Error loading image",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            )
        }
    }
}








