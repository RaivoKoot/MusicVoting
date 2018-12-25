package gui.party.funktionen;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import data.Gast;
import data.Party;
import data.Playlist;
import data.Song;
import data.Vote;
import database.MySQLAnbindung;
import database.MySQLDatabase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FunktionenTabPaneController implements Initializable {

	private MySQLAnbindung db = new MySQLAnbindung();

	// tabellen
	@FXML
	private TableView<Song> playlistTabelle;
	@FXML
	private TableView<Song> songVotenTabelle;
	@FXML
	private TableView<Song> deineSongsTabelle;
	@FXML
	private TableView<Song> deineVotesTabelle;

	// Listen fuer tabellen
	private Playlist pltSongsMitVotes;
	private Playlist pltAlleSongs;
	private Playlist pltDeineSongsList;
	private Playlist pltDeineVotesList;

	// Song Voten Controls
	@FXML
	Label selectedTitel;
	@FXML
	Label selectedInterpret;

	// Song Erstellen Controls
	@FXML
	JFXTextField txtFieldTitel;
	@FXML
	JFXTextField txtFieldInterpret;
	@FXML
	JFXTextField txtFieldGenre;
	@FXML
	Label fehlermeldung;

	public void clicked_liedEintragen()
	{
		Song neuerSong;
		String titel = txtFieldTitel.getText();
		String interpret = txtFieldInterpret.getText();
		String genre = txtFieldGenre.getText();

		if (!titel.isEmpty() && !interpret.isEmpty() && !genre.isEmpty())
		{
			txtFieldTitel.setText("");
			txtFieldInterpret.setText("");
			txtFieldGenre.setText("");
			neuerSong = new Song(titel, interpret, genre);
			neuerSong.setGast_ID(db.getCurrentUserID());
			neuerSong.setId_nummer(db.getMaxMusikID());
			if(db.neuerMusiktitel(neuerSong)){
				fehlermeldung.setText("Lied erfolgreich eingetragen");
				pltAlleSongs.addSong(neuerSong);
				pltDeineSongsList.addSong(neuerSong);
			}
			else
				fehlermeldung.setText("Lied eintragen fehlgeschlagen");
		}
	}

	// Listener ob auf eine reihe in der tabelle geclicked wurde
	public void clicked_selectSong()
	{
		Song selectedSong;
		if ((selectedSong = songVotenTabelle.getSelectionModel().getSelectedItem()) != null)
		{
			selectedTitel.setText(selectedSong.getTitel());
			selectedInterpret.setText(selectedSong.getInterpret());
		}
	}

	// ausgewaehlten song voten
	public void clicked_voteButton()
	{
		// nur wenn eine titel aus der tabelle ausgewaehlt ist
		if (!selectedTitel.getText().equals("keine Auswahl"))
		{
			// neuen vote tupel erstellen
			int songID = songVotenTabelle.getSelectionModel().getSelectedItem().getId_nummer();
			db.neuerVote(new Vote(songID));

			// reset auswahl
			selectedTitel.setText("keine Auswahl");
			selectedInterpret.setText("keine Auswahl");
			songVotenTabelle.getSelectionModel().clearSelection();
			
			// update lists
			listenAusDatenbankHolen();
			tabellenListenFestlegen();
		}

	}

	/*
	 * Methoden zur Initialisierung Der GUI Tabellen
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		initializeTabellenColums();
		listenAusDatenbankHolen();
		tabellenListenFestlegen();

	}

	
	
	// Song Listen aus datenbank holen
	private void listenAusDatenbankHolen()
	{
		pltSongsMitVotes = db.getSongsWithVotes();
		pltAlleSongs = db.getAllSongs();
		pltDeineSongsList = db.getDeineSongs();
		pltDeineVotesList = db.getDeineVotes();
	}

	// Den Tabellen die Songlisten fuettern
	private void tabellenListenFestlegen()
	{
		playlistTabelle.setItems(pltSongsMitVotes.getPlaylist());
		songVotenTabelle.setItems(pltAlleSongs.getPlaylist());
		deineSongsTabelle.setItems(pltDeineSongsList.getPlaylist());
		/////////////////////////////////////////////// finish
		deineVotesTabelle.setItems(pltDeineVotesList.getPlaylist());
	}

	// Colums erstellen und in tabellen einfuegen
	private void initializeTabellenColums()
	{
		// Spalten erstellen
		TableColumn<Song, String> columnTitel = new TableColumn<>();
		TableColumn<Song, String> columnInterpret = new TableColumn<>();
		TableColumn<Song, String> columnGenre = new TableColumn<>();
		TableColumn<Song, Integer> columnVotes = new TableColumn<>();

		TableColumn<Song, String> columnTitel_2 = new TableColumn<>();
		TableColumn<Song, String> columnInterpret_2 = new TableColumn<>();
		TableColumn<Song, String> columnGenre_2 = new TableColumn<>();
		TableColumn<Song, Integer> columnVotes_2 = new TableColumn<>();
		
		TableColumn<Song, String> columnTitel_4 = new TableColumn<>();
		TableColumn<Song, String> columnInterpret_4 = new TableColumn<>();
		TableColumn<Song, String> columnGenre_4 = new TableColumn<>();
		TableColumn<Song, Integer> columnVotes_4 = new TableColumn<>();

		TableColumn<Song, String> columnTitel_3 = new TableColumn<>();
		TableColumn<Song, String> columnInterpret_3 = new TableColumn<>();
		TableColumn<Song, String> columnGenre_3 = new TableColumn<>();

		// Spalteninhalt angeben
		columnTitel.setCellValueFactory(new PropertyValueFactory<>("titel"));
		columnInterpret.setCellValueFactory(new PropertyValueFactory<>("interpret"));
		columnGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
		columnVotes.setCellValueFactory(new PropertyValueFactory<>("votes"));

		columnTitel_2.setCellValueFactory(new PropertyValueFactory<>("titel"));
		columnInterpret_2.setCellValueFactory(new PropertyValueFactory<>("interpret"));
		columnGenre_2.setCellValueFactory(new PropertyValueFactory<>("genre"));
		columnVotes_2.setCellValueFactory(new PropertyValueFactory<>("votes"));
		
		columnTitel_4.setCellValueFactory(new PropertyValueFactory<>("titel"));
		columnInterpret_4.setCellValueFactory(new PropertyValueFactory<>("interpret"));
		columnGenre_4.setCellValueFactory(new PropertyValueFactory<>("genre"));
		columnVotes_4.setCellValueFactory(new PropertyValueFactory<>("votes"));

		columnTitel_3.setCellValueFactory(new PropertyValueFactory<>("titel"));
		columnInterpret_3.setCellValueFactory(new PropertyValueFactory<>("interpret"));
		columnGenre_3.setCellValueFactory(new PropertyValueFactory<>("genre"));

		// Den Spalten einen Titel geben
		columnTitel.setText("Titel");
		columnInterpret.setText("Interpret");
		columnGenre.setText("Genre");
		columnVotes.setText("Votes");

		columnTitel_2.setText("Titel");
		columnInterpret_2.setText("Interpret");
		columnGenre_2.setText("Genre");
		columnVotes_2.setText("Votes");
		
		columnTitel_4.setText("Titel");
		columnInterpret_4.setText("Interpret");
		columnGenre_4.setText("Genre");
		columnVotes_4.setText("Votes");

		columnTitel_3.setText("Titel");
		columnInterpret_3.setText("Interpret");
		columnGenre_3.setText("Genre");

		// Die Spalten in die Tabllen packen
		playlistTabelle.getColumns().add(columnTitel);
		playlistTabelle.getColumns().add(columnInterpret);
		playlistTabelle.getColumns().add(columnGenre);
		playlistTabelle.getColumns().add(columnVotes);

		songVotenTabelle.getColumns().add(columnTitel_4);
		songVotenTabelle.getColumns().add(columnInterpret_4);
		songVotenTabelle.getColumns().add(columnGenre_4);
		songVotenTabelle.getColumns().add(columnVotes_4);

		deineSongsTabelle.getColumns().add(columnTitel_2);
		deineSongsTabelle.getColumns().add(columnInterpret_2);
		deineSongsTabelle.getColumns().add(columnGenre_2);
		deineSongsTabelle.getColumns().add(columnVotes_2);

		deineVotesTabelle.getColumns().add(columnTitel_3);
		deineVotesTabelle.getColumns().add(columnInterpret_3);
		deineVotesTabelle.getColumns().add(columnGenre_3);

	}

}
