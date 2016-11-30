package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import main.GamerDice;

public class LibraryIntegrationController extends GamerDice implements Initializable{
	private static final long serialVersionUID = 1L;

	@FXML
	private ImageView steamStatusImage, originStatusImage, uplayStatusImage;
	@FXML
	private CheckBox steamDirectoryCheckbox, originDirectoryCheckbox, uplayDirectoryCheckbox;
	@FXML
	private ChoiceBox<String> steamChoiceBox, originChoiceBox, uplayChoiceBox;
	@FXML
	private ImageView steamSE, steamRD, steamAD, originSE, originRD, originAD, uplaySE, uplayRD, uplayAD;
	// Initializes library statuses, library check-boxes, and choice-boxes
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	
	// Add a directory for a given game library
	@FXML
	public void addDirectory(ActionEvent e){
		
	}
	// Remove a directory for a given game library
	@FXML
	public void removeDirectory(ActionEvent e){
		
	}
	
	
	// Changes a game libraries .exe location
	@FXML
	public void selectExe(ActionEvent e){
		
	}
	// Toggles a library status to on or off
	@FXML
	public void toggleLibrary(ActionEvent e){
		
	}
	
}