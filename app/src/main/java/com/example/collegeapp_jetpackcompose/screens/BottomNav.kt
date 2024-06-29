package com.example.collegeapp_jetpackcompose.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.collegeapp_jetpackcompose.R
import com.example.collegeapp_jetpackcompose.model.BottomNavItem
import com.example.collegeapp_jetpackcompose.model.NavItem
import com.example.collegeapp_jetpackcompose.navigation.Routes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNav(navController: NavHostController) {

    val navController1 = rememberNavController()

    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }


    val list = listOf(

        NavItem("Website", R.drawable.world),
        NavItem("Notice", R.drawable.notice),
        NavItem("Notes", R.drawable.notes),
        NavItem("Contact Us", R.drawable.contactmail),
        NavItem("Gallery", R.drawable.imagegallery)

    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {

            Column {
                Image(
                    painter = painterResource(id = R.drawable.yay),
                    contentDescription = "Profile Picture",
                    modifier = Modifier.height(200.dp),
                    contentScale = ContentScale.Crop
                )

                Divider()
                Text(text = "")

                list.forEachIndexed { index, navItem ->

                    NavigationDrawerItem(
                        label = { Text(text = navItem.title) },
                        selected = index == selectedItemIndex,
                        onClick = {

                            Toast.makeText(context, navItem.title, Toast.LENGTH_SHORT).show()

                            scope.launch {
                                drawerState.close()
                            }

                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = navItem.icon),
                                contentDescription = null,
                                modifier = Modifier.height(24.dp)
                            )
                        })

                    Spacer(modifier = Modifier.padding(5.dp))

                }
            }

        },
        content = {

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = "College App") },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(imageVector = Icons.Rounded.Menu, contentDescription = null)
                            }
                        },
                    )
                },
                bottomBar = {
                    MyBottomNav(navController = navController1)
                }
            ) { padding ->

                NavHost(
                    navController = navController1,
                    startDestination = Routes.Home.route,
                    modifier = Modifier.padding(padding)){

                    composable(Routes.Home.route){
                        Home(navController1)
                    }

                    composable(Routes.Faculty.route){
                        Faculty(navController)
                    }

                    composable(Routes.AboutUs.route){
                        AboutUs(navController1)
                    }

                    composable(Routes.Gallery.route){
                        Gallery(navController1)
                    }



                }
            }

        }
    )
}

@Composable
fun MyBottomNav(navController: NavController) {

    val backStackEntry = navController.currentBackStackEntryAsState()

    val list = listOf(
        BottomNavItem("Home", R.drawable.home, Routes.Home.route),
        BottomNavItem("Profile", R.drawable.graduate, Routes.Faculty.route),
        BottomNavItem("Gallery", R.drawable.imagegallery, Routes.Gallery.route),
        BottomNavItem("About Us", R.drawable.baseline_info_24, Routes.AboutUs.route)
    )

    BottomAppBar {
        list.forEach { item ->

            val curRoute = item.route

            val otherRoute = try {
                backStackEntry.value!!.destination.route
            } catch (e: Exception) {
                Routes.Home.route
            }

            val selected = curRoute == otherRoute

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp)
                    )
                })

        }
    }

}
