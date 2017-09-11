package Assignment4;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * A class that works like a controller.
 * It has contact with all the other
 * classes.
 */
public class Controller extends Component {
    /**
     * Declare instance variables.
     */
    private GUIMonitor gui;
    private List <String> arr = new LinkedList<>();
    private Buffer buffer;
    private Writer writer;
    private Reader reader;
    private Modifier modifier;
    protected String text;

    /**
     * Contstructor that takes a reference
     * to GUI.
     * @param gui
     */
    public Controller(GUIMonitor gui){
        this.gui = gui;
    }

    /**
     * Method that opens a file.
     */
    public void openPickedFile(){
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("txt", "Txt");
        fileChooser.setFileFilter(filter);

        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            file.getAbsolutePath();
            text = file.toString();
            pickFile();
        }

    }

    /**
     * Method that scanns the file path
     * to GUI.
     */
    public void pickFile() {
        String temp;
        Scanner scanner = null;

        try {
            scanner = new Scanner(new File(text)).useDelimiter(";" + "\n");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scanner.hasNext()) {
            temp = scanner.next();
            arr.add(temp);
            gui.setTxtPaneSource(temp + "\n");
        }
    }
/**
 * Method that starts all the threads.
 */
    public void startThreads(){
        writer.start();
        modifier.start();
        reader.start();
    }

    /**
     * Method that creates a new object for
     * buffer, writer, reader and modifier.
     */
    public void start1(){
        buffer = new Buffer(this, gui.getText(), gui.getReplace());
        writer = new Writer(buffer, arr);
        reader = new Reader(buffer, arr.size());
        modifier = new Modifier(buffer, arr);
        startThreads();
    }
    /**
     * Method that creates a new object for
     * buffer, writer, reader and modifier.
     */
    public void start2(){
        arr = reader.getList();
        buffer = new Buffer(this, gui.getText(), gui.getReplace());
        writer = new Writer(buffer, arr);
        reader = new Reader(buffer, arr.size());
        modifier = new Modifier(buffer, arr);
        startThreads();
    }

    /**
     * Method that returns the string
     * list
     * @return
     */
    public List<String> getReader(){
        return reader.getList();
    }

    /**
     * Method that appends the
     * text-pane destination
     * in GUI.
     */
    public void destination(){
        gui.appeandDestination();
    }
}
