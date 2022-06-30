package com.occupantsearch

import com.occupantsearch.chartjs.init
import com.occupantsearch.ui.PanelType
import com.occupantsearch.ui.RootPanel
import kotlinx.browser.document
import react.create
import react.dom.render

fun main() {
    init()

    val container = document.createElement("div")
    document.body!!.appendChild(container)
    render(RootPanel.create {
        query = ""
        occupants = listOf()
        page = 0
        lastPage = false
        leftPanelOpen = false
        visiblePanelType = PanelType.OCCUPANTS_LIST
    }, container)
}