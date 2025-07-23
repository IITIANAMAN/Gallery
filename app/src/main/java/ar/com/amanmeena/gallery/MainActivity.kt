package ar.com.amanmeena.gallery


import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ar.com.amanmeena.gallery.ui.theme.GalleryTheme

class MainActivity : ComponentActivity() {
    private val mediaReader by lazy {
        MediaReader(applicationContext)
    }

    private val viewModel by viewModels<MainViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MainViewModel(mediaReader) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        val permissions = if(Build.VERSION.SDK_INT >=33)
        {
            arrayOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VIDEO
            )

        }else
        {
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }

        ActivityCompat.requestPermissions(
            this,
            permissions,
            0
        )
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GalleryTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    MyAppNavigation(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel,
                    )

                }
            }
        }
    }
}

