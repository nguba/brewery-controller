# Hexagonal Architecture: Implementing use cases with input ports

Input ports play an integrating role because they are like pipes that allow the data to flow
from driving actors when they hit the hexagonal system through one of its adapters on the
Framework hexagon. In the same vein, input ports provide the pipes for communication with
business rules from the Domain hexagon. Through input ports, we also orchestrate
communication with external systems through output ports and adapters.