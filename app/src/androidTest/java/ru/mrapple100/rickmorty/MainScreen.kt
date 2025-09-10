package ru.mrapple100.rickmorty
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.swipeDown
import androidx.compose.ui.test.swipeUp
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.ComposeScreen.Companion.onComposeScreen
import io.github.kakaocup.compose.node.element.KNode
import okhttp3.internal.wait
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.mrapple100.rickmorty.ui.pages.characterlist.CharacterCardListState
import ru.mrapple100.rickmorty.ui.pages.characterlist.CharacterListPage

@OptIn(ExperimentalTestApi::class)
class MyComposeScreenTest : TestCase() {

    // Правило само запустит MainActivity
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testMainScreenAppearsAfterSplash() = run {
        // 4. Ждем, пока сплеш-скрин завершится и появится основной экран.
        // Для этого можно дождаться появления конкретного элемента на основном экране.
        // Используем waitUntil с таймаутом, чтобы дать время сплеш-скрину исчезнуть.
        step("Wait to end Splash screen") {
            composeTestRule.waitUntil(5000) { // Таймаут 5 секунд
                // 5. Пытаемся найти элемент, который уникален для основного экрана
              //  composeTestRule.onAllNodes(hasText("Rick&Morty")).fetchSemanticsNodes().isNotEmpty()
                val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
                device.wait(Until.findObject(By.text("Разрешить")), 3000)
                // Нажать кнопку "Разрешить"
                val allowButton = device.findObject(By.text("Разрешить"))
                allowButton?.click()
                true
            }
        }
        step("Check element visibility") {
            onComposeScreen<MyComposeScreen>(composeTestRule) {

                someTopBar {
                    assertIsDisplayed()
                }
                someLazyList {
                    performTouchInput {
                        swipeUp(
                            startY = centerY,
                            endY = centerY -1000f,
                            durationMillis = 1000
                        )
                    }
                }
                someTopBar {
                    assertIsNotDisplayed()
                }
                someSearchBar {
                    assertIsDisplayed()
                    performClick()
                    performTextInput("Rick")
                    assertTextEquals("Rick")
                }
                someSearchBar {
                    assertIsDisplayed()
                    performClick()
                    performTextReplacement("")
                    assertTextContains("")
                }
                someLazyList {
                    performTouchInput {
                        swipeDown(
                            startY = centerY,
                            endY = centerY +1000f,
                            durationMillis = 1000
                        )
                    }
                }


            }
        }
    }
}

class MyComposeScreen(
    semanticsProvider: SemanticsNodeInteractionsProvider
) : ComposeScreen<MyComposeScreen>(
    semanticsProvider = semanticsProvider,
    viewBuilderAction = { hasTestTag("my_compose_screen") }
) {
    val someTopBar: KNode = child { hasTestTag("TopBar") }
    val someSearchBar: KNode = child { hasTestTag("SearchBar") }
    val someLazyList: KNode = child { hasTestTag("LazyList") }

}