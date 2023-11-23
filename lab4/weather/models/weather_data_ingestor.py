from abc import ABC, abstractmethod


class Handler(ABC):
    """
    The Handler interface declares a method for building the chain of handlers.
    It also declares a method for executing a request.
    """

    @abstractmethod
    def set_next(self, handler):
        pass

    @abstractmethod
    def handle(self, request):
        pass


class AbstractHandler(Handler):
    """
    The default chaining behavior can be implemented inside a base handler
    class.
    """

    _next_handler = None

    def set_next(self, handler):
        self._next_handler = handler
        return handler

    @abstractmethod
    def handle(self, request):
        if self._next_handler:
            return self._next_handler.handle(request)

        return None


"""
All Concrete Handlers either handle a request or pass it to the next handler in
the chain.
"""


class WrapperHandler(AbstractHandler):
    def handle(self, request):
        return super().handle(request)


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


class AverageDataHandler(AbstractHandler):
    def handle(self, request):
        if request and not isinstance(request[1][0], str):
            key, value = request
            return {key: sum(value) / len(value)} 
        else:
            return super().handle(request)


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
