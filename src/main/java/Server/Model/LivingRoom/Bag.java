package Server.Model.LivingRoom;

import Enumeration.Color;
import Utils.Tile;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Class bag, it's the container of all tiles playable during a game.
 */
public class Bag {
    /**
     * The capacity of the bag.
     */
    public final int CAPACITY = 132;
    /**
     * The bag of tiles.
     */
    private final ArrayBlockingQueue<Tile> bag;
    /**
     * The number of tiles for each color.
     */
    private static final int  nTilesForColor = 22;

    /**
     * Constructor of the class.
     */
    public Bag() {
        Tile[] array = new Tile[CAPACITY];
        int offset=0;
        // generate tiles, 22 for color
        generateTile(array,offset);
        // shuffle
        shuffleArray(array);
        this.bag = new ArrayBlockingQueue<>(CAPACITY);
        Collections.addAll(bag, array);
    }

    /**
     * Standard getter method.
     * @return number of tiles remained.
     */
    public int getLastTiles() {
        return bag.size();
    }

    /**
     * Draw n tiles from the bag.
     * @param n number of tiles to be drawn.
     * @return list of tiles taken from the bag.
     */
    public ArrayList<Tile> draw(int n){
        ArrayList<Tile> extraction = new ArrayList<>();
        for(int i=0;i<n;i++){
            extraction.add(bag.poll());

        }
        return extraction;
    }

    /**
     * Method to shuffle the tiles  in the bag after the creation.
     * @param array bag transformed in array to shuffle.
     */
    public static void shuffleArray(Tile[] array){
        int index;
        Tile tmp;
        Random random = new Random();
        for(int i = array.length-1;i>0;i--){
            index = random.nextInt(i+1);
            tmp = array[index];
            array[index] = array[i];
            array[i] = tmp;
        }
    }

    private void generateTile(Tile[] array,int offset){
        Tile tile;
        for(Color tmp : Color.values()){
            for(int i=0;i<nTilesForColor;i++){
                tile = new Tile(tmp);
                array[i+offset] = tile;
            }
            offset+=22;
        }
    }

    /**
     * Standard getter method.
     * @return the bag of tiles.
     */
    public ArrayBlockingQueue<Tile> getBag() {
        return bag;
    }
}
