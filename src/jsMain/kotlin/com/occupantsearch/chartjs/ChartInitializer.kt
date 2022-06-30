package com.occupantsearch.chartjs

fun init() {
    Chart.register(ArcElement)
    Chart.register(LineElement)
    Chart.register(BarElement)
    Chart.register(PointElement)
    Chart.register(BarController)
    Chart.register(BubbleController)
    Chart.register(DoughnutController)
    Chart.register(LineController)
    Chart.register(PieController)
    Chart.register(PolarAreaController)
    Chart.register(RadarController)
    Chart.register(ScatterController)
    Chart.register(CategoryScale)
    Chart.register(LinearScale)
    Chart.register(LogarithmicScale)
    Chart.register(RadialLinearScale)
    Chart.register(TimeScale)
    Chart.register(TimeSeriesScale)
    Chart.register(Decimation)
    Chart.register(Filler)
    Chart.register(Legend)
    Chart.register(Title)
    Chart.register(Tooltip)
    Chart.register(SubTitle)
}
