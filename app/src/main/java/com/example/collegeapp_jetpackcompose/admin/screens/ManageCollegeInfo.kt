package com.example.collegeapp_jetpackcompose.admin.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.collegeapp_jetpackcompose.R
import com.example.collegeapp_jetpackcompose.model.CollegeInfoModel
import com.example.collegeapp_jetpackcompose.viewmodel.CollegeInfoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageCollegeInfo(navController: NavHostController) {

    val context = LocalContext.current

    val collegeInfoViewModel: CollegeInfoViewModel = viewModel()

    val isUploaded by collegeInfoViewModel.isPosted.observeAsState(false)
    val isDeleted by collegeInfoViewModel.isDeleted.observeAsState(false)
    val collegeInfo by collegeInfoViewModel.collegeInfo.observeAsState(null)

    collegeInfoViewModel.getCollegeInfo()

    var isNotice by remember {
        mutableStateOf(false)
    }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var name by remember {
        mutableStateOf("")
    }

    var address by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    var website by remember {
        mutableStateOf("")
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        imageUri = it
    }

    LaunchedEffect(key1 = collegeInfo) {

        if (collegeInfo != null) {
            name = collegeInfo!!.name!!
            address = collegeInfo!!.address!!
            description = collegeInfo!!.description!!
            website = collegeInfo!!.websiteLink!!
            imageUri = Uri.parse(collegeInfo!!.imageUrl)
        }
    }

    // for image upload
    LaunchedEffect(key1 = isUploaded) {
        if (isUploaded) {
            Toast.makeText(context, "CollegeInfo Updated", Toast.LENGTH_SHORT).show()
            imageUri = null

        }
    }

    Scaffold(

        topBar = {
            TopAppBar(
                title = { Text(text = "Manage CollegeInfo") },
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

        Column(modifier = Modifier.padding(padding)) {


            ElevatedCard(modifier = Modifier.padding(4.dp)) {

                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                    },
                    placeholder = { Text("College Name...") },
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()

                )

                OutlinedTextField(
                    value = address,
                    onValueChange = {
                        address = it
                    },
                    placeholder = { Text("College Address...") },
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                )


                OutlinedTextField(
                    value = website,
                    onValueChange = {
                        website = it
                    },
                    placeholder = { Text("College Website...") },
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = {
                        description = it
                    },
                    placeholder = { Text("College Description...") },
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                )


                Image(
                    painter =
                    if (imageUri == null) painterResource(id = R.drawable.placeholder_imageopener)
                    else rememberAsyncImagePainter(model = imageUri),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .height(200.dp)
                        .clickable {
                            launcher.launch("image/*")
                        },
                    contentScale =
                    if (imageUri == null) ContentScale.Fit
                    else ContentScale.Crop
                )


                Row {

                    Button(
                        onClick = {

                            if (imageUri == null) {
                                Toast.makeText(
                                    context,
                                    "Please Provide Image",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@Button
                            } else if (name.isEmpty() || description.isEmpty() || website.isEmpty()) {
                                Toast.makeText(
                                    context,
                                    "Please Provide All Details",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@Button
                            }


                            collegeInfoViewModel.saveCollegeInfo(
                                uri = imageUri!!,
                                CollegeInfoModel(
                                    name = name,
                                    address = address,
                                    description = description,
                                    websiteLink = website,
                                    imageUrl = imageUri.toString()
                                )
                            )

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(8.dp)
                    ) {
                        Text(text = if (collegeInfo == null) "Save" else "Update")

                    }
                    OutlinedButton(
                        onClick = {
                            imageUri = null
                            isNotice = false
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(8.dp)
                    ) {
                        Text("Cancel")
                    }

                }

            }

        }
    }
}

