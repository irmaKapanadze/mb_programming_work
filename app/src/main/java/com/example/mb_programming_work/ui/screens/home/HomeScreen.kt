package com.example.mb_programming_work.ui.screens.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.mb_programming_work.R
import com.example.mb_programming_work.ui.screens.home.model.Movie
import com.example.mb_programming_work.ui.theme.MyTheme
import com.example.mb_programming_work.ui.theme.components.MyAppTextField
import com.example.mb_programming_work.vm.HomeViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MyTheme.colors.background)
    ) {
        if (state.selectedMovie != null) {
            ModalBottomSheet(
                onDismissRequest = { viewModel.onEvent(HomeEvent.OnMovieClick(null)) },
                containerColor = MyTheme.colors.background,
                modifier = Modifier.fillMaxWidth()
            ) {
                BottomSheet(item = state.selectedMovie!!)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            MyAppTextField(
                value = state.searchQuery,
                onValueChange = { viewModel.onEvent(HomeEvent.OnSearchQueryChange(it)) },
                placeholder = stringResource(R.string.search_movies),
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            if (state.isSearchActive) viewModel.onEvent(HomeEvent.OnClearSearch)
                        }
                    ) {
                        Icon(
                            painter = painterResource(
                                if (state.isSearchActive) android.R.drawable.ic_menu_close_clear_cancel
                                else R.drawable.search_button_svgrepo_com
                            ),
                            contentDescription = null,
                            tint = MyTheme.colors.onBackground
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (state.isSearchActive) {
                val searchedMovies = state.movies.filter {
                    it.title.contains(state.searchQuery, ignoreCase = true)
                }

                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(items = searchedMovies, key = { it.id }) { item ->
                        MovieItem(
                            item = item,
                            isSelected = state.favouriteMovies.contains(item),
                            onFavouriteClick = { viewModel.onEvent(HomeEvent.OnFavouriteClick(item)) },
                            onItemClick = { viewModel.onEvent(HomeEvent.OnMovieClick(item)) }
                        )
                    }
                }
            } else {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(items = MovieGenre.entries.toTypedArray(), key = { it.name }) { item ->
                        CategoryItem(
                            item = item,
                            isSelected = item == state.selectedCategory,
                            onClick = { viewModel.onEvent(HomeEvent.OnCategoryClick(item)) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                val filteredMovies = if (state.selectedCategory == MovieGenre.ALL) {
                    state.movies
                } else {
                    state.movies.filter { movie ->
                        movie.genre.contains(state.selectedCategory.name, ignoreCase = true)
                    }
                }

                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(items = filteredMovies, key = { it.id }) { item ->
                        MovieItem(
                            item = item,
                            isSelected = state.favouriteMovies.contains(item),
                            onFavouriteClick = { viewModel.onEvent(HomeEvent.OnFavouriteClick(item)) },
                            onItemClick = { viewModel.onEvent(HomeEvent.OnMovieClick(item)) }
                        )
                    }
                }
            }
        }
    }
}

enum class MovieGenre(@StringRes val titleRes: Int) {
    ALL(R.string.genre_all),
    ACTION(R.string.genre_action),
    COMEDY(R.string.genre_comedy),
    DRAMA(R.string.genre_drama),
    THRILLER(R.string.genre_thriller),
    FANTASY(R.string.genre_fantasy),
    ROMANCE(R.string.genre_romance)
}

@Composable
fun CategoryItem(
    item: MovieGenre,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (isSelected) MyTheme.colors.onBackground else MyTheme.colors.border
            )
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = item.titleRes),
            style = MyTheme.typography.bodyLarge,
            color = if (isSelected) MyTheme.colors.background else MyTheme.colors.onBackground
        )
    }
}

@Composable
fun MovieItem(
    item: Movie,
    isSelected: Boolean,
    onFavouriteClick: () -> Unit,
    onItemClick: () -> Unit,
) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable(
                onClick = {
                    onItemClick()
                }
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 48.dp)
        ) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = null,
                modifier = Modifier.size(125.dp),
                placeholder = painterResource(R.drawable.ic_launcher_background)
            )
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    style = MyTheme.typography.bodyLarge,
                    color = MyTheme.colors.onBackground
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.duration),
                    style = MyTheme.typography.bodySmallest,
                    color = MyTheme.colors.onBackground
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = item.duration,
                    style = MyTheme.typography.bodySmallest,
                    color = MyTheme.colors.onBackground
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.release_year),
                    style = MyTheme.typography.bodySmallest,
                    color = MyTheme.colors.onBackground
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = item.releaseYear.toString(),
                    style = MyTheme.typography.bodySmallest,
                    color = MyTheme.colors.onBackground
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.rating),
                    style = MyTheme.typography.bodySmallest,
                    color = MyTheme.colors.onBackground
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = item.rating.toString(),
                    style = MyTheme.typography.bodySmallest,
                    color = MyTheme.colors.onBackground
                )
            }
        }
        IconButton(
            onClick = {
                onFavouriteClick()
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(4.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.heart_shape_svgrepo_com),
                null,
                tint = if (isSelected) {
                    MyTheme.colors.destructiveColor
                } else {
                    MyTheme.colors.border
                },
                modifier = Modifier
                    .padding(12.dp)
                    .size(28.dp)
            )
        }
    }
}


@Composable
fun BottomSheet(
    item: Movie,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 600.dp)
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = item.imageUrl,
            contentDescription = "movie poster",
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            placeholder = painterResource(R.drawable.ic_launcher_background)
        )

        Spacer(Modifier.height(16.dp))

        Column(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            InfoCard(
                item
            )

            Spacer(Modifier.height(20.dp))

            Text(
                text = item.title,
                style = MyTheme.typography.headlineMedium,
                color = MyTheme.colors.onBackground
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = stringResource(R.string.genre),
                style = MyTheme.typography.bodyMedium,
                color = MyTheme.colors.onBackground
            )

            Spacer(Modifier.width(4.dp))

            Text(
                text = item.genre,
                style = MyTheme.typography.bodyMedium,
                color = MyTheme.colors.onBackground
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.description),
                style = MyTheme.typography.bodyMedium,
                color = MyTheme.colors.onBackground
            )

            Spacer(Modifier.width(4.dp))

            Text(
                text = item.description,
                style = MyTheme.typography.bodyMedium,
                color = MyTheme.colors.onBackground
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.cast),
                style = MyTheme.typography.bodyMedium,
                color = MyTheme.colors.onBackground
            )

            Spacer(Modifier.width(4.dp))

            Text(
                text = item.cast.joinToString(separator = ", "),
                style = MyTheme.typography.bodyMedium,
                color = MyTheme.colors.textSecondary
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = stringResource(R.string.watch_trailer),
                style = MyTheme.typography.bodyLarge,
                color = MyTheme.colors.onBackground
            )

            Spacer(Modifier.height(4.dp))

            if (item.videoUrl.isNotEmpty()) {
                val videoId = extractYoutubeVideoId(item.videoUrl)

                AndroidView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    factory = { context ->
                        YouTubePlayerView(context).apply {
                            addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                                override fun onReady(youTubePlayer: YouTubePlayer) {
                                    youTubePlayer.cueVideo(videoId, 0f)
                                }
                            })
                        }
                    }
                )
            } else {
                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = "movie poster",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    placeholder = painterResource(R.drawable.ic_launcher_background)
                )
            }
        }
    }
}

@Composable
fun InfoCard(item: Movie) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, MyTheme.colors.border, RoundedCornerShape(16.dp))
            .background(MyTheme.colors.border.copy(alpha = 0.2f))
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.weight(1f))
        InfoItem(
            stringResource(R.string.duration),
            item.duration,
            R.drawable.sand_clock_svgrepo_com
        )
        Spacer(Modifier.weight(1f))
        InfoItem(
            stringResource(R.string.release_year),
            item.releaseYear.toString(),
            R.drawable.calendar_svgrepo_com
        )
        Spacer(Modifier.weight(1f))
        InfoItem(
            stringResource(R.string.rating),
            item.rating.toString(),
            R.drawable.rating_svgrepo_com
        )
        Spacer(Modifier.weight(1f))
    }
}

@Composable
fun InfoItem(title: String, value: String, @DrawableRes icon: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = MyTheme.colors.onBackground,
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = title,
            color = MyTheme.colors.onBackground,
            style = MyTheme.typography.bodySmallest
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = value,
            color = MyTheme.colors.onBackground,
            style = MyTheme.typography.bodyLarge
        )
    }
}

private fun extractYoutubeVideoId(url: String): String {
    return when {
        url.contains("v=") -> url.substringAfter("v=").substringBefore("&")
        url.contains("youtu.be/") -> url.substringAfter("youtu.be/").substringBefore("?")
        else -> url
    }
}

@Composable
@Preview
private fun HomeScreenPreview() {
    MyTheme {
        HomeScreen()
    }
}