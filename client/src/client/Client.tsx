import Analytics from '../model/Analytics'
import Group from '../model/Group'
import OccupantsResponse from '../model/OccupantsResponse'
import Post from '../model/Post'
import { ExportFormat } from './ExportFormat'

const apiUrl = process.env.NODE_ENV === 'production' ? `${window.location.origin}/api/v1` : 'http://localhost:8080/api/v1'

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

export function getAnalytics(callback: (result: Analytics) => void) {
    fetch(`${apiUrl}/analytics`)
        .then(result => result.json() as Promise<Analytics>)
        .then(callback)
}

export function getOccupantPosts(name: string, callback: (posts: Array<Post>) => void) {
    fetch(`${apiUrl}/occupant/${name}`)
        .then(result => result.json() as Promise<Array<Post>>)
        .then(callback)
}

export function getGroups(page: number, callback: (groups: Array<Group>) => void) {
    fetch(`${apiUrl}/groups?page=${page}`)
        .then(result => result.json() as Promise<Array<Group>>)
        .then(callback)
}
