package main;

import java.io.Serializable;
import java.util.ArrayList;


interface Directory extends Serializable{
	
	boolean isEnabled();
	ArrayList<Game> getGames();
	String getDirPath();
	
	void addGame(Game g);
	void addGames(ArrayList<Game> g);
	boolean containsGame(Game g);
	void toggle();
	void removeGame(Game g);
	void update();
	
}