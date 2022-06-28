package com.occupantsearch

import com.occupantsearch.ui.OccupantsView
import kotlinx.browser.document
import react.create
import react.dom.render

fun main() {
    val container = document.createElement("div")
    document.body!!.appendChild(container)
    render(OccupantsView.create {
        query = ""
        occupants = listOf()
        page = 0
        lastPage = false
        leftPanelOpen = false
    }, container)
}