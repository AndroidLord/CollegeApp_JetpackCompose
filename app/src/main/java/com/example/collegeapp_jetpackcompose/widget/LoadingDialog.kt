package com.example.collegeapp_jetpackcompose.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.collegeapp_jetpackcompose.ui.theme.SkyBlue

@Composable
fun LoadingDialog(
    onDismissRequest: () -> Unit
) {

    Dialog(onDismissRequest = { onDismissRequest() }) {

        Box(contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(120.dp)
                .background(
                    Color.White, shape = RoundedCornerShape(8.dp)

                )
        ) {

            Column(
                modifier = Modifier.align(Alignment.Center)
            ) {
                Spacer(modifier = Modifier.size(20.dp))
                CircularProgressIndicator(
                    color = SkyBlue,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(40.dp).align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "Loading...",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally)
                )

            }

        }

    }


}
