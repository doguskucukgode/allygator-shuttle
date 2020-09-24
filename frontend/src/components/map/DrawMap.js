
export default function DrawMap(map, maps) {
    var center = {
        lat: 52.53, lng: 13.403
    };
    var radius = 3500;

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