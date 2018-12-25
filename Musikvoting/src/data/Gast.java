package data;

public class Gast {

	private String vorname;
	private String nachname;
	private int id;
	private int partyID;
	private String adminKey;
	private String adelstitel;

	public Gast(String vorname, String nachname, int id)
	{
		this.vorname = vorname;
		this.nachname = nachname;
		this.id = id;
	}
	
	public Gast(){};

	public String getNachname()
	{
		return nachname;
	}

	public void setNachname(String nachname)
	{
		this.nachname = nachname;
	}

	public String getVorname()
	{
		return vorname;
	}

	public void setVorname(String vorname)
	{
		this.vorname = vorname;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getPartyID()
	{
		return partyID;
	}

	public void setPartyID(int partyID)
	{
		this.partyID = partyID;
	}

	public String getAdminKey()
	{
		return adminKey;
	}

	public void setAdminKey(String adminKey)
	{
		this.adminKey = adminKey;
	}

	public String getAdelstitel()
	{
		return adelstitel;
	}

	public void setAdelstitel(String adelstitel)
	{
		this.adelstitel = adelstitel;
	}

}
