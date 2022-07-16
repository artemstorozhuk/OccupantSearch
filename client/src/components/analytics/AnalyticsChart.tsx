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
import { Component } from 'react'
import { Line } from 'react-chartjs-2'
import { getAnalytics } from '../../client/Client'
import { formatDate } from '../../extensions/Date'

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
    responsive: true,
    plugins: {
        zoom: {
            pan: {
                enabled: true,
                mode: 'x',
            },
            zoom: {
                wheel: {
                    enabled: true,
                },
                pinch: {
                    enabled: true
                },
                mode: 'x',
            }
        },
    },
    elements: {
        point: {
            borderWidth: 0,
            backgroundColor: 'rgba(0,0,0,0)'
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
        const labels = Object.keys(this.state.data).map(x => formatDate(Number(x) * 1000))
        return (
            <Line
                data={{
                    labels: labels,
                    datasets: [{
                        borderColor: 'red',
                        label: '# of Posts',
                        data: values,
                    }]
                }}
                options={zoomOptions}
            />
        )
    }
}
