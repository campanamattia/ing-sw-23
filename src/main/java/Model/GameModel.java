package Model;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.*;
import java.lang.*;
public class GameModel implements CMD{
    private final Integer nPlayers;
    private final String firstPlayer;
    private Bag bag;
    private Board board;
    private List<Player> players;
    private List<CommonGoal> commonGoals;
    private CommonGoalFactory commonGoalFactory;
    private PersonalGoalFactory personalGoalFactory;
    private ChatRoom chatRoom;


    public GameModel(int nPlayers, @NotNull List<String> players) {
        this.nPlayers = nPlayers;
        this.firstPlayer = players.get(0);
        this.players = new ArrayList<Player>();
        this.commonGoals = new ArrayList<CommonGoal>();

        this.bag = new Bag();
        this.board = new Board();
        this.chatRoom = new ChatRoom();

        //factory
        Random random = new Random();
        //creating PersonalGoalFactory
        HashSet<Integer> enumPersonal = new HashSet<Integer>();
        for(int i=0; i<this.nPlayers; i++) {
            if(!enumPersonal.add(random.nextInt(12))) // if it generates an already present number I generate another one
                i--;
        }
        //generating personalGoal
        this.personalGoalFactory = new PersonalGoalFactory(enumPersonal);
        //creating & adding new players
        for(int i=0; i<this.nPlayers; i++)
            this.players.add(addPlayer(players.remove(0), personalGoalFactory.getPersonalGoal()));

        //creating CommonGoalFactory
        HashSet<Integer> enumCommon = new HashSet<Integer>();
        for(int i=0; i<2; i++)
            if(!enumCommon.add(random.nextInt(12))) // if it generates an already present number I generate another one
                i--;
        this.commonGoalFactory = new CommonGoalFactory(enumCommon);
        for(int i=0; i<2; i++)
            this.commonGoals.add(commonGoalFactory.getCommonGoal());
    }
    public static Player addPlayer(String nickname, PersonalGoal personalGoal){
        return new Player(nickname, personalGoal);
    }

    @Override
    public List<Tile> selectedTiles(List<Coordinates> coordinates) {
        if (!this.board.convalidateMove(coordinates))
            return null;
        else
            return this.board.getter(coordinates);
    }

    @Override
    public void insertTiles(String player, List<Integer> sort, List<Tile> tiles, int column) {
        //re-order the list of tile
        for (Integer integer : sort) tiles.add(tiles.get(integer - 1));
        tiles.subList(0, sort.size()).clear();
        // look for the current player
        Player executor;
        for(Player temp : this.players){
            if(temp.equals(player)){
                executor = temp;
                break;
            }
        }
        Shelf temp_shelf = executor.getShelf(); //give a look at the exception
        temp_shelf.insert(column, tiles);
    }

    @Override
    public void writeChat(String player, String message, LocalDateTime time) { /*
        maybe a json file instead of
        hypothetical deconstruction of json file
        */
        this.chatRoom.addMessage(player, message, time);
    }
}