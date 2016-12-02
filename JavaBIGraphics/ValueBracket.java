/* Structure holding information for how many properties are in a price range */
public class ValueBracket {
	
	// Price range 
	private String price;
	
	// Number of properties
	private int n;
	
	// Constructor
	public ValueBracket(String price, int n)
	{
		this.price = price;
		this.n = n;
	}

	// Getters
	public String getPrice() {
		return price;
	}

	public int getN() {
		return n;
	}

}
