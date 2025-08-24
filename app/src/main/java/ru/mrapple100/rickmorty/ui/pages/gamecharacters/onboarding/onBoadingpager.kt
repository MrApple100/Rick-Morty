package ru.mrapple100.rickmorty.ui.pages.gamecharacters.onboarding

import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.mrapple100.rickmorty.ui.components.organism.PagerIndicator
import ru.mrapple100.rickmorty.ui.theme.Colors

@ExperimentalPagerApi
@Composable
fun OnBoardingPager(
    item: List<OnBoardingData>,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    onClickSkipEnd: () -> Unit,
) {
    val scope = rememberCoroutineScope()

    Box(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                state = pagerState
            ) { page ->
                Column(
                    modifier = Modifier
                        .padding(60.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    /* Image(
                         painter = painterResource(id = item[page].image),
                         contentDescription = item[page].title,
                         modifier = Modifier
                             .height(250.dp)
                             .fillMaxWidth()
                     )*/
                    LoaderIntro(
                        modifier = Modifier
                            .size(200.dp)
                            .fillMaxWidth()
                            .align(alignment = Alignment.CenterHorizontally), item[page].image
                    )
                    Text(
                        text = item[page].title,
                        modifier = Modifier.padding(top = 50.dp),
                        color = Color.Black,
                        style = androidx.compose.material3.MaterialTheme.typography.headlineSmall,
                    )

                    Text(
                        text = item[page].desc,
                        modifier = Modifier.padding(top = 30.dp, start = 20.dp, end = 20.dp),
                        color = Color.Black,
                        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }

            PagerIndicator(item.size, pagerState.currentPage)
        }
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            BottomSection(
                pagerState.currentPage,
                onClickSkipEnd,
                {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            )
        }
    }
}

    @ExperimentalPagerApi
    @Composable
    fun rememberPagerState(
        @IntRange(from = 0) pageCount: Int,
        @IntRange(from = 0) initialPage: Int = 0,
        @FloatRange(from = 0.0, to = 1.0) initialPageOffset: Float = 0f,
        @IntRange(from = 1) initialOffscreenLimit: Int = 1,
        infiniteLoop: Boolean = false
    ): PagerState = rememberSaveable(
        saver = PagerState.Saver
    ) {
        PagerState(
            pageCount = pageCount,
            currentPage = initialPage,
            currentPageOffset = initialPageOffset,
            offscreenLimit = initialOffscreenLimit,
            infiniteLoop = infiniteLoop
        )
    }

    @Composable
    fun BottomSection(
        currentPager: Int,
        onClickSkipEnd: () -> Unit,
        onClickNext: () -> Unit
        ) {
        Row(
            modifier = Modifier
                .padding(bottom = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = if (currentPager != 2) Arrangement.SpaceBetween else Arrangement.Center
        ) {
            if (currentPager == 2) {
                OutlinedButton(
                    onClick = {
                        onClickSkipEnd()
                    },
                    shape = RoundedCornerShape(50),
                ) {
                    Text(
                        text = "Get Started",
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 40.dp),
                        color = Colors.gray
                    )
                }
            } else {
                SkipNextButton(text = "Skip", modifier = Modifier.padding(start = 20.dp).clickable { onClickSkipEnd() })
                SkipNextButton(text = "Next", modifier = Modifier.padding(end = 20.dp).clickable { onClickNext()})
            }
        }
    }

    @Composable
    fun SkipNextButton(text: String, modifier: Modifier) {
        Text(
            text = text,
            color = Color.Black,
            modifier = modifier,
            fontSize = 18.sp,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
    }

