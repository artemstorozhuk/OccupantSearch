import L from 'leaflet';
import { Component, ReactNode } from 'react';
import { MapContainer, Marker, TileLayer } from 'react-leaflet';
import { getImage } from '../../client/Client';
import { getMap } from './MapClient';

import MarkerClusterGroup from './MarkerClusterGroup';

const icon = L.icon({
    iconUrl: getImage('enemy.png'),
    iconSize: [30, 30]
});

export interface MapComponentState {
    occupantToLocations: Map<String, Array<Array<Number>>>
}

export default class OccupantMap extends Component<{}, MapComponentState> {
    state = {
        occupantToLocations: new Map()
    }

    componentDidMount(): void {
        getMap(0, 0, 0, result => {
            this.setState({ occupantToLocations: result })
        })
    }

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
            <MarkerClusterGroup>
                {
                    Object.values(this.state.occupantToLocations).map((arr, index) =>
                        <Marker
                            key={index}
                            icon={icon}
                            position={arr[0]} />
                    )
                }
            </MarkerClusterGroup>
        </MapContainer>
    }
}
