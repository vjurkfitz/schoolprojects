/* Structure holding information about the types of properties 
 * in a street or postal code */
public class HouseTypes
{
	// Either the street or postal code
	private String id;
	
	// Number of commercial properties
	private int commercial;
	
	// Number of one family dwellings
	private int oneFam;
	
	// Number of multi family dwellings
	private int multiFam;
	
	// Constructor
	public HouseTypes(String id, int commercial, int oneFam, int multiFam)
	{
		this.id = id;
		this.commercial = commercial;
		this.oneFam = oneFam;
		this.multiFam = multiFam;
	}

	// Getters
	public String getID() {
		return id;
	}

	public int getCommercial() {
		return commercial;
	}

	public int getOneFam() {
		return oneFam;
	}

	public int getMultiFam() {
		return multiFam;
	}
}

