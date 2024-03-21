package uk.ac.tees.mad.d3722574.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LoaderAnimation(modifier: Modifier, animRes: Int) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(animRes))
    LottieAnimation(
        composition = composition,
        modifier = modifier,
    )
}