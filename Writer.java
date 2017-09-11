package Assignment4;

import java.util.*;

/**
 * A class that works like a writer.
 * It fills the list with strings from the
 * text.
 */
public class Writer extends Thread{

    /**
     * Declares instance variables.
     */
    private Buffer buffer;
    private List<String>textWrite = new LinkedList<>();

    /**
     * Constructor that takes a buffer and a list as
     * arguments.
     * @param buffer
     * @param inText
     */
    public Writer(Buffer buffer, List<String> inText){
        this.buffer = buffer;
        this.textWrite = inText;
    }

    /**
     * The run method that loops through
     * the strings and calls the write
     * method of buffer.
     */
    public void run(){
        for(String text : textWrite){
            try{
                buffer.writeData(text);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
