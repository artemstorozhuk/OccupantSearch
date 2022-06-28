package com.occupantsearch.ui

import csstype.pct
import csstype.px
import mui.icons.material.FileDownload
import mui.icons.material.Search
import mui.material.Drawer
import mui.material.DrawerAnchor
import mui.material.DrawerVariant
import mui.material.List
import mui.material.ListItem
import mui.material.ListItemButton
import mui.material.ListItemIcon
import mui.material.ListItemText
import mui.system.sx
import react.FC
import react.Props

external interface LeftBarProps : Props {
    var onSearchClick: () -> Unit
    var onJsonClick: () -> Unit
    var onCsvClick: () -> Unit
    var open: Boolean
}

val LeftPanel = FC<LeftBarProps> { props ->
    Drawer {
        sx {
            width = 100.pct
            maxWidth = 500.px
        }
        variant = DrawerVariant.persistent
        anchor = DrawerAnchor.left
        open = props.open
        List {
            ListItem {
                disablePadding = true
                ListItemButton {
                    onClick = {
                        props.onSearchClick()
                    }
                    ListItemIcon {
                        Search()
                    }
                    ListItemText {
                        +"Search"
                    }
                }
            }
            ListItem {
                disablePadding = true
                ListItemButton {
                    onClick = {
                        props.onJsonClick()
                    }
                    ListItemIcon {
                        FileDownload()
                    }
                    ListItemText {
                        +"Export JSON"
                    }
                }
            }
            ListItem {
                disablePadding = true
                ListItemButton {
                    onClick = {
                        props.onCsvClick()
                    }
                    ListItemIcon {
                        FileDownload()
                    }
                    ListItemText {
                        +"Export CSV"
                    }
                }
            }
        }
    }
}