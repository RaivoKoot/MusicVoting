package data;

import java.sql.Time;

public class Song {
	
	

	private int id_nummer;
	private int gast_ID;
	private String interpret;
	private String titel;
	private String genre;
	private int votes;
	
	// default for now
	private String length = "00:03:00";
	
	public void generateID(){
		
	}

	public Song(String interpret, String titel, String genre, int votes)
	{
		this.interpret = interpret;
		this.titel = titel;
		this.genre = genre;
		this.votes = votes;
	}

	public Song(String titel, String interpret, String genre)
	{
		this.titel = titel;
		this.interpret = interpret;
		this.genre = genre;
	}

	public Song()
	{
		this.interpret = "Fuck My Ass";
		this.titel = "Test Titel Boy";
		this.genre = "Hip Hop";
		votes = 20;
	}

	public String getInterpret()
	{
		return interpret;
	}

	public void setInterpret(String interpret)
	{
		this.interpret = interpret;
	}

	public String getTitel()
	{
		return titel;
	}

	public void setTitel(String titel)
	{
		this.titel = titel;
	}

	public String getGenre()
	{
		return genre;
	}

	public void setGenre(String genre)
	{
		this.genre = genre;
	}

	public int getVotes()
	{
		return votes;
	}

	public void setVotes(int votes)
	{
		this.votes = votes;
	}

	public int getId_nummer()
	{
		return id_nummer;
	}

	public void setId_nummer(int id_nummer)
	{
		this.id_nummer = id_nummer;
	}

	public int getGast_ID()
	{
		return gast_ID;
	}

	public void setGast_ID(int gast_ID)
	{
		this.gast_ID = gast_ID;
	}

	public String getLength()
	{
		return length;
	}

	public void setLength(String length)
	{
		this.length = length;
	}

}
