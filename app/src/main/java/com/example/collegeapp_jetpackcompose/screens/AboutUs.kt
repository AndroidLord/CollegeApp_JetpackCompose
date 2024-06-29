package com.example.collegeapp_jetpackcompose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.collegeapp_jetpackcompose.itemview.NoticeItemView
import com.example.collegeapp_jetpackcompose.ui.theme.SkyBlue
import com.example.collegeapp_jetpackcompose.viewmodel.CollegeInfoViewModel
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator

@Composable
fun AboutUs(navController: NavHostController) {

    val collegeInfoViewModel: CollegeInfoViewModel = viewModel()
    val collegeInfo by collegeInfoViewModel.collegeInfo.observeAsState(null)
    collegeInfoViewModel.getCollegeInfo()




    Column {
        if (collegeInfo != null) {

            Image(
                painter = rememberAsyncImagePainter(model = collegeInfo!!.imageUrl!!),
                contentDescription = "College Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )

            Text(
                text = collegeInfo!!.name!!,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = collegeInfo!!.description!!,
                color = Color.White,
                fontWeight = FontWeight.Normal,
                fontSize = 17.sp,
                modifier = Modifier.padding(start = 4.dp)
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = collegeInfo!!.address!!,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(start = 4.dp)
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = collegeInfo!!.websiteLink!!,
                color = SkyBlue,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 4.dp)
            )


        }
    }


}