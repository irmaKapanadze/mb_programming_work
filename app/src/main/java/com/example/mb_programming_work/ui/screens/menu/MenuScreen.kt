package com.example.mb_programming_work.ui.screens.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mb_programming_work.R
import com.example.mb_programming_work.ui.theme.MyTheme
import com.example.mb_programming_work.ui.theme.components.AppButtonType
import com.example.mb_programming_work.ui.theme.components.MyAppButton

@Composable
fun MenuScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MyTheme.colors.background)
                .padding(vertical = 40.dp, horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Row(
                Modifier
                    .offset((0).dp, (-32).dp)
                    .align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    Image(
                        painter = painterResource(R.drawable.movie_clapperboard_part_2_svgrepo_com),
                        contentDescription = null,
                        modifier = Modifier.wrapContentSize()
                    )
                }

                Spacer(modifier = Modifier.width(24.dp))

                Text(
                    text = stringResource(R.string.app_name),
                    style = MyTheme.typography.headlineLarge,
                    color = MyTheme.colors.onBackground
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                MyAppButton(
                    text = stringResource(R.string.login),
                    onClick = {
                        onLoginClick()
                    },
                    type = AppButtonType.Outlined,
                    modifier = Modifier
                        .height(52.dp)
                        .weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                MyAppButton(
                    text = stringResource(R.string.register),
                    onClick = {
                        onRegisterClick()
                    },
                    type = AppButtonType.Outlined,
                    modifier = Modifier
                        .height(52.dp)
                        .weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuScreenPreview() {
    MyTheme {
        MenuScreen({}, {})
    }
}