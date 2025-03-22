package com.projectpulse.domain.model

enum class UserRole {
    ADMIN,
    PROJECT_MANAGER,
    TEAM_MEMBER
}

data class User(
    val id: String,
    val email: String,
    val fullName: String,
    val role: UserRole,
    val profilePictureUrl: String? = null
) 