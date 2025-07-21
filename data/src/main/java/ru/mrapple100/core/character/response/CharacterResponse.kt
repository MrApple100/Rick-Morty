package ru.mrapple100.core.character.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

internal typealias CharacterResponseList = List<CharacterResponse>
@Serializable
data class CharacterResponse(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("status") val status: String,
    @SerialName("species") val species: String,
    @SerialName("type") val type: String,
    @SerialName("gender") val gender: String,
    @SerialName("origin") val origin: Location,
    @SerialName("location") val location: Location,
    @SerialName("image") val image: String,
    @SerialName("episode") val episode: List<String>,
    @SerialName("url") val url: String,
    @SerialName("created") val created: String
) {
    enum class Status {
        ALIVE, DEAD, UNKNOWN;

        companion object {
            fun safeValueOf(value: String): Status {
                return try {
                    valueOf(value.uppercase())
                } catch (e: IllegalArgumentException) {
                    UNKNOWN
                }
            }
        }
    }

    enum class Gender {
        FEMALE, MALE, GENDERLESS, UNKNOWN;

        companion object {
            fun safeValueOf(value: String): Gender {
                return try {
                    valueOf(value.uppercase())
                } catch (e: IllegalArgumentException) {
                    UNKNOWN
                }
            }
        }
    }

    @Serializable
    data class Location(
        val name: String,
        val url: String
    )
}
