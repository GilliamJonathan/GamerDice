package main;

import java.util.ArrayList;

public class SteamDirectory implements Directory{
	private static final long serialVersionUID = 1L;
	private ArrayList<Game> games;
	private boolean isEnabled;
	private String path;
	
	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

	@Override
	public ArrayList<Game> getGames() {
		return games;
	}

	@Override
	public String getDirPath() {
		return path;
	}

	@Override
	public void addGame(Game g) {
		if (!containsGame(g))
			games.add(g);
	}

	@Override
	public void toggle() {
		isEnabled ^= true;
	}

	@Override
	public void removeGame(Game g) {
		games.remove(g);
	}

	@Override
	public void update() {
		
	}

	@Override
	public void addGames(ArrayList<Game> g) {
		for(Game game: g)
			addGame(game);
	}

	@Override
	public boolean containsGame(Game g) {
		for (Game game: games)
			if(game.getName().equals(g.getName()))
				return true;
		return false;
		
	}
	
}