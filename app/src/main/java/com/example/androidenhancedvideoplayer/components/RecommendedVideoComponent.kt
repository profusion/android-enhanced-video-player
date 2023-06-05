package com.example.androidenhancedvideoplayer.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.androidenhancedvideoplayer.R

@Composable
fun RecommendedVideosComponent() {
    LazyColumn {
        items(5) {
            RecommendedVideo(videoTitle = "Mock Recommended Video $it")
        }
    }
}

@Composable
private fun RecommendedVideo(
    videoTitle: String = "Video Title",
    thumbnailResId: Int = R.drawable.ic_launcher_background,
    duration: String = "00:40"
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(thumbnailResId),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(shape = RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = videoTitle,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = duration,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
