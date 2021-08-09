# co2-backend

## Start local server
```bash
mvn spring-boot:run
```

<http://localhost:8080/api/v1>

Connect will display, when a SenML message is sent to the SenML end point:
```json
{
  "timeStamp": 1619962716000,
  "name": "Kamer-1-23",
  "condition": {
    "co2": 645
  }
}
```
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

If `bt` (base time) is not passed, the current time is assumed. 
