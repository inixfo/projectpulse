package com.projectpulse.presentation.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectpulse.domain.model.User
import com.projectpulse.domain.model.UserRole
import com.projectpulse.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AuthUiState {
    object Initial : AuthUiState()
    object Loading : AuthUiState()
    data class Error(val message: String) : AuthUiState()
    data class Success(val user: User) : AuthUiState()
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthUiState>(AuthUiState.Initial)
    val authState: StateFlow<AuthUiState> = _authState.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.observeAuthState().collect { user ->
                user?.let {
                    _authState.value = AuthUiState.Success(it)
                }
            }
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthUiState.Loading
            try {
                val result = authRepository.signIn(email, password)
                result.fold(
                    onSuccess = { user ->
                        _authState.value = AuthUiState.Success(user)
                    },
                    onFailure = { exception ->
                        _authState.value = AuthUiState.Error(exception.message ?: "Sign in failed")
                    }
                )
            } catch (e: Exception) {
                _authState.value = AuthUiState.Error(e.message ?: "Sign in failed")
            }
        }
    }

    fun signUp(
        email: String,
        password: String,
        fullName: String,
        role: String
    ) {
        viewModelScope.launch {
            _authState.value = AuthUiState.Loading
            try {
                val userRole = when (role) {
                    "Project Manager" -> UserRole.PROJECT_MANAGER
                    else -> UserRole.TEAM_MEMBER
                }
                
                val result = authRepository.signUp(email, password, fullName, userRole)
                result.fold(
                    onSuccess = { user ->
                        _authState.value = AuthUiState.Success(user)
                    },
                    onFailure = { exception ->
                        _authState.value = AuthUiState.Error(exception.message ?: "Sign up failed")
                    }
                )
            } catch (e: Exception) {
                _authState.value = AuthUiState.Error(e.message ?: "Sign up failed")
            }
        }
    }

    fun resetPassword(email: String) {
        viewModelScope.launch {
            _authState.value = AuthUiState.Loading
            try {
                val result = authRepository.resetPassword(email)
                result.fold(
                    onSuccess = {
                        _authState.value = AuthUiState.Initial
                    },
                    onFailure = { exception ->
                        _authState.value = AuthUiState.Error(exception.message ?: "Password reset failed")
                    }
                )
            } catch (e: Exception) {
                _authState.value = AuthUiState.Error(e.message ?: "Password reset failed")
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
            _authState.value = AuthUiState.Initial
        }
    }
} 