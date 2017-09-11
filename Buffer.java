package Assignment4;

/**
 * A class that works like a
 * boundedbuffer.
 */
public class Buffer {
    /**
     * Declare instance variables.
     */
    private String[] strArr;
    private BufferStatus[] status;
    private int writePos;
    private int readPos;
    private int findPos;
    protected int maxElem = 10;
    private String findText;
    private String replaceText;
    protected Controller controller;

    /**
     * Constructor that takes controller and
     * two strings as arguments.
     * @param controller
     * @param find
     * @param replace
     */
    public Buffer(Controller controller, String find, String replace){
        this.findText = find;
        this.replaceText = replace;
        this.controller = controller;
        strArr = new String[maxElem];
        status = new BufferStatus[maxElem];

        for (int i = 0; i < status.length; i++) {
            status[i] = BufferStatus.Empty;
        }

    }

    /**
     * Method that modify the buffer
     * data.
     */
    public void modifyData() {
        synchronized(this){
         while (status[findPos] !=BufferStatus.New){
             try {
                 wait();
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }
            if(strArr[findPos].contains(findText)){
                String text = strArr[findPos].replaceAll(findText, replaceText);
                strArr[findPos] = text;
            }
            status[findPos] = BufferStatus.Checked;
            findPos = (findPos + 1) % maxElem;
            notifyAll();
        }
    }

    /**
     * Method that reads the buffer data.
     * @return
     */
    public String readData(){
        synchronized (this){
            while (status[readPos] != BufferStatus.Checked) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String s = strArr[readPos];
            status[readPos] = BufferStatus.Empty;
            readPos = (readPos + 1) % maxElem;
            notifyAll();
            return s;
        }
    }

    /**
     * Method that writes to the buffer data.
     * @param txtWrite
     */
    public void writeData(String txtWrite){
        synchronized (this){
            while (status[writePos] != BufferStatus.Empty) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            strArr[writePos] = txtWrite;
            status[writePos] = BufferStatus.New;
            writePos = (writePos + 1) % maxElem;

            notifyAll();
        }
    }

    /**
     * Method that calls controller to
     * show the destination text.
     */
    public void showDestText(){
        controller.destination();
    }

}
