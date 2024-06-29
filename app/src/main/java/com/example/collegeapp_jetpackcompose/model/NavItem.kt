package com.example.collegeapp_jetpackcompose.model

import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val title: String,
    val icon: Int
)

data class BottomNavItem(
    val title: String,
    val icon: Int,
    val route: String
)
