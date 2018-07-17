package ru.nickmiller.magpie.model

import ru.nickmiller.magpie.R


data class Topic(val id: Int, val title: String, val drawerId: Int = 0, val bgColor: Int = R.color.colorPrimary)