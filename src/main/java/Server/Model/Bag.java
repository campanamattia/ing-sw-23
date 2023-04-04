package Server.Model;

import Enumeration.Color;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class Bag {

    public final int CAPACITY = 132;
    private ArrayBlockingQueue<Tile> bag;

    public Bag() {
        Tile[] array = new Tile[CAPACITY];
        Tile tile;
        int offset=0;
        for(Color tmp : Color.values()){
            for(int i=0;i<22;i++){
                tile = new Tile(tmp);
                array[i+offset] = tile;

            }
            offset+=22;
        }
        // shuffle
        shuffleArray(array);
        this.bag = new ArrayBlockingQueue<Tile>(CAPACITY);
        Collections.addAll(bag, array);
    }

    public int getLastTiles() {
        return bag.size();
    }

    public ArrayList<Tile> draw(int n){
        ArrayList<Tile> extraction = new ArrayList<Tile>();
        for(int i=0;i<n;i++){
            extraction.add(bag.poll());

        }
        return extraction;
    }

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
    @Override
    public String toString() {
        return super.toString();
    }
}
