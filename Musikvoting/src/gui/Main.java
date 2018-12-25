package gui;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Main extends Application {

	private Stage primaryStage;
	public static AnchorPane rootPane;
	static BorderPane mainLayout;

	public static void main(String[] args)
	{
		launch(args);
	}

	public void start(Stage primaryStage)
	{
		primaryStage.initStyle(StageStyle.UNDECORATED);
		// primaryStage.initStyle(StageStyle.TRANSPARENT);
		this.primaryStage = primaryStage;
		try
		{
			initializeRootPane();
			showLoginScreen();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	private void initializeRootPane()
	{
		try
		{
			// rootpane
			rootPane = FXMLLoader.load(Main.class.getResource("RootPane.fxml"));
			Scene scene = new Scene(rootPane);
			mainLayout = (BorderPane) rootPane.lookup("#mainPane");

			// STYLING
			scene.setFill(Color.TRANSPARENT);
			erstelleFensterBewegeListeners(rootPane, primaryStage);

			// stage
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void showPartyScreen()
	{
		try
		{
			AnchorPane partyPane = FXMLLoader.load(Main.class.getResource("party/PartyTwo.fxml"));
			mainLayout.setCenter(partyPane);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void showLoginScreen()
	{
		try
		{
			AnchorPane loginPane = FXMLLoader.load(Main.class.getResource("login/loginGUI.fxml"));
			mainLayout.setCenter(loginPane);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	public static void showSettingsPopUp()
	{
		try
		{
			Parent settingsRoot = FXMLLoader.load(Main.class.getResource("gastErstellen/GastErstellenWindow.fxml"));
			Stage settingsStage = new Stage();
			settingsStage.setTitle("Gast Erstellen");
			settingsStage.setScene(new Scene(settingsRoot));
			settingsStage.show();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * Parameter: bekommt die unterste pane und die Stage. Funktion: ermoeglicht
	 * es die Applikation mit der Maus herumzuziehen
	 * 
	 */
	double x, y;

	public void erstelleFensterBewegeListeners(final Node pane, Stage primaryStage)
	{

		pane.setOnMousePressed((MouseEvent mouseEvent) -> {
			x = pane.getScene().getWindow().getX() - mouseEvent.getScreenX();
			y = pane.getScene().getWindow().getY() - mouseEvent.getScreenY();
		});

		pane.setOnMouseDragged((MouseEvent mouseEvent) -> {
			primaryStage.setX(mouseEvent.getScreenX() + x);
			primaryStage.setY(mouseEvent.getScreenY() + y);
		});
	}
}
