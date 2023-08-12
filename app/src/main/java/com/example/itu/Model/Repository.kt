package com.example.itu.Model


import androidx.compose.ui.graphics.Color
import com.google.gson.Gson
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

// Authors: Matej Kocman, Tereza Straková, Jakub Drobena


// Author: Matej Kocman
class Repository { // comment out Database(..) object and call initDataFromJSON() for persistent data
    private val database: Database = //initDataFromJSON()
    Database(
        arrayListOf(
            Day(
                CalendarDate(2022, 11, 26),
                phase = Phase.LIGHT_FLOW,
            ),
            Day(
                CalendarDate(2022, 11, 27),
                phase = Phase.DISASTER_FLOW,
            ),
            Day(
                CalendarDate(2022, 12, 23),
                phase = Phase.MEDIUM_FLOW,
            ),
            Day(
                CalendarDate(2022, 12, 24),
                phase = Phase.DISASTER_FLOW,
            ),
            Day(
                CalendarDate(2022, 12, 26),
                phase = Phase.LIGHT_FLOW,
            ),
            Day(
                CalendarDate(2022, 12, 25),
                phase = Phase.HEAVY_FLOW,
                sex = arrayListOf(Sex(condom = true), Sex(planB = true))
            ),
            Day(
                CalendarDate(2022, 12, 1),
                sex = arrayListOf(Sex(condom = true), Sex(planB = true))
            ),
            Day(
                CalendarDate(2022, 11, 30),
                phase = Phase.HEAVY_FLOW,
                sex = arrayListOf(Sex(condom = true), Sex(planB = true))
            ),
            Day(
                CalendarDate(2022, 11, 29),
                phase = Phase.MEDIUM_FLOW,
                sex = arrayListOf(Sex(condom = true), Sex(planB = true))
            ), Day(
                CalendarDate(2022, 12, 15),
                sex = arrayListOf(Sex(condom = true), Sex(planB = true))
            ), Day(
                CalendarDate(2022, 12, 2),
                lifestyle = Lifestyle(weight = 80f),
                sex = arrayListOf(Sex(condom = true), Sex(planB = true))
            ), Day(
                CalendarDate(2022, 11, 28),
                phase = Phase.LIGHT_FLOW,
                lifestyle = Lifestyle(weight = 50f),
                symptoms = mutableSetOf(Symptom.SPOTTING, Symptom.DIARRHEA)
            ), Day(CalendarDate(2022, 12, 12), lifestyle = Lifestyle(temp = 37.7f)), Day(
                CalendarDate(2022, 12, 20),
                null,
                arrayListOf(
                    Sex(masturbation = true),
                    Sex(condom = false, planB = true, note = "9 hodin po tabletka")
                ),
                Lifestyle(75.5f, 37.7f, 515, 2400),
                mutableSetOf(Symptom.SPOTTING, Symptom.SWEARING, Symptom.TIREDNESS),
                mutableSetOf(Mood.ANGRY, Mood.DEPRESSED, Mood.EMOTIONAL),
                "Divoky den toto joj.",
                null,
                Gravidity.NEGATIVE,
                null,
                mutableSetOf(Breasts.SWOLLEN),
                mutableSetOf(Mucus.CREAMY, Mucus.STICKY),
                Lochia.NONE
            )
        ), arrayListOf(
            Category.SEX,
            Category.LIFESTYLE,
            Category.SYMPTOMS,
            Category.MUCUS,
            Category.MOOD,
            Category.NOTE,
            Category.BREASTS,
            Category.TESTS,
            Category.LOCHIA
        ), mapOf(
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

    // TODO relative instead of absolute path
    private fun initDataFromJSON(): Database {
        val gson = Gson()
        if (!File("/data/user/0/com.example.itu/files/database.json").isFile) {
            return Database()
        }
        return gson.fromJson(
            File("/data/user/0/com.example.itu/files/database.json").readText(),
            Database::class.java
        ) ?: Database()
    }

    // TODO relative instead of absolute path
    fun saveDataAsJSON() {
        val gson = Gson()
        val jsonString = gson.toJson(database)
        File("/data/user/0/com.example.itu/files/database.json").writeText(jsonString)
    }

    private fun orderDaysInDatabase() {
        database.data =
            ArrayList(database.data.sortedWith(compareBy<Day> { it.date.year }.thenBy { it.date.month }
                .thenBy { it.date.day }))
    }

    fun getDay(date: CalendarDate): Day? {
        return database.data.firstOrNull { it.date == date }
    }

    private fun getLastPeriodDayBefore(date: CalendarDate): CalendarDate? {
        orderDaysInDatabase()

        for (day in database.data.asReversed()) {
            if ((day.date.year < date.year ||
                        (day.date.year == date.year && day.date.month < date.month) ||
                        (day.date.year == date.year && day.date.month == date.month && day.date.day < date.day))
                &&
                (day.phase == Phase.LIGHT_FLOW ||
                        day.phase == Phase.MEDIUM_FLOW ||
                        day.phase == Phase.HEAVY_FLOW ||
                        day.phase == Phase.DISASTER_FLOW)
            ) {
                return day.date
            }
        }
        return null
    }

    fun getFirstPeriodDayBefore(date: CalendarDate): CalendarDate? {
        var firstPeriodDayBefore = getLastPeriodDayBefore(date) ?: return null

        val calendar = Calendar.getInstance()
        calendar.set(
            firstPeriodDayBefore.year,
            firstPeriodDayBefore.month - 1,
            firstPeriodDayBefore.day
        )

        var dayBefore = getDay(
            CalendarDate(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        ) ?: return firstPeriodDayBefore

        while (dayBefore.phase == Phase.LIGHT_FLOW ||
            dayBefore.phase == Phase.MEDIUM_FLOW ||
            dayBefore.phase == Phase.HEAVY_FLOW ||
            dayBefore.phase == Phase.DISASTER_FLOW
        ) {
            firstPeriodDayBefore = dayBefore.date
            calendar.add(Calendar.DAY_OF_MONTH, -1)
            dayBefore = getDay(
                CalendarDate(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH) + 1,
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
            ) ?: return firstPeriodDayBefore
        }
        return firstPeriodDayBefore
    }

    fun getFirstPeriodDayAfter(date: CalendarDate): CalendarDate? {
        orderDaysInDatabase()

        val thisDate = getNonPeriodDayAfter(date)

        for (day in database.data) {
            if ((day.date.year > thisDate.year ||
                        (day.date.year == thisDate.year && day.date.month > thisDate.month) ||
                        (day.date.year == thisDate.year && day.date.month == thisDate.month && day.date.day > thisDate.day))
                &&
                (day.phase == Phase.LIGHT_FLOW ||
                        day.phase == Phase.MEDIUM_FLOW ||
                        day.phase == Phase.HEAVY_FLOW ||
                        day.phase == Phase.DISASTER_FLOW)
            ) {
                return day.date
            }
        }
        return null
    }

    private fun getNonPeriodDayAfter(date: CalendarDate): CalendarDate {
        val calendar = Calendar.getInstance()
        calendar.set(
            date.year,
            date.month - 1,
            date.day
        )

        var nonPeriodDayAfter = date
        var dayAfter = getDay(date) ?: return nonPeriodDayAfter

        while (dayAfter.phase == Phase.LIGHT_FLOW ||
            dayAfter.phase == Phase.MEDIUM_FLOW ||
            dayAfter.phase == Phase.HEAVY_FLOW ||
            dayAfter.phase == Phase.DISASTER_FLOW
        ) {
            nonPeriodDayAfter = dayAfter.date
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            dayAfter = getDay(
                CalendarDate(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH) + 1,
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
            ) ?: return nonPeriodDayAfter
        }
        return nonPeriodDayAfter
    }
    // End Author: Matej Kocman

    // Author: Tereza Straková
    fun getCategoryOrder(): MutableList<Category> {
        return database.categoryOrder
    }

    fun moveCategoryOrder(fromPos: Int, toPos: Int) {
        database.categoryOrder = database.categoryOrder.toMutableList().apply {
            add(toPos, removeAt(fromPos))
        }
    }

    fun getCategoryColor(): Map<Category, Color> {
        return database.categoryColor
    }

    fun setColorForCategory(newColor: Color, onCategory: Category) {
        val mutableMap = database.categoryColor.toMutableMap()
        mutableMap[onCategory] = newColor
        database.categoryColor = mutableMap
    }
    // End Author: Tereza Straková

    // Author: Jakub Drobena
    fun setWeight(kg: Float, dayToEdit: CalendarDate) {
        val day = database.data.firstOrNull { it.date == dayToEdit }
        if (day != null) {
            if (day.lifestyle != null)
                day.lifestyle?.weight = kg
            else
                day.lifestyle = Lifestyle(weight = kg)
        } else {
            database.data.add(Day(dayToEdit, lifestyle = Lifestyle(kg)))
        }
    }

    fun addSex(dayToEdit: CalendarDate) {
        val day = database.data.firstOrNull { it.date == dayToEdit }
        if (day != null) {
            if (day.sex != null) {
                day.sex!!.add(Sex())
            } else {
                day.sex = arrayListOf(Sex())
            }
        } else {
            database.data.add(Day(dayToEdit, sex = arrayListOf(Sex())))
        }
    }

    fun setSleep(startSleep: String, endSleep: String, dayToEdit: CalendarDate) {
        val splited = startSleep.split(":")
        val splited2 = endSleep.split(":")
        val startMinutes = splited[0].toInt() * 60 + splited[1].toInt()
        val endMinutes = splited2[0].toInt() * 60 + splited2[1].toInt()
        var sleep = endMinutes - startMinutes
        if (sleep < 0)
            sleep += 24 * 60
        val day = database.data.firstOrNull { it.date == dayToEdit }
        if (day != null) {
            if (day.lifestyle != null)
                day.lifestyle?.sleep = sleep
            else
                day.lifestyle = Lifestyle(sleep = sleep)
        } else {
            database.data.add(Day(dayToEdit, lifestyle = Lifestyle(sleep = sleep)))
        }
    }

    fun setTemp(celsius: Float, dayToEdit: CalendarDate) {
        val day = database.data.firstOrNull { it.date == dayToEdit }
        if (day != null) {
            if (day.lifestyle != null)
                day.lifestyle?.temp = celsius
            else
                day.lifestyle = Lifestyle(temp = celsius)
        } else {
            database.data.add(Day(dayToEdit, lifestyle = Lifestyle(temp = celsius)))
        }
    }

    fun setWater(cups: Int, dayToEdit: CalendarDate) {
        val day = database.data.firstOrNull { it.date == dayToEdit }
        if (day != null) {
            if (day.lifestyle != null)
                day.lifestyle?.water = cups
            else
                day.lifestyle = Lifestyle(water = cups)
        } else {
            database.data.add(Day(dayToEdit, lifestyle = Lifestyle(water = cups)))
        }
    }

    fun setCondom(index: Int, dayToEdit: CalendarDate) {
        val day = database.data.firstOrNull { it.date == dayToEdit }
        if (day != null) {
            day.sex!![index].condom = day.sex!![index].condom != true
        }

    }

    fun setOvulation(dayToEdit: CalendarDate) {
        val day = database.data.firstOrNull { it.date == dayToEdit }
        if (day != null) {
            day.ovulationTest = true
        } else {
            database.data.add(Day(dayToEdit, ovulationTest = true))
        }

    }

    fun setOvulationNeg(dayToEdit: CalendarDate) {
        val day = database.data.firstOrNull { it.date == dayToEdit }
        if (day != null) {
            day.ovulationTest = false
        } else {
            database.data.add(Day(dayToEdit, ovulationTest = false))
        }

    }

    fun setMasturbation(index: Int, dayToEdit: CalendarDate) {
        val day = database.data.firstOrNull { it.date == dayToEdit }
        if (day != null) {
            day.sex!![index].masturbation = day.sex!![index].masturbation != true
        }

    }

    fun setNote(note: String, index: Int, dayToEdit: CalendarDate) {
        val day = database.data.firstOrNull { it.date == dayToEdit }
        if (day != null) {
            day.sex!![index].note = note
        }

    }

    fun setPlan_B(index: Int, dayToEdit: CalendarDate) {
        val day = database.data.firstOrNull { it.date == dayToEdit }
        if (day != null) {
            day.sex!![index].planB = day.sex!![index].planB != true
        }

    }

    fun setOrgasm(index: Int, dayToEdit: CalendarDate) {
        val day = database.data.firstOrNull { it.date == dayToEdit }
        if (day != null) {
            day.sex!![index].orgasm = day.sex!![index].orgasm != true
        }

    }

    fun setSymptom(symptom: Symptom, dayToEdit: CalendarDate) {
        val day = database.data.firstOrNull { it.date == dayToEdit }
        if (day != null) {
            if (day.symptoms == null) {
                day.symptoms = mutableSetOf(symptom)
            } else {
                if (day.symptoms?.contains(symptom) == true) {
                    day.symptoms?.remove(symptom)
                } else {
                    day.symptoms?.add(symptom)

                }
            }
        } else {
            val newList: MutableSet<Symptom> = mutableSetOf(symptom)
            database.data.add(Day(dayToEdit, symptoms = newList))
        }
    }

    fun setMood(mood: Mood, dayToEdit: CalendarDate) {
        val day = database.data.firstOrNull { it.date == dayToEdit }
        if (day != null) {
            if (day.mood == null) {
                day.mood = mutableSetOf(mood)
            } else {
                if (day.mood?.contains(mood) == true) {
                    day.mood?.remove(mood)
                } else {
                    day.mood?.add(mood)

                }
            }
        } else {
            val newList: MutableSet<Mood> = mutableSetOf(mood)
            database.data.add(Day(dayToEdit, mood = newList))
        }
    }

    fun setBreast(breasts: Breasts, dayToEdit: CalendarDate) {
        val day = database.data.firstOrNull { it.date == dayToEdit }
        if (day != null) {
            if (day.breastExam == null) {
                day.breastExam = mutableSetOf(breasts)
            } else {
                if (day.breastExam?.contains(breasts) == true) {
                    day.breastExam?.remove(breasts)
                } else {
                    day.breastExam?.add(breasts)

                }
            }
        } else {
            val newList: MutableSet<Breasts> = mutableSetOf(breasts)
            database.data.add(Day(dayToEdit, breastExam = newList))
        }
    }

    fun setMucus(mucus: Mucus, dayToEdit: CalendarDate) {
        val day = database.data.firstOrNull { it.date == dayToEdit }
        if (day != null) {
            if (day.cervicalMucus == null) {
                day.cervicalMucus = mutableSetOf(mucus)
            } else {
                if (day.cervicalMucus?.contains(mucus) == true) {
                    day.cervicalMucus?.remove(mucus)
                } else {
                    day.cervicalMucus?.add(mucus)

                }
            }
        } else {
            val newList: MutableSet<Mucus> = mutableSetOf(mucus)
            database.data.add(Day(dayToEdit, cervicalMucus = newList))
        }
    }

    fun setLochia(lochia: Lochia, dayToEdit: CalendarDate) {
        val day = database.data.firstOrNull { it.date == dayToEdit }
        if (day != null) {
            day.lochia = lochia
        } else {
            database.data.add(Day(dayToEdit, lochia = lochia))
        }
    }

    fun setDayNote(note: String, dayToEdit: CalendarDate) {
        val day = database.data.firstOrNull { it.date == dayToEdit }
        if (day != null) {
            day.note = note
        } else {
            database.data.add(Day(dayToEdit, note = note))
        }
    }

    fun setGravidity(gravidity: Gravidity, dayToEdit: CalendarDate) {
        val day = database.data.firstOrNull { it.date == dayToEdit }
        if (day != null) {
            day.gravidityTest = gravidity
        } else {
            database.data.add(Day(dayToEdit, gravidityTest = gravidity))
        }
    }

    fun setFertility(fertility: Fertility, dayToEdit: CalendarDate) {
        val day = database.data.firstOrNull { it.date == dayToEdit }
        if (day != null) {
            day.fertilityTest = fertility
        } else {
            database.data.add(Day(dayToEdit, fertilityTest = fertility))
        }
    }
}
// End Author: Jakub Drobena

