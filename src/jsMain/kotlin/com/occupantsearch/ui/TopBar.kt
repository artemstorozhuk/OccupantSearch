package com.occupantsearch.ui

import csstype.Position
import csstype.integer
import csstype.number
import csstype.pct
import csstype.px
import mui.icons.material.Menu
import mui.icons.material.Search
import mui.material.AppBar
import mui.material.AppBarPosition
import mui.material.Box
import mui.material.IconButton
import mui.material.Input
import mui.material.Size
import mui.material.Toolbar
import mui.system.sx
import org.w3c.dom.HTMLInputElement
import react.FC
import react.Props
import react.css.css

external interface TopBarProps : Props {
    var onSearch: (String) -> Unit
    var onMenuClick: () -> Unit
}

val TopBar = FC<TopBarProps> { props ->
    Box {
        sx {
            flexGrow = number(1.0)
        }
        css {
            width = 100.pct
            position = Position.fixed
            zIndex = integer(100)
            margin = (-10).px
        }
        AppBar {
            position = AppBarPosition.sticky
            Toolbar {
                IconButton {
                    size = Size.large
                    Menu {
                    }
                    onClick = {
                        props.onMenuClick()
                    }
                }
                Input {
                    css {
                        width = 100.pct
                    }
                    placeholder = "Search…"
                    onChange = { event ->
                        props.onSearch((event.target as HTMLInputElement).value)
                    }
                }
                IconButton {
                    Search {
                    }
                }
            }
        }
    }
}