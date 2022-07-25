import { apiUrl } from '../../client/Client';
import { ExportFormat } from './ExportFormat';

export function download(format: ExportFormat) {
    window.open(`${apiUrl}/export?format=${format}`)
}