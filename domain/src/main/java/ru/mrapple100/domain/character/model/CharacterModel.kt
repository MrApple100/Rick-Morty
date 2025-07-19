package ru.mrapple100.domain.character.model

data class CharacterModel(
    val id: Int,
    val name: String,
    val status: Status,
    val species: String,
    val type: String,
    val gender: Gender,
    val origin: Location,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
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

    data class Location(
        val name: String,
        val url: String
    )
}
