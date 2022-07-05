import {
    ArcElement,
    BarController,
    BarElement,
    BubbleController,
    CategoryScale,
    Chart as ChartJS, ChartOptions, Decimation,
    DoughnutController,
    Filler,
    Legend,
    LinearScale,
    LineController,
    LineElement,
    LogarithmicScale,
    PieController, PointElement,
    PolarAreaController,
    RadarController,
    RadialLinearScale,
    ScatterController,
    SubTitle,
    TimeScale,
    TimeSeriesScale,
    Title,
    Tooltip
} from 'chart.js'
import zoomPlugin from 'chartjs-plugin-zoom'
import moment from 'moment'
import { Component } from 'react'
import { Bar } from 'react-chartjs-2'
import { getAnalytics } from '../client/Client'

ChartJS.register(
    ArcElement,
    LineElement,
    LineElement,
    BarElement,
    PointElement,
    BarController,
    BubbleController,
    DoughnutController,
    LineController,
    PieController,
    PolarAreaController,
    RadarController,
    ScatterController,
    CategoryScale,
    LinearScale,
    LogarithmicScale,
    RadialLinearScale,
    TimeScale,
    TimeSeriesScale,
    Decimation,
    Filler,
    Legend,
    Title,
    Tooltip,
    SubTitle,
    zoomPlugin,
)

const zoomOptions: ChartOptions = {
    plugins: {
        zoom: {
            zoom: {
                wheel: {
                    enabled: true,
                },
                pinch: {
                    enabled: true
                },
                mode: 'xy',
            }
        }
    }
}

export interface AnalyticsChartProps {
    data: Map<number, number>
}

export default class AnalyticsChart extends Component {
    state = {
        data: new Map<number, number>()
    }

    componentDidMount() {
        getAnalytics(result => this.setState({ data: result }))
    }

    render() {
        const values = Object.values(this.state.data)
        const max = Math.max(...values)
        const labels = Object.keys(this.state.data).map(this.toDateString)
        const bgColors = values.map(x => this.toBgColor(x, max))
        const colors = values.map(x => this.toColor(x, max))
        new Date(0)
        return (
            <Bar
                data={{
                    labels: labels,
                    datasets: [{
                        backgroundColor: bgColors,
                        borderColor: colors,
                        label: '# of Posts',
                        data: values,
                        borderWidth: 1
                    }]
                }}
                options={zoomOptions}
            />
        )
    }

    toColor(value: number, max: number) {
        return `hsl(${this.percent(value, max)}, 100%, 50%)`
    }

    toBgColor(value: number, max: number) {
        return `hsl(${this.percent(value, max)}, 100%, 50%, 0.2)`
    }

    percent(value: number, max: number) {
        return 100 - value * 100 / max
    }

    toDateString(x: string) {
        return moment(new Date(Number(x))).format('DD-MM-YYYY')
    }
}
