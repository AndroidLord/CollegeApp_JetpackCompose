package com.example.collegeapp_jetpackcompose.itemview

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextAlign
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
fun FacultyItemView(
    category: String,
    delete: (category: String) -> Unit,
    onClick: (category: String) -> Unit
) {

    OutlinedCard(
        modifier = Modifier.padding(4.dp).clickable {
            onClick(category)
        }
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (category_constraint, delete_constraint) = createRefs()

            Text(
                text = category,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .constrainAs(category_constraint) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(delete_constraint.start)
                    },
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            if(Constants.IS_ADMIN)
            Card(
                modifier = Modifier
                    .constrainAs(delete_constraint) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
                    .padding(4.dp)
                    .clickable {
                        delete(category)
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