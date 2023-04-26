package Server.Model;

import Enumeration.GamePhase;
import Exception.BoardException;
import Exception.ChatException;
import Exception.Player.NonConformingInputParametersException;
import Exception.PlayerException;
import Exception.PlayerNotFoundException;
import Utils.Coordinates;
import Utils.Rank;
import Utils.Tile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 Represents the game model, contain information about the game state, players, board, and chat.
 */
public class GameModel {
    /**
     * the unique identifier of the game session
     */
    @Expose
    private UUID uuid;
    /**
     * the current phase of the game
     */
    @Expose
    private GamePhase phase;
    /**
     * the total number of players in the game
     */
    @Expose
    private final int nPlayers;
    /**
     * the ID of the first player who starts the game
     */
    @Expose
    private final String firstPlayer;
    /**
     * the current player of the game
     */
    @Expose
    private Player currentPlayer;

    /**
     * list of all players in the game
     */
    @Expose
    private List<Player> players;
    /**
     * the list of the two common goals for the game
     */
    @Expose
    private List<CommonGoal> commonGoals;
    /**
     * the final leaderboard for the game
     * (null if the game is still ongoing)
     */
    @Expose
    private List<Rank> leaderboard = null;

    /**
     * the bag that contains the tiles used in the game
     */
    @Expose
    private Bag bag;
    /**
     * the board of the game
     */
    @Expose
    private Board board;
    /**
     * the chat room for the players to communicate with each other
     */
    @Expose
    private ChatRoom chatRoom;

    /**
     * the file path where the game is saved
     */
    @Expose
    private final String filepath;

    /**
     Creates a new instance of GameModel class using the specified unique identifier and the list of players.
     The method initializes all the class fields and generates the game objects (board, players, common goals)
     based on the JSON configuration files. It also sets the initial state of the game to STARTING and
     the current player to the first player in the list.
     @param uuid the unique identifier of the game
     @param players the list of names of players to be added to the game
     @throws FileNotFoundException if the configuration files are not found or updateStatus() fail
     */
    public GameModel(UUID uuid,  List<String> players) throws IOException {
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
        this.currentPlayer = this.players.get(0);

        //creating 2 commonGoal
        generateCommonGoal("src/main/resources/commonGoal.json");

        // updating instantly the state
        this.filepath = buildPath();
        updateStatus();
        this.phase = GamePhase.ONGOING;
    }

    /**
     This class represents a game model, which contains information about a game, including the game's UUID,
     current phase, number of players, first player, current player, list of players, common goals, leaderboard,
     bag, board, chat room, and file path.
     It's needed when GameController needs to reload a game that has already started and never ended
     @param uuid the unique identifier of the game
     @param phase the current phase of the game
     @param nPlayers the number of players in the game
     @param firstPlayer the first player in the game
     @param currentPlayer the current player in the game
     @param players the list of players in the game
     @param commonGoals the list of common goals in the game
     @param leaderboard the leaderboard of the game
     @param bag the bag of tiles in the game
     @param board the board of the game
     @param chatRoom the chat room of the game
     @param filepath the file path of the game
     */
    public GameModel(UUID uuid, GamePhase phase, int nPlayers, String firstPlayer, Player currentPlayer, List<Player> players, List<CommonGoal> commonGoals, List<Rank> leaderboard, Bag bag, Board board, ChatRoom chatRoom, String filepath) {
        this.uuid = uuid;
        this.phase = phase;
        this.nPlayers = nPlayers;
        this.firstPlayer = firstPlayer;
        this.currentPlayer = currentPlayer;
        this.players = players;
        this.commonGoals = commonGoals;
        this.leaderboard = leaderboard;
        this.bag = bag;
        this.board = board;
        this.chatRoom = chatRoom;
        this.filepath = filepath;
    }

    /**
     Builds a unique pathname for the game file by concatenating the IDs of all the players and the game UUID.
     @return a string representing the unique pathname for the game file
     */
    private String buildPath(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("src/main/resources/game/");
        for(Player tmp : this.players)
            stringBuilder.append(tmp.getID()).append("_");
        stringBuilder.append(LocalDate.now()).append(".json");
        return stringBuilder.toString();
    }

    /**
     Updates the status of the current GameModel object by converting it to JSON format
     and writing it to a file specified by the filepath attribute.
     @throws IOException if there is an error writing to the file.
     */
    public void updateStatus() throws IOException {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(this);
        FileWriter writer = new FileWriter(this.filepath);
        writer.write(json);
        writer.close();
        System.out.println("Successfully update the json");
    }

    /**
     This method reads a JSON file containing the information for a board and returns the JSON object
     corresponding to the board for the specified number of players.
     @param filepath the path to the JSON file containing the board information
     @return the JSON object representing the board for the specified number of players
     @throws FileNotFoundException if the specified file is not found
     */
    private JsonObject decoBoard(String filepath) throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader;
        reader = new JsonReader(new FileReader(filepath));
        JsonObject json = gson.fromJson(reader, JsonObject.class);
        return json.getAsJsonObject(Integer.toString(this.nPlayers));
    }

    /**
     Reads a JSON file containing an array of PersonalGoal objects and returns a JsonArray
     representing the array.
     @param filepath the path of the JSON file to read
     @return a JsonArray containing the PersonalGoal objects read from the file
     @throws FileNotFoundException if the file at the given filepath cannot be found
     */
    private JsonArray decoPersonal(String filepath) throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader;
        reader = new JsonReader(new FileReader(filepath));
        return gson.fromJson(reader, JsonArray.class);
    }

    /**
     This method generates two common goals for the game by reading a json file from the specified filepath.
     The method gets the common goals and the corresponding scoring tokens from the json file, and selects a random common goal to assign to each of the two goals.
     The selected common goals are then added to the game's list of common goals.
     @param filepath the filepath of the json file containing the common goals and scoring tokens
     @throws FileNotFoundException if the specified filepath is not found
     */
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

    /**
     Returns a list of integers (scoringTokens) given a JsonArray.
     The integers are added to the list in reverse order because they are saved in ascending
     order but are assigned in descending order .
     @param array the JsonArray to be converted to a list of integers.
     @return a list of integers in reverse order from the JsonArray.
     */
    private List<Integer> getAsList(JsonArray array){
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i <=array.size(); i++)
            list.add(array.get(array.size()-i).getAsInt());
        return list;
    }

    /**
     This method allows to retrieve the tiles at the given coordinates on the board.
     It first validates that the move is legal by calling the convalidateMove method of the board object.
     If the move is valid, it returns the tiles at the given coordinates using the getTiles method of the board object.
     @param coordinates a list of Coordinates objects representing the tiles to retrieve
     @return a list of Tile objects representing the tiles at the given coordinates
     @throws BoardException if the move is not valid according to the rules of the game
     */
    public List<Tile> selectedTiles(List<Coordinates> coordinates) throws BoardException {
        this.board.convalidateMove(coordinates);
        return this.board.getTiles(coordinates);
    }

    /**
     This method is used to insert tiles on the player's personal shelf.
     @param sort a list of integers representing the order in which the tiles should be inserted
     @param tiles a list of tiles that the player wants to insert
     @param column an integer representing the column of the personal shelf where the tiles should be inserted
     @throws PlayerException if the player doesn't have enough space in their personal shelf
     @throws NonConformingInputParametersException if the player didn't insert the correct parameters
     */
    // TODO: 26/04/2023
    public void insertTiles(List<Integer> sort, List<Tile> tiles, int column) throws PlayerException {
        if(sort.size()!=tiles.size()) throw new NonConformingInputParametersException();
        for (Integer integer : sort) 
            tiles.add(tiles.get(integer - 1));
        tiles.subList(0, sort.size()).clear();
        this.currentPlayer.getMyShelf().insert(column-1, tiles);
    }

    /**
     Writes a message in the chat room.
     @param user the player who sent the message
     @param text the body of the message
     */
    public void writeChat(String user, String text) throws ChatException {
        if(text.equals(""))
            throw new ChatException();
        this.chatRoom.addMessage(new ChatMessage(user, text));
    }

    /**
     Returns the player with the specified ID.
     @param id the ID of the player
     @return the player with the specified ID
     @throws PlayerNotFoundException if the player with the specified ID is not found
     */
    public Player getPlayer(String id) throws PlayerNotFoundException{
        for(Player tmp : this.players)
            if(tmp.equals(id)) return tmp;
        throw new PlayerNotFoundException(id);
    }


    /**
     Sets the current player.
     @param currentPlayer the current player
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     Sets the leaderboard.
     @param rank the leaderboard
     */
    public void setLeaderboard(List<Rank> rank){
        this.leaderboard = rank;
    }

    /**
     Sets the game phase.
     @param phase the game phase
     */
    public void setPhase(GamePhase phase){
        this.phase = phase;
    }


    /**
     Returns the UUID of the game.
     @return the UUID of the game
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     Returns the game phase.
     @return the game phase
     */
    public GamePhase getPhase(){
        return this.phase;
    }

    /**
     Returns the number of players.
     @return the number of players
     */
    public int getNPlayers() {
        return this.nPlayers;
    }

    /**
     Returns the first player.
     @return the first player
     */
    public String getFirstPlayer() {
        return this.firstPlayer;
    }

    /**
     Returns the current player.
     @return the current player
     */
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     Returns the list of players.
     @return the list of players
     */
    public List<Player> getPlayers() {
        return this.players;
    }

    /**
     Returns the list of common goals.
     @return the list of common goals
     */
    public List<CommonGoal> getCommonGoals() {
        return this.commonGoals;
    }

    /**
     Returns the bag of tile.
     @return the bag of tile
     */
    public Bag getBag() {
        return this.bag;
    }

    /**
     Returns the board.
     @return the board
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     Returns the chat room.
     @return the chat room
     */
    public ChatRoom getChatRoom() {
        return this.chatRoom;
    }

    /**
     Returns the leaderboard.
     @return the leaderboard
     */
    public List<Rank> getLeaderboard() {
        return leaderboard;
    }
}