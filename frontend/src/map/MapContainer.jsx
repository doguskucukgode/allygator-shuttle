import React from 'react';
import GoogleMapReact from 'google-map-react';
import supercluster from 'points-cluster';
import Marker from './Marker';
import ClusterMarker from './ClusterMarker';

const MAP = {
    defaultZoom: 8,
    options: {
        maxZoom: 19
    },
};


export class MapContainer extends React.PureComponent {

    state = {
        options: {
            center: {
                lat: 52.53, lng: 13.403
            },
            zoom: 11,
            bounds: {}
        },
        clusters: []
    };

    markersData() {
        return [{ id: '1', lat: 52.63, lng: 13.503 },
        { id: '2', lat: 52.68, lng: 13.533 },
        { id: '3', lat: 52.78, lng: 13.539 },
        { id: '4', lat: 52.54, lng: 13.542 },
        { id: '5', lat: 52.75, lng: 13.54 }
        ]
    }

    getClusters = () => {
        const clusters = supercluster(this.markersData(), {
            minZoom: 0,
            maxZoom: 16,
            radius: 60,
        });
        return clusters(this.state.options);
    };


    mapChange = ({ center, zoom, bounds }) => {
        this.setState(
            {
                options: {
                    center,
                    zoom,
                    bounds,
                }
            },
            () => {
                this.createClusters(this.props);
            }
        );
    };

    createClusters = (props) => {
        this.setState({
            clusters: this.state.options.bounds
                ? this.getClusters(props).map(({ wx, wy, numPoints, points }) => ({
                    lat: wy,
                    lng: wx,
                    numPoints,
                    id: `${numPoints}_${points[0].id}`,
                    points,
                }))
                : [],
        });
    };


    render() {
        return (
            <div style={{ height: '100vh', width: '100%' }}>
                <GoogleMapReact
                    defaultCenter={this.state.options.center}
                    defaultZoom={this.state.options.zoom}
                    onChange={this.mapChange}
                    options={MAP.options}
                    yesIWantToUseGoogleMapApiInternals
                    bootstrapURLKeys={{ key: 'No-API-KEY' }}
                >
                    {this.state.clusters.map(item => {
                        if (item.numPoints === 1) {
                            return (
                                <Marker
                                    key={item.id}
                                    lat={item.points[0].lat}
                                    lng={item.points[0].lng}
                                />
                            );
                        }

                        return (
                            <ClusterMarker
                                key={item.id}
                                lat={item.lat}
                                lng={item.lng}
                                points={item.points}
                            />
                        );
                    })}
                </GoogleMapReact>
            </div>
        );
    }
}

export default MapContainer;
