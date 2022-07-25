import { apiUrl } from '../../client/Client';
import Analytics from './Analytics';

export function getAnalytics(callback: (result: Analytics) => void) {
    fetch(`${apiUrl}/analytics`)
        .then(result => result.json() as Promise<Analytics>)
        .then(callback)
}
