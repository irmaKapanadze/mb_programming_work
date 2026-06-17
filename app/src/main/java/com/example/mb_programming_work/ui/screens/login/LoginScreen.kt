package com.example.mb_programming_work.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mb_programming_work.R
import com.example.mb_programming_work.ui.theme.MyTheme
import com.example.mb_programming_work.ui.theme.components.AppButtonType
import com.example.mb_programming_work.ui.theme.components.MyAppButton
import com.example.mb_programming_work.ui.theme.components.MyAppPasswordField
import com.example.mb_programming_work.ui.theme.components.MyAppTextField
import com.example.mb_programming_work.vm.LoginViewModel

@Composable
fun LoginScreen(
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel(),
) {
    val loginState by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(loginState.isSuccess) {
        if (loginState.isSuccess) {
            onLoginClick()
        }
    }

    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier
            .fillMaxSize()
            .background(MyTheme.colors.background)
            .padding(24.dp, 48.dp)
    ) {
        IconButton(
            onClick = {
                onBackClick()
            },
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
            text = stringResource(R.string.login),
            style = MyTheme.typography.headlineLarge,
            color = MyTheme.colors.onBackground
        )

        Spacer(modifier = Modifier.height(24.dp))

        MyAppTextField(
            value = loginState.email,
            onValueChange = { email ->
                viewModel.onEvent(LoginEvent.OnEmailChange(email))
            },
            placeholder = stringResource(R.string.email),
            keyboardType = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(16.dp))

        MyAppPasswordField(
            value = loginState.password,
            isPasswordVisible = isPasswordVisible,
            onPasswordChange = { password ->
                viewModel.onEvent(LoginEvent.OnPasswordChange(password))
            },
            onIconClick = {
                isPasswordVisible = !isPasswordVisible
            },
            placeholder = stringResource(R.string.password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(48.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if (loginState.isLoading) {
                CircularProgressIndicator(
                    color = MyTheme.colors.accent,
                    modifier = Modifier.size(48.dp)
                )
            } else {
                MyAppButton(
                    text = stringResource(R.string.login).uppercase(),
                    onClick = {
                        viewModel.onEvent(event = LoginEvent.OnLoginClick(context))
                    },
                    type = AppButtonType.Primary,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
@Preview
fun LoginScreenPreview() {
    MyTheme {
        LoginScreen({}, {})
    }
}