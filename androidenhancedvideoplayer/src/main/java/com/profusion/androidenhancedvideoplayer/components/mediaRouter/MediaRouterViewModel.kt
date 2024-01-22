package com.profusion.androidenhancedvideoplayer.components.mediaRouter

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.mediarouter.app.MediaRouteActionProvider
import androidx.mediarouter.media.MediaControlIntent
import androidx.mediarouter.media.MediaRouteSelector

class MediaRouterViewModel(context: Context) : ViewModel() {
    private val mediaRouteActionProvider = MediaRouteActionProvider(context)

    init {
        mediaRouteActionProvider.onCreateMediaRouteButton()
        mediaRouteActionProvider.routeSelector = MediaRouteSelector.Builder()
            .addControlCategory(MediaControlIntent.CATEGORY_REMOTE_PLAYBACK)
            .build()
    }

    fun mediaRouteButton(): View {
        return mediaRouteActionProvider.onCreateActionView()
    }
}
