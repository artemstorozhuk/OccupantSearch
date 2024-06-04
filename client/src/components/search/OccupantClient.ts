import { apiUrl } from '../../client/Client';
import OccupantsResponse from './OccupantsResponse';

export function getOccupants(query: string, page: number, callback: (result: OccupantsResponse) => void) {
    fetch(`${apiUrl}/occupants?` + new URLSearchParams({
        'query': query,
        'page': page.toString(),
    }))
        .then(result => result.json() as Promise<OccupantsResponse>)
        .then(callback)
}