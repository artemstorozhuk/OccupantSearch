import com.occupantsearch.occupant.Occupant
import csstype.BoxSizing
import csstype.Display
import csstype.FlexDirection
import csstype.FlexWrap
import csstype.JustifyContent
import csstype.ObjectFit
import csstype.TextAlign
import csstype.pct
import csstype.px
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.FC
import react.Props
import react.css.css
import react.dom.html.InputType
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.input
import react.useEffectOnce
import react.useState

private const val noImage =
    "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ad/Placeholder_no_text.svg/1200px-Placeholder_no_text.svg.png"
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
    input {
        css {
            margin = 5.px
            width = 100.pct
            boxSizing = BoxSizing.borderBox
        }
        type = InputType.text
        value = name
        onChange = { event ->
            scope.launch {
                page = 0
                query = event.target.value
                lastPage = false
                occupants = client.getOccupants(event.target.value, page)
            }
        }
    }
    val itemSize = 400
    div {
        css {
            id = "content"
            display = Display.flex
            flexWrap = FlexWrap.wrap
        }
        occupants.forEach { occupant ->
            a {
                css {
                    margin = 10.px
                }
                href = "https://vk.com/wall${occupant.postIds.first()}"
                div {
                    css {
                        width = itemSize.px
                        height = itemSize.px
                        display = Display.flex
                        flexDirection = FlexDirection.column
                        justifyContent = JustifyContent.center
                    }
                    img {
                        css {
                            padding = 50.px
                            maxWidth = 90.pct
                            maxHeight = 90.pct
                            objectFit = ObjectFit.contain
                        }
                        src = occupant.faceImageUrls.firstOrNull() ?: noImage
                    }
                    div {
                        css {
                            textAlign = TextAlign.center
                            marginTop = (-50).px
                        }
                        +occupant.person.fullName()
                    }
                }
            }
        }
    }
    window.onscroll = {
        if (!lastPage && isInBottom(itemSize)) {
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

fun isInBottom(itemSize: Int) =
    window.scrollY + itemSize + window.innerHeight > document.getElementById("content")!!.clientHeight