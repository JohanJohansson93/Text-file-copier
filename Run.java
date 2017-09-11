
package Assignment4;


public class Run {
	public static void main(String[] args)
	{
		GUIMonitor test = new GUIMonitor();
		Controller controller = new Controller(test);
		test.Start(controller);
	}
}
