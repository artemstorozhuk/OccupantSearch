import { apiUrl } from '../../client/Client';
import Group from './Group';

export function getGroups(page: number, callback: (groups: Array<Group>) => void) {
    fetch(`${apiUrl}/groups?page=${page}`)
        .then(result => result.json() as Promise<Array<Group>>)
        .then(callback)
}