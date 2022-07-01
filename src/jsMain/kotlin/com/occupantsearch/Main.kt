package com.occupantsearch

import com.occupantsearch.chartjs.init
import com.occupantsearch.ui.RootPanel
import kotlinx.browser.document
import react.create
import react.dom.client.createRoot

fun main() {
    init()
    document.createElement("div").also {
        document.body!!.appendChild(it)
        createRoot(it).render(RootPanel.create {})
    }
}