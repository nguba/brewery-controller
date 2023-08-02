# Hexagonal Architecture: Output Ports

Output ports, also known as secondary ports, represent the application's intent to deal
with external data. It's through output ports that we prepare the system to communicate
with the outside world. By allowing this communication, we can associate output ports
with driven actors and operations.

An interesting observation is that the methods defined in output ports are useful in
unit testing. Well-defined output ports should encapsulate all the methods needed to
support these.