package com.example.mb_programming_work.ui.screens.register

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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mb_programming_work.R
import com.example.mb_programming_work.ui.theme.MyTheme
import com.example.mb_programming_work.ui.theme.components.MyAppButton
import com.example.mb_programming_work.ui.theme.components.MyAppPasswordField
import com.example.mb_programming_work.ui.theme.components.MyAppTextField
import com.example.mb_programming_work.vm.RegisterViewModel

@Composable
fun RegisterScreen(
    onBackClick: () -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier,
    viewModel: RegisterViewModel = viewModel(),
) {
    val context = LocalContext.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    var isPasswordVisible by remember { mutableStateOf(false) }
    var isPassword2Visible by remember { mutableStateOf(false) }

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            onRegisterClick()
        }
    }
    Column(
        modifier = modifier
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
            text = stringResource(R.string.register),
            style = MyTheme.typography.headlineLarge,
            color = MyTheme.colors.onBackground
        )

        Spacer(modifier = Modifier.height(24.dp))

        MyAppTextField(
            value = state.email,
            onValueChange = { newValue ->
                viewModel.onEvent(RegisterEvent.OnEmailChange(newValue))
            },
            placeholder = stringResource(R.string.email),
            keyboardType = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password
        MyAppPasswordField(
            value = state.password,
            isPasswordVisible = isPasswordVisible,
            onPasswordChange = { newValue ->
                viewModel.onEvent(RegisterEvent.OnPasswordChange(newValue))
            },
            onIconClick = {
                isPasswordVisible = !isPasswordVisible
            },
            placeholder = stringResource(R.string.password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Repeat password
        MyAppPasswordField(
            value = state.password2,
            isPasswordVisible = isPassword2Visible,
            onPasswordChange = { newValue ->
                viewModel.onEvent(RegisterEvent.OnPassword2Change(newValue))
            },
            onIconClick = {
                isPassword2Visible = !isPassword2Visible
            },
            placeholder = stringResource(R.string.repeat_password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Register button
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    color = MyTheme.colors.accent,
                    modifier = Modifier.size(48.dp)
                )
            } else {
                MyAppButton(
                    text = stringResource(R.string.register).uppercase(),
                    onClick = {
                        viewModel.onEvent(RegisterEvent.OnRegisterClick(context))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
@Preview
fun RegisterScreenPreview() {
    MyTheme {
        RegisterScreen({}, {}, Modifier)
    }
}