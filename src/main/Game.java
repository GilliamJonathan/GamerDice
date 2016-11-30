package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class Game implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	private String directory;
	private int multiplier;
	private int sucks;
	// Default Constructor
	public Game(){
		name = "";
		directory = "";
		multiplier = -1;
	}
	// Regular Constructor
	public Game(String n, String d){
		name = fixExtention(n);
		directory = d;
		multiplier = 1;
	}
	// UNIMPLEMENTED - Constructor that adds a multiplier value
	public Game(String n, String d, int m){
		name = fixExtention(n);
		directory = d;
		multiplier = m;
	}
	
	// Returns the game's name
	public String getName(){
		return name;
	}
	// Returns multiplier value
	public int getMultiplier(){
		return multiplier;
	}
	// Returns the game's directory
	public String getDirectory(){
		return directory;
	}
	
	// Increases Game multiplier, max of 50
	public void increase(){
		if (multiplier <= 50)
			multiplier++;
	}
	// Decreases Game multiplier, if called 5 times after multiplier is 1, sets multiplier to 0
	public void decrease(){
		if (sucks == 5)
			multiplier = 0;
		else if (multiplier == 1)
			sucks++;
		else if (multiplier <= 0)
			return;
		else 
			multiplier--;
	}
	
	// UNIMPLEMENTED - Sets game name
	public void setName(String n){
		name = fixExtention(n);
	}
	// UNIMPLEMENTED - Sets game directory
	public void setDirectory(String d){
		directory = d;
	}
	// UNIMPLEMENTED - Sets multiplier value
	public void setMultiplier(int m){
		multiplier = m;
	}
	
	// Removes things that will cause errors -- like your face
	public String fixExtention(String s){
		return s.replaceAll(":", "").replaceAll(".exe", "");
	}
	
	// Gets the image from the files path
	public Image getImage() throws FileNotFoundException{
		try{
		File file;
		if(this.getDirectory().contains("Steam.exe"))
		{
			int i = this.getDirectory().indexOf("Steam.exe")+9;
			file = new File(this.getDirectory().substring(0, i));
		}
			
		else
		{
			file = new File(this.getDirectory());
		}
		sun.awt.shell.ShellFolder sf = sun.awt.shell.ShellFolder.getShellFolder(file);
		BufferedImage bi = new BufferedImage(sf.getIcon(true).getWidth(null), sf.getIcon(true).getHeight(null), BufferedImage.TYPE_INT_ARGB);
		//draws graphics
		Graphics2D g2d = bi.createGraphics();
		g2d.drawImage(sf.getIcon(true), 0, 0, null);
		g2d.dispose();
		return SwingFXUtils.toFXImage(bi,null);
		}
		catch(IOException e){
			e.printStackTrace();
			return null;
		}
		
		}
	
	// toString ... if you don't know what this is, please stop looking at my code
	public String toString(){
		return multiplier + " " + name + " : " + directory;
	}
}

