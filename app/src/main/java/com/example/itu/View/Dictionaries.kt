package com.example.itu.View

import com.example.itu.Model.*

// Author: Jakub Drobena

val PhaseDict = mapOf(
    Phase.LIGHT_FLOW to "slabý výtok",
    Phase.MEDIUM_FLOW to "stredný výtok",
    Phase.HEAVY_FLOW to "silný výtok",
    Phase.DISASTER_FLOW to "pohroma",
    Phase.FERTILE to "plodné obdobie",
    Phase.OVULATING to "ovulácia"
)

val SymptomsDict = mapOf(
    Symptom.BELLY_CRAMPS to "Kŕče v bruchu",
    Symptom.BREAST_PAIN to "Bolestivé prsníky",
    Symptom.LOWER_BACK_PAIN to "Bolesť v krížoch",
    Symptom.BLOATING to "Nadúvanie",
    Symptom.HEADACHE to "Bolesť hlavy",
    Symptom.ACNE to "Akné",
    Symptom.SWEARING to "Chúlostivosť",
    Symptom.TIREDNESS to "Únava",
    Symptom.INSOMNIA to "Nespavosť",
    Symptom.CONGESTION to "Zápcha",
    Symptom.DIARRHEA to "Hnačka",
    Symptom.GASSINESS to "Plynatosť",
    Symptom.SPOTTING to "Špinenie"
)

val MoodDict = mapOf(
    Mood.NORMAL to "Normálna",
    Mood.HAPPY to "Šťastná",
    Mood.ANGRY to "Nahnevaná",
    Mood.IN_LOVE to "Zaľúbená",
    Mood.TIRED to "Vyčerpaná",
    Mood.SAD to "Smutná",
    Mood.DEPRESSED to "Depresívna",
    Mood.EMOTIONAL to "Emocionálna",
    Mood.ANGSTY to "Úzkostná",
)

val GravidityDict = mapOf(
    Gravidity.POSITIVE to "Pozitívny",
    Gravidity.NEGATIVE to "Negatívny",
    Gravidity.FEINT_LINE to "Slabá čiarka"
)

val FertilityDict = mapOf(
    Fertility.LOW to "Nízka",
    Fertility.HIGH to "Vysoká",
    Fertility.HIGHEST to "najvyššia"
)

val BreastDict = mapOf(
    Breasts.SWOLLEN to "Opuch",
    Breasts.KNOT to "Hrčka",
    Breasts.DIMPLE to "Priehlbina",
    Breasts.REDNESS to "Začervenanie pokožky",
    Breasts.FISSURES to "Praskliny na bradavkách",
    Breasts.PAIN to "Bolesť",
    Breasts.DISCHARGE to "Výtok z bradavky"
)

val MucusDict = mapOf(
    Mucus.DRY to "Sucho",
    Mucus.STICKY to "Lepkavý",
    Mucus.CREAMY to "Krémový",
    Mucus.WATERY to "Vodnatý",
    Mucus.EGGWHITE to "Kvalita vaječného bielka",
    Mucus.UNUSUAL to "Nezvyčajný"
)

val LochiaDict = mapOf(
    Lochia.YELLOW to "Žltá",
    Lochia.NONE to "Žiadna",
    Lochia.RED to "Červená",
    Lochia.PINK to "Ružová"
)