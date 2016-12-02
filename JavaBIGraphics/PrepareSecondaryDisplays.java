import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/* Class holding methods that prepare the secondary displays
 * (with information by street or postal code).
 * Used a different class for this so the code in Assignment1.java
 * wouldn't be too long and confusing.
 */
public abstract class PrepareSecondaryDisplays {
	

	/* Opens a new window with a list showing all the streets in the dataset.
	 * When a street is selected, the needed information is shown below it. 	 
	 * table: list of properties
	 * what: string used to select the appropriate method to get info
	 * */
	@SuppressWarnings("unchecked")
	public static void getInfoByStreet(List<Property> table,String what)
	{
		VBox root = new VBox();
		root.setPadding(new Insets(10,10,10,10));
		root.setSpacing(10);
		
		Stage newStage = new Stage();
		newStage.setTitle("Data by Street");
		Scene newScene = new Scene(root, 500, 500);
		ListView<String> list = new ListView<>();
		List items;
		
		/* Table for information about properties by increments. 
		*  Used only for item5 		*/
		final TableView<ValueBracket> houseValues = PrepareData.prepareItem5Table();
		
		// Will show the requested information
		Text label = new Text("No street selected.");
		label.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		
		
		// Get a list of all streets in the dataset
		ArrayList<String> streets = new ArrayList<>();
		
		for (Property p : table)
		{
			if (!streets.contains(p.getStreetName()))
				streets.add(p.getStreetName());
		}
		
		/* List of items is parameterized to appropriate class
		 * according to what kind of information has been requested */
		switch (what)
		{
			case "age":
			case "price": items = new ArrayList<AvgValueByID>(); break;
			
			case "totalValueChange": items = new ArrayList<TotalValueChange>(); break;
			
			case "houseType": items = new ArrayList<HouseTypes>(); break;
			
			case "maxMin": items = new ArrayList<MaxMin>(); break;
			
			default: System.out.println("Error: " + what);
					 return;
		}
		
		/* Separates the whole dataset into different lists
 		 * or properties according to their street */
		for (String s : streets)
		{
			ArrayList<Property> aux = new ArrayList<Property>();
			
			for (Property p : table)
			{
				if (p.getStreetName().compareTo(s) == 0)
					aux.add(p);
			}
			
			String [] data = {"",""};
			
			// Gets the needed information about the list of properties
			switch (what)
			{
				case "price":
					data = PrepareData.getItem1(aux).split(" ");
					items.add(new AvgValueByID(s,Double.parseDouble(data[0]),Double.parseDouble(data[1])));
					break;
							  
				case "age":
					data = PrepareData.getItem2(aux).split(" ");
				  	items.add(new AvgValueByID(s,Double.parseDouble(data[0]),Double.parseDouble(data[1])));
				  	break;
				  			
				case "totalValueChange":
					data[0] = PrepareData.getItem3(aux);
					items.add(new TotalValueChange(s,Double.parseDouble(data[0])));
					break;
				
				case "houseType":
					data = PrepareData.getItem4(aux).split(" ");
					items.add(new HouseTypes(s,Integer.parseInt(data[0]),Integer.parseInt(data[1]),Integer.parseInt(data[2])));
					break;
				
				case "maxMin":
					data = PrepareData.getItem5(aux).split(" ");
					items.add(new MaxMin(s,Double.parseDouble(data[0]),Double.parseDouble(data[1]),PrepareData.getHouseValueItems(aux, Double.parseDouble(data[0]))));
					break;
			}
		}
		
		list.setItems(FXCollections.observableArrayList(streets));
		
		/* When a street is selected, the label below the list changes
		 * to show the information */
		list.getSelectionModel().selectedItemProperty().addListener(
	            new ChangeListener<String>() {
	                public void changed(ObservableValue<? extends String> ov, 
	                    String old_val, String new_val) {
	                	
	                	label.setText(decideSetLabel(items,what,new_val));
	                	
	                	// In case the information needed include 
	                	// number of properties by brackets, also sets the table
	                	if (what.compareTo("maxMin") == 0)
	                	{
	                		for (MaxMin mamin : (ArrayList<MaxMin>) items)
	                    	{
	                    		if (mamin.getID().compareTo(new_val) == 0)
	                    		{
	                    			houseValues.setItems(mamin.getIncrements());
	                    		}
	                    	}
	                	}
	            }
	        });
		
		/* Method to open a new window with more detailed information
		 * in case of double click */
		list.setOnMouseClicked(new EventHandler<MouseEvent>()
				{
					@Override
				    public void handle(MouseEvent click) {
		
				        if (click.getClickCount() == 2) {
				        	
				        	String st = list.getSelectionModel().getSelectedItem();
				        	
				        	// Creates a new list with all the properties in the selected street
				        	ArrayList<Property> stProperties = new ArrayList<>();
				        	
				        	for (Property p : table)
				        	{
				        		if (p.getStreetName().compareTo(st) == 0)
				        			stProperties.add(p);
				        	}
				        	
				        	// Opens a new window
				        	getValueByPostalCode(stProperties,what);
				        }
				    }
				});
		
		root.getChildren().addAll(list,label);
		
		if (what.compareTo("maxMin") == 0)
			root.getChildren().add(houseValues); // Also adds the table if needed
		
		//Tell the stage which scene to display
		newStage.setScene(newScene);
		
		//make the stage visible
		newStage.show();
	}
	
	// Decides what to write on the label according to the information needed
	@SuppressWarnings("unchecked")
	private static String decideSetLabel(List items, String what, String new_val)
	{
		
		// Gets what kind of structures the list of storing
		String whatClassIsList = items.get(0).getClass().toString();
		
		if (whatClassIsList.contains("AvgValueByID"))
			return setLabelAvgValueAge((ArrayList<AvgValueByID>) items,what,new_val);
		
		else if (whatClassIsList.contains("HouseTypes"))
			return setLabelHouseTypes((ArrayList<HouseTypes>) items,new_val);
		
		else if (whatClassIsList.contains("MaxMin"))
			return setLabelMaxMin((ArrayList<MaxMin>) items,new_val);
		
		else if (whatClassIsList.contains("TotalValueChange"))
			return setLabelValueChange((ArrayList<TotalValueChange>) items,new_val);
		
		else
			return "This got way too complicated and there was an error.";
	}
	
	// Writes information on the label for maximum and minimum value
	private static String setLabelMaxMin(ArrayList<MaxMin> items, String new_val)
	{
		for (MaxMin mamin : items)
    	{
    		if (mamin.getID().compareTo(new_val) == 0)
    			return "Maximum value: $" + ((long)mamin.getMaxValue()) + "\nMinimum value: $" + mamin.getMinValue();
    	}
	
		return "ERROR";
	}
	
	// Writes information on the label for average house value or age
	private static String setLabelAvgValueAge(ArrayList<AvgValueByID> items, String what, String new_val)
	{
		for (AvgValueByID avs : items)
    	{
    		if (avs.getID().compareTo(new_val) == 0)
    		{
    			if (what.compareTo("age") == 0)
    				return "Average Property Age: " + (int) avs.getAvgValue() + " years\nStandard Deviation: " + avs.getSd();
    			
    			else
    				return "Average Property Value: $" + avs.getAvgValue() + "\nStandard Deviation: " + avs.getSd();
    		}
    	}
		
		return "ERROR";
	}
	
	// Writes information on the label for house types
	private static String setLabelHouseTypes(ArrayList<HouseTypes> items, String new_val)
	{
		for (HouseTypes ht : items)
    	{
    		if (ht.getID().compareTo(new_val) == 0)
    			return "House types:\nCommercial: " + ht.getCommercial() + "\nOne family dwellings: " + ht.getOneFam() + "\nMulti family dwellings: " + ht.getMultiFam();
    	}
		
		return "ERROR";
	}
	
	// Writes information on the label for total value change
	private static String setLabelValueChange(ArrayList<TotalValueChange> items, String new_val)
	{
		for (TotalValueChange tvc : items)
    	{
    		if (tvc.getID().compareTo(new_val) == 0)
    			return "Total value change: $" + (long)tvc.getTotalValue();
    	}
		
		return "ERROR";
	}
	
	/* Opens a new window with a list showing all the postal codes in the dataset.
	 * When a postal code is selected, the needed information is shown below it. 	 
	 * table: list of properties
	 * what: string used to select the appropriate method to get info
	 * */
	@SuppressWarnings("unchecked")
	protected static void getValueByPostalCode(List<Property> table, String what)
	{
		VBox root = new VBox();
		root.setPadding(new Insets(10,10,10,10));
		root.setSpacing(10);
		
		Stage newStage = new Stage();
		newStage.setTitle("Data by Postal Code");
		Scene newScene = new Scene(root, 500, 500);
		ListView<String> list = new ListView<>();
		List items;
		
		/* Table for information about properties by increments. 
		*  Used only for item5 		*/
		final TableView<ValueBracket> houseValues = PrepareData.prepareItem5Table();
		
		// Will show the requested information
		Text label = new Text("No postal code selected.");
		label.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		
		// Get a list of all postal codes in the dataset
		ArrayList<String> postalCodes = new ArrayList<>();
		
		for (Property p : table)
		{
			if (!postalCodes.contains(p.getPostalCode()))
				postalCodes.add(p.getPostalCode());
		}
		
		/* List of items is parameterized to appropriate class
		 * according to what kind of information has been requested */
		switch (what)
		{
			case "age":
			case "price": items = new ArrayList<AvgValueByID>(); break;
			
			case "totalValueChange": items = new ArrayList<TotalValueChange>(); break;
			
			case "houseType": items = new ArrayList<HouseTypes>(); break;
			
			case "maxMin": items = new ArrayList<MaxMin>(); break;
			
			default: System.out.println("Error: " + what);
					 return;
		}
		
		// Gets all the properties in a given postal code
		for (String pc : postalCodes)
		{
			ArrayList<Property> aux = new ArrayList<Property>();
			
			for (Property p : table)
			{
				if (p.getPostalCode().compareTo(pc) == 0)
					aux.add(p);
			}
			
			String [] data = {"",""};
			
			// Gets appropriate data
			switch (what)
			{
				case "price":
					data = PrepareData.getItem1(aux).split(" ");
					items.add(new AvgValueByID(pc,Double.parseDouble(data[0]),Double.parseDouble(data[1])));
					break;
							  
				case "age":
					data = PrepareData.getItem2(aux).split(" ");
				  	items.add(new AvgValueByID(pc,Double.parseDouble(data[0]),Double.parseDouble(data[1])));
				  	break;
				  			
				case "totalValueChange":
					data[0] = PrepareData.getItem3(aux);
					items.add(new TotalValueChange(pc,Double.parseDouble(data[0])));
					break;
				
				case "houseType":
					data = PrepareData.getItem4(aux).split(" ");
					items.add(new HouseTypes(pc,Integer.parseInt(data[0]),Integer.parseInt(data[1]),Integer.parseInt(data[2])));
					break;
				
				case "maxMin":
					data = PrepareData.getItem5(aux).split(" ");
					items.add(new MaxMin(pc,Double.parseDouble(data[0]),Double.parseDouble(data[1]),PrepareData.getHouseValueItems(aux, Double.parseDouble(data[0]))));
					break;
			}
			
		}
		
		list.setItems(FXCollections.observableArrayList(postalCodes));
		
		/* When a postal code is selected, the information about the
		 * properties belonging to it is shown below the list. 		 */
		list.getSelectionModel().selectedItemProperty().addListener(
	            new ChangeListener<String>() {
	                public void changed(ObservableValue<? extends String> ov, 
	                    String old_val, String new_val)
	                {
	                	// Decides what to write on the label
	                	label.setText(decideSetLabel(items,what,new_val));
	                	
	                	// If the table is needed
	                	if (what.compareTo("maxMin") == 0)
	                	{
	                		for (MaxMin mamin : (ArrayList<MaxMin>) items)
	                    	{
	                    		if (mamin.getID().compareTo(new_val) == 0)
	                    		{
	                    			houseValues.setItems(mamin.getIncrements());
	                    		}
	                    	}
	                	}
	                }
	            });
		
		root.getChildren().addAll(list,label);
		
		if (what.compareTo("maxMin") == 0)
			root.getChildren().add(houseValues); // Adds table if needed
		
		//Tell the stage which scene to display
		newStage.setScene(newScene);
		
		//make the stage visible
		newStage.show();
	}
}
