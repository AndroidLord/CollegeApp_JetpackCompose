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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import com.example.collegeapp_jetpackcompose.itemview.BannerItemView
import com.example.collegeapp_jetpackcompose.itemview.NoticeItemView
import com.example.collegeapp_jetpackcompose.model.NoticeModel
import com.example.collegeapp_jetpackcompose.viewmodel.NoticeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageNotice(navController: NavHostController) {

    val context = LocalContext.current

    val noticeViewModel: NoticeViewModel = viewModel()

    val isUploaded by noticeViewModel.isPosted.observeAsState(false)
    val isDeleted by noticeViewModel.isDeleted.observeAsState(false)
    val noticeList by noticeViewModel.noticeList.observeAsState(null)

    noticeViewModel.getNotice()

    var isNotice by remember {
        mutableStateOf(false)
    }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var title by remember {
        mutableStateOf("")
    }

    var link by remember {
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
            Toast.makeText(context, "Notice Uploaded", Toast.LENGTH_SHORT).show()
            imageUri = null
            isNotice = false
        }
    }

    // for image delete
    LaunchedEffect(key1 = isDeleted) {
        if (isDeleted) {
            Toast.makeText(context, "Notice Deleted", Toast.LENGTH_SHORT).show()
        }
    }


    Scaffold(

        topBar = {
            TopAppBar(
                title = { Text(text = "Manage Notice") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = null
                        )
                    }
                })
        }, floatingActionButton = {
            FloatingActionButton(onClick = {
                isNotice = true
            }) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
            }
        }


    ) { padding ->

        Column(modifier = Modifier.padding(padding)) {

            if (isNotice)
                ElevatedCard(modifier = Modifier.padding(4.dp)) {

                    OutlinedTextField(
                        value = title,
                        onValueChange = {
                            title = it
                        },
                        placeholder = { Text("Notice Title") },
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()

                    )

                    OutlinedTextField(
                        value = link,
                        onValueChange = {
                            link = it
                        },
                        placeholder = { Text("Notice Link") },
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
                                    Toast.makeText(context, "Please Provide Image", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }
                                else if (title.isEmpty()) {
                                    Toast.makeText(context, "Please Provide Title", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }

                                noticeViewModel.saveNotice(
                                    NoticeModel(
                                        title = title,
                                        link = link
                                    ), imageUri!!
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(8.dp)
                        ) {
                            Text("Add Notice")
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

            LazyColumn {

                items(noticeList ?: emptyList()) {
                    NoticeItemView(
                        notice = it,
                        delete = {
                            noticeViewModel.deleteNotice(it)
                        })
                }
            }

        }
    }
}

