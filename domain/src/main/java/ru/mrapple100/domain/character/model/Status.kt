package ru.mrapple100.domain.character.model

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