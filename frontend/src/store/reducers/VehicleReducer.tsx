import Vehicle from "../../components/model/Vehicle";
import { VehicleActions } from "../actions/VehicleActions";

export function vehicleReducer(state: Vehicle[] = [], action: VehicleActions) {
    switch (action.type) {
        case "SetVehicles":
            return action.payload;
        case "FetchVehicles":
            return [];
        default:
            neverReached(action);
    }
    return state;
}

function neverReached(never: never) { }