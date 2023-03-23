package Model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.*;

public class GameModel implements CMD{
    private final int nPlayers;
    private final String firstPlayer;
    private Bag bag;
    private Board board;
    private List<Player> players;
    private List<CommonGoal> commonGoals;
    private ChatRoom chatRoom;

    public GameModel(int nPlayers, List<String> players) throws FileNotFoundException {
        this.nPlayers = nPlayers;
        this.firstPlayer = players.get(0);

        this.bag = new Bag();
        this.chatRoom = new ChatRoom();

        this.players = new ArrayList<Player>();
        this.commonGoals = new ArrayList<CommonGoal>();

        //creating board
        JsonObject board_json = decoBoard("src/main/resources/board.json");
        this.board = new Board(board_json, this.bag);

        //creating Players
        JsonArray array = decoPersonal("src/main/resources/personalgoal.json");
        Random random = new Random();
        for (String tmp : players){
            PersonalGoal pGoal = new PersonalGoal(array.remove(random.nextInt(array.size())));
            this.players.add(new Player(tmp, pGoal);
        }

        //creating 2 commonGoal
        List<Integer> set = new ArrayList<Integer>();
        while(set.size()<2){
            Integer num = random.nextInt(12);
            if(!set.contains(num))
                set.add(num);
        }
        this.commonGoals.add(CommonGoalFactory.getCommonGoal(set.remove(0), this.nPlayers));

        // updating instantly the state
        updateStatus();
    }

    public JsonObject decoBoard(String filepath) throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader;
        reader = new JsonReader(new FileReader(filepath));
        JsonObject json = gson.fromJson(reader, JsonObject.class);
        return json.getAsJsonObject(Integer.toString(this.nPlayers));
    }
    public JsonArray decoPersonal(String filepath) throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader;
        reader = new JsonReader(new FileReader(filepath));
        return gson.fromJson(reader, JsonArray.class);
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
    public void writeChat(String message) {
        Gson gson = new Gson();
        ChatMessage text = gson.fromJson(message, ChatMessage.class);
        this.chatRoom.addMessage(text);
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

    public void reloadGame(String filepath){

    }

    public void updateStatus(){
        //save the new json in the resource
    }

    public String showStatus(){
        return this.json;
    }
}