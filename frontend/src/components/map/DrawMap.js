
export default function DrawMap(config, map, maps) {
    var center = {
        lat: config.centerLat, lng: config.centerLng
    };
    var radius = config.centerRadius;

    new maps.Circle({
        strokeColor: '#D3D3D3',
        strokeOpacity: 0.8,
        strokeWeight: 2,
        fillColor: '#D3D3D3 ',
        fillOpacity: 0.3,
        map,
        radius: radius,
        center: center
    });

    new maps.Marker({
        position: center,
        label: 'D',
        map: map,
        title: "Door2Door"
      });
}