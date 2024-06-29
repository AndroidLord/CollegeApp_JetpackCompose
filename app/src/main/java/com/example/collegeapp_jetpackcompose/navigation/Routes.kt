package com.example.collegeapp_jetpackcompose.navigation

sealed class Routes(val route: String) {

    // User routes
    object Home: Routes("home")
    object Faculty: Routes("faculty")
    object Gallery: Routes("gallery")
    object AboutUs: Routes("about_us")
    object BottomNav: Routes("bottom_nav")

    // Admin routes
    object AdminDashboard: Routes("admin_dashboard")
    object ManageFaculty: Routes("manage_faculty")
    object ManageBanner: Routes("manage_banner")
    object ManageGallery: Routes("manage_gallery")
    object ManageCollegeInfo: Routes("college_info")

    // Notice
    object ManageNotice: Routes("manage_notice")

    // Faculty Detail Screen
    object FacultyDetailScreen: Routes("faculty_detail/{category}")
}