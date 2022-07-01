package com.occupantsearch.ui

import com.occupantsearch.occupant.Occupant
import com.occupantsearch.vk.postUrl
import csstype.ObjectFit
import csstype.px
import mui.material.Card
import mui.material.CardActionArea
import mui.material.CardContent
import mui.material.CardMedia
import mui.material.Typography
import mui.material.styles.TypographyVariant
import mui.system.sx
import react.FC
import react.Props
import react.css.css
import react.dom.html.AnchorTarget
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img

external interface OccupantCardProps : Props {
    var occupant: Occupant
}

const val cardSize = 400

private const val noImage =
    "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ad/Placeholder_no_text.svg/1200px-Placeholder_no_text.svg.png"

val OccupantCard = FC<OccupantCardProps> { props ->
    a {
        css {
            margin = 10.px
        }
        target = AnchorTarget._blank
        rel = "noopener noreferrer"
        href = props.occupant.postIds.first().postUrl
        Card {
            sx {
                width = cardSize.px
                height = cardSize.px
            }
            CardActionArea {
                CardMedia {
                    component = img
                    image = props.occupant.faceImageUrls.firstOrNull() ?: noImage
                    sx {
                        maxHeight = (cardSize - 50).px
                        objectFit = ObjectFit.contain
                    }
                }
                CardContent {
                    Typography {
                        gutterBottom
                        variant = TypographyVariant.h5
                        component = div
                    }
                    +props.occupant.person.fullName()
                }
            }
        }
    }
}