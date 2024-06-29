package com.example.collegeapp_jetpackcompose.admin.screens

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.collegeapp_jetpackcompose.itemview.TeacherItemView
import com.example.collegeapp_jetpackcompose.model.FacultyModel
import com.example.collegeapp_jetpackcompose.viewmodel.FacultyViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FacultyDetailScreen(navController: NavHostController, category: String) {

    val context = LocalContext.current

    val facultyViewModel: FacultyViewModel = viewModel()

    val facultyList by facultyViewModel.facultyList.observeAsState(null)

    facultyViewModel.getFaculty(category)


    Scaffold(

        topBar = {
            TopAppBar(
                title = { Text(text = "$category Teachers") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = null
                        )
                    }
                })
        }

    ) { padding ->

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 125.dp),
            modifier = Modifier.padding(padding)
        ) {
//            if (facultyList!!.isEmpty()) {
//                Toast.makeText(context, "No Teachers Found", Toast.LENGTH_SHORT).show()
//            }
            items(facultyList ?: emptyList()) { faculty ->
                TeacherItemView(faculty = faculty) {facultyModel ->
                    facultyViewModel.deleteFaculty(facultyModel)
                }
            }
        }

    }

}

