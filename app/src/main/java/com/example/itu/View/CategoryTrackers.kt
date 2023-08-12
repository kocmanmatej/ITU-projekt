package com.example.itu.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.itu.Model.*
import com.example.itu.R
import java.util.ArrayList
import java.util.Dictionary

/* Autor: Matej Kocman */

@Composable
fun Tracker(
    iconID: Int,
    title: String,
    Text: @Composable () -> Unit,
    primaryColor: Color,
    dataToDisplay: Any?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = if (dataToDisplay != null) primaryColor.copy(alpha = 0.25f) else Color.LightGray,
                    shape = CircleShape
                )
                .size(40.dp), contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = iconID),
                contentDescription = null,
                tint = if (dataToDisplay != null) primaryColor else Color.Black,
                modifier = Modifier.fillMaxSize(0.5f)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column(horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Top) {
            Text(
                text = title,
                color = if (dataToDisplay != null) primaryColor else Color.Black,
                letterSpacing = 0.1.sp,
                fontWeight = FontWeight(800),
            )
            if (dataToDisplay != null) {
                Text()
            }
        }
    }
}

@Composable
fun IntimityTrackerText(sex: ArrayList<Sex>) {
    var text = ""
    for (instance in sex) {
        text += if (instance.masturbation) "Masturbácia - " else "Sex - "
        if (instance.condom == true) {
            text += "kondóm, "
        }
        if (instance.condom == false) {
            text += "bez kondómu, "
        }
        if (instance.planB) {
            text += "tabletka po, "
        }
        if (instance.orgasm == true) {
            text += "orgazmus, "
        }
        if (instance.orgasm == false) {
            text += "bez orgazmu, "
        }
        if (instance.note != null) {
            text += "poznámka: " + instance.note
        }
        text += "\n"
    }
    text = text.dropLast(1)
    Text(
        text = text, fontSize = 12.sp, letterSpacing = 0.1.sp
    )
}

@Composable
fun LifestyleTrackerText(lifestyle: Lifestyle) {
    var text = ""
    if (lifestyle.weight != null) {
        text += "Váha - " + lifestyle.weight + "kg\n"
    }
    if (lifestyle.temp != null) {
        text += "Teplota - " + lifestyle.temp + "°C\n"
    }
    if (lifestyle.sleep != null) {
        text += "Spánok - " + lifestyle.sleep!! / 60 + "h" +
                lifestyle.sleep!! % 60 + "m\n"
    }
    if (lifestyle.water != null) {
        text += "Voda - " + lifestyle.water + "ml\n"
    }
    text = text.dropLast(1)
    Text(
        text = text, fontSize = 12.sp, letterSpacing = 0.1.sp
    )
}

@Composable
fun SetTrackerText(set: Set<Any>, dictionary: Map<Any, String>) {
    var text = ""
    for (descriptor in set) {
        text += dictionary[descriptor] + ", "
    }
    text = text.dropLast(2)
    Text(
        text = text, fontSize = 12.sp, letterSpacing = 0.1.sp
    )
}

@Composable
fun NoteTrackerText(note: String) {
    Text(
        text = note, fontSize = 12.sp, letterSpacing = 0.1.sp
    )
}

@Composable
fun TestTrackerText(
    ovulationTest: Boolean?,
    gravidityTest: Gravidity?,
    fertilityTest: Fertility?,
) {
    var text = ""
    if (ovulationTest == true) {
        text += "Ovulácia - pozitívny\n"
    }
    if (ovulationTest == false) {
        text += "Ovulácia - negatívny\n"
    }
    if (gravidityTest == Gravidity.NEGATIVE) {
        text += "Gravidita - negatívny\n"
    }
    if (gravidityTest == Gravidity.FEINT_LINE) {
        text += "Gravidita - slabá čiarka\n"
    }
    if (gravidityTest == Gravidity.POSITIVE) {
        text += "Gravidita - pozitívny\n"
    }
    if (fertilityTest == Fertility.LOW) {
        text += "Plodnosť - nízka\n"
    }
    if (fertilityTest == Fertility.HIGH) {
        text += "Plodnosť - vysoká\n"
    }
    if (fertilityTest == Fertility.HIGHEST) {
        text += "Plodnosť - najvyššia\n"
    }
    text = text.dropLast(1)
    Text(
        text = text, fontSize = 12.sp, letterSpacing = 0.1.sp
    )
}

@Composable
fun LochiaTrackerText(lochia: Lochia?) {
    var text = ""
    if (lochia == Lochia.NONE) {
        text += "Žiadna\n"
    }
    if (lochia == Lochia.YELLOW) {
        text += "Žltkastá/belavá\n"
    }
    if (lochia == Lochia.PINK) {
        text += "Hnedkastá/ružová\n"
    }
    if (lochia == Lochia.RED) {
        text += "Červená\n"
    }
    text = text.dropLast(1)
    Text(
        text = text, fontSize = 12.sp, letterSpacing = 0.1.sp
    )
}

