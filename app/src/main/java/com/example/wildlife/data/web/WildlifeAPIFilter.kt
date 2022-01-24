package com.example.wildlife.data.web

enum class WildlifeAPIFilter(val group: String) {
    SHOW_ALL(""),
    SHOW_MAMMALS("mammals"),
    SHOW_BIRDS("birds"),
    SHOW_REPTILES("reptiles"),
    SHOW_AMPHIBIANS("amphibians"),
    SHOW_FISH("fish"),
    SHOW_INVERTEBRATES("invertebrates")
}