package com.example.mangachaduvu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mangachaduvu.composables.grid.SimpleMangaCard
import com.example.mangachaduvu.mangadexapi.MangaViewModel
import com.example.mangachaduvu.ui.theme.AnimeGirlProfilePicturesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel: MangaViewModel = MangaViewModel()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimeGirlProfilePicturesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    );
                    val list:List<String> = listOf("https://drive.google.com/uc?export=view&id=1cFGLagaoUzqk0P3AVDYSUt-0i1o-VhAB","https://drive.google.com/uc?export=view&id=1cFGLagaoUzqk0P3AVDYSUt-0i1o-VhAB","https://drive.google.com/uc?export=view&id=1cFGLagaoUzqk0P3AVDYSUt-0i1o-VhAB","https://drive.google.com/uc?export=view&id=1cFGLagaoUzqk0P3AVDYSUt-0i1o-VhAB","https://drive.google.com/uc?export=view&id=1cFGLagaoUzqk0P3AVDYSUt-0i1o-VhAB","https://drive.google.com/uc?export=view&id=1cFGLagaoUzqk0P3AVDYSUt-0i1o-VhAB","https://drive.google.com/uc?export=view&id=1cFGLagaoUzqk0P3AVDYSUt-0i1o-VhAB","https://drive.google.com/uc?export=view&id=1cFGLagaoUzqk0P3AVDYSUt-0i1o-VhAB","https://drive.google.com/uc?export=view&id=1cFGLagaoUzqk0P3AVDYSUt-0i1o-VhAB","https://drive.google.com/uc?export=view&id=1cFGLagaoUzqk0P3AVDYSUt-0i1o-VhAB","https://drive.google.com/uc?export=view&id=1cQWVCs77DNJXHalbZ9V86ZZBkl1KtXe5","https://drive.google.com/uc?export=view&id=1cFGLagaoUzqk0P3AVDYSUt-0i1o-VhAB","https://drive.google.com/uc?export=view&id=1cQWVCs77DNJXHalbZ9V86ZZBkl1KtXe5","https://drive.google.com/uc?export=view&id=1cFGLagaoUzqk0P3AVDYSUt-0i1o-VhAB","https://drive.google.com/uc?export=view&id=1cQWVCs77DNJXHalbZ9V86ZZBkl1KtXe5") // Placeholder data for now
//                ImageGrid(
//                    list,
//                    viewModel = viewModel
//                ){}
                    SimpleMangaCard(
                        mangaId = "9316b513-7adc-4c1e-9405-fb65ea8570bc",
                        viewModel = viewModel,
                        onClick = {}
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = false)
@Composable
fun GreetingPreview() {
    AnimeGirlProfilePicturesTheme {
        Greeting("Android")
    }
}

