import sys
from interfaces.controls_interface import ControlsInterface


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
