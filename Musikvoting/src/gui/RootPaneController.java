package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RootPaneController implements Initializable {
	
	@FXML
	ImageView backgroundBlur;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		/*
		// Blur out: background
		DoubleProperty opacity = backgroundBlur.opacityProperty();
		Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, -0.5)),
				new KeyFrame(new Duration(1500), new KeyValue(opacity, 1.0)));
		fadeIn.play();
		*/
	}
	
	public void settingsPressed(){
		Main.showSettingsPopUp();
	}
	
	public void minimizePressed(ActionEvent event)
	{
		Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setIconified(true);
	}

	public void exitPressed()
	{
		Platform.exit();
	}

}
