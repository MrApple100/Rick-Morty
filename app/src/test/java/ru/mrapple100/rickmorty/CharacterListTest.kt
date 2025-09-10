package ru.mrapple100.rickmorty

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.mrapple100.domain.character.model.CharacterCardModel
import ru.mrapple100.domain.character.usecases.ScrollDownPageUseCase
import ru.mrapple100.domain.character.usecases.SearchCharacterByNameUseCase
import ru.mrapple100.domain.character.usecases.UpdateCharacterListPageUseCase
import ru.mrapple100.rickmorty.ui.common.UiStatus
import ru.mrapple100.rickmorty.ui.pages.characterlist.CharacterListSideEffect
import ru.mrapple100.rickmorty.ui.pages.characterlist.CharacterListViewModel
import java.io.IOException


@OptIn(ExperimentalCoroutinesApi::class)
class CharacterListViewModelTest {

    private lateinit var viewModel: CharacterListViewModel
    private val searchUseCase: SearchCharacterByNameUseCase = mockk()
    private val updateUseCase: UpdateCharacterListPageUseCase = mockk()
    private val scrollDownUseCase: ScrollDownPageUseCase = mockk()
    private val savedStateHandle: SavedStateHandle = mockk()

    @Before
    fun setUp() {
        coEvery { searchUseCase(any()) } returns flowOf(emptyList())
    }

    @Test
    fun `init should set loading state and trigger search`() = runTest {
        // Act
        viewModel = CharacterListViewModel(
            searchCharacterByNameUseCase = searchUseCase,
            updateCharacterListPageUseCase = updateUseCase,
            scrollDownPageUseCase = scrollDownUseCase,
            savedStateHandle = savedStateHandle
        )

        // Assert
        assertEquals(UiStatus.Loading, viewModel.container.stateFlow.value.status)
    }

    @Test
    fun `searchCharacter should set success state when characters found`() = runTest {
        // Arrange
        val characters = listOf(
            CharacterCardModel(id = 1, name = "Rick Sanchez"),
            CharacterCardModel(id = 2, name = "Morty Smith")
        )
        coEvery { searchUseCase("") } returns flow {
            delay(1000) // Имитируем задержку как в реальном коде
            emit(characters)
        }
        viewModel = CharacterListViewModel(
            searchCharacterByNameUseCase = searchUseCase,
            updateCharacterListPageUseCase = updateUseCase,
            scrollDownPageUseCase = scrollDownUseCase,
            savedStateHandle = savedStateHandle
        )

        // Act
        viewModel.searchCharacter("")
        // Assert
        // Assert с использованием Turbine для тестирования Flow
        viewModel.container.stateFlow.test {
            // Пропускаем initial состояние
            awaitItem() // Loading state from init

            // Ждем успешного состояния
            val successState = awaitItem()
            assertEquals(UiStatus.Success, successState .status)
            assertEquals(2, successState .detailsList.size)
            assertEquals("Rick Sanchez", successState .detailsList[0].name)

            cancel() // Завершаем наблюдение
        }
    }
    @Test
    fun `searchCharacter should set failed state when no characters found`() = runTest {
        // Arrange
        coEvery { searchUseCase("") } returns flow {
            delay(1000) // Имитируем задержку как в реальном коде
            emit( emptyList())
        }

        viewModel = CharacterListViewModel(
            searchCharacterByNameUseCase = searchUseCase,
            updateCharacterListPageUseCase = updateUseCase,
            scrollDownPageUseCase = scrollDownUseCase,
            savedStateHandle = savedStateHandle
        )

        // Act
        viewModel.searchCharacter("unknown")

        // Assert
        viewModel.container.stateFlow.test {
            // Первое состояние - Loading из init
            val initialState = awaitItem()
            assertEquals(UiStatus.Loading::class, initialState.status::class)
            awaitItem()//success

            // Запускаем поиск
            viewModel.searchCharacter("unknown")

            // Второе состояние - Loading из searchCharacter
            val loadingState = awaitItem()
            assertEquals(UiStatus.Loading::class, loadingState.status::class)


            // Ждем failed состояния
            val failedState = awaitItem()
            assertEquals(UiStatus.Failed("Not Found"), failedState.status)
            assertEquals(0, failedState.detailsList.size)

            cancel()
        }
    }
    @Test
    fun `searchCharacter should cancel previous search job`() = runTest {
        // Arrange
        val slowFlow = flow<List<CharacterCardModel>> {
            delay(2000) // Имитируем долгий запрос
            emit(listOf(CharacterCardModel(id = 1, name = "Slow Rick")))
        }
        val fastFlow = flowOf(listOf(CharacterCardModel(id = 2, name = "Fast Morty")))

        coEvery { searchUseCase("slow") } returns slowFlow
        coEvery { searchUseCase("fast") } returns fastFlow

        viewModel = CharacterListViewModel(
            searchCharacterByNameUseCase = searchUseCase,
            updateCharacterListPageUseCase = updateUseCase,
            scrollDownPageUseCase = scrollDownUseCase,
            savedStateHandle = savedStateHandle
        )
// Assert
        viewModel.container.stateFlow.test {
            // Первое состояние - Loading из init
            val initialState = awaitItem()
            assertEquals(UiStatus.Loading::class, initialState.status::class)
            // Act
            viewModel.searchCharacter("slow") // Долгий запрос

            // Второе состояние - Loading из searchCharacter
            val loadingState = awaitItem()
            assertEquals(UiStatus.Loading::class, loadingState.status::class)

            delay(100) // Ждем немного
            viewModel.searchCharacter("fast") // Быстрый запрос (должен отменить предыдущий)
            // Третье состояние - Loading из searchCharacter
            val loadingState2 = awaitItem()
            assertEquals(UiStatus.Loading::class, loadingState.status::class)

            // Ждем успешного состояния
            val successState = awaitItem()
            assertEquals("Fast Morty", successState.detailsList[0].name)

            cancel()

        }
    }
    @Test
    fun `refreshCharacterPage should reset state and trigger new search`() = runTest {
        // Arrange
        val initialCharacters = listOf(CharacterCardModel(id = 1, name = "Initial"))
        val refreshedCharacters = listOf(CharacterCardModel(id = 2, name = "Refreshed"))

        coEvery { searchUseCase("") } returns flowOf(initialCharacters) andThen flowOf(refreshedCharacters)
        coEvery { updateUseCase() } returns Unit

        viewModel = CharacterListViewModel(
            searchCharacterByNameUseCase = searchUseCase,
            updateCharacterListPageUseCase = updateUseCase,
            scrollDownPageUseCase = scrollDownUseCase,
            savedStateHandle = savedStateHandle
        )

        viewModel.container.stateFlow.test {
            awaitItem()//init loading
            // Act
            viewModel.refreshCharacterPage()

            val state = awaitItem()//refresh loading
            assertEquals("Refreshed", state.detailsList[0].name)

            cancel()
        }
    }
    @Test
    fun `scrollDown should add filtered characters to existing list`() = runTest {
        // Arrange
        val initialCharacters = listOf(CharacterCardModel(id = 1, name = "Rick"))
        val newCharacters = listOf(
            CharacterCardModel(id = 2, name = "Morty"),
            CharacterCardModel(id = 3, name = "Summer")
        )

        coEvery { searchUseCase("R") } returns flowOf(initialCharacters)
        coEvery { scrollDownUseCase() } returns flowOf(newCharacters)

        viewModel = CharacterListViewModel(
            searchCharacterByNameUseCase = searchUseCase,
            updateCharacterListPageUseCase = updateUseCase,
            scrollDownPageUseCase = scrollDownUseCase,
            savedStateHandle = savedStateHandle
        )
        viewModel.container.stateFlow.test {
            awaitItem()//init loading
            // Сначала выполняем поиск
            viewModel.searchCharacter("R")

            awaitItem()//loading
            awaitItem()//success
            // Act
            viewModel.scrollDown()
            awaitItem()//scrollLoading


            val state = awaitItem()//success
            assertEquals(
                1,
                state.detailsList.size
            ) // Только "Rick", так как "Morty" и "Summer" не содержат "R"
            assertEquals("Rick", state.detailsList[0].name)

            cancel()
        }
    }
    @Test
    fun `showDetails should post ShowDetails side effect`() = runTest {
        // Arrange
        coEvery { searchUseCase(any()) } returns flowOf(emptyList())

        viewModel = CharacterListViewModel(
            searchCharacterByNameUseCase = searchUseCase,
            updateCharacterListPageUseCase = updateUseCase,
            scrollDownPageUseCase = scrollDownUseCase,
            savedStateHandle = savedStateHandle
        )

        val sideEffects = mutableListOf<CharacterListSideEffect>()
        // Используем take(1) чтобы собрать только один side effect и завершиться
        val collectJob = launch {
            viewModel.container.sideEffectFlow
                .take(1) // Берем только первый элемент
                .collect { sideEffect ->
                    sideEffects.add(sideEffect)
                }
        }
        viewModel.container.stateFlow.test {
            awaitItem()//init loading
            awaitItem()//success
            // Act
            viewModel.showDetails(123)

            // Ждем завершения коллекции
            collectJob.join()
            // Assert
            assertEquals(1, sideEffects.size)
            assert(sideEffects[0] is CharacterListSideEffect.ShowDetails)
            assertEquals(123, (sideEffects[0] as CharacterListSideEffect.ShowDetails).id)
            cancel()
        }
    }
    @Test
    fun `isDataLoadEnd should be updated after search completion`() = runTest {
        // Arrange
        val characters = listOf(CharacterCardModel(id = 1, name = "Test"))
        coEvery { searchUseCase(any()) } returns flowOf(characters)

        viewModel = CharacterListViewModel(
            searchCharacterByNameUseCase = searchUseCase,
            updateCharacterListPageUseCase = updateUseCase,
            scrollDownPageUseCase = scrollDownUseCase,
            savedStateHandle = savedStateHandle
        )
        viewModel.container.stateFlow.test {
            awaitItem()//init loading
            awaitItem()//success

            // Initially should be true (from init)
            assertEquals(true, viewModel.isDataLoadEnd.value)
            // During search should become false
            viewModel.searchCharacter("test")
            awaitItem()//loading
            assertEquals(false, viewModel.isDataLoadEnd.value)
            awaitItem()//success
            assertEquals(true, viewModel.isDataLoadEnd.value)

            cancel()
        }
    }
    @Test
    fun `searchCharacter should handle exceptions gracefully`() = runTest {
        // Arrange
        coEvery { searchUseCase(any()) } returns flow {
            throw IOException("Network error")
        }

        viewModel = CharacterListViewModel(
            searchCharacterByNameUseCase = searchUseCase,
            updateCharacterListPageUseCase = updateUseCase,
            scrollDownPageUseCase = scrollDownUseCase,
            savedStateHandle = savedStateHandle
        )
        viewModel.container.stateFlow.test {
            awaitItem()//init loading
            awaitItem()//success
            // Act
            viewModel.searchCharacter("test")
            awaitItem()//loading

            val state = awaitItem()
            assert(state.status is UiStatus.Failed)
            cancel()
        }
    }
}