import { apiUrl } from '../../client/Client';

export function getMap(latitude: number, longitude: number, zoom: number, callback: (map: Map<String, Array<Array<Number>>>) => void) {
    fetch(`${apiUrl}/map?latitude=${latitude}&longitude=${longitude}&zoom=${zoom}`)
        .then(result => result.json() as Promise<Map<String, Array<Array<Number>>>>)
        .then(callback)
}
