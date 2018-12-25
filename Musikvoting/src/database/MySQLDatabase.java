package database;

import data.Gast;
import data.Playlist;
import data.Song;

public class MySQLDatabase {

	private static int currentUserID;
	
	// CHECK
	public boolean loginCheck(Gast loginCredentials){
		boolean erfolgreichEingelogt = true;
		
		// SQL ABFRAGE ZUM CHECKEN OB EINLOGDATEN KORREKT SIND
		// WENN LOGIN ERFOLGREICH WAR DANN currentUserID = ID vom login user
		
		return erfolgreichEingelogt;
	}

	// CHECK
	public void loggedIn(int userId)
	{
		setCurrentUserID(userId);
	}
	
	// CHECK
	public boolean createNewVote(int songID)
	{
		boolean erfolgreicheingetragen = false;

		// SQL BEFEHLE ZUM VOTE IN DB EINTRAGE

		return erfolgreicheingetragen;
	}

	// CHECK
	public boolean neuenSongEintragen(Song s)
	{
		boolean erfolgreicheingetragen = false;

		// SQL BEFEHLE ZUM SONG IN DB EINTRAGEN

		return erfolgreicheingetragen;
	}

	// CHECK
	public Playlist getAllSongs()
	{
		Playlist playlist = new Playlist();

		// LISTE AUS DATENBANK FUELLEN

		playlist.addSong(new Song());
		playlist.addSong(new Song());
		playlist.addSong(new Song());
		playlist.addSong(new Song());
		playlist.addSong(new Song());
		playlist.addSong(new Song());
		playlist.addSong(new Song());

		return playlist;

	}

	// CHECK
	public Playlist getDeineSongs()
	{
		Playlist deineSongsPlaylist = new Playlist();

		// LISTE AUS DATENBANK FUELLEN

		deineSongsPlaylist.addSong(new Song("Pimmel", "test", "lak", 15));

		return deineSongsPlaylist;
	}

	public Playlist getDeineVotes()
	{
		Playlist deineVotes = new Playlist();

		// LISTE AUS DATENBANK FUELLEN

		deineVotes.addSong(new Song("TestTitel", "Vote jaa", "Voteee", 12));

		return deineVotes;

	}

	// CHECK
	public boolean songEintragen(Song neuerSong)
	{

		// SQL BEFEHLE ZUM EINTRAGEN DES NEUEN SONGS IN DATENBANK

		return true;
	}

	public static int getCurrentUserID()
	{
		return currentUserID;
	}

	public static void setCurrentUserID(int currentUserID)
	{
		MySQLDatabase.currentUserID = currentUserID;
	}

}
