package Assignment4;


import java.util.LinkedList;
import java.util.*;

/**
 * A class that works like a reader.
 * The reader knows the total strings
 * to read.
 */
public class Reader extends Thread{
    /**
     * Declare instance variables.
     */
    private Buffer buffer;
    private int count;
    private List<String> list = new LinkedList<>();

    /**
     * Constructor that takes a buffer and an int
     * as arguments.
     * @param buffer
     * @param nbrStrings
     */
    public Reader(Buffer buffer, int nbrStrings){
        this.buffer = buffer;
        this.count = nbrStrings;
    }

    /**
     * The run method that runs a loop
     * and calls the reader part of the
     * buffer to read a line.
     */
    public void run() {
        while(list.size() < count){
            try{
                list.add(buffer.readData());
                buffer.showDestText();
            } catch (Exception e ){
                e.printStackTrace();
            }

        }
    }

    /**
     * Method that returns the list.
     * @return
     */
    public List<String> getList(){
        return list;
    }
}
