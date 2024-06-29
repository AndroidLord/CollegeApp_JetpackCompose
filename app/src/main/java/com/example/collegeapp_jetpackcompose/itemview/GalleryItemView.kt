package com.example.collegeapp_jetpackcompose.itemview

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.example.collegeapp_jetpackcompose.R
import com.example.collegeapp_jetpackcompose.model.BannerModel
import com.example.collegeapp_jetpackcompose.model.GalleryModel
import com.example.collegeapp_jetpackcompose.utils.Constants
import com.example.collegeapp_jetpackcompose.utils.Constants.IS_ADMIN

@Composable
fun GalleryItemView(
    gallery: GalleryModel,
    delete: (gallery: GalleryModel) -> Unit,
    deleteImage: (image: String, category: String) -> Unit
) {

    OutlinedCard(
        modifier = Modifier.padding(4.dp)
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (category_constraint, delete_constraint) = createRefs()

            Text(
                text = gallery.category!!,
                modifier = Modifier
                    .padding(start = if (!IS_ADMIN) 90.dp else 8.dp
                        , top = 8.dp, bottom = 8.dp, end = 8.dp)
                    .constrainAs(category_constraint) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(delete_constraint.start)
                    },
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            if (IS_ADMIN) {
                Card(
                    modifier = Modifier
                        .constrainAs(delete_constraint) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        }
                        .padding(4.dp)
                        .clickable {
                            delete(gallery)
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

        LazyRow() {
//            if (facultyList!!.isEmpty()) {
//                Toast.makeText(context, "No Teachers Found", Toast.LENGTH_SHORT).show()
//            }
            items(gallery.image ?: emptyList()) {
                ImageItemView(
                    imageUrl = it,
                    category = gallery.category!!
                ) { img, cat ->
                    deleteImage(img, cat)
                }
            }
        }
    }

}