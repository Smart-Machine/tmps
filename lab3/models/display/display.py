import io
import climage
import requests
import threading
from PIL import Image


class Display:

    def __init__(self) -> None:
        self.photos: list[str] = []

    def download_image(self, link: str) -> None:
        try:
            response = requests.get(link)
            image = Image.open(io.BytesIO(response.content)).convert("RGB")
            converted_image = climage.convert_pil(image, is_unicode=True)
            self.photos.append(converted_image)
        except Exception as e:
            print("[*] Couldn't display the image")

    def load(self, photos_links: list[str]) -> None:
        threads = [threading.Thread(
            target=self.download_image, args=(link,)) for link in photos_links]
        for thread in threads:
            thread.start()
            thread.join()

    def load_from_cache(self, photos: list[str]) -> None:
        self.photos = photos

    def get_photos(self) -> list[str]:
        return self.photos

    def show(self) -> None:
        for _ in range(len(self.photos)):
            print(self.photos.pop(0))
