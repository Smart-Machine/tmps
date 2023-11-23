from abc import ABC, abstractmethod

from .weather_display import Observer


class Subject(ABC):

    """
    The Subject interface declares a set of methods for managing subscribers.
    """

    @abstractmethod
    def attach(self, observer: Observer) -> None:
        """
        Attach an observer to the subject.
        """
        pass

    @abstractmethod
    def detach(self, observer: Observer) -> None:
        """
        Detach an observer from the subject.
        """
        pass

    @abstractmethod
    def notify(self, *args) -> None:
        """
        Notify all observers about an event.
        """
        pass


class WeatherStation(Subject):
    """
    Concrete Subject notifies the attached Observers, after an event.
    """

    def __init__(self):
        self.temperature = None
        self.humidity = None
        self.wind = None
        self.observers = []

    def attach(self, observer):
        self.observers.append(observer)

    def detach(self, observer):
        self.observers.remove(observer)

    def notify(self):
        for observer in self.observers:
            observer.update(self.temperature, self.humidity, self.wind)

    def set_weather(self, temperature, humidity, wind):
        self.temperature = temperature
        self.humidity = humidity
        self.wind = wind
        self.notify()
