import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/* Class holding methods that prepare data for the dashboard.
 * Used a different class for this so the code in Assignment1.java
 * wouldn't be too long and confusing.
 */
public abstract class PrepareData {
	
	/* Gets average value and standard deviation for properties
	   in a list */ 
	public static String getItem1(List<Property> table)
	{
		
		double mean = 0;
		double sd = 0;
		
		// Holds all the property values of the current dataset
		ArrayList<Double> values = new ArrayList<Double>();
		
		for (Property p : table)
		{
			/* Sometimes the data is incomplete, so
			 * there's an error when trying to parse to double 
			 */
			try
			{
				if (Double.parseDouble(p.getCurrValue()) > 0) {
					mean += Double.parseDouble(p.getCurrValue());
					values.add(Double.parseDouble(p.getCurrValue()));
				}
			} catch (Exception e) {}
		}

		mean /= table.size();
		
		sd = Math.sqrt(differenceOfSquares(values,mean)/values.size());
		
		// Formats number to .2
		double meanRounded = Math.round(mean);
		
		if (meanRounded > mean)
			meanRounded--;
		
		mean = Math.round((mean - meanRounded) * 100);
		mean = (mean / 100) + meanRounded;
		
		// Formats sd to .2
		double sdRounded = Math.round(sd);
		
		if (sdRounded > sd)
			sdRounded--;
		
		sd = Math.round((sd - sdRounded) * 100);
		sd = (sd / 100) + sdRounded;
	
		return "" + mean + " " + sd;
	}
	
	/* Gets average year a property was built and standard deviation for 
	   properties in a list */
	public static String getItem2(List<Property> table)
	{
		double sd;
		
		// Average year is an int instead of a double
		int avgYear = 0;
		
		// Average age = current year - average year
		int mean = 0;
		
		// All years
		ArrayList<Double> values = new ArrayList<Double>();
		
		for (Property p : table)
		{
			if (Integer.parseInt(p.getYearBuilt()) > 0) {
				avgYear += Integer.parseInt(p.getYearBuilt());
				values.add(Double.parseDouble(p.getYearBuilt()));
			}
		}
		
		// In many cases, this information is incomplete
		if (values.size() > 0)
		{
			avgYear /= values.size();
			mean = Calendar.getInstance().get(Calendar.YEAR) - avgYear;
			sd = Math.sqrt(differenceOfSquares(values,avgYear)/values.size());
		}
		else
			sd = 0;
		
		// Formats sd to .2
		double sdRounded = Math.round(sd);
		
		if (sdRounded > sd)
			sdRounded--;
		
		sd = Math.round((sd - sdRounded) * 100);
		sd = (sd / 100) + sdRounded;
		
		return "" + mean + " " + sd;
	}

	// Gets total value change for properties in a list	
	public static String getItem3(List<Property> table)
	{
		// Total current value
		int totalCurr = 0;
		
		// Total previous value
		int totalPrev = 0;
		
		for (Property p : table)
		{
			try
			{
				if (Integer.parseInt(p.getCurrValue()) > 0 &&
					Integer.parseInt(p.getPrevValue()) > 0) {
					
					totalCurr += Integer.parseInt(p.getCurrValue());
					totalPrev += Integer.parseInt(p.getPrevValue());
				}
			} catch (Exception e) {} // In case of incomplete data, that property is ignored
		}
		
		return "" + (totalCurr-totalPrev);
	}
	
	/* Gets number of commercial, one family dwellings
	 * and multi family dwellings in a list of properties 	 */
	public static String getItem4(List<Property> table)
	{
		
		// Number of commercial buildings
		int nCommercial = 0;
		
		// Number of one family dwellings
		int nOneFam = 0;
		
		// Number of multi family dwellings
		int nMultiFam = 0;
		
		for (Property p : table)
		{
			if (p.getCategory().contains("Commercial"))
				nCommercial++;
			
			else if (p.getCategory().contains("One"))
				nOneFam++;
			
			else if (p.getCategory().contains("Multiple"))
				nMultiFam++;
		}
		
		return "" + nCommercial + " " + nOneFam + " " + nMultiFam;
	}

	/* Gets maximum and minimum value of properties in a list */
	public static String getItem5(List<Property> table)
	{
		long maxValue = 0;
		long minValue = -99;
		
		// Get max and min value
		for (Property p : table)
		{
			try
			{
				long currValue = Long.parseLong(p.getCurrValue()); 
				if (currValue > maxValue)
				{
					maxValue = currValue;
					
					if (minValue == -99)
						minValue = maxValue;
				}
				
				if (currValue > 0 && currValue < minValue)
					minValue = currValue;
				
			} catch (Exception e) {}	// If data is incomplete, it's just ignored
		}
		
		return "" + maxValue + " " + minValue;
	}

	/* Gets number of properties by increments of 25k in a list */
	public static ObservableList<ValueBracket> getHouseValueItems(List<Property> table, double maxValue)
	{
		ArrayList<ValueBracket> houses = new ArrayList<ValueBracket>();
		
		int [] increments = new int[(int)(maxValue / 25000) + 1];
		
		ArrayList<Double> values = new ArrayList<Double>();
		
		for (Property p : table)
		{
			try
			{
				values.add(Double.parseDouble(p.getCurrValue()));
			}
			catch (Exception e) {} // Incomplete data is ignored
		}
		
		for (double x : values)
		{
			int i = (int) x / 25000;
			increments[i]++;
		}
		
		// Ignores first 100k
		for (int i =4; i<increments.length; i++)
		{
			int min = i * 25;
			int max = (i+1) * 25;
			String sections = min + "k - " + max + "k";
			
			if (increments[i] > 0)
				houses.add(new ValueBracket(sections,increments[i]));
			
			// Stops when it reaches the first empty increment
			else if (!houses.isEmpty())
				break;
		}
		
		return FXCollections.observableList(houses);
	}

	
	/* Prepares table showing number of properties by increments of 25k 	 */
	public static TableView<ValueBracket> prepareItem5Table()
	{
		final TableView<ValueBracket> houseValues = new TableView<ValueBracket>();
		
		// Two columns: price and n
		final TableColumn<ValueBracket,String> bracket = new TableColumn<ValueBracket,String>("Price (in $CAD)");
		bracket.setCellValueFactory(
	    	    new PropertyValueFactory<>("price") 
	    	);
		bracket.setSortable(false);
		
		final TableColumn<ValueBracket,Integer> nBracket = new TableColumn<ValueBracket,Integer>("N");
		nBracket.setCellValueFactory(
	    	    new PropertyValueFactory<>("n") 
	    	);
		nBracket.setSortable(false);
		
		houseValues.getColumns().addAll(bracket,nBracket);
		
		houseValues.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		return houseValues;
	}
	
	// Used to calculate the standard deviation
	protected static double differenceOfSquares(List<Double> list, double mean)
	{
		double sum = 0;
		for (Double curr : list)
		{
			sum += (curr-mean) * (curr-mean);
		}	
		return sum;
	}

}
