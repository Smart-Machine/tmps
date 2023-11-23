from .weather_command import ChangeWeatherCommand

class WeatherApp:
    def __init__(self, weather_display, weather_station):
        self.weather_display = weather_display
        self.weather_station = weather_station
        self.weather_station.attach(self.weather_display)

    def change_weather(self, temperature, humidity, wind):
        change_weather_command = ChangeWeatherCommand(
            self.weather_station, temperature, humidity, wind)
        change_weather_command.execute()
