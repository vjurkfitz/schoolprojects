import javafx.collections.ObservableList;

/* Structure holding information about the maximum and minimum values of properties 
 * in a street or postal code */
public class MaxMin {
	
	// Either the street or postal code
	private String id;
	
	// Maximum value
	private double maxValue;
	
	// Minimum value
	private double minValue;
	
	/* List of items to be shown in a table of values
	 * by increments of 25 thousand  */
	private ObservableList<ValueBracket> increments;
	
	// Constructor
	public MaxMin(String id, double maxValue, double minValue, ObservableList<ValueBracket> increments)
	{
		this.id = id;
		this.maxValue = maxValue;
		this.minValue = minValue;
		this.increments = increments;
	}
	
	// Getters
	public String getID() {
		return id;
	}

	public double getMaxValue() {
		return maxValue;
	}

	public double getMinValue() {
		return minValue;
	}

	public ObservableList<ValueBracket> getIncrements() {
		return increments;
	}
	
}
