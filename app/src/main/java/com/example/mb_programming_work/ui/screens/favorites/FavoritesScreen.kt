package com.example.mb_programming_work.ui.screens.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.mb_programming_work.R
import com.example.mb_programming_work.ui.screens.home.model.MovieUi
import com.example.mb_programming_work.ui.theme.MyTheme
import com.example.mb_programming_work.vm.FavoritesViewModel

@Composable
fun FavoritesScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MyTheme.colors.background)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 24.dp)
            ) {
                IconButton(
                    onClick = { onBackClick() },
                    Modifier.padding(4.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.left_arrow_alt_svgrepo_com),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MyTheme.colors.onBackground
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = stringResource(R.string.favorite_movies),
                    style = MyTheme.typography.headlineLarge,
                    color = MyTheme.colors.onBackground,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.personal_list),
                    style = MyTheme.typography.bodyMedium,
                    color = MyTheme.colors.onBackground.copy(alpha = 0.6f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MyTheme.colors.primary)
                }
            } else if (state.favoriteMovies.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Your favorite list is empty",
                        color = MyTheme.colors.onBackground.copy(alpha = 0.5f),
                        style = MyTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.favoriteMovies, key = { it.id }) { movie ->

                        MovieItem(
                            item = movie,
                            isFavorite = true,
                            onFavouriteClick =  {
                                viewModel.onEvent(
                                    FavoritesEvent.OnUnfavouriteClick(
                                        movie
                                    )
                                )
                            }
                        )

                    }
                    item { Spacer(modifier = Modifier.height(80.dp)) }
                }
            }
        }
    }
}

@Composable
fun MovieItem(
    item: MovieUi,
    isFavorite: Boolean,
    onFavouriteClick: () -> Unit
) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(end = 48.dp)) {
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
                tint = if (isFavorite) {
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
@Preview
fun FavoritesScreenPreview(){
    MyTheme{
        FavoritesScreen({})
    }
}

