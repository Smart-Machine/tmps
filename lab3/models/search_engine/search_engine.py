import re
import json
import requests


class SearchEngine:

    def __init__(self) -> None:
        self.search_link_formatted = None
        self.query_link_formatted = None

        self.search_link = "https://duckduckgo.com/?q={}&iax=images&ia=images"
        self.query_link = "https://duckduckgo.com/i.js?o=json&q={}"

    def format_url(self, search_query_param: str = "") -> None:
        self.search_link_formatted = self.search_link.format(
            search_query_param)
        self.query_link_formatted = self.query_link.format(search_query_param)

    def get_search_link(self) -> str | None:
        return self.search_link_formatted

    def query(self, number_of_photos: int = 1) -> list[str]:
        try:
            main_page = requests.get(url=self.search_link_formatted, headers={
                "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8",
                "Accept-Encoding": "gzip, deflate, br",
                "Connection": "keep-alive",
                "Host": "duckduckgo.com",
                "User-Agent": "Mozilla/5.0 (X11; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0",
            })
            special_key = re.search(
                r"&vqd=([0-9]*\-*[0-9]*)", main_page.text).group(1)

            data = requests.get(
                self.query_link_formatted + f"&vqd={special_key}")
            json_data = json.loads(data.content)
            results = json_data["results"]
            return [results[i]["image"] for i in range(number_of_photos)]

        except requests.exceptions.MissingSchema:
            print("[*] Wrong URL schema.")
            return []
        except requests.exceptions.ConnectionError:
            print("[*] Wrong URL or Refused connection.")
            return []
        except Exception as e:
            print("[*] Got an unexpected error.")
            print(f"[*] ERROR: {e}")
            return []
