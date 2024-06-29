package com.example.collegeapp_jetpackcompose.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.collegeapp_jetpackcompose.itemview.GalleryItemView
import com.example.collegeapp_jetpackcompose.viewmodel.GalleryViewModel

@Composable
fun Gallery(navController: NavHostController) {

    val galleryViewModel: GalleryViewModel = viewModel()
    val galleryList by galleryViewModel.galleryList.observeAsState(null)
    galleryViewModel.getGallery()




    LazyColumn(
        modifier = Modifier.padding(4.dp)
    ) {
        item {
            Text(text = "Gallery")
        }
        items(galleryList ?: emptyList()) {
            GalleryItemView(
                gallery = it,
                delete = {docId ->
                    galleryViewModel.deleteGallery(docId)
                },
                deleteImage = {image,cat ->
                    galleryViewModel.deleteImage(image, cat)
                })
        }
    }
}