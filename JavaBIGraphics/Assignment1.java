import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// Main class
public class Assignment1 extends Application {
	
	// Main method
	public static void main(String[] args) {
		launch(args);
	}
	
	// Starts main window
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		GridPane root = new GridPane();
		
		Label getAddLbl = new Label("Enter the file location: ");
		GridPane.setConstraints(getAddLbl, 0, 0);
		GridPane.setColumnSpan(getAddLbl, 2);
		
		TextField getFileLoc = new TextField();
		getFileLoc.setMinWidth(350);
		GridPane.setConstraints(getFileLoc, 0, 1);
		GridPane.setColumnSpan(getFileLoc, 2);
		
		Button selectButton = new Button("...");
		GridPane.setConstraints(selectButton, 2, 1);
		
		Button loadButton = new Button("Load dataset");
		GridPane.setConstraints(loadButton, 0, 2);
		
		Button openDash = new Button("Open dashboard");
		GridPane.setConstraints(openDash, 1, 2);
		
		Label status = new Label("");
		GridPane.setConstraints(status, 0, 3);
		
		// Table with all Properties on the file
		TableView<Property> table = setTable();
		
		selectButton.setOnMouseClicked(clickEvent -> getFileLoc.setText(getFile()));
		loadButton.setOnMouseClicked(clickEvent -> readCSVFile(getFileLoc.getText(),table,status));
		openDash.setOnMouseClicked(clickEvent -> startMainDashboard(table));
		
		// Set main window 
		root.getChildren().addAll(getAddLbl,getFileLoc,loadButton,openDash,selectButton,status);
		
		primaryStage.setScene(new Scene(root, 400, 100));
		primaryStage.setTitle("");
		primaryStage.setResizable(false);
	
		primaryStage.show();
	}
	
	// Opens a pop-up window to select a file. Its path is returned as a string.
	protected String getFile()
	{
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV",
	        "csv");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION)
	    {
	       return chooser.getSelectedFile().getPath();
	    }
	    return "";
	}
	
	protected void startMainDashboard(TableView<Property> table)
	{
		GridPane root = setGridPane(table);
		root.setPadding(new Insets(10));
		
		Stage newStage = new Stage();
		newStage.setTitle("Dashboard");
		Scene newScene = new Scene(root, 900, 600);
		
		//Tell the stage which scene to display
		newStage.setScene(newScene);
		
		//make the stage visible
		newStage.show();
	}
	
	
	// Creates a GridPane that will show the dashboard
	protected GridPane setGridPane(TableView<Property> table)
	{
		GridPane root = new GridPane();
		
		// Adjusts columns
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(33);
		
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(33);
		
		ColumnConstraints column3 = new ColumnConstraints();
		column3.setPercentWidth(33);
		column3.setHgrow(Priority.ALWAYS);
		
		root.getColumnConstraints().addAll(column1,column2,column3);
		
		
		// Sets the 6 displays
		// Average property value and standard deviation
		VBox item1 = PrepareMainDisplays.setItem1(table.getItems());
		GridPane.setConstraints(item1, 0, 0);
		
		// Average house age and standard deviation
		VBox item2 = PrepareMainDisplays.setItem2(table.getItems());
		GridPane.setConstraints(item2, 1, 0);
		
		// Total house value change
		VBox item3 = PrepareMainDisplays.setItem3(table.getItems());
		GridPane.setConstraints(item3, 2, 0);
		
		// Types of properties
		VBox item4 = PrepareMainDisplays.setItem4(table.getItems());
		GridPane.setConstraints(item4, 0, 1);
		
		// Maximum value, minimum value and table with number of 
		// properties by increments
		HBox item5 = PrepareMainDisplays.setItem5(table.getItems());
		GridPane.setConstraints(item5, 1, 1);
		GridPane.setColumnSpan(item5, 2);
		
		root.getChildren().addAll(item1,item2,item3,item4,item5);
		
		return root;
	}
	
	// Reads a CSV file into a table
	public void readCSVFile(String filename, TableView<Property> table, Label status)
	{
		
	    //Create a list of data:
	    final ObservableList<Property> data = FXCollections.observableArrayList();
	    
		try(BufferedReader reader = Files.newBufferedReader(Paths.get(filename)))
		{
			String line = "";
			// Ignores first line -> Column names
			reader.readLine();
			
			//get a line of text from the file
			line = reader.readLine();
			
			while(line != null){
				
				Property propTemp;
				
				// In case the last item is not filled
				if (line.charAt(line.length()-1) == ',')
				{
					line += '0';
				}
				
				//Split the line by commas
				String [] partsOfLine = line.split(",");
				
				try{
				
					propTemp = new Property(partsOfLine[0],partsOfLine[1],
						partsOfLine[2],partsOfLine[3],partsOfLine[4],
						partsOfLine[5],partsOfLine[6]);
					
					data.add(propTemp);
				}
				catch (Exception e)
				{ }
				
				line = reader.readLine();
				
			}
			table.setItems(data);
			status.setText("File was loaded.");
			
		}catch(IOException ioe)
		{
			System.out.println("Problem reading csv: " + ioe.getMessage());
			status.setText("There was an error.");
		}
	}
	
	// Sets the property table
	@SuppressWarnings("unchecked")
	private TableView<Property> setTable()

	{
		TableView<Property> table = new TableView<Property>();
		
		TableColumn<Property, String> pidCol = new TableColumn<>("PID");	
	    TableColumn<Property, String> catCol = new TableColumn<>("Category");
	    TableColumn<Property, String> streetCol = new TableColumn<>("Street Name");
	    TableColumn<Property, String> postCol = new TableColumn<>("Postal Code");
	    TableColumn<Property, String> currValCol = new TableColumn<>("Current Value");
	    TableColumn<Property, String> prevValCol = new TableColumn<>("Previous Value");
	    TableColumn<Property, String> yearCol = new TableColumn<>("Year Built");
	    table.getColumns().addAll(pidCol, catCol, streetCol, postCol, currValCol, prevValCol, yearCol);
	    
	    //Tell the columns what getter function to call for their data:
	    pidCol.setCellValueFactory(
	    	    new PropertyValueFactory<Property,String>("PID") 
	    	);
	    
	    catCol.setCellValueFactory(
	    	    new PropertyValueFactory<Property,String>("Category")
	    	);
	    
	    streetCol.setCellValueFactory(
	    	    new PropertyValueFactory<Property,String>("StreetName")
	    	);
		
		
	    postCol.setCellValueFactory(
	    	    new PropertyValueFactory<Property,String>("PostalCode")
	    	);
	    
	    currValCol.setCellValueFactory(
	    	    new PropertyValueFactory<Property,String>("CurrValue")
	    	);
	    
	    prevValCol.setCellValueFactory(
	    	    new PropertyValueFactory<Property,String>("PrevValue")
	    	);
	    
	    yearCol.setCellValueFactory(
	    	    new PropertyValueFactory<Property,String>("YearBuilt")
	    	);
		
		return table;
		
	}


}