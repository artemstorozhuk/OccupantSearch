import OccupantsResponse from '../model/OccupantsResponse'
import { ExportFormat } from './ExportFormat'

const apiUrl = process.env.NODE_ENV === 'production' ? '' : 'http://localhost:8080'

export function download(format: ExportFormat) {
    window.open(`${apiUrl}/export?format=${format}`)
}

export function getOccupants(query: string, page: number, callback: (result: OccupantsResponse) => void) {
    fetch(`${apiUrl}/occupants?` + new URLSearchParams({
        'query': query,
        'page': page.toString(),
    }))
        .then(result => result.json() as Promise<OccupantsResponse>)
        .then(callback)
}

export function getAnalytics(callback: (result: Map<number, number>) => void) {
    fetch(`${apiUrl}/analytics`)
        .then(result => result.json() as Promise<Map<number, number>>)
        .then(callback)
}
