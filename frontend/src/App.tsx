import React from 'react';
import { Provider } from "react-redux";
import './App.css';
import MapContainer from './components/map/MapContainer';
import configureStore from './store/RootStore';

const store = configureStore();

function App() {
  return (
    <div className="App">
      <Provider store={store}>
        <MapContainer />
      </Provider>
    </div>
  );
}

export default App;
