from models.controls.controls import Controls
from models.controls.controls import TerminalConstrols
from models.search_engine.search_engine import SearchEngine
from models.proxy.proxy import Proxy
from models.display.display import Display

from models.photo_hunt.photo_hunt import PhotoHunt

# Implemented Bridge, Facade, and Proxy
photo_hunt = PhotoHunt(Controls(TerminalConstrols()),
                       SearchEngine(), Proxy(), Display())
photo_hunt.start()
