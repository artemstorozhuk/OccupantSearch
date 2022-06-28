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

private val scope = MainScope()
private val client = Client()

external interface OccupantsListProps : Props {
    var query: String
    var occupants: List<Occupant>
    var page: Int
    var lastPage: Boolean
    var leftPanelOpen: Boolean
}

val OccupantsView = FC<OccupantsListProps> { props ->
    var query by useState(props.query)
    var occupants by useState(props.occupants)
    var page by useState(props.page)
    var lastPage by useState(props.lastPage)
    var leftPanelOpen by useState(props.leftPanelOpen)

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
        onMenuClick = {
            leftPanelOpen = !leftPanelOpen
        }
    }
    LeftPanel {
        open = leftPanelOpen
        onSearchClick = {
            leftPanelOpen = false
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
    div {
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

    window.onscroll = {
        if (!lastPage && isInBottom()) {
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

fun isInBottom() =
    window.scrollY + 5 * cardSize + window.innerHeight > document.getElementById("content")!!.clientHeight