package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import main.GamerDice;

public class ErrorController extends GamerDice implements Initializable{
	private static final long serialVersionUID = 1L;
	static String message = "You fucked up";
	@FXML
	Label errorLabel;
	
	// Set error message
	public static void setMessage(String m){
		message = m;
	}

	// Sets label to message text
	@Override
	public void initialize(URL arg0, ResourceBundle arg1){
		errorLabel.setText(message);
	}
}
