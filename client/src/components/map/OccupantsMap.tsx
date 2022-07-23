import { Component, ReactNode } from 'react';
import { MapContainer, TileLayer } from 'react-leaflet';


export default class OccupantMap extends Component<{}, {}> {
    render(): ReactNode {
        return <MapContainer
            style={{
                height: 'calc(100vh - 60px)'
            }}
            center={[61, 105]}
            zoom={4}
            scrollWheelZoom={true}>
            <TileLayer
                attribution={'&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'}
                url='https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png' />
        </MapContainer>
    }
}