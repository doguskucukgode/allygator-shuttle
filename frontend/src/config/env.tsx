export type Config = {
    centerLat : number,
    centerLng : number,
    centerRadius : number,
    centerRadiusCheck: boolean
}

export let AppConfig : Config

export async function configureConfig() {
    const response = await fetch("./config.json")
    AppConfig = await response.json() as Config
    return AppConfig
}