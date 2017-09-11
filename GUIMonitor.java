
package Assignment4;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.*;


/**
 * The GUI for assignment 4
 */
public class GUIMonitor extends Component implements ActionListener{
	/**
	 * These are the components you need to handle.
	 * You have to add listeners and/or code
	 */
	private JFrame frame;				// The Main window
	private JMenu fileMenu;					// The menu
	private JMenuItem openItem;			// File - open
	private JMenuItem saveItem;			// File - save as
	private JMenuItem exitItem;			// File - exit
	private JTextField txtFind;			// Input string to find
	private JTextField txtReplace; 		// Input string to replace
	private JCheckBox chkNotify;		// User notification choise
	private JLabel lblInfo;				// Hidden after file selected
	private JButton btnCreate;			// Start copying
	private JButton btnClear;			// Removes dest. file and removes marks
	private JLabel lblChanges;			// Label telling number of replacements
	private Controller controller;
	protected JFileChooser fileChooser;
	private JTextArea txtPaneDestination;
	private JTextArea txtPaneSource;
	private int index = 0;
	Highlighter.HighlightPainter myHighlightPainter = new MyHighlightPainter(Color.red);

	/**
	 * Constructor
	 */
	public GUIMonitor()
	{
	}

	/**
	 * Starts the application
	 */
	public void Start(Controller controller)
	{
		this.controller = controller;
		frame = new JFrame();
		frame.setBounds(0, 0, 714,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setTitle("Text File Copier - with Find and Replace");
		InitializeGUI();					// Fill in components
		frame.setVisible(true);
		frame.setResizable(false);			// Prevent user from change size
		frame.setLocationRelativeTo(null);	// Start middle screen
	}

	/**
	 * Sets up the GUI with components
	 */
	private void InitializeGUI()
	{
		fileChooser = new JFileChooser();

		fileMenu = new JMenu("File");
		openItem = new JMenuItem("Open Source File");
		openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		saveItem = new JMenuItem("Save Destination File As");
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		saveItem.setEnabled(false);
		exitItem = new JMenuItem("Exit");
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		JMenuBar  bar = new JMenuBar();
		frame.setJMenuBar(bar);
		bar.add(fileMenu);

		JPanel pnlFind = new JPanel();
		pnlFind.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Find and Replace"));
		pnlFind.setBounds(12, 32, 436, 122);
		pnlFind.setLayout(null);
		frame.add(pnlFind);
		JLabel lab1 = new JLabel("Find:");
		lab1.setBounds(7, 30, 80, 13);
		pnlFind.add(lab1);
		JLabel lab2 = new JLabel("Replace with:");
		lab2.setBounds(7, 63, 80, 13);
		pnlFind.add(lab2);

		txtFind = new JTextField();
		txtFind.setBounds(88, 23, 327, 20);
		pnlFind.add(txtFind);
		txtReplace = new JTextField();
		txtReplace.setBounds(88, 60, 327, 20);
		pnlFind.add(txtReplace);
		chkNotify = new JCheckBox("Notify user on every match");
		chkNotify.setBounds(88, 87, 180, 17);
		pnlFind.add(chkNotify);

		lblInfo = new JLabel("Select Source File..");
		lblInfo.setBounds(485, 42, 120, 13);
		frame.add(lblInfo);

		btnCreate = new JButton("Copy to Destination");
		btnCreate.setBounds(465, 119, 230, 23);
		frame.add(btnCreate);
		btnClear = new JButton("Clear dest. and remove marks");
		btnClear.setBounds(465, 151, 230, 23);
		frame.add(btnClear);

		lblChanges = new JLabel("No. of Replacements:");
		lblChanges.setBounds(279, 161, 200, 13);
		frame.add(lblChanges);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(12, 170, 653, 359);
		frame.add(tabbedPane);
		txtPaneSource = new JTextArea();
		JScrollPane scrollSource = new JScrollPane(txtPaneSource);
		tabbedPane.addTab("Source", null, scrollSource, null);

		txtPaneDestination = new JTextArea();
		JScrollPane scrollDest = new JScrollPane(txtPaneDestination);
		tabbedPane.addTab("Destination", null, scrollDest, null);

		btnCreate.addActionListener(this);
		btnClear.addActionListener(this);
		openItem.addActionListener(this);
		chkNotify.addActionListener(this);

	}

	/**
	 * Method that returns the founded text.
	 * @return
     */
	public String getText(){
		return txtFind.getText();
	}

	/**
	 * Method that returns the replaced text.
	 * @return
     */
	public String getReplace(){
		return txtReplace.getText();
	}

	/**
	 * Method that returns the textpane.
	 * @return
     */
	public JTextArea getTxtPane(){
		return txtPaneSource;
	}

	/**
	 * ActionEvent method that listens for users
	 * choice.
	 * @param e
     */
	public void actionPerformed(ActionEvent e){

		if (e.getSource() == openItem){
			controller.openPickedFile();
		}
		if(e.getSource() == btnCreate){
			if(txtFind.getText().isEmpty() || txtReplace.getText().isEmpty()){
				JOptionPane.showMessageDialog(null, "You have to fill in both fields");
			}
			else if(chkNotify.isSelected()){
				int answer = JOptionPane.showConfirmDialog(null, "Replace " + txtFind.getText() + " with " + txtReplace.getText(), " Confirm " , JOptionPane.YES_OPTION);
				if(answer == JOptionPane.YES_OPTION);
				controllHighlight();
			} else {
				controllHighlight();
			}
		}
		if(e.getSource() == btnClear){
			txtPaneSource.setText("");
			txtFind.setText("");
			txtReplace.setText("");
			index = 0;
		}

	}

	/**
	 * Method that appends the destination
	 * text.
	 */
	public void appeandDestination() {
		txtPaneDestination.setText("");
		for (String s : controller.getReader()) {
			txtPaneDestination.append(s);
		}
	}

	/**
	 * Method that controlls the highlight.
	 */
	private void controllHighlight() {
		if (index == 0) {
			controller.start1();
		}
		if (index > 0) {
			controller.start2();
		}
		index++;
		highLight(txtPaneSource, txtFind.getText());
	}

	/**
	 * Method that sets the source text.
	 * @param txtPaneSource
     */
	public void setTxtPaneSource(String txtPaneSource) {
		this.txtPaneSource.append(txtPaneSource);
	}

	/**
	 * Method for find and highlighting text.
	 * @param txtPaneSource
	 * @param pattern
     */
	public void highLight(JTextArea txtPaneSource, String pattern) {

		try {
			Highlighter light = txtPaneSource.getHighlighter();
			Document doc = txtPaneSource.getDocument();
			String t = doc.getText(0, doc.getLength());
			int pos = 0;

			while ((pos = t.toUpperCase().indexOf(pattern.toUpperCase(), pos)) >= 0) {
				light.addHighlight(pos, pos + pattern.length(), myHighlightPainter);
				pos += pattern.length();

			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * A private class that highlights the text that was
	 * found.
	 */
		private class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter {

			public MyHighlightPainter(Color color) {
				super(color.GREEN);
			}
		}

}
