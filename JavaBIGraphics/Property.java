// Structure describing a real estate property
public class Property {
	
	// Property ID
	private String PID;
	
	// Category (commercial, one family dwelling, etc)
	private String category;
	
	// Street name
	private String streetName;
	
	// Postal code
	private String postalCode;
	
	// Current property value
	private String currValue;
	
	// Previous property value
	private String prevValue;
	
	// Year it was built
	private String yearBuilt;
	
	// Constructor
	public Property(String PID, String category, String streetName,
					String postalCode, String currValue, String prevValue,
					String yearBuilt){
		
		this.PID = PID;
		this.category = category;
		this.streetName = streetName;
		this.postalCode = postalCode;
		this.currValue = currValue;
		this.prevValue = prevValue;
		this.yearBuilt = yearBuilt;
		
	}
	
	// Constructor with no parameters -> error
	public Property()
	{
		this.PID = "ERROR";
		this.category = "ERROR";
		this.streetName = "ERROR";
		this.postalCode = "ERROR";
		this.currValue = "ERROR";
		this.prevValue = "ERROR";
		this.yearBuilt = "ERROR";
		
	}

	
	// GETTERS ***************************************************************
	
	public String getPID() {
		return PID;
	}

	public String getCategory() {
		return category;
	}

	public String getStreetName() {
		return streetName;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getCurrValue() {
		return currValue;
	}

	public String getPrevValue() {
		return prevValue;
	}

	public String getYearBuilt() {
		return yearBuilt;
	}
}
