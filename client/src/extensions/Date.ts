import moment from "moment";

export function formatDate(date: number, format: string = 'DD-MM-YYYY'): string {
    return moment(new Date(Number(date))).format(format)
}