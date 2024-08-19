package com.example.semprace.model

import java.io.Serializable

data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val timestamp: Long
) : Serializable
