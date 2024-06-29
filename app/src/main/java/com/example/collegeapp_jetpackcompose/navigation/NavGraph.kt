package com.example.collegeapp_jetpackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.collegeapp_jetpackcompose.admin.screens.AdminDashboard
import com.example.collegeapp_jetpackcompose.admin.screens.FacultyDetailScreen
import com.example.collegeapp_jetpackcompose.admin.screens.ManageBanner
import com.example.collegeapp_jetpackcompose.admin.screens.ManageCollegeInfo
import com.example.collegeapp_jetpackcompose.admin.screens.ManageFaculty
import com.example.collegeapp_jetpackcompose.admin.screens.ManageGallery
import com.example.collegeapp_jetpackcompose.admin.screens.ManageNotice
import com.example.collegeapp_jetpackcompose.screens.AboutUs
import com.example.collegeapp_jetpackcompose.screens.BottomNav
import com.example.collegeapp_jetpackcompose.screens.Faculty
import com.example.collegeapp_jetpackcompose.screens.Gallery
import com.example.collegeapp_jetpackcompose.screens.Home
import com.example.collegeapp_jetpackcompose.utils.Constants.IS_ADMIN

@Composable
fun NavGraph(navController: NavHostController) {


    NavHost(
        navController = navController,
        startDestination = if(IS_ADMIN) Routes.AdminDashboard.route else Routes.BottomNav.route
    ){

        composable(Routes.BottomNav.route){
           BottomNav(navController)
        }


        composable(Routes.Home.route){
            Home(navController)
        }

        composable(Routes.Faculty.route){
            Faculty(navController)
        }

        composable(Routes.Gallery.route){
            Gallery(navController)
        }

        composable(Routes.AboutUs.route){
            AboutUs(navController)
        }

        // Admin routes

        composable(Routes.AdminDashboard.route){
            AdminDashboard(navController)
        }

        composable(Routes.ManageFaculty.route){
             ManageFaculty(navController)
        }

        composable(Routes.ManageBanner.route){
             ManageBanner(navController)
        }

        composable(Routes.ManageGallery.route){
            ManageGallery(navController)
        }

        composable(Routes.ManageCollegeInfo.route){
            ManageCollegeInfo(navController)
        }

        composable(Routes.ManageNotice.route){
            ManageNotice(navController)
        }

        composable(Routes.FacultyDetailScreen.route){
            val data  = it.arguments!!.getString("category")
            FacultyDetailScreen(navController, data!!)
        }


    }

}