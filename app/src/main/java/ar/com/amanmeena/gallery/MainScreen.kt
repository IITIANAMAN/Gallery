package ar.com.amanmeena.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage


@Composable
fun MainScreen(modifier: Modifier = Modifier, navController: NavController, viewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(10.dp)
    ) {
        Scaffold(topBar = {
            Text(
                text = "Gallery",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.padding(bottom = 16.dp)
            )

        }) {
            val folderNames = listOf("Camera","WhatsApp", "Download", "Screenshots", "Telegram", "Favourite","All")

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize().padding(it),
                contentPadding = PaddingValues(4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(folderNames) { folder ->
                    FolderCard(folderName = folder,navController,viewModel)
                }
            }
        }
        }
        // Top App Bar Title



}

@Composable
fun FolderCard(folderName: String, navController: NavController, viewModel: MainViewModel) {
    val filteredFiles = viewModel.files.filter {
        it.path.toString().contains(if (folderName == "All") "" else folderName)
    }

    val imageUris = filteredFiles.map { it.uri }

    Card(
        modifier = Modifier
            .height(165.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate("folder/$folderName")
            }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            if (imageUris.isNotEmpty()) {
                AsyncImage(
                    model = imageUris[0],
                    contentDescription = "CardImage",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    contentScale = ContentScale.Crop
                )
            }else if(viewModel.favourites.isNotEmpty())
            {
               AsyncImage(
                   model = viewModel.favourites[0].uri,
                   contentDescription = "First favourite image",
                   modifier = Modifier
                       .fillMaxWidth()
                       .height(120.dp),
                   contentScale = ContentScale.Crop
               )
            }  else{
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No Image", fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = folderName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(4.dp)
            )
        }
    }

}
