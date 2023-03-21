package Model;

import com.google.gson.*;

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
    private ChatRoom chatRoom;
    String json;


    public GameModel(int nPlayers, List<String> players) {
        this.nPlayers = nPlayers;
        this.firstPlayer = players.get(0);
        this.players = new ArrayList<Player>();
        this.commonGoals = new ArrayList<CommonGoal>();

        this.bag = new Bag();
        this.board = new Board(nPlayers, this.bag);
        this.chatRoom = new ChatRoom();


        //creating new personalGoal
        Random random = new Random();
        List<Integer> set = new ArrayList<Integer>();
        while(set.size()<nPlayers){
            Integer num = random.nextInt(12);
            if(!set.contains(num))
                set.add(num);
        }
        //creating new Players
        for (String tmp : players)
            this.players.add(new Player(tmp, PersonalGoalFactory.getPersonalGoal(set.remove(0))));

        //creating 2 commonGoal
        set.clear();
        while(set.size()<2){
            Integer num = random.nextInt(12);
            if(!set.contains(num))
                set.add(num);
        }
        this.commonGoals.add(CommonGoalFactory.getCommonGoal(set.remove(0)));

        /* updating instantly the state*/
        updateStatus();
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
    public void writeChat(String player, String message, LocalDateTime time) {
        /*
        maybe a json file instead of
        hypothetical deconstruction of json file
        */
        this.chatRoom.addMessage(player, message, time);
    }

    public HashMap<String, Integer> finalRank(){
        for(Player temp : this.players)
            temp.endGame();
        HashMap<String, Integer> rank = new HashMap<String, Integer>();
        while(rank.size() < this.nPlayers){
            Player min = null;
            for(Player temp : this.players) {
                if (min == null)
                    min = temp;
                else if (min.getScore() > temp.getScore())
                    min = temp;
            }
            rank.add(min.getID, min.getScore);
        }
        return rank;
    }

    public void updateStatus(){
        Gson gson = new Gson();
        this.json = gson.toJson(this);
    }

    public String showStatus(){
        return this.json;
    }
}