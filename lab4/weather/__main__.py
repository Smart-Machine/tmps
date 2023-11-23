from api.api import GatewayAPI
from models.weather_app import WeatherApp
from models.weather_display import WeatherDisplay
from models.weather_station import WeatherStation
from models.weather_data_ingestor import WeatherDataIngestor


app = WeatherApp(WeatherDisplay(), WeatherStation())

data = GatewayAPI.fetch_data()

data_ingestor = WeatherDataIngestor()
clean_data = data_ingestor.ingest(data.get("hourly", {}))

app.change_weather(clean_data.get("temperature_2m"), clean_data.get(
    "relative_humidity_2m"), clean_data.get("wind_speed_10m"))
