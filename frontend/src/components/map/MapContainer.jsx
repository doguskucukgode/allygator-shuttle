import React from 'react';
import GoogleMapReact from 'google-map-react';
import supercluster from 'points-cluster';
import Marker from './Marker';
import DrawMap from './DrawMap';
import ClusterMarker from './ClusterMarker';
import axios from "axios";
import DefaultConstants from '../../config/Constants';
import { runInThisContext } from 'vm';

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
            axios
                .get(DefaultConstants.API_BASE_URL + DefaultConstants.API_VEHICLE_LOCATIONS)
                .then(({ data }) => {
                    this.setState({ markers: data },
                        () => {
                            this.createClusters(this.props);
                        })
                });

        }, 1000);
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
                    defaultCenter={this.state.options.center}
                    defaultZoom={this.state.options.zoom}
                    onChange={this.mapChange}
                    options={MAP.options}
                    yesIWantToUseGoogleMapApiInternals
                    onGoogleApiLoaded={({ map, maps }) => DrawMap(map, maps)}
                >
                    {this.state.clusters.map(item => {
                        if (item.numPoints === 1) {
                            return (
                                <Marker
                                    key={item.id}
                                    lat={item.points[0].lat}
                                    lng={item.points[0].lng}
                                    text={item.points[0].direction}
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
