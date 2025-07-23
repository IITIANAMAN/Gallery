package ar.com.amanmeena.gallery

import kotlin.toString
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
@Composable

fun MediaFileShow(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    navController: NavHostController,
    folderName: String
) {
    var filteredFiles = viewModel.files.filter { it.path.toString().contains(if(folderName == "All") "" else folderName)}
    if(folderName == "Favourite")
    {
        filteredFiles = viewModel.favourites
    }

    val filesInRows = filteredFiles.chunked(3)
    val imageUris = filteredFiles.map { it.uri }


    LazyColumn(
        modifier = Modifier.fillMaxSize().statusBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(filesInRows) { rowFiles ->
            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                rowFiles.forEach { file ->

                    MediaFileItem(
                        modifier = Modifier.weight(1f),
                        File = file,
                        navController = navController,
                        path = file.path.toString(),
                        viewModel = viewModel,
                        imageUris = imageUris
                    )



                }

                // Add empty Spacers to balance the row
//                repeat(4 - rowFiles.size) {
//                    Spacer(modifier = Modifier.weight(1f))
//                }
            }
        }
    }

}




@Composable
fun MediaFileItem(
    modifier: Modifier = Modifier,
    File: MediaFile,
    navController: NavHostController,
    path: String,
    viewModel: MainViewModel,
    imageUris: List<android.net.Uri>
) {


    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (File.type == MediaType.IMAGE) {
            val encodedUri = URLEncoder.encode(File.uri.toString(), StandardCharsets.UTF_8.toString())
            if(File.path == path)
            {
                AsyncImage(
                    model = File.uri,
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                        .clickable {
                            val index = imageUris.indexOf(File.uri)
                            viewModel.setImageUris(imageUris)
                            navController.navigate("imagePage/$index")
                        }.padding(5.dp),
                    contentScale = ContentScale.Crop
                )
            }

        }
    }
}

