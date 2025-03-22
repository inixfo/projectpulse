package com.bda.projectpulse.presentation.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object AdminDashboard : Screen("admin_dashboard")
    object ProjectManagerDashboard : Screen("pm_dashboard")
    object TeamMemberDashboard : Screen("team_dashboard")
    object ProjectList : Screen("projects")
    object ProjectDetails : Screen("project/{projectId}") {
        fun createRoute(projectId: String) = "project/$projectId"
    }
    object CreateProject : Screen("create_project")
    object EditProject : Screen("edit_project/{projectId}") {
        fun createRoute(projectId: String) = "edit_project/$projectId"
    }
    object TaskList : Screen("tasks")
    object TaskDetails : Screen("task/{taskId}") {
        fun createRoute(taskId: String) = "task/$taskId"
    }
    object CreateTask : Screen("create_task")
    object UserManagement : Screen("users")
    object Settings : Screen("settings")
    object AIGenerator : Screen("ai_generator")
    object ProjectChat : Screen("chat/{projectId}") {
        fun createRoute(projectId: String) = "chat/$projectId"
    }
} 