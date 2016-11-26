package application;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

// best for my brain that I keep the controller and actual stuff separate
public class GamerDiceController extends GamerDice{
	private static final long serialVersionUID = 1L;
	public Game current;
	@FXML
	public Label chanceLabel, gameDisplayLabel;
	@FXML
	public ImageView gameImageView;
	@FXML
	public MenuItem addGameMenu, addDirectoryMenu, removeGameMenu, removeDirectoryMenu, aboutMenu, versionMenu, addSteamDirectory, documentationMenu;
	@FXML
	public MenuBar masterMenu;
	
	
	@FXML
	private void removeGameAction(ActionEvent e){
		openWindow("RemoveGame");
	}
	
	@FXML
	private void removeDirectoryAction(ActionEvent e){
		openWindow("RemoveDirectory");
	}
	
	@FXML
	private void weightedRollButtonPress(ActionEvent e)throws FileNotFoundException{
		if (current != null)
			decreaseGame(current);
		current = weightedRoll();
		if (current == null)
			return;
		int odds = (int)((current.getMultiplier()*1.00/multiplierTotal())*100);
		gameImageView.setImage(current.getImage());
		chanceLabel.setText("Chance: " + odds +"%");
		gameDisplayLabel.setText(current.getName());
	}
	
	@FXML
	private void steamExeDirectory(ActionEvent e){
		try{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select Steam.exe File");
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Steam Executable", "Steam.exe"));
		File selectedFile = fileChooser.showOpenDialog(null);
		addDirectory(selectedFile.getAbsolutePath());
		}
		catch (NullPointerException ce)
		{
			handleError("Please select the Steam Directory",ce);
		}
		
	}
	
	@FXML
	private void openDocumentation(ActionEvent e){
		try{
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		 if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE))
			 desktop.browse((new URL("https://github.com/JoNation/GamerDice/wiki")).toURI());
		}
		catch (Exception ce){
			handleError("Unable to open browser",ce);
		}
		
	}
	
	@FXML
	private void openAbout(ActionEvent e){
		try{
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		 if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE))
			 desktop.browse((new URL("https://sites.google.com/site/jonationfigureitout/")).toURI());
		}
		catch (Exception ce){
			handleError("Unable to open browser",ce);
		}
		
	}
	
	@FXML
	private void updateVersion(Event e){
		versionMenu.setText(version);
	}
	
	@FXML
	private void rollButtonPress(ActionEvent e) throws FileNotFoundException{
		if (current != null)
			decreaseGame(current);
		current = roll();
		if (current == null)
			return;
		int odds = (int)((1.00/multiplierTotal())*100);
		gameImageView.setImage(current.getImage());
		chanceLabel.setText("Chance: " + odds +"%");
		gameDisplayLabel.setText(current.getName());
	}
	
	@FXML
	private void addGameDirectory(ActionEvent e){
		DirectoryChooser directChooser = new DirectoryChooser();
		directChooser.setTitle("Select Game to Add");
		File selectedFile = directChooser.showDialog(null);
		try{
		addDirectory(selectedFile.getAbsolutePath());
		}
		catch (NullPointerException ce){
			handleError("Please select a directory to add",ce);
		}
		
	}
	
	@FXML
	private void runGame(InputEvent e){
		try {
			Runtime.getRuntime().exec(current.getDirectory());
		} catch (IOException|NullPointerException ce) {
			handleError("Try rolling for a game before launching the game that hasn't been picked yet bud.", ce);
			return;
		}
		final Node source = (Node) e.getSource();
		final Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	private void addSingleGame(ActionEvent e){
		try{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select Game to Add");
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Steam Game File", "*.acf"),
				//new ExtensionFilter("Link Files", "*.lnk"),
				new ExtensionFilter("Executable Game File", "*.exe"));
		File selectedFile = fileChooser.showOpenDialog(null);
		addGame(selectedFile);
		}
		catch (Exception ce)
		{
			handleError("Please select a game to add",ce);
		}
		
	}
}