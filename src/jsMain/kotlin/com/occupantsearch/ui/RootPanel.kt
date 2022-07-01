package com.occupantsearch.ui

import com.occupantsearch.client.Client
import com.occupantsearch.export.Format
import com.occupantsearch.occupant.Occupant
import csstype.AlignItems
import csstype.Color
import csstype.Display
import csstype.FlexWrap
import csstype.FontWeight
import csstype.JustifyContent
import csstype.Position
import csstype.TextAlign
import csstype.pct
import csstype.px
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import mui.material.Box
import mui.material.LinearProgress
import mui.material.LinearProgressVariant
import mui.material.Typography
import mui.material.styles.TypographyVariant
import mui.system.sx
import org.w3c.dom.Element
import org.w3c.dom.asList
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
    var page by useState(0)
    var loading by useState(false)
    var foundCount by useState(0)
    var progress by useState(0)

    val hasMorePages = {
        (occupants.isEmpty() && foundCount == 0) || occupants.size < foundCount
    }

    val load = { query: String, index: Int, list: List<Occupant> ->
        if (!loading) {
            loading = true
            searchQuery = query
            scope.launch {
                val response = client.getOccupants(query, index)
                foundCount = response.foundCount
                occupants = list + response.occupants
                page = index
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
            Box {
                sx {
                    width = 100.pct
                    position = Position.fixed
                    bottom = 0.px
                    backgroundColor = Color("rgba(0, 0, 0, 0.5)")
                }
                Box {
                    sx {
                        paddingLeft = 20.px
                        display = Display.flex
                        alignItems = AlignItems.center
                    }
                    Box {
                        sx {
                            width = 100.pct
                        }
                        LinearProgress {
                            variant = LinearProgressVariant.determinate
                            value = (progress * 100 / foundCount)
                        }
                    }
                    Box {
                        sx {
                            width = 100.px
                        }
                        Typography {
                            sx {
                                textAlign = TextAlign.center
                                color = Color("lightblue")
                                fontWeight = FontWeight.bold
                            }
                            variant = TypographyVariant.body2
                            +"$progress / $foundCount"
                        }
                    }
                }
            }
        }
        PanelType.ANALYTICS_CHART -> AnalyticsChart {
            id = "chart"
        }
    }


    LeftPanel {
        open = leftPanelOpen
        onSearchClick = {
            load("", 0, listOf())
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
        progress = getFirstVisibleChildIndex()
        if (isInBottom() && hasMorePages()) {
            load(searchQuery, page + 1, occupants)
        }
    }
}

fun getFirstVisibleChildIndex() = document.getElementById("content")
    ?.children
    ?.asList()
    ?.indexOfFirst { it.isVisible() }
    ?: 0

fun Element.isVisible(): Boolean {
    return getBoundingClientRect().bottom >= 0
}

fun isInBottom() =
    window.scrollY + 5 * cardSize + window.innerHeight > document.getElementById("content")!!.clientHeight