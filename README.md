# co2-backend

## Start local server
```bash
mvn spring-boot:run
```
or to start the docker
```json
docker run -d -p 8080:8080 --name co2-backend rimvanvliet/co2-backend
```

## Access the API

The API is exposed as swagger-ui on <http://localhost:8080/api/v1/swagger-ui.html>

Example SenML message that can be POSTed
```json
 [{
  "bn": "00:11:22:33:44:55",
  "bt": 1.619962716E9
}, {
  "n": "CO2Concentration",
  "u": "ppm",
  "v": 665.0
}
]
```
Example room that can be POSTed
```json
 {
  "name": "Kamer-3.21",
  "sensors": [
    "55:44:33:22:11:00"
  ]
}
```

If `bt` (base time) is not passed, the current time is assumed. 

## Show the results
Connect with <http://localhost:8080/api/v1>, when a SenML message is sent to the SenML end point, the message is shown:
```json
{
  "timeStamp": 1619962716000,
  "name": "Kamer-1-23",
  "condition": {
    "co2": 645
  }
}
```
