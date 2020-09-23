import axios, { AxiosError, AxiosRequestConfig, AxiosResponse, AxiosInstance, CancelTokenStatic, CancelTokenSource } from "axios";

const config: AxiosRequestConfig = {
    maxContentLength: 5000,
    maxRedirects: 5,
    responseType: "json",
    timeout: 10000,
    validateStatus: (status: number) => status >= 200 && status < 300,
    withCredentials: false,
};

export class AxiosService<T> {
    public endPoint: string = "";
    public instance: AxiosInstance;
    public baseUrl: string = "";
    public source: CancelTokenSource;     


    constructor(baseUrl: string, endPoint: string, customConfig: AxiosRequestConfig = {}) {
        this.instance = axios.create();
        const cancelToken: CancelTokenStatic = axios.CancelToken;
        this.source = cancelToken.source();
        
        this.instance.interceptors.request.use(async (axiosConfig: AxiosRequestConfig) => {
            axiosConfig.baseURL = baseUrl;

            /*
            const accessToken = getLocalStorageItem(LocalStorageConstants.AUTH_TOKEN) || "";
            if (accessToken) {
                axiosConfig.headers.Authorization = `Bearer ${accessToken}`;
            }
            */

            return Object.assign(axiosConfig, customConfig);
        });

        this.endPoint = endPoint;
        this.baseUrl = baseUrl;
    }
    
    public async post<K>(data: K, customConfig: AxiosRequestConfig = {}): Promise<any> {
        const _c: AxiosRequestConfig = Object.assign(customConfig, config);
        const result: any = await this.instance.post<T>(`${this.endPoint}`, data, _c)
            .then(this.handleResponse)
            .catch(this.handleError);
        return result;
    }

    public async get<K>(parameters?: K, customConfig: AxiosRequestConfig = {}): Promise<any> {
        const _c: AxiosRequestConfig = Object.assign(customConfig, config);
        const result: any = await this.instance.get<T>(`${this.endPoint}${this.getUrlParam(parameters)}`, _c)
            .then(this.handleResponse)
            .catch(this.handleError);
        return result;
    }

    public async delete<K>(parameter?: K, customConfig: AxiosRequestConfig = {}): Promise<any> {
        const _c: AxiosRequestConfig = Object.assign(customConfig, config);
        const result: any = await this.instance.delete(`${this.endPoint}${this.getUrlParam(parameter)}`, _c)
            .then(this.handleResponse)
            .catch(this.handleError);
        return result;
    }

    /**
     * handles response data
     */
    private handleResponse(response: AxiosResponse): T | null {
        if (response.data) {
            const result = response.data.result !== undefined ? response.data.result : response.data;
            return result as T;
        } else {
            return null;
        }
    }

    /**
     * handles errors on error
     */
    private handleError = (error: AxiosError): any => {
        const { response } = error;
        if (response?.status === 401) {
            //clearLocalStorage();
            window.location.href = "/login";
        } else if (response?.status === 404) {
            window.location.href = "/not-found";
        } else {
            if (response?.data?.meta) {
                throw new Error(response?.data?.meta?.message);
            } else {
                throw new Error("Unhandled exception occurred!")
            }
        }
    }

    private getUrlParam: (parameters: any) => string = (parameters: any) => {
        let query: string = "";

        if (parameters) {
            if (typeof parameters !== "string" && Object.keys(parameters).length > 0) {
                query = "?";
                Object
                    .keys(parameters)
                    .forEach((key) => {
                        if (parameters[key] !== undefined && parameters[key] !== null) {
                            query += `${key}=${parameters[key]}&`;
                        }
                    });
            } else {
                query = `/${parameters}`;
            }
        }

        return query;
    }

}