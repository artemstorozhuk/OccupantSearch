import Occupant from '../occupant/Occupant';

export default interface OccupantsResponse {
    occupants: Array<Occupant>,
    foundCount: number,
}