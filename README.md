# Evaluation API Test

The goal of this excercise is to create and implement some test scenarios based on an existing API

## User Story

>     As an API User
>     I want to be able to retrieve current data for weather conditions at a given location

### Acceptance Criteria

User should be able to search using the following options:

* Latitude/Longitude (e.g 48.8567,2.3508)
* Postcode/Zip Code (e.g EC2Y 5AS)
* City name (e.g City)

## Weather API

The Weather API is hosted by Rapid API with [documentation available on their site](https://rapidapi.com/weatherapi/api/weatherapi-com/)

### Sample Request

```
GET /current.json?q=<QUERY VALUE> HTTP/1.1
X-Rapidapi-Key: <API KEY>
X-Rapidapi-Host: weatherapi-com.p.rapidapi.com
Host: weatherapi-com.p.rapidapi.com
```

### Sample Response (HTTP 200)

```json
{
  "location": {
    "name": "London",
    "region": "London",
    "country": "UK",
    "lat": 51.52,
    "lon": -0.1,
    "tz_id": "Europe/London",
    "localtime_epoch": 1702342165,
    "localtime": "2023-12-12 0:49"
  },
  "current": {
    "last_updated_epoch": 1702341900,
    "last_updated": "2023-12-12 00:45",
    "temp_c": 6,
    "temp_f": 42.8,
    "is_day": 0,
    "condition": {
      "text": "Mist",
      "icon": "//cdn.weatherapi.com/weather/64x64/night/143.png",
      "code": 1030
    },
    "wind_mph": 2.5,
    "wind_kph": 4,
    "wind_degree": 140,
    "wind_dir": "SE",
    "pressure_mb": 1005,
    "pressure_in": 29.68,
    "precip_mm": 0.36,
    "precip_in": 0.01,
    "humidity": 93,
    "cloud": 75,
    "feelslike_c": 4.5,
    "feelslike_f": 40.1,
    "vis_km": 8,
    "vis_miles": 4,
    "uv": 1,
    "gust_mph": 8.1,
    "gust_kph": 13
  }
}
```

### Sample Failure Response (HTTP 400)

```json
{
  "error": {
    "code": 1006,
    "message": "No matching location found."
  }
}
```