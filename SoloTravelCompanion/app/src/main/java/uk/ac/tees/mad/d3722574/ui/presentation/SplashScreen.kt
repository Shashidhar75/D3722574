package uk.ac.tees.mad.d3722574.ui.presentation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import uk.ac.tees.mad.d3722574.R
import uk.ac.tees.mad.d3722574.navigation.NavigationDestination
import uk.ac.tees.mad.d3722574.ui.components.LoaderAnimation


object SplashDestination : NavigationDestination {
    override val routeName: String
        get() = "splash"
}

@Composable
fun SplashScreen(
    onAnimationFinish: () -> Unit
) {

    val animDuration = remember {
        Animatable(0f)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(240.dp))
            LoaderAnimation(
                modifier = Modifier

                    .size(260.dp)
                    .clip(CircleShape)
                    .scale(animDuration.value).background(Color.White),
                animRes = R.raw.travel
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.alpha(animDuration.value)
            )
            Spacer(modifier = Modifier.height(7.dp))
            Text(
                text = "Explore. Connect. Adventure.",
                fontSize = 16.sp,
                color = Color.White,
//                textAlign = TextAlign.Center,
                modifier = Modifier.alpha(animDuration.value)
            )
            Spacer(modifier = Modifier.weight(0.5f))
        }

    }
    LaunchedEffect(true) {
        animDuration.animateTo(1f, animationSpec = tween(2000))
        delay(3000L)
        onAnimationFinish()
    }
}

