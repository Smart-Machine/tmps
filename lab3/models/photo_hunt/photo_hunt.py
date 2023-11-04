from models.controls.controls import Controls
from models.search_engine.search_engine import SearchEngine
from models.proxy.proxy import Proxy
from models.display.display import Display


class PhotoHunt:

    def __init__(self, controls: Controls, search_engine: SearchEngine, proxy: Proxy, display: Display) -> None:
        self.controls = controls
        self.search_engine = search_engine
        self.proxy = proxy
        self.display = display

        self.number_of_photos = 5

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
