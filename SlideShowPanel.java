// Import the basic graphics classes.
import java.awt.*;

import javax.imageio.ImageIO;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.util.ArrayList;
import java.util.Scanner;

public class SlideShowPanel extends JPanel {

	private JButton prevButton, 
	nextButton, 
	exitButton, 
	loadPhotosButton, 
	saveButton, 
	openButton;
	
	static BufferedImage image = null;
	private static boolean loadPB = false;
	public int index = 0;
	public static ArrayList<String> fileNames = new ArrayList<String>();

	public static String dir = "Pictures";
	File file = new File(dir);
	private static int counter = 0;
	
	//=================================================================================//
	// Constructor for the panel 
	SlideShowPanel() {
		
		try {
			image = ImageIO.read(new File("\\C:\\Users\\Jonabel\\Documents\\CS112\\project3\\test.jpg"));
		} catch (IOException e) {
		}

		//========================GUI=======================================//
		
		this.setLayout(new BorderLayout());

		// buttonPanelTop contains the top row of buttons:
		// load photos files, save and open
		JPanel buttonPanelTop = new JPanel();
		buttonPanelTop.setLayout(new GridLayout(1, 3));
		loadPhotosButton = new JButton("Load photos");
		saveButton = new JButton("Save Library");
		openButton = new JButton("Load Library");
		
		loadPhotosButton.setToolTipText("Import photo library directly from " + dir);
		saveButton.setToolTipText("Write to a file called output.txt");
		openButton.setToolTipText("Read in a file called output.txt");
		

		loadPhotosButton.addActionListener(new MyButtonListener());
		saveButton.addActionListener(new MyButtonListener());
		openButton.addActionListener(new MyButtonListener());
		
		//buttonPanelTop.add(new JSeparator(SwingConstants.VERTICAL));
		buttonPanelTop.add(loadPhotosButton);
		buttonPanelTop.add(saveButton);
		buttonPanelTop.add(openButton);
		//buttonPanelTop.add(new JSeparator(SwingConstants.VERTICAL));
		buttonPanelTop.setBackground(Color.blue);
		this.add(buttonPanelTop, BorderLayout.NORTH);

		// Bottom panel with panels: Previous, Next, Exit buttons
		JPanel buttonPanelBottom = new JPanel();
		buttonPanelBottom.setLayout(new GridLayout(1, 3));
		prevButton = new JButton("Previous");
		nextButton = new JButton("Next");
		exitButton = new JButton("Exit");
		
		prevButton.setToolTipText("Load previous");
		nextButton.setToolTipText("Load next");
		exitButton.setToolTipText("Exit program");

		prevButton.addActionListener(new MyButtonListener());
		nextButton.addActionListener(new MyButtonListener());
		exitButton.addActionListener(new MyButtonListener());
		
		//buttonPanelBottom.add(new JSeparator(SwingConstants.VERTICAL));
		buttonPanelBottom.add(prevButton);
		buttonPanelBottom.add(nextButton);
		buttonPanelBottom.add(exitButton);
		//buttonPanelBottom.add(new JSeparator(SwingConstants.VERTICAL));
		buttonPanelBottom.setBackground(Color.blue);
		this.add(buttonPanelBottom, BorderLayout.SOUTH);

	}
	
	//=================================================================================//
	
	public void paintComponent(Graphics g) {

		// Draw our Image object.
		g.drawImage(image, 0, 0, 800, 580, this); // at location 50,10
		// 200 wide and high
		updateUI();
	}
	
	//=================================================================================//
	
	class MyButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// The function that does something whenever each
			// button is pressed
			if (e.getSource() == loadPhotosButton) {
				// FILL IN CODE - make the calls to other methods/classes, do
				// NOT place all the code here

				// 1) read all the photos files from photos directory using recursion
				// 2) Display the first image in the arraylist  

				//class variable loadPB 0->loaded=false, 1->loaded=true
				loadPB = true;
				long startTime = System.nanoTime();
				System.out.println("Importing library...this may take some time");
				//System.out.println("MARKER1 " + fileNames);
				P3Helper.loadPhotos(fileNames, file);
				//System.out.println("MARKER2 " + fileNames);
				try {
					counter = 0;
					image = ImageIO.read(new File(fileNames.get(counter)));
				} catch (IOException e2) {
				}
				
				long estimatedTime = (System.nanoTime() - startTime);
				double Time = estimatedTime / 1000000000.0;
				System.out.println("Done in " + Time + " seconds");
				
				updateUI();

			} else if (e.getSource() == saveButton) {
				// FILL IN CODE : make the calls to other methods/classes, do
				// NOT place all the code here
				// Save all the names in the arraylist to a file called output.txt
				if (loadPB){
					P3Helper.saveLibrary(fileNames);
				}
				else{
					JOptionPane.showMessageDialog(null, "No imported photos detected");
				}
				
			} else if (e.getSource() == openButton) {
				// FILL IN CODE: make the calls to other methods/classes, do NOT
				// place all the code here
				// open the file output.txt and load photo file paths 
				// into the arrayList of strings
				counter = 0;
				long startTime = System.nanoTime();
				System.out.println("Importing library...this may take some time");
				P3Helper.loadLibrary(fileNames, new File("output.txt"));
				try {
					counter = 0;
					image = ImageIO.read(new File(fileNames.get(counter)));
				} catch (IOException e2) {
				}
				long estimatedTime = (System.nanoTime() - startTime);
				double Time = estimatedTime / 1000000000.0;
				System.out.println("Done in " + Time + " seconds");
				updateUI();

			} else if (e.getSource() == prevButton) {
				if (loadPB){
					if (counter != 0){
						try {
							counter--;
							image = ImageIO.read(new File(fileNames.get(counter)));
						} catch (IOException e2) {
						}
					}
					else{
						JOptionPane.showMessageDialog(null, "No more previous photos");
					}
				}
				else{
					//http://www.java2s.com/Code/Java/Swing-JFC/DisplayerrormessagedialogwithJOptionPaneERRORMESSAGE.htm
					JOptionPane.showMessageDialog(null, "Press the load photos button first", "Error", JOptionPane.ERROR_MESSAGE);
				}
				updateUI();

			} else if (e.getSource() == nextButton) {

				if (loadPB){
					if (counter != fileNames.size() - 1){
						try {
							counter++;
							image = ImageIO.read(new File(fileNames.get(counter)));
						} catch (IOException e2) {
						}
					}
					else{
						JOptionPane.showMessageDialog(null, "\nReached end of library");
						try {
							counter = 0;
							image = ImageIO.read(new File(fileNames.get(counter)));
						} catch (IOException e2) {
						}
					}
				}
				else{
					//http://www.java2s.com/Code/Java/Swing-JFC/DisplayerrormessagedialogwithJOptionPaneERRORMESSAGE.htm
					JOptionPane.showMessageDialog(null, "Press the load photos button first", "Error", JOptionPane.ERROR_MESSAGE);
				}
				updateUI();

			} else if (e.getSource() == exitButton) {
				// Exit the program
				System.out.println("Exitting program");
				System.exit(0);

			}
		}
	}
	//=================================================================================//
	public static void main(String[] args) throws InterruptedException {
		
		System.out.println("BEGIN CONSOLE SPAM");
		
		JFrame frame = new JFrame("Slide show program");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SlideShowPanel panel = new SlideShowPanel();
		panel.setPreferredSize(new Dimension(800, 600));
		frame.getContentPane().add(panel);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		/*String[] options = {"OK"};
		JPanel panel2 = new JPanel();
		JLabel lbl = new JLabel("Please be sure to select the 'Load photos' button before browsing ;)");
		panel2.add(lbl);
		JOptionPane.showOptionDialog(null, panel2, " ", JOptionPane.NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options , options[0]);*/
		//http://stackoverflow.com/questions/11204878/joptionpane-showconfirmdialog-with-only-one-button
		Object[] options = {"OK"};
		JOptionPane.showOptionDialog(frame, "Please be sure to select the 'Load photos' button before browsing\n"
				+ "Please ignore/beware the console spam","Warning",
		                   JOptionPane.ERROR_MESSAGE,
		                   JOptionPane.ERROR_MESSAGE,
		                   null,
		                   options,
		                   options[0]);
		
		while(true){
			//This println is a bit cheaty, but it helps the program remember to come back to slideShow() after loading photos
			System.out.println("");
			if(loadPB){
				System.out.println("loadPB is true");
				slideShow();
			}
			else{
				
			}
		}
	}
	
	public static void slideShow() throws InterruptedException{
		//DO NOT SET THIS LOWER THAN 3..DONT CHANGE THIS AT ALL
		//DISREGARD THE ABOVE. IF YOU SET THIS LOWER THAN 3, 
		//MAKE SURE TO WAIT A WHILE BEFORE PRESSING 'Load Photos'
		int seconds = 5;
		System.out.println("Sleeping for " + seconds + "s");
		Thread.sleep(seconds * 1000);
		if (loadPB){
			if (counter != fileNames.size() - 1){
				System.out.println("Attempting to change photo (fileNames[" + counter + "])");
				try {
					image = ImageIO.read(new File(fileNames.get(++counter)));
					//updateUI();
				} catch (IOException e2) {
				}
			}
			else if (counter == fileNames.size() - 1){
				System.out.println("Reached end of library...\nSetting counter to 0");
				try {
					counter = 0;
					image = ImageIO.read(new File(fileNames.get(counter)));
					//updateUI();
				} catch (IOException e2) {
				}
			}
		}
	}
	
}