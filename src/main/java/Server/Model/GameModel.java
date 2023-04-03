package Server.Model;

import Interface.CMD;
import Exception.*;
import Enumeration.*;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GameModel implements CMD {
    private UUID uuid;
    private GamePhase phase;
    private final int nPlayers;
    private final String firstPlayer;
    private Player currPlayer;

    private List<Player> players;
    private List<CommonGoal> commonGoals;

    private Bag bag;
    private Board board;
    private ChatRoom chatRoom;

    String filepath;


    public GameModel(UUID uuid,  List<String> players) throws FileNotFoundException {
        this.uuid = uuid;
        this.phase = GamePhase.STARTING;
        this.nPlayers = players.size();
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
            PersonalGoal pGoal = new PersonalGoal(array.remove(random.nextInt(array.size())).getAsJsonObject());
            this.players.add(new Player(tmp, pGoal));
        }
        this.currPlayer = this.players.get(0);

        //creating 2 commonGoal
        generateCommonGoal("src/main/resources/commonGoal.json");

        // updating instantly the state
        this.filepath = buildPath();
        updateStatus();
        this.phase = GamePhase.ONGOING;
    }

    public GameModel(String filepath) throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader ;
        reader = new JsonReader(new FileReader(filepath));
        JsonObject json = gson.fromJson(reader, JsonObject.class);
        this.phase = getAsPhase(json.get("phase").getAsString());
        this.nPlayers = json.get("nPlayers").getAsInt();
        this.firstPlayer = json.get("firstPlayer").toString();
    }

    private String buildPath(){
        StringBuilder stringBuilder = new StringBuilder();
        for(Player tmp : this.players)
            stringBuilder.append(tmp.getID()).append("_");
        stringBuilder.append(uuid).append(".json");
        return stringBuilder.toString();
    }

    private void updateStatus() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        try{
            FileWriter writer = new FileWriter(this.filepath);
            writer.write(json);
            writer.close();
            System.out.println("Successfully update the json");
        } catch (IOException e) {
            System.out.println("An error occurred updating the json file.");
        }
    }

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

    private List<Integer> getAsList(JsonArray array){
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i <=array.size(); i++)
            list.add(array.get(array.size()-i).getAsInt());
        return list;
    }

    private GamePhase getAsPhase(String code) throws FileNotFoundException {
        for(GamePhase phase : GamePhase.values())
            if(code.equals(phase.toString()))
                return phase;
        throw new FileNotFoundException("GamePhase didn't found");
    }

    public void endTurn(){
        for(CommonGoal com : this.commonGoals)
            if(!com.getAccomplished().contains(this.currPlayer.getID()))
                com.check(currPlayer);
        if(this.currPlayer.getMyShelf().full())
            this.phase = GamePhase.ENDING;
        nextPlayer();
        updateStatus();
    }

    private void nextPlayer(){
        do{
            int nextIndex = (this.players.indexOf(this.currPlayer)+1) % players.size();
            if(this.phase == GamePhase.ENDING && nextIndex == 0){
                this.phase = GamePhase.ENDED;
                break;
            }
            else
                this.currPlayer = this.players.get(nextIndex);
        } while(!this.currPlayer.getStatus());
    }

    public List<Rank> finalRank(){
        for(Player tmp : this.players)
            tmp.endGame();
        List<Rank> rank = new ArrayList<Rank>();
        Player min;
        while(0 < this.players.size()){
            min = null;
            for(Player tmp : this.players){
                if(min ==  null)
                    min = tmp;
                else if (min.getScore() > tmp.getScore())
                    min = tmp;
            }
            this.players.remove(min);
            rank.add(new Rank(min.getID(), min.getScore()));
        }
        return rank;
    }

    @Override
    public List<Tile> selectedTiles(List<Coordinates> coordinates) throws BoardException {
        this.board.convalidateMove(coordinates);
        return this.board.getTiles(coordinates);
    }

    @Override
    public void insertTiles(List<Integer> sort, List<Tile> tiles, int column) throws PlayerException {
        //re-order the list of tile
        for (Integer integer : sort) tiles.add(tiles.get(integer - 1));
        tiles.subList(0, sort.size()).clear();
        this.currPlayer.getMyShelf().insert(column-1, tiles);
    }

    @Override
    public void writeChat(String message) {
        Gson gson = new Gson();
        ChatMessage text = gson.fromJson(message, ChatMessage.class);
        this.chatRoom.addMessage(text);
    }


    public Player getPlayer(String id) throws PlayerNotFoundException{
        for(Player tmp : this.players)
            if(tmp.equals(id)) return tmp;
        throw new PlayerNotFoundException(id);
    }

    public UUID getUuid() {
        return uuid;
    }

    public GamePhase getPhase(){
        return this.phase;
    }

    public int getNPlayers() {
        return this.nPlayers;
    }

    public String getFirstPlayer() {
        return this.firstPlayer;
    }

    public Player getCurrPlayer() {
        return this.currPlayer;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public List<CommonGoal> getCommonGoals() {
        return this.commonGoals;
    }

    public Bag getBag() {
        return this.bag;
    }

    public Board getBoard() {
        return this.board;
    }

    public ChatRoom getChatRoom() {
        return this.chatRoom;
    }
}