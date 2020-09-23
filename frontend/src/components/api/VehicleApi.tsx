import { AxiosService } from "../../common/AxiosService";
import DefaultConstants from '../../config/Constants';

export const getVehicles: () => Promise<any> = () =>
new AxiosService<any>(DefaultConstants.API_BASE_URL, DefaultConstants.API_VEHICLE_LOCATIONS).get();