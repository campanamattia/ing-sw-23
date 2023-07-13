# IS23-AM01
![MyShelfie](https://github.com/campanamattia/IS23-AM01/blob/main/src/main/resources/img/misc/Sacchetto%20Aperto.png)
# Implemented features
| Functionality       |                                                                          Status                                                                          |
|:------------------- |:--------------------------------------------------------------------------------------------------------------------------------------------------------:|
| MODEL               |[✅](https://github.com/campanamattia/IS23-AM01/tree/main/src/main/java/Server/Model)|
| SERVER CONTROLLER   |[✅](https://github.com/campanamattia/IS23-AM01/tree/main/src/main/java/Server/Controller)|
| SERVER NETWORK      |[✅](https://github.com/campanamattia/IS23-AM01/tree/main/src/main/java/Server/Network)|
| CLIENT NETWORK      |[✅](https://github.com/campanamattia/IS23-AM01/tree/main/src/main/java/Client/Network)|
| CLIENT CLI          |[✅](https://github.com/campanamattia/IS23-AM01/tree/main/src/main/java/Client/View/Cli)|
| CLIENT GUI          |[✅](https://github.com/campanamattia/IS23-AM01/tree/main/src/main/java/Client/View/Gui)|
| MULTI MATCH         |[✅](https://github.com/campanamattia/IS23-AM01/tree/main/src/main/java/Server/Network/Lobby)|
| CHAT                |[✅](https://github.com/campanamattia/IS23-AM01/tree/main/src/main/java/Utils)|
| RESILIENCE          |[✅](https://github.com/campanamattia/IS23-AM01/tree/main/src/main/java/Server/Network/Lobby)|
| PERSISTENCE         |⛔|
#### Legend
✅ Implemented &nbsp;&nbsp; ⛔ Won't Implemented &nbsp;&nbsp; ⚙️ Testing &nbsp;&nbsp; ⏱ Developig


## Tools
| Tool                                                     | Description           |
|:---------------------------------------------------------|:----------------------|
| [IntelliJ IDEA Ultimate](https://www.jetbrains.com/idea) | IDE                   |
|[DRAW](https://app.diagrams.net)                          | UML                   |
| [SequenceDiagram.org](https://sequencediagram.org)       | UML Sequence Diagram  |
| [Maven](https://maven.apache.org)                        | Dependency Management |
| [JUnit](https://junit.org/junit5)                        | Unit Testing          |
| [JavaFX](https://openjfx.io)                             | Graphical Library     |


# Testing
### Model
![Test Model](https://github.com/campanamattia/IS23-AM01/blob/main/deliverables/TEST%20COVERAGE/MODEL.png)
### Player
![Test Player](https://github.com/campanamattia/IS23-AM01/blob/main/deliverables/TEST%20COVERAGE/PLAYER.png)
### LivingRoom
![Test LivingRoom](https://github.com/campanamattia/IS23-AM01/blob/main/deliverables/TEST%20COVERAGE/LIVING%20ROOM.png)





# Jar
The jars are used to launch the software. A detailed guide on how to launch jar files is present in the next section. It is possible to find them at [_this link_](https://github.com/campanamattia/IS23-AM01/tree/main/deliverables/JAR).


## Jar Execution
In order to run the jar files, _Java SE Development Kit_ is required to be installed. It can be downloaded from the official [_Oracle website_](https://www.oracle.com/java/technologies/downloads).


#### Server
The server can be run with the following command in a terminal window (remember to run the command when you are in the folder containing the jar).
 ```
java -jar --enable-preview MSH-SERVER-v1.01.jar <ipHost> [<-s><socketPort> <-r><rmiPort>]
 ```
The IP address is mandatory, but if desired, you can write "localhost" to use 127.0.0.1 or "default" to use the one loaded from the JSON file. The RMI and socket ports will also be extracted from there unless specified through the command line as indicated above.

#### Client[^1]
The client can be run with one of the following commands in a terminal window.
 ```
java -jar --enable-preview MSH-CLIENT-v1.01.jar <GUI/CLI>
 ```
Upon launching the client, you will be prompted to specify the type of connection: either 'rmi' or 'socket'. Additionally, you will need to provide the IP address of the server. You have the option to enter 'default' if you wish to use a predefined value stored in a JSON file. Alternatively, you can use 'localhost' as the IP address, which corresponds to 127.0.0.1. Lastly, you will be asked to enter the port number. If you prefer the default value, you can input 'default', which is 2000 for socket connections and 3000 for RMI connections.

[^1]: If the GUI is not working, you will need to locally rebuild the jar using Maven by executing the following command `mvn package -DskipTests -U -Pclient `

## Team
- [Mattia Campana](https://github.com/camanamattia) - `mattia.campana@mail.polimi.it`
- [Matteo Bettiati](https://github.com/matteobettiati) - `matteo.bettiati@mail.polimi.it`
- [Alessio Caggiano](https://github.com/falcro02) -`alessio.caggiano@mail.polimi.it`
- [Gionata Brebbia](https://github.com/gionatabrebbia) -`gionata.brebbia@mail.polimi.it`


## License
This project has been developed in collaboration with [Cranio Creations](https://www.craniocreations.it/prodotto/my-shelfie).
