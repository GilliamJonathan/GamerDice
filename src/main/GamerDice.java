package main;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.*;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import sun.applet.Main;

public class GamerDice extends Application implements Serializable{
	private static final long serialVersionUID = 1L;
	private static String filePath = "C:/GamerDice/";
	public static ArrayList<Directory> directorys;
	public static ArrayList<Game> games;
	private static String steamDirectory;
	public String version = "v1.0.1-alpha";
	
	public static void main(String[] args){
		directorys = new ArrayList<String>();
		games = new ArrayList<Game>();
		//Reads through saved games one file
		readFile();
		//Launches GUI
		Application.launch(GamerDice.class, (java.lang.String[])null);
    }

	//Starts GUI
    @Override
    public void start(Stage primaryStage){
        try {
            Pane page = (Pane) FXMLLoader.load(GamerDice.class.getResource("/application/GamerDice.fxml"));
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Gamer Dice");
            primaryStage.resizableProperty().setValue(Boolean.FALSE);
            primaryStage.getIcons().add(new Image("/assets/Icon_trans.png"));
            primaryStage.show();
            GamerDice.directorys = new ArrayList<String>();
    		GamerDice.games = new ArrayList<Game>();
    		GamerDice.readFile();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
	// Reads GameRoller's saved games
	public static void readFile(){
		try
		{
		File savedGames = new File(filePath);
		File savedDirectorys = new File(filePath + "\\directorys.dat");
		savedGames.mkdirs();   // if the directory exists this will do nothing
		savedDirectorys.createNewFile(); // if the directory file exists this will do nothing
		for (File file : savedGames.listFiles()){
			if (file.getName().toLowerCase().endsWith(".ser")){
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);
				games.add((Game) ois.readObject());
				ois.close();
			}
			if (file.getName().toLowerCase().equals("directorys.dat")){
				Scanner scan = new Scanner(file);
				while(scan.hasNext())
					directorys.add(scan.nextLine());
				scan.close();
			}
		}
		if (directorys.size() != 0)
			checkDirectorys();
		// checkGames();
		}
		catch(Exception e){
			handleError("Failed to read saved file, did you run application \'as administrator\'?",e);
		}
		
	}
	
	// Returns a weighted random Game
	public static Game weightedRoll(){
			ArrayList<Game> g = new ArrayList<Game>();
		for(Game gm : games)
			for (int i = 0; i < gm.getMultiplier(); i++)
				g.add(gm);
		return roll(g);
	}
	
	// Returns a random Game from a Game ArrayList
	public static Game roll(ArrayList<Game> g){
		if (g.size() == 1)
			return g.get(0);
		if (g.size() == 0)
			return null;
		Random ran = new Random();
		return g.get(ran.nextInt(g.size()-1));
	}
	
	// Returns a random Game from the games ArrayList
	public static Game roll(){
		return roll(games);
	}

	// Returns multiplier total from the games ArrayList
	public static int multiplierTotal(){
		int sum = 0;
		for(Game g : games)
				sum += g.getMultiplier();
		return sum;
	}
	
	// Sends path to possible directory types
	public static void checkDirectorys(){
		for (String path : directorys){
			if(path.contains("Steam.exe"))
				steamDirectory = path;
			else if(path.contains(".exe")||path.contains(".lnk"))
				exeDirectory(path);
			else if (path.contains("Steam"))
				steamDirectory(path);
			else
				exeDirectory(path);
		}
	}
	
	// Adds a directory to file and the directory list
	public static void addDirectory(String d){
		try{
		for (String path : directorys)
			if(d.equals(path) || d.equals(steamDirectory))
				return;
			else if(d.contains("Steam.exe"))
				steamDirectory = d;
			directorys.add(d);
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + "\\directorys.dat", false));
		for (String line : directorys){
			writer.write(line);
			writer.newLine();
		}
		writer.close();
		checkDirectorys();
		}
		catch (IOException e){
			handleError("Failed to add directory, are you running appliaction \'as administrator\'?",e);
		}
		
	}
	
	// Goes through a steam directory and adds any games that are not already on file
	public static void steamDirectory(String path){
		for (File file : new File(path).listFiles()){
			if (steamDirectory == null)
				return;
			if (file.getName().toLowerCase().endsWith(".acf")){
				Scanner scan;
				try {
					scan = new Scanner(new File(path + "\\" + file.getName()));
				String appid = "";
				String appname = "";
				while(!appid.contains("\"appid\""))
					{appid = scan.nextLine();}
				appid = appid.substring(appid.lastIndexOf("\"",appid.lastIndexOf("\"")-1)+1, appid.lastIndexOf("\""));
				while(!appname.contains("\"name\""))
					{appname = scan.nextLine();}
				appname = appname.substring(appname.lastIndexOf("\"",appname.lastIndexOf("\"")-1)+1, appname.lastIndexOf("\""));
				Game g = new Game(appname, steamDirectory + " -applaunch " + appid);
				addGame(g);
				scan.close();
				}
				catch (IOException e)
				{
					handleError("Steam-game file reader error, are all the .acf files in the directory Steam app cache file?",e);
				}
			}
		}
	}
	
	// Goes through a exe (or lnk) directory and adds any games that are not already on file
	public static void exeDirectory(String path){
		for (File file : new File(path).listFiles()){
			if (file.getName().toLowerCase().endsWith(".exe")||file.getName().toLowerCase().endsWith(".lnk")){
			Game g = new Game(file.getName(),file.getPath());
			addGame(g);
			}
		}
	}

	// Adds a game to file to file if it doesn't already exist
	public static void addGame(Game g){
		for(Game i : games){
			if (i.getName().equals(g.getName())||i.getDirectory().equals(g.getDirectory()))
				return;
		}
		games.add(g);
		File gfile = new File(filePath + "\\" + g.getName() + ".ser");
		try{
		gfile.createNewFile();
		FileOutputStream fos = new FileOutputStream(gfile);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(g);
		oos.close();
		}
		catch(IOException e)
		{
			handleError("Could not write file, try running application \'as admin\'",e);
		}
		
	}

	// Adds a game using a directory
	public static void addGame(File file){
		if (file.getName().toLowerCase().endsWith(".exe")||file.getName().toLowerCase().endsWith(".lnk")){
			Game g = new Game(file.getName(),file.getPath());
			addGame(g);
			}
	}
	
	//Removes a game
	public static void removeGame(Game g){
		File savedGames = new File(filePath);
		for (File file : savedGames.listFiles()){
			if (file.getName().equals(g.getName() + ".ser")){
				file.delete();
			}
		}
		for (int i = 0; i < games.size(); i++)
			if (games.get(i).toString().equals(g.toString()))
				games.remove(i);
	}
	
	// Removes a game
	public static void removeGame(String gn){
		Game g = new Game(gn,gn);
		removeGame(g);
	}
	
	//Removes a directory
	public static void removeDirectory(String d){
		directorys.remove(d);
		File df = new File(filePath + "\\directorys.dat");
		df.delete();
		try {
			df.createNewFile();
		BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + "\\directorys.dat", false));
		for (String line : directorys){
			writer.write(line);
			writer.newLine();
		}
		writer.close();
		checkDirectorys();
		}
		catch (IOException e){
			handleError(d + " could not be found to be removed",e);
		}
	}

	//decreases game's probability
	public static void decreaseGame(Game g){
		removeGame(g);
		g.decrease();
		addGame(g);
	}
	
	//increase game's probability
	public static void increaseGame(Game g){
		removeGame(g);
		g.increase();
		addGame(g);
	}
	
	// Opens pop-up window
	public void openWindow(String window){
		try{
		Parent root = FXMLLoader.load(getClass().getResource("/application/" + window+".fxml"));
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.resizableProperty().setValue(Boolean.FALSE);
		stage.setTitle(window);
		stage.getIcons().add(new Image("/assets/Icon_trans.png"));
		stage.setScene(new Scene(root));
		stage.show();
		}
		catch(IOException e)
		{
			handleError("Failed to open window: " + window,e);
		}
	}

	//error handler
	public static void handleError(String m, Exception e){
		try{
		File file = new File("errorLog.log");
		file.delete();
		file.createNewFile();
		PrintStream ps = new PrintStream(file);
		e.printStackTrace(ps);
		ErrorController.setMessage(m);
		new GamerDice().openWindow("Error");
		}
		catch(IOException ee)
		{
			System.out.println("Well, you fucked up bud");
		}
		
	}
}
