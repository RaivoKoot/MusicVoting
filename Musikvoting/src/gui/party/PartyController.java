package gui.party;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import data.Gast;
import data.Party;
import database.MySQLAnbindung;
import gui.Main;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class PartyController implements Initializable {

	MySQLAnbindung db = new MySQLAnbindung();
	
	@FXML
	private AnchorPane backgroundPane;

	@FXML
	private AnchorPane tabPaneHolder;

	// Party Info Anzeige
	@FXML
	Label lblMotto;
	@FXML
	Label lblDatum;
	@FXML
	Label lblEinlass;
	@FXML
	Label lblOrt;
	@FXML
	Label lblAdresse;
	@FXML
	Label lblLocation;

	// User Info Anzeige
	@FXML
	Label lblVorname;
	@FXML
	Label lblNachname;
	@FXML
	Label lblHausname;
	@FXML
	Label lblID;

	public void initialize(URL arg0, ResourceBundle arg1)
	{
		DoubleProperty opacity = backgroundPane.opacityProperty();
		Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
				new KeyFrame(new Duration(1000), new KeyValue(opacity, 1.0)));
		fadeIn.play();
		
		bannerInfoEinlesen();

		setupTabPane();

	}
	
	public void clicked_logOut(){
		Main.showLoginScreen();
	}

	public void setupTabPane()
	{
		try
		{
			tabPaneHolder.getChildren()
					.add(FXMLLoader.load(Main.class.getResource("party/funktionen/funktionenTabPane.fxml")));

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void bannerInfoEinlesen()
	{
		Party currentParty = db.getCurrentParty();
		lblMotto.setText(currentParty.getMotto());
		lblDatum.setText(currentParty.getDatum());
		lblEinlass.setText(currentParty.getEinlass());
		lblOrt.setText(currentParty.getOrt());
		lblAdresse.setText(currentParty.getAdresse());
		lblLocation.setText(currentParty.getLocation());

		Gast currentGast = db.getCurrentGast();
		lblVorname.setText(currentGast.getVorname());
		lblNachname.setText(currentGast.getNachname());
		lblHausname.setText(currentGast.getAdelstitel());
		lblID.setText(Integer.toString(currentGast.getId()));
	}

}
