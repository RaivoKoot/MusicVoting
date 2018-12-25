package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;

import data.Gast;
import data.Party;
import data.Playlist;
import data.Song;
import data.Vote;
import javafx.collections.ObservableList;

public class MySQLAnbindung {

	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost/musikvoting";
	private String user = "root";
	private String password = "";

	private static int currentUserID;
	private Connection con;
	private Statement stmt;

	public int getCurrentUserID()
	{
		return currentUserID;
	}

	public Gast getCurrentGast()
	{
		Gast currentGast = new Gast();
		try
		{
			createConnection();

			// Zeichenkette für SQL-Befehl erstellen
			String sql = "Select * FROM t_gaeste WHERE p_gast_nr = " + currentUserID;

			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next())
			{
				currentGast.setId(currentUserID);
				currentGast.setVorname(rs.getString(2));
				currentGast.setNachname(rs.getString(3));
				currentGast.setAdelstitel(rs.getString(5));
			}

			con.close(); // Verbindung schließen

		}
		catch (Exception ex)
		{ // Fehler abfangen
			ex.printStackTrace();// Fehlermeldung ausgeben
		}

		return currentGast;
	}

	public Party getCurrentParty()
	{
		Party currentParty = new Party();
		try
		{
			createConnection();

			// Zeichenkette für SQL-Befehl erstellen
			String sql = "SELECT * FROM t_parties WHERE p_party_id = (SELECT f_party_id FROM t_gaeste WHERE p_gast_nr = 1)";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				currentParty.setMotto(rs.getString(2));
				currentParty.setDatum(rs.getString(3));
				currentParty.setEinlass(rs.getString(4));
				currentParty.setOrt(rs.getString(5));
				currentParty.setAdresse(rs.getString(6));
				currentParty.setLocation(rs.getString(7));
			}

			con.close(); // Verbindung schließen

		}
		catch (Exception ex)
		{ // Fehler abfangen
			ex.printStackTrace();// Fehlermeldung ausgeben
		}

		return currentParty;
	}

	private void createConnection()
	{
		try
		{
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			stmt = con.createStatement();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public boolean gastAnmelden(Gast g)
	{
		int gastvorhanden = 0;
		try
		{
			// verbindung erstellen
			createConnection();
			// Gast Infos extrahieren
			int gastID = g.getId();
			String vorname = g.getVorname();
			String nachname = g.getNachname();

			// SQL Befehl
			String sql = "SELECT COUNT(p_gast_nr) FROM t_gaeste WHERE p_gast_nr = " + gastID + " AND vorname = '"
					+ vorname + "' AND name = '" + nachname + "';";

			// SQL-Anweisungen ausfÃ¼hren
			ResultSet rs = stmt.executeQuery(sql);
			// ResultSet auswerten
			if (rs.next())
			{
				gastvorhanden = rs.getInt(1);
				currentUserID = gastID;
			}
			// Verbindung schlieÃŸen
			con.close();

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		if (gastvorhanden == 1)
			return true;
		else
			return false;
	}

	/*
	 * public boolean neueParty(Party p) { try {
	 * 
	 * // JDBC-Treiber laden Class.forName(driver); // Verbindung aufbauen
	 * Connection con = DriverManager.getConnection(url, user, password); //
	 * Zeichenkette für SQL-Befehl erstellen String sql =
	 * "INSERT INTO T_Parties VALUES (?,?,?,?);";
	 * 
	 * PreparedStatement pstat = con.prepareStatement(sql); pstat.setString(1,
	 * p.getParty_id()); pstat.setString(2, p.getMotto()); pstat.setString(3,
	 * p.getDatum()); pstat.setString(4, p.getEinlass()); pstat.execute();
	 * 
	 * con.close(); // Verbindung schließen
	 * 
	 * } catch (Exception ex) { // Fehler abfangen ex.printStackTrace();//
	 * Fehlermeldung ausgeben return false; } return true; }
	 */

	// neuen Gast eintrage
	public int neuerGast(Gast g)
	{
		int neueId = -1; // -1 bedeutet fehlgeschlagen
		try
		{
			createConnection();

			String sql = "SELECT p_party_id FROM T_parties WHERE adminkey = '" + g.getAdminKey() + "'";

			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next())
				g.setPartyID(rs.getInt(1));
			else
				return neueId;

			// neue id erstellen
			sql = "SELECT MAX(p_gast_nr) FROM t_gaeste";

			rs = stmt.executeQuery(sql);

			if (rs.next())
				g.setId(rs.getInt(1) + 1);
			else
				return neueId;

			// Zeichenkette für SQL-Befehl erstellen
			sql = "INSERT INTO T_Gaeste VALUES (?,?,?,?,?);";

			PreparedStatement pstat = con.prepareStatement(sql);
			pstat.setInt(1, g.getId());
			pstat.setString(2, g.getVorname());
			pstat.setString(3, g.getNachname());
			pstat.setInt(4, g.getPartyID());
			pstat.setString(5, g.getAdelstitel());
			if(g.getAdelstitel().equals("NULL"))
				pstat.setNull(5, Types.VARCHAR);
			pstat.execute();

			con.close(); // Verbindung schließen

		}
		catch (Exception ex)
		{ // Fehler abfangen
			ex.printStackTrace();// Fehlermeldung ausgeben
			return neueId;
		}
		neueId = g.getId();
		return neueId;
	}

	// neue Musik ID fuer neuen Song
	public int getMaxMusikID()
	{
		int maxMusikId = 0;
		try
		{
			createConnection();

			// Zeichenkette für SQL-Befehl erstellen
			String sql = "SELECT MAX(p_musik_nr) FROM t_musik;";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next())
				maxMusikId = rs.getInt(1) + 1;

			con.close(); // Verbindung schließen

		}
		catch (Exception ex)
		{ // Fehler abfangen
			ex.printStackTrace();// Fehlermeldung ausgeben
		}

		return maxMusikId;
	}

	// neuen Song in DB eintragen
	public boolean neuerMusiktitel(Song s)
	{
		try
		{
			createConnection();

			// Zeichenkette für SQL-Befehl erstellen
			String sql = "INSERT INTO T_MUSIK VALUES (?,?,?,?,?,?);";

			PreparedStatement pstat = con.prepareStatement(sql);
			pstat.setInt(1, s.getId_nummer());
			pstat.setString(2, s.getInterpret());
			pstat.setString(3, s.getTitel());
			pstat.setString(4, s.getLength());
			pstat.setString(5, s.getGenre());
			pstat.setInt(6, s.getGast_ID());
			pstat.execute();

			con.close(); // Verbindung schließen

		}
		catch (Exception ex)
		{ // Fehler abfangen
			ex.printStackTrace();// Fehlermeldung ausgeben
			return false;
		}
		return true;
	}

	// neuen Vote in DB eintragen
	// if(votes > 5): aeltestem loeschen
	public boolean neuerVote(Vote v)
	{

		createConnection();
		// Zeichenkette für SQL-Befehl erstellen
		String sql = "INSERT INTO t_gaeste_musik_votet VALUES (?,?, CURRENT_TIMESTAMP());";

		try
		{
			// Vote per SQL einfuegen
			PreparedStatement pstat = con.prepareStatement(sql);
			pstat.setInt(1, currentUserID);
			pstat.setInt(2, v.getMusik_nr());
			pstat.execute();

			// check ob user mehr als 5 votes hat
			// SQL Befehl gibt anzahl an votes eines nutzers her
			sql = "SELECT COUNT(*) FROM t_gaeste_musik_votet WHERE pf_gast_nr = " + currentUserID;

			// SQL-Anweisungen ausfÃ¼hren
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next() && rs.getInt(1) > 5)
			{
				sql = "DELETE FROM t_gaeste_musik_votet WHERE pf_gast_nr = " + currentUserID
						+ " AND Zeitpunkt = (SELECT * FROM (SELECT MIN(Zeitpunkt) FROM t_gaeste_musik_votet WHERE pf_gast_nr = "
						+ currentUserID + ") AS p);";
				stmt.executeUpdate(sql);
			}

			con.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}

		return true;

	}

	// Liste aller lieder, die mindesten 1 vote haben
	public Playlist getSongsWithVotes()
	{
		Playlist songsMitVotes = new Playlist();
		try
		{
			createConnection();

			String sql = "SELECT (SELECT COUNT(*) FROM t_gaeste_musik_votet, t_gaeste WHERE t_gaeste_musik_votet.pf_musik_nr= m.p_musik_nr AND t_gaeste.p_gast_nr = t_gaeste_musik_votet.pf_gast_nr AND t_gaeste.hausname IS NOT NULL) AS Votes ,m.p_musik_nr, m.Titel, m.Interpret, m.Genre, m.f_gast_nr FROM t_gaeste_musik_votet AS v, t_musik AS m GROUP BY m.p_musik_nr HAVING Votes > 0 ORDER BY Votes DESC";

			ResultSet rs = stmt.executeQuery(sql);

			fillPlaylistFromResultSet(songsMitVotes, rs);
			con.close();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return songsMitVotes;
	}

	// Liste aller Lieder
	public Playlist getAllSongs()
	{
		createConnection();
		Playlist alleSongs = new Playlist();

		String sql = "SELECT (SELECT COUNT(*) FROM t_gaeste_musik_votet, t_gaeste WHERE t_gaeste_musik_votet.pf_musik_nr= m.p_musik_nr AND t_gaeste.p_gast_nr = t_gaeste_musik_votet.pf_gast_nr AND t_gaeste.hausname IS NOT NULL) AS Votes ,m.p_musik_nr, m.Titel, m.Interpret, m.Genre, m.f_gast_nr FROM t_gaeste_musik_votet AS v, t_musik AS m GROUP BY m.p_musik_nr ORDER BY Votes DESC;";
		try
		{
			ResultSet rs = stmt.executeQuery(sql);
			fillPlaylistFromResultSet(alleSongs, rs);
			con.close();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return alleSongs;
	}

	// Liste aller Lieder die vom momentanen benutzer vorgeschlagen wurden
	public Playlist getDeineSongs()
	{
		createConnection();
		Playlist deineSongs = new Playlist();

		String sql = "SELECT (SELECT COUNT(*) FROM t_gaeste_musik_votet, t_gaeste WHERE t_gaeste_musik_votet.pf_musik_nr= m.p_musik_nr AND t_gaeste.p_gast_nr = t_gaeste_musik_votet.pf_gast_nr AND t_gaeste.hausname IS NOT NULL) AS Votes, m.p_musik_nr, m.Titel, m.Interpret, m.Genre, m.f_gast_nr FROM t_gaeste_musik_votet AS v, t_musik AS m WHERE m.f_gast_nr = "
				+ currentUserID + " GROUP BY m.p_musik_nr ORDER BY Votes DESC";
		try
		{
			ResultSet rs = stmt.executeQuery(sql);
			fillPlaylistFromResultSet(deineSongs, rs);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return deineSongs;

	}

	// traegt Eintraege des ResultSets in eine Liste ein
	private Playlist fillPlaylistFromResultSet(Playlist songListe, ResultSet rs)
	{
		try
		{
			while (rs.next())
			{
				Song nextSong = new Song();
				nextSong.setVotes(rs.getInt(1));
				nextSong.setId_nummer(rs.getInt(2));
				nextSong.setTitel(rs.getString(3));
				nextSong.setInterpret(rs.getString(4));
				nextSong.setGenre(rs.getString(5));
				nextSong.setGast_ID(rs.getInt(6));
				songListe.addSong(nextSong);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return songListe;
	}

	// gibt eine liste mit den liedern, die du gevotet hast, ggf lieder doppelt
	public Playlist getDeineVotes()
	{
		createConnection();
		Playlist deineGevotetenSongs = new Playlist();

		String sql = "SELECT m.Titel, m.Interpret, m.Genre FROM t_musik AS m, t_gaeste_musik_votet WHERE p_musik_nr = t_gaeste_musik_votet.pf_musik_nr AND t_gaeste_musik_votet.pf_gast_nr ="
				+ currentUserID;
		try
		{
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				Song gevoteterSong = new Song();
				gevoteterSong.setTitel(rs.getString(1));
				gevoteterSong.setInterpret(rs.getString(2));
				gevoteterSong.setGenre(rs.getString(3));
				deineGevotetenSongs.addSong(gevoteterSong);
			}

			con.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return deineGevotetenSongs;

	}

}