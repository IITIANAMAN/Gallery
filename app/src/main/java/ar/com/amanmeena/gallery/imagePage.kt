package ar.com.amanmeena.gallery

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagePage(viewModel: MainViewModel, startIndex: Int) {
    val context = LocalContext.current
    val imageUris = viewModel.imageUris
    val pagerState = rememberPagerState(initialPage = startIndex, pageCount = { imageUris.size })
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Image ${pagerState.currentPage + 1} of ${imageUris.size}",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                tonalElevation = 4.dp
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {

                    IconButton(onClick = {

                    }) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = "Share")
                    }

                    IconButton(onClick = {
                        Toast.makeText(context, "Delete feature coming soon", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                    }
                    val currentUri = imageUris[pagerState.currentPage]
                    val isFavourite = viewModel.isFavourite(currentUri)

                    IconButton(onClick = {
                        viewModel.toggleFavouriteByUri(currentUri)
                        val msg = if (viewModel.isFavourite(currentUri)) "Marked as Favorite!" else "Removed from Favorites"
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(
                            imageVector = if (isFavourite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "Favorite"
                        )
                    }


                    IconButton(onClick = {
                        Toast.makeText(context, "More info coming soon", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(imageVector = Icons.Default.Info, contentDescription = "Information")
                    }
                }
            }
        }
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
        ) {page->
            Box(
                modifier = Modifier
                    .fillMaxSize()

                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = imageUris[page],
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }

    }
}
