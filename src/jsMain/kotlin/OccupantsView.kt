import com.occupantsearch.occupant.Occupant
import csstype.AlignItems
import csstype.Display
import csstype.FlexWrap
import csstype.JustifyContent
import csstype.Position
import csstype.integer
import csstype.pct
import csstype.px
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import mui.icons.material.Search
import mui.material.IconButton
import mui.material.Input
import mui.material.Paper
import mui.system.sx
import org.w3c.dom.HTMLInputElement
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.form
import react.useEffectOnce
import react.useState

private val scope = MainScope()
private val client = Client()

external interface OccupantsListProps : Props {
    var query: String
    var occupants: List<Occupant>
    var page: Int
    var lastPage: Boolean
}

val OccupantsView = FC<OccupantsListProps> { props ->
    var query by useState(props.query)
    var occupants by useState(props.occupants)
    var page by useState(props.page)
    var lastPage by useState(props.lastPage)
    useEffectOnce {
        scope.launch {
            occupants = client.getOccupants("", 0)
        }
    }
    Paper {
        css {
            width = 100.pct
            position = Position.fixed
            zIndex = integer(100)
        }
        component = form
        sx {
            display = Display.flex
            alignItems = AlignItems.center
            width = 100.pct
        }
        Input {
            css {
                marginLeft = 10.px
                width = 100.pct
            }
            placeholder = "Search"
            value = name
            onChange = { event ->
                val value = (event.target as HTMLInputElement).value
                scope.launch {
                    page = 0
                    query = value
                    lastPage = false
                    occupants = client.getOccupants(value, page)
                    window.scrollTo(0.0, 0.0)
                }
            }
        }
        IconButton {
            Search {
            }
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