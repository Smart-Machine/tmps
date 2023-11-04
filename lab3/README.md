# Topic: *Structural Design Patterns*
## Author: *Radu Calin*
------
## Objectives:
&ensp; &ensp; __1. Study and understand the Structural Design Patterns.__

&ensp; &ensp; __2. As a continuation of the previous laboratory work, think about the functionalities that 
your system will need to provide to the user.__

&ensp; &ensp; __3. Implement some additional functionalities, or create a new project using structural design patterns.__

## Theory:
&ensp; &ensp; Structural design patterns are a crucial component of software design patterns, a set of recurring solutions 
to common problems in software design. These patterns focus on how classes and objects can be combined to form larger structures 
while keeping the system flexible and efficient. They help in organizing code in a way that promotes reusability, maintainability, 
and scalability.

&ensp; &ensp; Some examples of this kind of design patterns are:

   * Adapter
   * Bridge
   * Composite
   * Decorator
   * Facade
   * Flyweight
   * Proxy

## Implementation:
### Structural Design Pattern

**Facade Design Pattern**

Facade is a structural design pattern that provides a simplified interface to a library, a framework, or any other complex set of classes.

The project uses Facade Design Pattern to bound and resume the use of multiple classes that have different roles. 

```python
class PhotoHunt:

    ....

    def start(self) -> None:
        while True:
            self.controls.set_search_query()
            self.controls.format_search_query()

            search_query_param = self.controls.get_search_query()
            self.search_engine.format_url(search_query_param)

            if self.proxy.check_cache(search_query_param):
                print("[*] Cache hit. Loading from cache.")
                photos = self.proxy.get_cache(search_query_param)
                self.display.load_from_cache(photos)

            else:
                print("[*] Cache miss. Downloading images.")
                photos_links = self.search_engine.query(self.number_of_photos)
                self.display.load(photos_links)
                self.proxy.add_cache(search_query_param,
                                     self.display.get_photos())

            self.display.show()
```

In the `start` method instances from different classes like the `SearchEngine` and `Display` are used, in order to return the desired images to the user.

**Bridge Design Pattern**

Bridge is a structural design pattern that lets you split a large class or a set of closely related classes into two separate hierarchies—abstraction and implementation—which can be developed independently of each other.

The application uses a search engine in order query the images from web. A great design decision is to split the use of the search engine API and the interface that the user uses to see the queried images. Thus the Bridge pattern is a great fit for the Control Interface. 

```python
class ControlsInterface(ABC):
    """
    The 'implementation' interface declares methods common to all
    concrete implementation classes. It doesn't have to match the
    abstraction's interface.
    """

    @abstractmethod
    def add_search_key(self, keyword: str) -> None:
        pass

    @abstractmethod
    def construct_search_query(self, keywords: str) -> None:
        pass

    @abstractmethod
    def get_search_query(self) -> str:
        pass

    @abstractmethod
    def check_command(self, command: str) -> None:
        pass
```

The above class is the implementation, used to create classes that the `Abstraction` will then use to performe interface operations.

```python
class TerminalConstrols(ControlsInterface):
    """
    Concrete implementation of the interface specific to a platform,
    i. e. terminal interface.
    """

    def __init__(self) -> None:
        self.keyword: str = ""
        self.search_query_param: str = ""

    def add_search_key(self, keyword: str) -> None:
        self.search_query_param += f"{keyword}+"

    def construct_search_query(self, keywords: str) -> None:
        self.search_query_param: str = ""
        for keyword in keywords.split(" "):
            self.add_search_key(keyword)

    def get_search_query(self) -> str:
        return self.search_query_param

    def check_command(self, command: str) -> None:
        if command == ":quit":
            sys.exit(0)
```

And this is an example of a concrete implementation. In this case, an implementation for the terminal users, but it can be easily replaced by another class with a different implementation, i.e. for a web browser. 

```python
class Controls:
    """
    The Abstraction defines the interface for the 'control' part
    of the two class hierarchies. It maintains a reference to an
    object of the Implementation hierarchy and delegates all of
    the real work to this object.
    """

    def __init__(self, controls_interface: ControlsInterface) -> None:
        self.controls_interface: ControlsInterface = controls_interface
        self.search_query_string: str = ""

    def set_search_query(self) -> None:
        sq: str = input("Search: ").strip()
        self.controls_interface.check_command(sq)
        while not sq:
            print(
                "[*] You have provided an invalid search query. Please enter a valid one..."
            )
            sq = input("Search: ").strip()
        self.search_query_string = sq

    def format_search_query(self) -> None:
        self.controls_interface.construct_search_query(
            self.search_query_string)

    def get_search_query(self) -> str:
        return self.controls_interface.get_search_query()
```

The `Controls` being the `Abstraction` for the concrete implementation of the interface is used as the `bridge` in the core logic of the application, which then talks with components, like the search engine, in the case of this project.

**Proxy Design Pattern**

Proxy is a structural design pattern that lets you provide a substitute or placeholder for another object. A proxy controls access to the original object, allowing you to perform something either before or after the request gets through to the original object.

In the case of this project, the Proxy pattern was used to achieve the caching of images. This way the application runs quicker, as it doesn't have to use the search engine and process downloaded images.

```python
class Proxy:

    def __init__(self) -> None:
        self.cached_links: dict[str, list[str]] = {}

    def check_cache(self, search_param: str) -> bool:
        if self.cached_links.get(search_param, None):
            return True
        return False

    def get_cache(self, search_param: str) -> list[str]:
        return self.cached_links.get(search_param)

    def add_cache(self, search_link: str, photos: list[str]) -> None:
        if not self.cached_links.get(search_link, None):
            self.cached_links[search_link] = photos.copy()
        else:
            self.cached_links[search_link] += photos.copy()
```

`Proxy` class acts very much as an `dict` in python, with the only difference of having extra checks.

```python
if self.proxy.check_cache(search_query_param):
    print("[*] Cache hit. Loading from cache.")
    photos = self.proxy.get_cache(search_query_param)
    self.display.load_from_cache(photos)
else:
    print("[*] Cache miss. Downloading images.")
    photos_links = self.search_engine.query(self.number_of_photos)
    self.display.load(photos_links)
    self.proxy.add_cache(search_query_param,
                         self.display.get_photos())
```

In the application itself the proxy is always checking first if there is a cache hit, before moving to the use of the search engine. 

## Conclusion:

Overall, structural design patterns are great techniques for achieving a consistence behavior among objects and organization in the project's architecture and design. It introduces flexibility and readability into the project. A big monolithic project can be easily splitted into smaller subsystems that can be development seperatly and this increases the integraty and robustness, which helps a lot in the development process. 