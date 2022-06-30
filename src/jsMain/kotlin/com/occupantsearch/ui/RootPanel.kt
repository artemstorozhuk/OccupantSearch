package com.occupantsearch.ui

import com.occupantsearch.client.Client
import com.occupantsearch.export.Format
import com.occupantsearch.occupant.Occupant
import kotlinx.browser.window
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.FC
import react.Props
import react.useEffectOnce
import react.useState

val scope = MainScope()
val client = Client()

external interface RootPanelProps : Props {
    var query: String
    var occupants: List<Occupant>
    var page: Int
    var lastPage: Boolean
    var leftPanelOpen: Boolean
    var visiblePanelType: PanelType
}

val RootPanel = FC<RootPanelProps> { props ->
    var query by useState(props.query)
    var occupants by useState(props.occupants)
    var page by useState(props.page)
    var lastPage by useState(props.lastPage)
    var leftPanelOpen by useState(props.leftPanelOpen)
    var visiblePanel by useState(props.visiblePanelType)

    useEffectOnce {
        scope.launch {
            occupants = client.getOccupants("", 0)
        }
    }

    TopBar {
        onSearch = { value ->
            scope.launch {
                page = 0
                query = value
                lastPage = false
                occupants = client.getOccupants(value, page)
                window.scrollTo(0.0, 0.0)
            }
        }
        showSearchInput = visiblePanel == PanelType.OCCUPANTS_LIST
        onMenuClick = {
            leftPanelOpen = !leftPanelOpen
        }
    }

    OccupantsList {
        visible = visiblePanel == PanelType.OCCUPANTS_LIST
        list = occupants
        onScrollBottom = {
            if (!lastPage) {
                scope.launch {
                    val nextPage = client.getOccupants(query, page + 1)
                    if (nextPage.isEmpty()) {
                        lastPage = true
                    } else {
                        page++
                        occupants = occupants + nextPage
                    }
                }
            }
        }
    }

    AnalyticsChart {
        visible = visiblePanel == PanelType.ANALYTICS_CHART
    }

    LeftPanel {
        open = leftPanelOpen
        onSearchClick = {
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
}
