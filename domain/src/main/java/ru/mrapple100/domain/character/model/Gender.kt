package ru.mrapple100.domain.character.model

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