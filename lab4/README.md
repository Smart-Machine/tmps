# Topic: *Behavioral Design Patterns*
## Author: *Radu Calin*
------
## Objectives:
&ensp; &ensp; __1. Study and understand the Behavioral Design Patterns.__

&ensp; &ensp; __2. As a continuation of the previous laboratory work, think about what communication between software entities might be involed in your system.__

&ensp; &ensp; __3. Create a new Project or add some additional functionalities using behavioral design patterns.__

## Theoretical background:
&ensp; &ensp; Behavioral design patterns are a category of design patterns that focus on the interaction and communication between objects and classes. They provide a way to organize the behavior of objects in a way that is both flexible and reusable, while separating the responsibilities of objects from the specific implementation details. Behavioral design patterns address common problems encountered in object behavior, such as how to define interactions between objects, how to control the flow of messages between objects, or how to define algorithms and policies that can be reused across different objects and classes.

&ensp; &ensp; Some examples from this category of design patterns are :

   * Chain of Responsibility
   * Command
   * Interpreter
   * Iterator
   * Mediator
   * Observer
   * Strategy

## Implementation:
A weather app was a perfect example for the implementation of all these three Behavioral design patterns.

**Observer** is a behavioral design pattern that allows some objects to notify other objects about changes in their state.

Based on the `Observer` interface the following class represents the concrete implementation:
```python
class WeatherDisplay(Observer):
    """
    Concrete Observers react to the updates issued by the Subject they had been
    attached to.
    """

    def update(self, temperature, humidity, wind):
        print(f"Current Conditions:\ntemperature={temperature}°C\nhumidity={humidity}%\nwind={wind}km/h")
```

And it is being notified or triggered by the following `Subject`:
```python
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
```

**Command** is a behavioral design pattern that turns a request into a stand-alone object that contains all information about the request. 

The Command pattern was used to push data to the `WeatherStation`, which is the `Subject`, such that the process of pushing that data is decoupled and compressed into a single call.
```python
class ChangeWeatherCommand(Command):
    def __init__(self, weather_station, temperature, humidity, wind):
        self.weather_station = weather_station
        self.temperature = temperature
        self.humidity = humidity
        self.wind = wind

    def execute(self):
        self.weather_station.set_weather(
            self.temperature, self.humidity, self.wind)
```

**Chain of Responsibility** is behavioral design pattern that allows passing request along the chain of potential handlers until one of them handles request.

As the app operates on large amounts of data, it created a great chance for the use of the Chain of Responsibility, which greatly fit into this use-case.  
The following class ingested the data the API gathered, thus tranforming it to into the desired form:
```python
class WeatherDataIngestor:
    def __init__(self):
        self.handler = WrapperHandler()
        self.filter_data_handler = FilterDataHandler()
        self.clean_data_handler = CleanDataHandler()
        self.average_data_handler = AverageDataHandler()

        (self.handler.set_next(self.filter_data_handler)
                     .set_next(self.average_data_handler)
                     .set_next(self.clean_data_handler))

    def ingest(self, data):
        processed_data = {}
        for key, value in data.copy().items():
            processed_data = {
                **self.handler.handle((key, value)),
                **processed_data,
            }
        return processed_data 
```

The class itself used the following handlers to achieve its purpose:
```python
class WrapperHandler(AbstractHandler):
    def handle(self, request):
        return super().handle(request)
```
The `WrapperHandler` provides a way to beatifully group the handlers, which improves readability.

```python
class FilterDataHandler(AbstractHandler):
    def __init__(self):
        self.fields = ["time"]

    def handle(self, request):
        if request and request[0] in self.fields:
            key, value = request
            for field in self.fields:
                if field == key:
                    return {}
            return {key:value} 
        else:
            return super().handle(request)
```
The `FilterDataHandler` takes out the data that matches the keys that we want filtered out.

```python
class CleanDataHandler(AbstractHandler):
    def handle(self, request):
        if request and isinstance(request[1][0], str):
            key, value = request
            data = {key:[]}
            for elem in value:
                data[key].append(elem.strip())
            return data
        else:
            return super().handle(request)
```
The `CleanDataHandler` is responsible for stripping the values that are of type string.

```python
class AverageDataHandler(AbstractHandler):
    def handle(self, request):
        if request and not isinstance(request[1][0], str):
            key, value = request
            return {key: sum(value) / len(value)} 
        else:
            return super().handle(request)
```
The `AverageDataHandler` is reducing a list of values to a single averaged value.

## Testing:
```
python -m venv venv
source venv/bin/activate
python -m pip install -r requirements.txt
python weather
```

## Conclusion:
Structural design patterns are an essential aspect of software design that focus on the composition and organization of classes and objects to build flexible 
and maintainable systems. They provide solutions to common structural problems and challenges in software development. 

However, it's crucial to note that not all structural patterns are suitable for every situation. Developers should carefully evaluate the specific needs of their 
project before choosing and applying a structural pattern. Overuse or misuse of patterns can lead to overly complex code and unnecessary abstraction.

In conclusion, structural design patterns are a valuable resource for software developers, helping them create well-structured, maintainable, and flexible software systems.
By understanding and applying these patterns appropriately, developers can build software that is easier to develop, understand, and maintain, ultimately leading to more robust
and efficient applications.




