package com.example.mangachaduvu.composables.grid

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mangachaduvu.mangadexapi.MangaViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageGrid(
    imageUrls: List<String>,
    viewModel: MangaViewModel,
    onDownloadClick: (String) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(150.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),  // Reduced padding for tighter layout
        horizontalArrangement = Arrangement.spacedBy(4.dp),  // Reduced spacing
        verticalItemSpacing = 4.dp  // Reduced spacing
    ) {
        items(imageUrls.size) { index ->
//            ImageCard(
//                imageUrl = imageUrls[index],
//                onDownloadClick = onDownloadClick
//            )
            SimpleMangaCard(
                mangaId = "a2f404b3-5b83-42e1-b775-50ed21ff595d",
                viewModel = viewModel,
                onClick = onDownloadClick
            )
        }
    }
}