package ru.mrapple100.rickmorty.ui.components.organism

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.mrapple100.domain.character.model.CharacterCardModel

@Composable
fun WidgetManagementScreen() {
    var currentCharacter by remember { mutableStateOf<Character?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        loadCurrentCharacter()
    }

    suspend fun loadCurrentCharacter() {
        isLoading = true
        val prefs = context.getSharedPreferences("widget_prefs", Context.MODE_PRIVATE)
        val name = prefs.getString("character_name", null)
        val image = prefs.getString("character_image", null)

        if (name != null && image != null) {
            currentCharacter = CharacterCardModel(name = name, image = image)
        }
        isLoading = false
    }

    fun refreshCharacter() {
        isLoading = true
        CharacterOfTheDayWorker.scheduleImmediateWork(context)
        // Можно добадеть задержку для обновления UI
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Виджет 'Персонаж дня'",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (isLoading) {
            CircularProgressIndicator()
        } else if (currentCharacter != null) {
            // Показываем текущего персонажа
            AsyncImage(
                model = currentCharacter!!.image,
                contentDescription = currentCharacter!!.name,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = currentCharacter!!.name,
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { refreshCharacter() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Обновить сейчас")
            }
        } else {
            Text("Данные не загружены")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Добавьте виджет на главный экран:",
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )

        Text(
            text = "1. Долгое нажатие на домашнем экране\n" +
                    "2. Выберите 'Виджеты'\n" +
                    "3. Найдите 'Rick and Morty'\n" +
                    "4. Добавьте виджет 'Персонаж дня'",
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}