package com.ap.instadiv.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.flowlayout.FlowRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ap.instadiv.R
import com.ap.instadiv.Viewmodel.TagCloudViewModel
import com.ap.instadiv.ui.theme.Backgroundcolor
import com.ap.instadiv.ui.theme.Selectedcolor

import com.ap.instadiv.ui.theme.UnSelectedcolor
import android.widget.Toast
@Composable
fun TagCloudScreen(navController: NavHostController, viewModel: TagCloudViewModel = viewModel()) {
    val tags by viewModel.tags.collectAsState()
    val selectedTag by viewModel.selectedTag.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loadinganim))
    val progress by animateLottieCompositionAsState(composition)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Backgroundcolor)
            .padding(5.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        LottieAnimation(
                            modifier = Modifier.size(150.dp),
                            composition = composition,
                            progress = { progress },
                            alignment = Alignment.BottomCenter
                        )
                        Text(text = "Loading...", color = Color.Black, fontSize = 18.sp)
                    }
                }
            }

            tags.isNotEmpty() -> {
                FlowRow(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .wrapContentWidth(),
                    mainAxisSpacing = 5.dp,
                    crossAxisSpacing = 10.dp
                ) {
                    tags.forEach { tag ->
                        Text(
                            text = tag,
                            fontSize = 18.sp,
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.afcadflux)),
                            modifier = Modifier
                                .clip(RoundedCornerShape(32.dp))
                                .background(if (selectedTag == tag) Selectedcolor else UnSelectedcolor)
                                .clickable { viewModel.selectTag(tag) }
                                .padding(horizontal = 15.dp, vertical = 8.dp)
                                .wrapContentWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(36.dp))

                // Submit Button
                Button(
                    onClick = {
                        if (selectedTag == null) {
                            Toast.makeText(
                                navController.context,
                                "Please select a tag",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            selectedTag?.let { tag ->
                                navController.navigate("selectedTag/$tag")
                            }
                        }
                    },
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(vertical = 9.dp)
                        .wrapContentWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF769CEB)),
                    shape = RoundedCornerShape(20)
                ) {
                    Text(text = "Submit", fontSize = 24.sp, fontFamily = FontFamily.SansSerif)
                }
            }
        }
    }
}
