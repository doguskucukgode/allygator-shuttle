# allygator-shuttle

- This project divided into two parts frontend and backend. 
- Backend is written in SpringBoot and Kotlin with gradle build
- Frontend is written in React 
- Data is stored in Mongo as long as Docker volume


## Running project with docker
 `docker-compose up -d`
 
## Rest Api Calls
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
        "lat": 10,
        "lng": 20
    }
  ]
  ```

 
 
