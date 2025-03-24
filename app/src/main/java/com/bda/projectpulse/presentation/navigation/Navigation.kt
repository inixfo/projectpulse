package com.bda.projectpulse.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bda.projectpulse.presentation.screens.auth.LoginScreen
import com.bda.projectpulse.presentation.screens.auth.SignUpScreen
import com.bda.projectpulse.presentation.screens.splash.SplashScreen

@Composable
fun Navigation(
    navController: NavHostController,
    startDestination: String = Screen.Splash.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        
        // Login screen
        composable(route = Screen.Login.route) {
            LoginScreen(
                onNavigateToSignUp = {
                    navController.navigate(Screen.SignUp.route)
                },
                onNavigateToAdmin = {
                    navController.navigate(Screen.AdminDashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToProjectManager = {
                    navController.navigate(Screen.ProjectManagerDashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToTeamMember = {
                    navController.navigate(Screen.TeamMemberDashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        
        // Sign up screen
        composable(route = Screen.SignUp.route) {
            SignUpScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.SignUp.route) { inclusive = true }
                    }
                },
                onSignUpSuccess = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.SignUp.route) { inclusive = true }
                    }
                }
            )
        }
        
        // Admin Dashboard
        composable(route = Screen.AdminDashboard.route) {
            // TODO: Implement admin dashboard
        }
        
        // Project Manager Dashboard
        composable(route = Screen.ProjectManagerDashboard.route) {
            // TODO: Implement PM dashboard
        }
        
        // Team Member Dashboard
        composable(route = Screen.TeamMemberDashboard.route) {
            // TODO: Implement team member dashboard
        }
        
        // Project List
        composable(route = Screen.ProjectList.route) {
            // TODO: Implement project list
        }
        
        // Project Details
        composable(
            route = Screen.ProjectDetails.route
        ) {
            // TODO: Implement project details
        }
        
        // Create Project
        composable(route = Screen.CreateProject.route) {
            // TODO: Implement create project
        }
        
        // Task List
        composable(route = Screen.TaskList.route) {
            // TODO: Implement task list
        }
        
        // Task Details
        composable(
            route = Screen.TaskDetails.route
        ) {
            // TODO: Implement task details
        }
        
        // Create Task
        composable(route = Screen.CreateTask.route) {
            // TODO: Implement create task
        }
        
        // User Management
        composable(route = Screen.UserManagement.route) {
            // TODO: Implement user management
        }
        
        // Settings
        composable(route = Screen.Settings.route) {
            // TODO: Implement settings
        }
        
        // AI Generator
        composable(route = Screen.AIGenerator.route) {
            // TODO: Implement AI generator
        }
        
        // Project Chat
        composable(
            route = Screen.ProjectChat.route
        ) {
            // TODO: Implement project chat
        }
    }
} 