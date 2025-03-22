package com.projectpulse.domain.repository

import com.projectpulse.domain.model.User
import com.projectpulse.domain.model.UserRole
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Result<User>
    
    suspend fun signUp(
        email: String,
        password: String,
        fullName: String,
        role: UserRole
    ): Result<User>
    
    suspend fun signOut()
    
    suspend fun getCurrentUser(): User?
    
    fun observeAuthState(): Flow<User?>
    
    suspend fun resetPassword(email: String): Result<Unit>
} 