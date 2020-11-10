import React from 'react';
import './App.css';
import MapContainer from './components/map/MapContainer';
import { Config } from './config/env';

function App(props : {config: Config}) {
  return (
    <div className="App">
      <MapContainer config = {props.config}/>
    </div>
  );
}

export default App;
