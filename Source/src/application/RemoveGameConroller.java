package application;

import java.awt.Desktop;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class RemoveGameConroller extends GamerDice implements Initializable{
	private static final long serialVersionUID = 1L;
	@FXML
	public ComboBox<String> comboBox;
	@FXML
	public Button helpButton, cancelButton, removeButton;
	
	@Override
	public void initialize(URL url,ResourceBundle resource){
		List<String> list = new ArrayList<String>();
		for(Game g: games)
			list.add(g.getName());
		ObservableList<String> oblist = FXCollections.observableList(list);
		comboBox.getItems().clear();
		comboBox.setItems(oblist);
	}

	@FXML 
	void removeGameHandler(ActionEvent e){
		try{
		removeGame(comboBox.getSelectionModel().getSelectedItem().toString());
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
		}
		catch(NullPointerException ce){
			handleError("Select a game to be removed",ce);
		}
		
	}
	
	@FXML
	public void help(ActionEvent e){
		try{
			Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
			 if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE))
				 desktop.browse((new URL("https://github.com/JoNation/GamerDice/wiki")).toURI());
			}
			catch (Exception ce){
				handleError("Could not open browser",ce);
			}
	}
	
	@FXML
	public void close(ActionEvent e){
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
}