from abc import ABC, abstractmethod


class Observer(ABC):
    """
    The Observer interface declares the update method, used by subjects.
    """

    @abstractmethod
    def update(self, temperature, humidity, wind):
        """
        Receive update from subject.
        """
        pass


class WeatherDisplay(Observer):
    """
    Concrete Observers react to the updates issued by the Subject they had been
    attached to.
    """

    def update(self, temperature, humidity, wind):
        print(f"Current Conditions:\ntemperature={temperature}°C\nhumidity={humidity}%\nwind={wind}km/h")
