from abc import ABC, abstractmethod


class Command(ABC):

    @abstractmethod
    def execute(self):
        pass


class ChangeWeatherCommand(Command):
    def __init__(self, weather_station, temperature, humidity, wind):
        self.weather_station = weather_station
        self.temperature = temperature
        self.humidity = humidity
        self.wind = wind

    def execute(self):
        self.weather_station.set_weather(
            self.temperature, self.humidity, self.wind)
