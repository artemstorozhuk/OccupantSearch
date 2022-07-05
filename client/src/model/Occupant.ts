import Person from './Person';

export default interface Occupant {
    person: Person,
    postIds: Array<string>,
    faceImageUrls: Array<string>,
}