package com.example.collegeapp_jetpackcompose.itemview

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.collegeapp_jetpackcompose.model.FacultyModel
import com.example.collegeapp_jetpackcompose.model.NoticeModel
import com.example.collegeapp_jetpackcompose.ui.theme.SkyBlue
import com.example.collegeapp_jetpackcompose.utils.Constants

@Composable
fun TeacherItemView(
    faculty: FacultyModel,
    delete: (faculty: FacultyModel) -> Unit
) {

    OutlinedCard(
        modifier = Modifier.padding(4.dp)
    ) {
        ConstraintLayout {
            val (image, delete) = createRefs()

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(160.dp)
            ) {

                Spacer(modifier = Modifier.height(20.dp))

                Image(
                    painter = rememberAsyncImagePainter(model = faculty.imageUrl),
                    contentDescription = "Image of Teacher",
                    modifier = Modifier
                        .padding(0.dp)
                        .width(120.dp)
                        .height(120.dp)
                        .clip(CircleShape)
                        .clickable {
                            //   launcher.launch("image/*")
                        },
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = faculty.name!!,
                    modifier = Modifier
                        .padding(
                            horizontal = 12.dp,
                            vertical = 8.dp
                        ),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Text(
                    text = faculty.position!!,
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
                        delete(faculty)
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