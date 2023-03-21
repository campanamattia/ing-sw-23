package Model;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/*
 *  capacity: è unico, il numero massimo di tessere che contiene la bag ( 132 a inizio partita )
 *  size: dimensione istantanea
 */

public class Bag {

    public final int CAPACITY = 132;
    private ArrayBlockingQueue<Tile> bag;
    private int size;


    public Bag() {
        this.bag = new ArrayBlockingQueue<Tile>(CAPACITY);
        this.size = CAPACITY;
        for(int i=0;i<CAPACITY;i++){
            if(i<22) {
                Tile tile = new Tile(Color.GREEN);
                bag.add(tile);
            }
            if(i>=22 && i<44){
                Tile tile = new Tile(Color.WHITE);
                bag.add(tile);
            }
            if(i>=44 && i<66){
                Tile tile = new Tile(Color.YELLOW);
                bag.add(tile);
            }
            if(i>=66 && i<88){
                Tile tile = new Tile(Color.BLUE);
                bag.add(tile);
            }
            if(i>=88 && i<110){
                Tile tile = new Tile(Color.AZURE);
                bag.add(tile);
            }
            if(i>=110){
                Tile tile = new Tile(Color.PURPLE);
                bag.add(tile);
            }
        }
        // shuffle
        Tile[] array = new Tile[bag.size()];
        bag.toArray(array);
        shuffleArray(array);
        bag.clear();
        Collections.addAll(bag, array);
    }

    public boolean isEmpty() {
        return size == 0;
    }
    public int getSize() {
        return size;
    }

    public ArrayList<Tile> draw(int n){
        ArrayList<Tile> extraction = new ArrayList<Tile>();
        for(int i=0;i<n;i++){
            if(!bag.isEmpty()) {
                // metodo poll(): recupero ed elimino il primo elemento di bag e lo metto in extracion
                extraction.add(bag.poll());
                this.size --;
            }else{
                /* empty exception */
                return null; //da sostituire con eccezione
            }
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
