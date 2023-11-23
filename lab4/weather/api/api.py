import json
import requests
from geopy.geocoders import Nominatim


class WeatherAPI:

    def _format_url(self, params):
        query_url = self.url[:]
        for k, v in params.items():
            query_url += f"{k}={v}&"
        return query_url

    def __init__(self, params):
        self.url = "https://api.open-meteo.com/v1/forecast?"

        self.params = params
        self.query_url = self._format_url(params)

    def fetch_data(self):
        response = requests.get(self.query_url)
        return response


class GeolocationAPI:

    geocode = "Chisinau"
    geolocator = Nominatim(user_agent='weather-app')

    location = geolocator.geocode(geocode)
    lat = location.raw.get("lat")
    lon = location.raw.get("lon")


class GatewayAPI:

    weatherAPI_instance = WeatherAPI({
        "latitude": GeolocationAPI.lat,
        "longitude": GeolocationAPI.lon,
        "hourly": "temperature_2m,relative_humidity_2m,wind_speed_10m",
    })

    @staticmethod
    def fetch_data():
        data = GatewayAPI.weatherAPI_instance.fetch_data()
        return json.loads(data.text)
