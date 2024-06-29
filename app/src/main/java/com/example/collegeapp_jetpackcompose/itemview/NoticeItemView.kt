package com.example.collegeapp_jetpackcompose.itemview

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.example.collegeapp_jetpackcompose.R
import com.example.collegeapp_jetpackcompose.model.BannerModel
import com.example.collegeapp_jetpackcompose.model.NoticeModel
import com.example.collegeapp_jetpackcompose.ui.theme.SkyBlue
import com.example.collegeapp_jetpackcompose.utils.Constants

@Composable
fun NoticeItemView(notice: NoticeModel, delete: (notice: NoticeModel) -> Unit) {

    OutlinedCard(
        modifier = Modifier.padding(8.dp)
    ) {
        ConstraintLayout {
            val (image, delete) = createRefs()

            Column {
                Text(
                    text = notice.title!!,
                    modifier = Modifier
                        .padding(
                            horizontal = 12.dp,
                            vertical = 8.dp
                        ),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Text(
                    text = notice.link!!,
                    modifier = Modifier
                        .padding(
                            top = 0.dp,
                            start = 12.dp,
                            end = 12.dp,
                            bottom = 12.dp
                        ),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = SkyBlue
                )
                Image(
                    painter = rememberAsyncImagePainter(model = notice.imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }
            if(Constants.IS_ADMIN)
            Card(
                modifier = Modifier
                    .constrainAs(delete) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
                    .padding(4.dp)
                    .clickable {
                        delete(notice)
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_delete_24),
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }

}