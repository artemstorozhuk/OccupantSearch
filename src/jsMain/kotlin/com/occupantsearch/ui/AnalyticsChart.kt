package com.occupantsearch.ui

import com.occupantsearch.chartjs.Chart
import csstype.Visibility
import csstype.px
import kotlinx.browser.document
import kotlinx.coroutines.launch
import org.w3c.dom.HTMLCanvasElement
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML.canvas
import react.useEffectOnce
import kotlin.js.Date

external interface AnalyticsChartProps : Props {
    var visible: Boolean
}

val AnalyticsChart = FC<AnalyticsChartProps> { props ->
    canvas {
        id = "canvas"
        css {
            visibility = if (props.visible) {
                Visibility.visible
            } else {
                Visibility.hidden
            }
            marginTop = 60.px
        }
    }
    if (props.visible) {
        useEffectOnce {
            scope.launch {
                val analytics = client.getAnalytics()
                val max = analytics.values.max()

                val canvas = document.getElementById("canvas") as HTMLCanvasElement
                Chart(canvas, jso {
                    type = "bar"
                    data = jso {
                        labels = analytics.keys
                            .map { it.toDateString() }
                            .toTypedArray()
                        datasets = arrayOf(jso {
                            backgroundColor = analytics.values.map { toBgColor(it, max) }.toTypedArray()
                            borderColor = analytics.values.map { toColor(it, max) }.toTypedArray()
                            label = "# of Posts"
                            data = analytics.values.map { it.toInt() }.toTypedArray()
                            borderWidth = 1
                        })
                    }
                })

                canvas.style.display = "unset"
                canvas.width = analytics.size * 20
            }
        }
    }
}

fun Long.toDateString() = Date(this).toISOString().split('T')[0]

fun percent(value: Long, max: Long) = 100 - value * 100 / max

fun toColor(value: Long, max: Long) = "hsl(${percent(value, max)}, 100%, 50%)"

fun toBgColor(value: Long, max: Long) = "hsl(${percent(value, max)}, 100%, 50%, 0.2)"

inline fun <T : Any> jso(): T = js("({})")

inline fun <T : Any> jso(builder: T.() -> Unit): T = jso<T>().apply(builder)
