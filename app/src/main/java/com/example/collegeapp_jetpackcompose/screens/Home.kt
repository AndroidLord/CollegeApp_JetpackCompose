package com.example.collegeapp_jetpackcompose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.LaunchedEffect
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
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.collegeapp_jetpackcompose.itemview.NoticeItemView
import com.example.collegeapp_jetpackcompose.ui.theme.SkyBlue
import com.example.collegeapp_jetpackcompose.viewmodel.BannerViewModel
import com.example.collegeapp_jetpackcompose.viewmodel.CollegeInfoViewModel
import com.example.collegeapp_jetpackcompose.viewmodel.NoticeViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield


@OptIn(ExperimentalPagerApi::class)
@Composable
fun Home(navController: NavHostController) {

    val bannerViewModel: BannerViewModel = viewModel()
    val bannerList by bannerViewModel.bannerList.observeAsState(null)
    bannerViewModel.getBanner()


    val collegeInfoViewModel: CollegeInfoViewModel = viewModel()
    val collegeInfo by collegeInfoViewModel.collegeInfo.observeAsState(null)
    collegeInfoViewModel.getCollegeInfo()

    val noticeViewModel: NoticeViewModel = viewModel()
    val noticeList by noticeViewModel.noticeList.observeAsState(null)
    noticeViewModel.getNotice()

    val pagerState = rememberPagerState(initialPage = 0)
    val imageSlider = ArrayList<AsyncImagePainter>()

    if (bannerList != null)
        bannerList!!.forEach {
            imageSlider.add(rememberAsyncImagePainter(model = it.url))
        }

    LaunchedEffect(Unit) {
        while (true) {
            yield()
            delay(2600)
            pagerState.animateScrollToPage((pagerState.currentPage + 1) % pagerState.pageCount)
        }
    }

    LazyColumn() {
        item {

            HorizontalPager(
                count = imageSlider.size,
                state = pagerState,
                modifier = Modifier.padding(4.dp)
            )
            { pager ->

                Card {
                    Image(
                        painter = imageSlider[pager],
                        contentDescription = "banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp),
                        contentScale = ContentScale.Crop
                    )
                }

            }

        }
        item {

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillParentMaxWidth()
            ) {

                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    modifier = Modifier.padding(4.dp)
                )

            }

        }
        item {

            if (collegeInfo != null) {


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
                    modifier = Modifier.padding(start = 4.dp)                )
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

                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = "Notice Board",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )

            }


        }
        items(noticeList ?: emptyList()) {
            NoticeItemView(
                notice = it,
                delete = {
                    noticeViewModel.deleteNotice(it)
                })
        }
    }


}