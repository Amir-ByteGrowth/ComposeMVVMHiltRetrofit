package com.example.composemvvmhiltretrofit.ui.listscreen


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composemvvmhiltretrofit.data.models.MotivationDataItem
import com.example.composemvvmhiltretrofit.ui.theme.bold
import com.example.composemvvmhiltretrofit.ui.theme.regular

@Composable
fun ListItemUi(modifier: Modifier = Modifier, motivationDataItem: MotivationDataItem) {
    Card(modifier = modifier.padding(10.dp)) {
        Column(modifier = modifier.padding(10.dp)) {
            Text(
                text = motivationDataItem.category,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = bold
                )
            )
            Text(
                text = motivationDataItem.text,
                style = TextStyle(color = Color.Black, fontFamily = regular)
            )
        }
    }
}


//@Preview
//@Composable
//fun ListItemPRev() {
//    ListItemUi(
//        motivationDataItem = MotivationDataItem(
//            "Motivation",
//            "Motivation is used as tool to fuel up your energy. you must listent to them"
//        )
//    )
//}