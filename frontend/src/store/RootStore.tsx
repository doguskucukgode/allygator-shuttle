import 'react';
import { combineReducers, createStore, Store } from "redux";
import { getVehicles } from '../components/api/VehicleApi';
import AppState from '../components/model/AppState';
import Vehicle from '../components/model/Vehicle';
import { vehicleReducer } from './reducers/VehicleReducer';

const rootReducer = combineReducers<AppState>({
    vehicles: vehicleReducer
});

const userInitialValue = (async () => {
    return await getVehicles().catch(e => {console.error(e); return null}) as Vehicle[];
  })()

export default function configureStore(): Store<AppState> {
    const store = createStore(rootReducer, undefined);
    return store;
}
