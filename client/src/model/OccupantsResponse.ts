import Occupant from './Occupant';

export default interface OccupantsResponse {
    occupants: Array<Occupant>,
    foundCount: number,
}