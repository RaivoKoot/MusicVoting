package data;

public class Vote {
	private int gast_nr;
	private int musik_nr;

	public Vote(int gast_nr, int musik_nr)
	{
		this.gast_nr = gast_nr;
		this.musik_nr = musik_nr;
	}

	public Vote(int musik_nr)
	{
		this.musik_nr = musik_nr;
	}

	public int getMusik_nr()
	{
		return musik_nr;
	}

	public void setMusik_nr(int musik_nr)
	{
		this.musik_nr = musik_nr;
	}

	public int getGast_nr()
	{
		return gast_nr;
	}

	public void setGast_nr(int gast_nr)
	{
		this.gast_nr = gast_nr;
	}

}
