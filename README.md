# IS23-AM01

## Implemented features
| Functionality       |                                                                          Status                                                                          |
|:------------------- |:--------------------------------------------------------------------------------------------------------------------------------------------------------:|
| MODEL               |[✅](https://github.com/campanamattia/IS23-AM01/tree/main/src/main/java/Server/Model)|
| SERVER CONTROLLER   |[✅](https://github.com/campanamattia/IS23-AM01/tree/main/src/main/java/Server/Controller)|
| SERVER NETWORK      |[✅](https://github.com/campanamattia/IS23-AM01/tree/main/src/main/java/Server/Network)|
| CLIENT NETWORK      |[✅](https://github.com/campanamattia/IS23-AM01/tree/main/src/main/java/Client/Network)|
| CLIENT CLI          |[✅](https://github.com/campanamattia/IS23-AM01/tree/main/src/main/java/Client/View/Cli)|
| CLIENT GUI          |[⏱️](https://github.com/campanamattia/IS23-AM01/tree/main/src/main/java/Client/View/Gui)|
| MULTI MATCH         |[✅](https://github.com/campanamattia/IS23-AM01/tree/main/src/main/java/Server/Network/Lobby)|
| CHAT                |[✅](https://github.com/campanamattia/IS23-AM01/tree/main/src/main/java/Utils)|
| RESILIENCE          |[✅](https://github.com/campanamattia/IS23-AM01/tree/main/src/main/java/Server/Network/Lobby)|
| PERSISTENCE         |⛔|
#### Legend
⛔ Won't Implemented &nbsp;&nbsp; ✅ Implemented &nbsp;&nbsp; ⏱️ On the Way &nbsp;&nbsp; ⚙️ Testing


## Tools
| Tool                                                     | Description           |
|:---------------------------------------------------------|:----------------------|
| [IntelliJ IDEA Ultimate](https://www.jetbrains.com/idea) | IDE                   |
| [SequenceDiagram.org](https://sequencediagram.org)       | UML Sequence Diagram  |
| [Maven](https://maven.apache.org)                        | Dependency Management |
| [JUnit](https://junit.org/junit5)                        | Unit Testing          |
| [JavaFX](https://openjfx.io)                             | Graphical Library     |


## Testing
TODO


## Jar
The jars are used to launch the software. A detailed guide on how to launch jar files is present in the next section. It is possible to find them at [_this link_](link after we update it in delivers).


### Jar Execution
In order to run the jar files, _Java SE Development Kit_ is required to be installed. It can be downloaded from the official [_Oracle website_](https://www.oracle.com/java/technologies/downloads).


#### Server
The server can be run with the following command in a terminal window (remember to run the command when you are in the folder containing the jar). The parameter is optional and indicates the server ip. If it is null will be prompted when starting the executable


 ```
java --enable-preview -jar MyShelfie-Server.jar [ip_server]
 ```


#### Client
The client can be run with the one following command in a terminal window.


Once the client is opened, it is asked to specify the type of connection ('rmi' or 'socket'), the ip of the server (it's possible to insert 'default' for a default value written in a json file or 'localhost' for ip=127.0.0.1) and the port (it'possible to insert 'default' for a defalut of socket_port = 20000 or rmi_port = 30000)


##### For the GUI:
 ```
java --enable-preview -jar MyShelfie-Client.jar
 ```


##### For the CLI:
 ```
java --enable-preview -jar MyShelfie-Client.jar cli
 ```
Should you see misaligned layout, we recommend to maximise the terminal window.


## Team
- [Mattia Campana](https://github.com/camanamattia) - `mattia.campana@mail.polimi.it`
- [Matteo Bettiati](https://github.com/matteobettiati) - `matteo.bettiati@mail.polimi.it`
- [Alessio Caggiano](https://github.com/falcro02) -`alessio.caggiano@mail.polimi.it`
- [Gionata Brebbia](https://github.com/gionatabrebbia) -`gionata.brebbia@mail.polimi.it`


## License
This project has been developed in collaboration with [Cranio Creations](https://www.craniocreations.it/prodotto/my-shelfie).
