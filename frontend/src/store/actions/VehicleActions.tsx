import Vehicle from "../../components/model/Vehicle";

export type VehicleActions = 
| ReturnType<typeof setVehiclesState>
| ReturnType<typeof fetchVehicles>;

export function setVehiclesState(vehicles: Vehicle[]) {
  return {
    type: "SetVehicles",
    payload: vehicles
  } as const;
}

export function fetchVehicles() {
  return {
    type: "FetchVehicles"
  } as const;
}