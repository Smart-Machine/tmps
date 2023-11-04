from abc import ABC, abstractmethod


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
