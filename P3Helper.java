import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class P3Helper{

	public static int getSize(File file){
		int size = 0;
		
		if (file.isDirectory()){
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++){
				size += getSize(files[i]);
			}
		}
		
		else {
			size += file.length();
		}
		
		return size;
	}

	public static void saveLibrary(ArrayList<String> arraylist){
		long startTime = System.nanoTime();
		try {
			System.out.println("Writing to external file...this may take some time");
			File file = new File("\\C:\\Users\\Jonabel\\Documents\\CS112\\project3\\output.txt");
			
			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file.getAbsoluteFile())));
			for (int i = 0; i < arraylist.size(); i++){
				out.write(arraylist.get(i) + "\n");
			}
			out.close();
			long estimatedTime = (System.nanoTime() - startTime);
			double Time = estimatedTime / 1000000000.0;
			System.out.println("Done in " + Time + " seconds");
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}

	//http://stackoverflow.com/questions/3571223/how-do-i-get-the-file-extension-of-a-file-in-java
	//public static Boolean isJPG(File file);
	public static void loadLibrary(ArrayList<String> arraylist, File file){
		long startTime = System.nanoTime();
		if (file.exists()) {
			
			arraylist.clear();
			Scanner scanner = null;
			try {
				scanner = new Scanner(file);
				//arraylist.add(scanner.nextLine());
				while (scanner.hasNextLine()){
					arraylist.add(scanner.nextLine());
				}
				System.out.println("New arraylist: " + arraylist);
				scanner.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		else{
			JOptionPane.showMessageDialog(null, "Error: Cannot find file " + file);
		}
	}
	
	public static void loadPhotos(ArrayList<String> arraylist, File file){
		if(file.isDirectory()){
			/*JOptionPane.showMessageDialog(null, */System.out.println("Found directory");
			File[] files = file.listFiles();
			System.out.println("listFiles() = " + files);
			for (int j = 0; j < files.length; j++){
				loadPhotos(arraylist, files[j]);
			}
		}
		else {	//  Base case
			System.out.println("File was not a directory");
			if(file.getAbsolutePath().toLowerCase().endsWith("jpg")){
				System.out.println("Found JPG, adding " + file.getAbsolutePath());
				arraylist.add(file.getAbsolutePath());
			}
			else{
				/*JOptionPane.showMessageDialog(null, */System.out.println("Found non-JPG");
			}
		}
	}
	
}