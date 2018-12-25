package gui.gastErstellen;

import com.jfoenix.controls.JFXTextField;

import data.Gast;
import database.MySQLAnbindung;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GastErstellenWindowController {

	MySQLAnbindung db = new MySQLAnbindung();

	@FXML
	Label fehlermeldung;
	@FXML
	JFXTextField txfVorname;
	@FXML
	JFXTextField txfNachname;
	@FXML
	JFXTextField txfAdminKey;
	@FXML 
	JFXTextField txfAdelstitel;

	public void clicked_button()
	{
		if (!txfVorname.getText().isEmpty() && !txfNachname.getText().isEmpty() && !txfAdminKey.getText().isEmpty() && !txfAdelstitel.getText().isEmpty())
		{
			Gast neuerGast = new Gast();
			neuerGast.setVorname(txfVorname.getText());
			neuerGast.setNachname(txfNachname.getText());
			neuerGast.setAdminKey(txfAdminKey.getText());
			neuerGast.setAdelstitel(txfAdelstitel.getText());
			
			if(txfAdelstitel.getText().equals("keinen"))
				neuerGast.setAdelstitel("NULL");

			int neueId = db.neuerGast(neuerGast);

			if (neueId == -1)
			{
				fehlermeldung.setText("Gast Erstellen Fehlgeschlagen");
				txfVorname.setText("");
				txfNachname.setText("");
				txfAdminKey.setText("");
			}
			else
				fehlermeldung.setText("Gast Erstellt! ID: " + neueId);

		}

	}
}
