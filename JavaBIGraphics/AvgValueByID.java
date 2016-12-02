/* Structure holding information about the average values of properties 
 * in a street or postal code */
public class AvgValueByID {
	
	// Either the street name or postal code
	private String id;
	
	// Average value 
	private double avgValue;
	
	// Standard deviation
	private double sd;
	
	// Constructor
	public AvgValueByID(String id, double avgValue, double sd)
	{
		this.id = id;
		this.avgValue = avgValue;
		this.sd = sd;
	}

	// Getters
	public String getID() {
		return id;
	}

	public double getAvgValue() {
		return avgValue;
	}

	public double getSd() {
		return sd;
	}

}
