import React from 'react';
import logo from './logo.svg';
import './App.css';
import SimpleMap from './map/SimpleMap';
import GoogleMapContainer from './map/GoogleMapContainer';
import MapContainer from './map/MapContainer';

function App() {
  return (
    <div className="App">
      <MapContainer/>
    </div>
  );
}

export default App;
