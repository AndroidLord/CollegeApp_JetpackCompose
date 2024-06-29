package com.example.collegeapp_jetpackcompose.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.collegeapp_jetpackcompose.itemview.FacultyItemView
import com.example.collegeapp_jetpackcompose.navigation.Routes
import com.example.collegeapp_jetpackcompose.viewmodel.FacultyViewModel

@Composable
fun Faculty(navController: NavHostController) {

    val facultyViewModel : FacultyViewModel = viewModel()
    val categoryList by facultyViewModel.categoryList.observeAsState(null)
    facultyViewModel.getCategory()

    LazyColumn {

        items(categoryList ?: emptyList()) {
            FacultyItemView(
                category = it,
                delete = {
                    facultyViewModel.deleteCategory(it)
                },
                onClick = {
                    val route = Routes.FacultyDetailScreen.route.replace(
                        "{category}",
                        it
                    )
                    println("Route: $route")
                    navController.navigate(route)
                })
        }
    }

}