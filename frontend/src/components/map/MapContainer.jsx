import React from 'react';
import GoogleMapReact from 'google-map-react';
import supercluster from 'points-cluster';
import Marker from './Marker';
import DrawMap from './DrawMap';
import ClusterMarker from './ClusterMarker';
import { getVehicles } from '../api/VehicleApi';

export class MapContainer extends React.PureComponent {
    state = {
        options: {
            center: {
                lat: 52.53, lng: 13.403
            },
            zoom: 13,
            bounds: {}
        },
        clusters: [],
        markers: [],
        radius: 3500
    };

    getClusters = () => {
        const clusters = supercluster(this.state.markers, {
            minZoom: 0,
            maxZoom: 16,
            radius: 60,
        });
        return clusters(this.state.options);
    };

    componentDidMount() {
        const interval = setInterval(() => {
            getVehicles()
                .then((data) => {
                    if (data) {
                        this.setState({ markers: data },
                            () => {
                                this.createClusters(this.props);
                            })
                    }
                })
                .catch(({ e }) => {
                    console.error("Connection error to API");
                })
        }, 1500);
        return () => clearInterval(interval);
    }

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
                    center={this.state.options.center}
                    zoom={this.state.options.zoom}
                    onChange={this.mapChange}
                    yesIWantToUseGoogleMapApiInternals
                    onGoogleApiLoaded={({ map, maps }) => DrawMap(this.props.config, map, maps)}
                >
                    {this.state.clusters.map(item => {
                        if (item.numPoints === 1) {
                            return (
                                <Marker
                                    key={item.id}
                                    lat={item.points[0].lat}
                                    lng={item.points[0].lng}
                                    direction={item.points[0].direction}
                                />
                            );
                        }
                        return (
                            <ClusterMarker
                                key={item.id}
                                text={item.id}
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
