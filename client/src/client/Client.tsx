
const isProduction = process.env.NODE_ENV === 'production'

export const apiUrl = isProduction ? `${window.location.origin}/api/v1` : 'http://localhost:8080/api/v1'

export function getImage(image: string) {
    return isProduction ? `app/${image}` : image
}
