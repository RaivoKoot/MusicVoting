package gui.login;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import data.Gast;
import database.MySQLAnbindung;
import database.MySQLDatabase;
import gui.Main;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginController implements Initializable {

	MySQLAnbindung db = new MySQLAnbindung();

	// Login Credentials
	@FXML
	JFXTextField txtFieldVorname;
	@FXML
	JFXTextField txtFieldNachname;
	@FXML
	JFXTextField txtFieldID;

	@FXML
	private AnchorPane loginControls;

	private boolean loggedIn = false;

	public void loggedIn()
	{
		if (!loggedIn)
			try
			{
				loggedIn = true;
				// Fade out auf login controls
				FadeTransition fadeOut = new FadeTransition(new Duration(1000), loginControls);
				fadeOut.setFromValue(1.0);
				fadeOut.setToValue(0);

				// ShowPartyScreen() erst dann invoken, wenn die transition
				// fertig
				// ist
				SequentialTransition waitForTransition = new SequentialTransition(fadeOut);
				waitForTransition.setOnFinished(e -> Main.showPartyScreen());
				waitForTransition.play();

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
	}

	public void clicked_logIn()
	{
		Gast loginCredentials;
		String vorname = txtFieldVorname.getText();
		String nachname = txtFieldNachname.getText();
		int id = Integer.parseInt("0" + txtFieldID.getText().replaceAll("[^0-9]", ""));
		if (!vorname.isEmpty() && !nachname.isEmpty() && id != 0)
		{
			loginCredentials = new Gast(vorname, nachname, id);
			if (db.gastAnmelden(loginCredentials))
				loggedIn();
		}
		txtFieldVorname.setText("");
		txtFieldNachname.setText("");
		txtFieldID.setText("");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		// Fade In: login controls
		DoubleProperty opacity = loginControls.opacityProperty();
		Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, -0.5)),
				new KeyFrame(new Duration(1000), new KeyValue(opacity, 1.0)));
		fadeIn.play();
	}

}
