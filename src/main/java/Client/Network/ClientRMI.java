package Client.Network;

import Client.View.View;
import Interface.Client.RemoteClient;
import Interface.Client.RemoteView;
import Interface.Scout;
import Interface.Server.GameCommand;
import Interface.Server.LobbyInterface;
import Utils.Coordinates;


import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import static Client.ClientApp.executorService;
import static Client.ClientApp.view;


public class ClientRMI extends Network {
    private GameCommand gc;
    private LobbyInterface lobby;

    public ClientRMI() throws RemoteException {
        super();
    }

    @Override
    public void init(String ip, int port) {
        port = (port == -1) ? 50001 : port;
        try {
            Registry registry = LocateRegistry.getRegistry(ip, port);
            this.lobby = (LobbyInterface) registry.lookup("Lobby");
            this.lobby.getLobbyInfo(view);
        } catch (Exception e) {
            try {
                view.outcomeException(e);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void selectTiles(String playerID, List<Coordinates> coordinates) throws RemoteException {
        executorService.execute(() -> {
            try {
                this.gc.selectTiles(playerID, coordinates);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void writeChat(String from, String message, String to) throws RemoteException {
        executorService.execute(() -> {
            try {
                this.gc.writeChat(from, message, to);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void addScout(Scout scout) throws RemoteException {
        executorService.execute(() -> {
            try {
                this.gc.addScout(this);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void insertTiles(String playerID, List<Integer> sorted, int column) throws RemoteException {
        executorService.execute(() -> {
            try {
                this.gc.insertTiles(playerID, sorted, column);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }


    @Override
    public void getLobbyInfo(RemoteView remote) throws RemoteException {
        executorService.execute(() -> {
            try {
                this.lobby.getLobbyInfo(remote);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void setLobbySize(String playerID, String lobbyID, int lobbySize) throws RemoteException {
        executorService.execute(() -> {
            try {
                this.lobby.setLobbySize(playerID, lobbyID, lobbySize);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void login(String playerID, String lobbyID, RemoteView remoteView, RemoteClient client) throws RemoteException {
        executorService.execute(() -> {
            try {
                this.lobby.login(playerID, lobbyID, remoteView, client);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void ping(String playerID, String lobbyID) throws RemoteException {
        executorService.execute(() -> {
            try {
                this.lobby.ping(playerID, lobbyID);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void getGameController(String lobbyID, RemoteClient remote) throws RemoteException {
        executorService.execute(() -> {
            try {
                this.lobby.getGameController(lobbyID, remote);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void logOut(String playerID, String lobbyID) throws RemoteException {
        executorService.execute(() -> {
            try {
                this.lobby.logOut(playerID, lobbyID);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void setGameController(GameCommand gameController) throws RemoteException {
        this.gc = gameController;
        executorService.execute(() -> {
            try {
                this.gc.addScout(this);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void update(Object objects) throws RemoteException {
        if (scouts.containsKey(objects.getClass())) {
            scouts.get(objects.getClass()).update(objects);
        } else {
            view.printError("Scout-Handler not found");
            throw new RemoteException("Scout not found");
        }
    }
}
