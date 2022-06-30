package com.occupantsearch.ui

import com.occupantsearch.occupant.Occupant
import csstype.Display
import csstype.FlexWrap
import csstype.JustifyContent
import csstype.px
import kotlinx.browser.document
import kotlinx.browser.window
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML.div
import react.useEffectOnce

external interface OccupantsListProps : Props {
    var list: List<Occupant>
    var onScrollBottom: () -> Unit
    var visible: Boolean
}

val OccupantsList = FC<OccupantsListProps> { props ->
    if (props.visible) {
        div {
            css {
                paddingTop = 60.px
                display = Display.flex
                flexWrap = FlexWrap.wrap
                justifyContent = JustifyContent.spaceEvenly
            }
            id = "content"
            props.list.forEach {
                OccupantCard { occupant = it }
            }
        }

        useEffectOnce {
            window.onscroll = {
                if (isInBottom()) {
                    props.onScrollBottom()
                }
            }
        }
    }
}

fun isInBottom() =
    window.scrollY + 5 * cardSize + window.innerHeight > document.getElementById("content")!!.clientHeight