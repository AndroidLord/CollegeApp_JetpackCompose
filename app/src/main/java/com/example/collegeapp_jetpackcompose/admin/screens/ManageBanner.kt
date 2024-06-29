package com.example.collegeapp_jetpackcompose.admin.screens

import android.graphics.drawable.Icon
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.collegeapp_jetpackcompose.itemview.BannerItemView
import com.example.collegeapp_jetpackcompose.viewmodel.BannerViewModel
import com.example.collegeapp_jetpackcompose.widget.LoadingDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageBanner(navController: NavHostController) {

    val context = LocalContext.current

    val bannerViewModel: BannerViewModel = viewModel()

    val isUploaded by bannerViewModel.isPosted.observeAsState(false)
    val isDeleted by bannerViewModel.isDeleted.observeAsState(false)
    val bannerList by bannerViewModel.bannerList.observeAsState(null)

    bannerViewModel.getBanner()

    val showLoader = remember{
        mutableStateOf(false)
    }

    if(showLoader.value)
    {
        LoadingDialog {
            showLoader.value = false
        }
    }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        imageUri = it
    }

    // for image upload
    LaunchedEffect(key1 = isUploaded) {
        if (isUploaded) {
            showLoader.value = false
            Toast.makeText(context, "Image Uploaded", Toast.LENGTH_SHORT).show()
            imageUri = null
        }
    }

    // for image delete
    LaunchedEffect(key1 = isDeleted) {
        if (isDeleted) {
            showLoader.value = false
            Toast.makeText(context, "Image Deleted", Toast.LENGTH_SHORT).show()
        }
    }


    Scaffold(

        topBar = {
            TopAppBar(
                title = { Text(text = "Manage Banner") },
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
                launcher.launch("image/*")
            }) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
            }
        }


    ) { padding ->

        Column(modifier = Modifier.padding(padding)) {

            if (imageUri != null)
                ElevatedCard(modifier = Modifier.padding(4.dp)) {

                    Image(
                        painter = rememberAsyncImagePainter(model = imageUri),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Crop
                    )
                    Row {

                        Button(
                            onClick = {
                                showLoader.value = true
                                bannerViewModel.saveImage(imageUri!!)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(8.dp)
                        ) {
                            Text("Upload")
                        }
                        OutlinedButton(
                            onClick = { imageUri = null },
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

                items(bannerList?: emptyList()){
                    BannerItemView(
                        bannerModel = it,
                        delete = {
                            bannerViewModel.deleteBanner(it)
                        })
                }
            }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Prev_ManageBanner() {

    ManageBanner(navController = rememberNavController())

}