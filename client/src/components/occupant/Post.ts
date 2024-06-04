export default interface Post {
    postId: string,
    text: string,
    imageUrls: Array<string>,
    views: number | null,
    date: number | null,
}