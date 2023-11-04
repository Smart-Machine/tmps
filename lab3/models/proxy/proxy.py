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
