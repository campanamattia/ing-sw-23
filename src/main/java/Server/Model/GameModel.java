package Server.Model;

import Exception.BoardException;
import Exception.PlayerException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import Server.Exception.*;
import java.io.*;
import java.util.*;

public class GameModel implements CMD{

    private UUID uuid = UUID.randomUUID();
    private final int nPlayers;
    private final String firstPlayer;
    private String currPlayer;

    private List<Player> players;
    private List<CommonGoal> commonGoals;

    private Bag bag;
    private Board board;
    private ChatRoom chatRoom;

    public GameModel(int nPlayers, List<String> players) throws FileNotFoundException {
        this.nPlayers = nPlayers;
        this.firstPlayer = players.get(0);
        this.currPlayer = firstPlayer;

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
            PersonalGoal pGoal = new PersonalGoal(array.remove(random.nextInt(array.size())).getAsJsonObject());
            this.players.add(new Player(tmp, pGoal));
        }

        //creating 2 commonGoal
        generateCommonGoal("src/main/resources/commonGoal.json");

        // updating instantly the state
        updateStatus();
    }

    // TODO: 24/03/2023
    /* 
    public GameModel(String filepath) {

    }

     */

    private JsonObject decoBoard(String filepath) throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader;
        reader = new JsonReader(new FileReader(filepath));
        JsonObject json = gson.fromJson(reader, JsonObject.class);
        return json.getAsJsonObject(Integer.toString(this.nPlayers));
    }

    private JsonArray decoPersonal(String filepath) throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader;
        reader = new JsonReader(new FileReader(filepath));
        return gson.fromJson(reader, JsonArray.class);
    }

    private void generateCommonGoal(String filepath) throws FileNotFoundException{
        Gson gson = new Gson();
        JsonReader reader;
        reader = new JsonReader(new FileReader(filepath));
        JsonObject json = gson.fromJson(reader, JsonObject.class);
        JsonArray array = json.get("commonGoal").getAsJsonArray();
        json = json.get("scoringToken").getAsJsonObject();
        List<Integer> scoringToken = getAsList(json.get(Integer.toString(this.nPlayers)).getAsJsonArray());
        Random random = new Random();
        this.commonGoals.add(CommonGoalFactory.getCommonGoal(scoringToken, array.remove(random.nextInt(array.size())).getAsJsonObject()));
    }

    private static List<Integer> getAsList(JsonArray array){
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i <=array.size(); i++)
            list.add(array.get(array.size()-i).getAsInt());
        return list;
    }
    
    @Override
    public List<Tile> selectedTiles(List<Coordinates> coordinates) throws BoardException {
        try {
            this.board.convalidateMove(coordinates);
            return this.board.getTiles(coordinates);
        } catch (BoardException exception) {
            throw exception;
        }
    }

    @Override
    public void insertTiles(String player, List<Integer> sort, List<Tile> tiles, int column) throws PlayerException {
        //re-order the list of tile
        for (Integer integer : sort) tiles.add(tiles.get(integer - 1));
        tiles.subList(0, sort.size()).clear();
        // look for the current player
        Player executor = null;
        for(Player temp : this.players){
            if(temp.equals(player)){
                executor = temp;
                break;
            }
        }
        Shelf temp_shelf = executor.getMyShelf(); //give a look at the exception
       try{
           temp_shelf.insert(column-1, tiles);
       } catch (PlayerException exception){
           throw exception;
       }
    }

    @Override
    public void writeChat(String message) {
        Gson gson = new Gson();
        ChatMessage text = gson.fromJson(message, ChatMessage.class);
        this.chatRoom.addMessage(text);
    }

    // TODO: 24/03/2023  
    public List<Rank<String, Integer>> finalRank(){
        for(Player tmp : this.players)
            tmp.endGame();
        List<Rank<String, Integer>> rank = new ArrayList<>();
        Player min;
        while(rank.size() < this.nPlayers){
            min = null;
            for(Player tmp : this.players){
                if(min ==  null)
                    min = tmp;
                else if (min.getScore() > tmp.getScore())
                    min = tmp;
            }
            this.players.remove(min);
            rank.add(new Rank<>(min.getID(), min.getScore()));
        }
        return rank;
    }

    // TODO: 24/03/2023  
    public GameModel reloadGame(String hash){
        /*
        reload the game that crashed
        return new GameModel(hash);
        */
        return null;
    }
    // TODO: 24/03/2023  
    private static void updateStatus(){
        /*
        update json in the resource
        the first time a generate the file json, where the name is composed by
        a hashcode that I will use tu identify the match's .json file
        */
    }

}

class Rank<String, Integer> {
    private String ID;
    private int score;

    public Rank(String ID, int score) {
        this.ID = ID;
        this.score = score;
    }
}