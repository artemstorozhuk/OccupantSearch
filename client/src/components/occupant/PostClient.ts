import { apiUrl } from '../../client/Client';
import Post from './Post';

export function getOccupantPosts(name: string, callback: (posts: Array<Post>) => void) {
    fetch(`${apiUrl}/occupant/${name}`)
        .then(result => result.json() as Promise<Array<Post>>)
        .then(callback)
}
