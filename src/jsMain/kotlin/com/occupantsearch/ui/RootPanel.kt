package com.occupantsearch.ui

import com.occupantsearch.client.Client
import com.occupantsearch.export.Format
import com.occupantsearch.occupant.Occupant
import csstype.Display
import csstype.FlexWrap
import csstype.JustifyContent
import csstype.px
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML.div
import react.useEffectOnce
import react.useState

val scope = MainScope()
val client = Client()

external interface RootPanelProps : Props

val RootPanel = FC<RootPanelProps> {
    var searchQuery by useState("")
    var occupants by useState(listOf<Occupant>())
    var leftPanelOpen by useState(false)
    var visiblePanel by useState(PanelType.OCCUPANTS_LIST)
    var lastPage by useState(false)
    var page by useState(0)
    var loading by useState(false)

    val load = { query: String, index: Int, list: List<Occupant> ->
        if (!loading && !lastPage) {
            loading = true
            searchQuery = query
            scope.launch {
                val nextPage = client.getOccupants(query, index)
                if (nextPage.isEmpty()) {
                    lastPage = true
                    page = index
                    occupants = list
                } else {
                    lastPage = false
                    page = index + 1
                    occupants = list + nextPage
                }
                loading = false
            }
        }
    }

    useEffectOnce { load("", 0, listOf()) }

    TopBar {
        onSearch = { value ->
            load(value, 0, listOf())
            window.scrollTo(0.0, 0.0)
        }
        showSearchInput = visiblePanel == PanelType.OCCUPANTS_LIST
        onMenuClick = {
            leftPanelOpen = !leftPanelOpen
        }
    }

    when (visiblePanel) {
        PanelType.OCCUPANTS_LIST -> div {
            css {
                paddingTop = 60.px
                display = Display.flex
                flexWrap = FlexWrap.wrap
                justifyContent = JustifyContent.spaceEvenly
            }
            id = "content"
            occupants.forEach {
                OccupantCard { occupant = it }
            }
        }
        PanelType.ANALYTICS_CHART -> AnalyticsChart {
            id = "chart"
        }
    }

    LeftPanel {
        open = leftPanelOpen
        onSearchClick = {
            searchQuery = ""
            leftPanelOpen = false
            visiblePanel = PanelType.OCCUPANTS_LIST
        }
        onChartClick = {
            leftPanelOpen = false
            visiblePanel = PanelType.ANALYTICS_CHART
        }
        onJsonClick = {
            leftPanelOpen = false
            client.export(Format.JSON)
        }
        onCsvClick = {
            leftPanelOpen = false
            client.export(Format.CSV)
        }
    }

    window.onscroll = {
        if (isInBottom()) {
            load(searchQuery, page, occupants)
        }
    }
}

fun isInBottom() =
    window.scrollY + 5 * cardSize + window.innerHeight > document.getElementById("content")!!.clientHeight