# allygator-shuttle

- This project divided into two parts frontend and backend. 
- Backend is written in SpringBoot and Kotlin with gradle build
- Frontend is written in React and GoogleMapReact library
- Data is stored in Mongo as long as Docker volume

![alt text](./static/ss_berlin.png "Sample Output")

## Running project with docker
Project can be run with docker-compose basicly type `docker-compose up -d` in root foolder. Backend can be accesible at http://localhost:8080 , mongo express at http://localhost:8081 and frontend at http://localhost:3000

## Circle CI
Project is configured Circle CI and pipelines are observable form [this link](https://app.circleci.com/pipelines/github/doguskucukgode/allygator-shuttle?branch=master)
 
## Rest api calls
- ### Vehicle registration
  `POST http://localhost:8080/vehicles`

  ```
  REQUEST BODY:
  { "id": "vehicle1" }
  ```
  ```
  RESPONSE: HTTP-201
  ```

- ### Location update
  `POST http://localhost:8080/vehicles/vecicle1/locations`
  ```
  REQUEST BODY:
  { "lat": 10.0, "lng": 20.0, "at": "2017-09-01T12:00:00Z" }
  ```
  
  ```
  RESPONSE: HTTP-201
  ```
 
- ### Vehicle de-registration
  `DELETE http://localhost:8080/vehicles/vecicle1`
  ```
  NO REQUEST BODY
  ```
   
  ```
  RESPONSE: HTTP-204
  ```
 
- ### Vehicle list
  `GET http://localhost:8080/vehicles/list`
  ```
  NO REQUEST BODY
  ```
  
  ```
  RESPONSE: HTTP-200
  [
    {
        "id": "vehicle1",
        "lat": 11,
        "lng": 20,
        "at": "2019-09-01T12:00:00.000+00:00",
        "direction": 250
    },
    {
        "id": "vehicle2",
        "lat": null,
        "lng": null,
        "at": null,
        "direction": null
    },
  ]
  ```
  
 - ### Vehicle list with locations
   `GET http://localhost:8080/vehicles/locations`
   ```
   NO REQUEST BODY
   ```

   ```
   RESPONSE: HTTP-200
   [
     {
         "id": "vehicle1",
         "lat": 11,
         "lng": 20,
         "at": "2019-09-01T12:00:00.000+00:00",
         "direction": 250
     }
   ]
   ```

 
 
