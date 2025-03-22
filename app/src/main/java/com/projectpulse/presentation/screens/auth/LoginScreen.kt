package com.projectpulse.presentation.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.projectpulse.R
import com.projectpulse.domain.model.UserRole
import com.projectpulse.presentation.components.PasswordTextField
import com.projectpulse.presentation.components.ProjectPulseButton
import com.projectpulse.presentation.components.ProjectPulseTextField
import com.projectpulse.presentation.components.TextButton

@Composable
fun LoginScreen(
    onNavigateToSignUp: () -> Unit,
    onNavigateToAdmin: () -> Unit,
    onNavigateToProjectManager: () -> Unit,
    onNavigateToTeamMember: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val authState by viewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        when (authState) {
            is AuthUiState.Success -> {
                when ((authState as AuthUiState.Success).user.role) {
                    UserRole.ADMIN -> onNavigateToAdmin()
                    UserRole.PROJECT_MANAGER -> onNavigateToProjectManager()
                    UserRole.TEAM_MEMBER -> onNavigateToTeamMember()
                }
            }
            is AuthUiState.Error -> {
                showError = true
                errorMessage = (authState as AuthUiState.Error).message
            }
            else -> {
                showError = false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo and Title
        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "App Logo",
            modifier = Modifier.size(120.dp)
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Project Pulse",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            ),
            color = MaterialTheme.colorScheme.primary
        )
        
        Text(
            text = "Please sign in to continue",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Error message
        if (showError) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        // Login Form
        ProjectPulseTextField(
            value = email,
            onValueChange = { 
                email = it
                showError = false
            },
            label = "Email",
            placeholder = "Enter your email address",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            isError = showError
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        PasswordTextField(
            value = password,
            onValueChange = { 
                password = it
                showError = false
            },
            label = "Password",
            isError = showError
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        TextButton(
            onClick = { 
                if (email.isNotBlank()) {
                    viewModel.resetPassword(email)
                }
            },
            text = "Forgot Password?",
            modifier = Modifier.align(Alignment.End)
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        ProjectPulseButton(
            onClick = {
                viewModel.signIn(email, password)
            },
            enabled = email.isNotBlank() && password.isNotBlank() && authState !is AuthUiState.Loading,
            text = if (authState is AuthUiState.Loading) "Signing in..." else "Sign In"
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Don't have an account?",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            TextButton(
                onClick = onNavigateToSignUp,
                text = "Sign Up"
            )
        }
    }
} 