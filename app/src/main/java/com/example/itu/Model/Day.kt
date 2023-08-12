package com.example.itu.Model

import androidx.compose.ui.graphics.Color
import java.util.*

// Author: Matej Kocman

data class Database(
    var data: ArrayList<Day> = arrayListOf(),
    var categoryOrder: MutableList<Category> = arrayListOf(
        Category.SEX,
        Category.LIFESTYLE,
        Category.SYMPTOMS,
        Category.MUCUS,
        Category.MOOD,
        Category.NOTE,
        Category.BREASTS,
        Category.TESTS,
        Category.LOCHIA
    ),
    var categoryColor: Map<Category, Color> = mapOf(
        Category.SEX to Color(0xFFFF2C2C),
        Category.LIFESTYLE to Color(0xFF2596FF),
        Category.SYMPTOMS to Color(0xFF6C009F),
        Category.MUCUS to Color(0xFF8AC926),
        Category.MOOD to Color(0xFFFFAA00),
        Category.NOTE to Color(0xFFFFAA00),
        Category.BREASTS to Color(0xFFFFAA00),
        Category.TESTS to Color(0xFFFFAA00),
        Category.LOCHIA to Color(0xFFFFAA00)
    )
)

data class Day(
    var date: CalendarDate,
    var phase: Phase? = null,
    var sex: ArrayList<Sex>? = null,
    var lifestyle: Lifestyle? = null,
    var symptoms: MutableSet<Symptom>? = null,
    var mood: MutableSet<Mood>? = null,
    var note: String? = null,
    var ovulationTest: Boolean? = null,
    var gravidityTest: Gravidity? = null,
    var fertilityTest: Fertility? = null,
    var breastExam: MutableSet<Breasts>? = null,
    var cervicalMucus: MutableSet<Mucus>? = null,
    var lochia: Lochia? = null
)

data class CalendarDate(
    var year: Int = 0,
    var month: Int = 0,
    var day: Int = 0,
)

data class Sex(
    var condom: Boolean? = null,
    var orgasm: Boolean? = null,
    var masturbation: Boolean = false,
    var planB: Boolean = false,
    var note: String? = null
)

data class Lifestyle(
    var weight: Float? = null,
    var temp: Float? = null,
    var sleep: Int? = null,
    var water: Int? = null
)

enum class Phase {
    LIGHT_FLOW, MEDIUM_FLOW, HEAVY_FLOW, DISASTER_FLOW, FERTILE, OVULATING
}

enum class Symptom {
    BELLY_CRAMPS, BREAST_PAIN, LOWER_BACK_PAIN, BLOATING, HEADACHE, ACNE, SWEARING, TIREDNESS,
    INSOMNIA, CONGESTION, DIARRHEA, GASSINESS, SPOTTING
}

enum class Mood {
    NORMAL, HAPPY, ANGRY, IN_LOVE, TIRED, SAD, DEPRESSED, EMOTIONAL, ANGSTY
}

enum class Gravidity {
    POSITIVE, FEINT_LINE, NEGATIVE
}

enum class Fertility {
    LOW, HIGH, HIGHEST
}

enum class Breasts {
    SWOLLEN, KNOT, DIMPLE, REDNESS, FISSURES, PAIN, DISCHARGE
}

enum class Mucus {
    DRY, STICKY, CREAMY, WATERY, EGGWHITE, UNUSUAL
}

enum class Lochia {
    RED, PINK, YELLOW, NONE
}

enum class Category {
    SEX, LIFESTYLE, SYMPTOMS, MOOD, NOTE, TESTS, BREASTS, MUCUS, LOCHIA
}