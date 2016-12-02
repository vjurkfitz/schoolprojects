/* Structure holding information about total value change of properties 
 * in a street or postal code */
public class TotalValueChange {
	
	// Either the street or postal code
	String id;
	
	// Total value change
	double totalValue;
	
	// Constructor
	public TotalValueChange(String id, double totalValue) {
		super();
		this.id = id;
		this.totalValue = totalValue;
	}

	// Getters
	public String getID() {
		return id;
	}

	public double getTotalValue() {
		return totalValue;
	}
	
}
