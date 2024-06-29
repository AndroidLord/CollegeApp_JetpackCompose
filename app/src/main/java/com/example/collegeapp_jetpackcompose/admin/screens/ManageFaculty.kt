package com.example.collegeapp_jetpackcompose.admin.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.collegeapp_jetpackcompose.R
import com.example.collegeapp_jetpackcompose.itemview.FacultyItemView
import com.example.collegeapp_jetpackcompose.itemview.NoticeItemView
import com.example.collegeapp_jetpackcompose.model.FacultyModel
import com.example.collegeapp_jetpackcompose.model.NoticeModel
import com.example.collegeapp_jetpackcompose.navigation.Routes
import com.example.collegeapp_jetpackcompose.viewmodel.FacultyViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageFaculty(navController: NavHostController) {

    val context = LocalContext.current

    val facultyViewModel: FacultyViewModel = viewModel()

    val isUploaded by facultyViewModel.isPosted.observeAsState(false)
    val isDeleted by facultyViewModel.isDeleted.observeAsState(false)
    val categoryList by facultyViewModel.categoryList.observeAsState(null)

    val option = arrayListOf<String>()

    facultyViewModel.getCategory()

    var isCategory by remember {
        mutableStateOf(false)
    }

    var isTeacher by remember {
        mutableStateOf(false)
    }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var name by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }

    var category by remember {
        mutableStateOf("")
    }

    var mExpanded by remember {
        mutableStateOf(false)
    }


    var position by remember {
        mutableStateOf("")
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        imageUri = it
    }

    // for image upload
    LaunchedEffect(key1 = isUploaded) {
        if (isUploaded) {
            Toast.makeText(context, "Data Added", Toast.LENGTH_SHORT).show()
            imageUri = null
            isCategory = false
            isTeacher = false
            category = ""
            name = ""
            email = ""
            position = ""
        }
    }

    // for image delete
    LaunchedEffect(key1 = isDeleted) {
        if (isDeleted) {
            Toast.makeText(context, "Data Deleted", Toast.LENGTH_SHORT).show()
        }
    }


    Scaffold(

        topBar = {
            TopAppBar(title = { Text(text = "Manage Faculty") }, navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack, contentDescription = null
                    )
                }
            })
        }

    ) { padding ->

        Column(modifier = Modifier.padding(padding)) {

            Row(
                modifier = Modifier.padding(8.dp)

            ) {

                Card(modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
                    .clickable {
                        isCategory = true
                        isTeacher = false
                    }) {

                    Text(
                        text = "Add Category",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                }

                Card(modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
                    .clickable {
                        isTeacher = true
                        isCategory = false
                    }) {

                    Text(
                        text = "Add Teacher",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

            }

            if (isCategory) ElevatedCard(modifier = Modifier.padding(4.dp)) {

                OutlinedTextField(
                    value = category,
                    onValueChange = {
                        category = it
                    },
                    placeholder = { Text("Category...") },
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                )




                Row {

                    Button(
                        onClick = {

                            if (category == "") {
                                Toast.makeText(
                                    context, "Please Provide Category", Toast.LENGTH_SHORT
                                ).show()
                                return@Button
                            }

                            facultyViewModel.uploadCategory(category)
                        }, modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(8.dp)
                    ) {
                        Text("Add Category")
                    }
                    OutlinedButton(
                        onClick = {
                            imageUri = null
                            isCategory = false
                            isTeacher = false
                        }, modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(8.dp)
                    ) {
                        Text("Cancel")
                    }

                }

            }

            if (isTeacher) ElevatedCard(modifier = Modifier.padding(4.dp)) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = if (imageUri == null) painterResource(id = R.drawable.placeholder_imageopener)
                        else rememberAsyncImagePainter(model = imageUri),
                        contentDescription = "Image of Teacher",
                        modifier = Modifier
                            .padding(4.dp)
                            .width(120.dp)
                            .height(120.dp)
                            .clip(CircleShape)
                            .clickable {
                                launcher.launch("image/*")
                            },
                        contentScale = if (imageUri == null) ContentScale.Fit
                        else ContentScale.Crop
                    )

                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
                        },
                        placeholder = { Text("Name...") },
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()

                    )

                    OutlinedTextField(
                        value = position,
                        onValueChange = {
                            position = it
                        },
                        placeholder = { Text("Position...") },
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                        },
                        placeholder = { Text("Email...") },
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                    )

                    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {

                        OutlinedTextField(
                            value = category,
                            onValueChange = {
                                category = it
                            },
                            readOnly = true,
                            placeholder = { Text("Select your department...") },
                            label = { Text("Department Name") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = mExpanded,

                                    )
                            },
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxWidth()
                        )

                        DropdownMenu(expanded = mExpanded,
                            onDismissRequest = { mExpanded = false }) {

                            if (categoryList != null && categoryList!!.isNotEmpty()) {
                                option.clear()
                                for (data in categoryList!!) {
                                    option.add(data)
                                }

                            }

                            option.forEach { selectedOption ->
                                DropdownMenuItem(text = { Text(selectedOption) }, onClick = {
                                    category = selectedOption
                                    mExpanded = false
                                }, modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }

                        Spacer(
                            modifier = Modifier
                                .matchParentSize()
                                .padding(10.dp)
                                .clickable {
                                    mExpanded = !mExpanded
                                })
                    }

                    Row {

                        Button(
                            onClick = {

                                if (imageUri == null) {
                                    Toast.makeText(
                                        context, "Please Provide Image", Toast.LENGTH_SHORT
                                    ).show()
                                    return@Button
                                } else if (name.isEmpty()) {
                                    Toast.makeText(
                                        context, "Please Provide Title", Toast.LENGTH_SHORT
                                    ).show()
                                    return@Button
                                }

                                facultyViewModel.saveFaculty(
                                    FacultyModel(
                                        name = name, email = email, position = position
                                    ), imageUri!!, category
                                )
                            }, modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(8.dp)
                        ) {
                            Text("Add Teacher")
                        }
                        OutlinedButton(
                            onClick = {
                                imageUri = null
                                isCategory = false
                                isTeacher = false
                            }, modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(8.dp)
                        ) {
                            Text("Cancel")
                        }

                    }
                }

            }

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
    }
}

