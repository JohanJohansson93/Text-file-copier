package Assignment4;

import java.util.List;

/**
 * A class that works almost like
 * the reader class.
 * It calls modifyData method in the
 * buffer.
 */
public class Modifier extends Thread{
    /**
     * Declare instance variables.
     */
    private Buffer buffer;
    private int count;

    /**
     * Constructor that takes a buffer and a list
     * as arguments.
     * @param buffer
     * @param text
     */
    public Modifier(Buffer buffer, List<String> text){
        this.buffer = buffer;
        this.count = text.size();
    }


    /**
     * The run method that loops
     * and calls the modifyData
     * method in buffer.
     */
    public void run(){

        for (int i=0; i < count; i++){

            try{
                buffer.modifyData();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
