import React, { useEffect } from 'react';
import GoogleMapReact from 'google-map-react';
import supercluster from 'points-cluster';
import Marker from './Marker';
import ClusterMarker from './ClusterMarker';
import DefaultConstants from '../../config/Constants';
import { setVehiclesState } from '../../store/actions/VehicleActions';
import { useDispatch, useSelector } from "react-redux";
import axios from "axios";

const MAP = {
    defaultZoom: 8,
    options: {
        maxZoom: 19
    },
};

var googleMap,googleMaps;
//const dispatch = useDispatch();

//export class MapContainer extends React.PureComponent {
export default function MapContainer(props) {

    const [options, setOptions] = React.useState({
        center: {
            lat: 52.53, lng: 13.403
        },
        zoom: 11
    });

    const vehicleData = (useSelector((state) => state.vehicles));

    const dispatch = useDispatch();

    const [clusters, setClusters] = React.useState([]);

    function markersData() {
        return [{ id: '1', lat: 52.63, lng: 13.503 },
        { id: '2', lat: 52.68, lng: 13.533 },
        { id: '3', lat: 52.78, lng: 13.539 },
        { id: '4', lat: 52.54, lng: 13.542 },
        { id: '5', lat: 52.75, lng: 13.54 }
        ]
    }

    const getClusters = () => {
        const clusters = supercluster(vehicleData, {
            minZoom: 0,
            maxZoom: 16,
            radius: 60,
        });
        return clusters(options);
    };

    const mapChange = ({ center, zoom, bounds }) => {
        const newOptions = {
            center,
            zoom,
            bounds
        };
        setOptions(newOptions);
    };

    useEffect(() => { 
        createClusters(props);
    }, [options])

    const createClusters = (props) => {
        setClusters(options.bounds
            ? getClusters(props).map(({ wx, wy, numPoints, points }) => ({
                lat: wy,
                lng: wx,
                numPoints,
                id: `${numPoints}_${points[0].id}`,
                points,
            }))
            : []);
    };

    useEffect(() => {
        const interval = setInterval(() => {
            axios
            .get(DefaultConstants.API_BASE_URL + DefaultConstants.API_VEHICLE_LOCATIONS)
            .then(({ data }) => {
                dispatch(setVehiclesState(data));
            });
        }, 3000);
        return () => clearInterval(interval);
    }, [dispatch]);


    const handleApiLoaded = (map, maps) => {
        // use map and maps objects
        googleMap = map;
        googleMaps = maps;
    };
    
    return (
        <div style={{ height: '100vh', width: '100%' }}>
            <GoogleMapReact
                center={options.center}
                zoom={options.zoom}
                onChange={mapChange}
                options={MAP.options}
                yesIWantToUseGoogleMapApiInternals
                onGoogleApiLoaded={({ map, maps }) => handleApiLoaded(map, maps)}
                bootstrapURLKeys={{ key: 'AIzaSyBpi7tqELqp-rvD0SI1lmrlEe0whsAA7vc' }}
            >
                {clusters.map(item => {
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
