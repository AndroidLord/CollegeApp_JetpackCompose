package com.example.collegeapp_jetpackcompose.admin.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.collegeapp_jetpackcompose.model.DashBoardItemModel
import com.example.collegeapp_jetpackcompose.navigation.Routes
import com.example.collegeapp_jetpackcompose.ui.theme.Purple80
import com.example.collegeapp_jetpackcompose.ui.theme.PurpleGrey40
import com.example.collegeapp_jetpackcompose.ui.theme.PurpleGrey80
import com.example.collegeapp_jetpackcompose.ui.theme.TITLE_SIZE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboard(navController: NavHostController) {

    val list =  listOf(
        DashBoardItemModel("Manage Banner", Routes.ManageBanner.route),
        DashBoardItemModel("Manage Faculty", Routes.ManageFaculty.route),
        DashBoardItemModel("Manage Gallery", Routes.ManageGallery.route),
        DashBoardItemModel("Manage College Info", Routes.ManageCollegeInfo.route),
        DashBoardItemModel("Manage Notice", Routes.ManageNotice.route)
    )

    Scaffold(
        topBar = {

            TopAppBar(title = {
                Text(text = "Admin Dashboard")
            }

            )

        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {

            items(list.size) { index ->

                val item = list[index]

                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        navController.navigate(item.route)
                    }
                ) {
                    Text(
                        text = item.title,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = TITLE_SIZE
                    )
                }
            }

        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun prev_adminDashboard() {

    AdminDashboard(navController = rememberNavController())

}