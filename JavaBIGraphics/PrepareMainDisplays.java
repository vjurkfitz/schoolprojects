import java.util.List;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/* Class holding methods that prepare the first dashboard.
 * Used a different class for this so the code in Assignment1.java
 * wouldn't be too long and confusing.
 */
public abstract class PrepareMainDisplays
{
	// Sets the first display: Average property values and standard deviation
	public static VBox setItem1(List<Property> table)
	{
		
		VBox aux = new VBox();
		aux.setStyle("-fx-background-color: #FFFFFF; -fx-alignment: center;");
		
		
		// Average property value
		Text propValue = new Text("Average Property Value:");
		propValue.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		
		String [] data = PrepareData.getItem1(table).split(" ");
		
		Text propValueNo = new Text("CAD$" + data[0]);
		propValueNo.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		propValueNo.setFill(Color.BLUE);
		
		// Standard deviation
		Text sdText = new Text("Standard Deviation:");
		sdText.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		
		Text sdValue = new Text("CAD$" + data[1]);
		sdValue.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		sdValue.setFill(Color.DODGERBLUE);
		
		aux.setPadding(new Insets(50));
		aux.setSpacing(10);
		
		aux.getChildren().addAll(propValue,propValueNo,sdText,sdValue);
		
		// Opens a new display in case of double click showing info by street
		aux.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
		    @Override
		    public void handle(MouseEvent click) {

		        if (click.getClickCount() == 2) {

		        	PrepareSecondaryDisplays.getInfoByStreet(table,"price");
		        }
		    }
		});
		
		return aux;
	}
	
	// Sets the second display: Average house age and standard deviation
	public static VBox setItem2(List<Property> table)
	{
		VBox aux = new VBox();
		aux.setStyle("-fx-background-color: #FFFFFF; -fx-alignment: center;");
		
		String [] data = PrepareData.getItem2(table).split(" ");
		
		
		// House age
		Text avgHouseAgeText = new Text("Average House Age:");
		avgHouseAgeText.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		
		Text avgHouseAgeData = new Text(data[0] + " years");
		avgHouseAgeData.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		avgHouseAgeData.setFill(Color.BLUE);
		
		// Standard deviation
		Text sdText = new Text("Standard Deviation:");
		sdText.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		
		Text sdData = new Text(data[1] + " years");
		sdData.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		sdData.setFill(Color.DODGERBLUE);
		
		aux.setPadding(new Insets(50));
		aux.setSpacing(10);
		
		aux.getChildren().addAll(avgHouseAgeText,avgHouseAgeData,sdText,sdData);
		
		// Opens a new display in case of double click showing info by street
		aux.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
		    @Override
		    public void handle(MouseEvent click) {

		        if (click.getClickCount() == 2) {
		        	PrepareSecondaryDisplays.getInfoByStreet(table,"age");
		        }
		    }
		});
		
		return aux;
	}

	// Sets the third display: Total house value change
	public static VBox setItem3(List<Property> table)
	{
		VBox aux = new VBox();
		aux.setStyle("-fx-background-color: #FFFFFF; -fx-alignment: center;");
		
		Text houseValueChangeText = new Text("Total House Value Change:");
		houseValueChangeText.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		
		Text houseValueChangeData = new Text("CAD$" + PrepareData.getItem3(table));
		houseValueChangeData.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
		houseValueChangeData.setFill(Color.GREEN);
		
		aux.setPadding(new Insets(50));
		aux.setSpacing(10);
		
		aux.getChildren().addAll(houseValueChangeText,houseValueChangeData);
		
		// Opens a new display in case of double click showing info by street
		aux.setOnMouseClicked(new EventHandler<MouseEvent>() {

		    @Override
		    public void handle(MouseEvent click) {

		        if (click.getClickCount() == 2) {
		        	PrepareSecondaryDisplays.getInfoByStreet(table,"totalValueChange");
		        }
		    }
		});
		
		return aux;
	}

	// Sets the fourth display: A bar chart showing the types of property
	@SuppressWarnings("unchecked")
	public static VBox setItem4(List<Property> table)
	{
		VBox aux = new VBox();
		aux.setStyle("-fx-background-color: #FFFFFF; -fx-alignment: center;");
		
		String [] data = PrepareData.getItem4(table).split(" ");
		
		
		// Create the bar chart
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		
		final BarChart<String,Number> chart = new BarChart<String,Number>(xAxis,yAxis);
		chart.setTitle("Property Types");
		
		xAxis.setLabel("");
		yAxis.setLabel("N");
		
		XYChart.Series<String,Number> commercial = new XYChart.Series<String,Number>();
		commercial.setName("Commercial");
		commercial.getData().add(new XYChart.Data<String,Number>("",Double.parseDouble(data[0])));
		
		XYChart.Series<String, Number> oneFam = new XYChart.Series<String,Number>();
		oneFam.setName("One Family Dwelling");
		oneFam.getData().add(new XYChart.Data<String,Number>("",Double.parseDouble(data[1])));
		
		XYChart.Series<String, Number> multiFam = new XYChart.Series<String, Number>();
		multiFam.setName("Multi Family Dwelling");
		multiFam.getData().add(new XYChart.Data<String,Number>("",Double.parseDouble(data[2])));
		
	    chart.getData().addAll(oneFam,multiFam,commercial);
	    chart.setCategoryGap(0);
	    chart.setBarGap(0);
	    chart.setHorizontalGridLinesVisible(false);
	    
		aux.setPadding(new Insets(50));
		aux.setSpacing(10);
		
		aux.getChildren().add(chart);
		
		// Opens a new display in case of double click showing info by street
		aux.setOnMouseClicked(new EventHandler<MouseEvent>() {

		    @Override
		    public void handle(MouseEvent click) {

		        if (click.getClickCount() == 2) {
		        	PrepareSecondaryDisplays.getInfoByStreet(table,"houseType");
		        }
		    }
		});
		
		return aux;
	}
	
	/* Sets the fifth display: maximum and minimum value
	 * 		+ table with number of properties by price increments 	 */
	public static HBox setItem5(List<Property> table)
	{
		HBox aux = new HBox();
		aux.setStyle("-fx-background-color: #FFFFFF; -fx-alignment: center;");
		
		// Left: max and min values
		VBox left = new VBox();
		left.setStyle("-fx-background-color: #FFFFFF; -fx-alignment: center;");
		
		String [] data = PrepareData.getItem5(table).split(" ");
		
		// Maximum value
		Text maxValueText = new Text("Maximum Property Value:");
		maxValueText.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		
		Text maxValueData = new Text("CAD$" + data[0]);
		maxValueData.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
		maxValueData.setFill(Color.DARKBLUE);
		
		// Minimum value
		Text minValueText = new Text("Minimum Property Value:");
		minValueText.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		
		Text minValueData = new Text("CAD$" + data[1]);
		minValueData.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
		minValueData.setFill(Color.DODGERBLUE);
		
		left.setPadding(new Insets(50));
		left.setSpacing(10);
		
		left.getChildren().addAll(maxValueText,maxValueData,minValueText,minValueData);
		
		
		// Right: table with number of properties by increments in house value
		VBox right = new VBox();
		right.setStyle("-fx-background-color: #FFFFFF; -fx-alignment: center;");
		
		Text tableTitle = new Text("Number of Homes by Value");
		tableTitle.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		
		final TableView<ValueBracket> houseValues = PrepareData.prepareItem5Table();
		houseValues.setItems(PrepareData.getHouseValueItems(table,Double.parseDouble(data[0])));
		
		right.getChildren().addAll(tableTitle,houseValues);
		right.setPadding(new Insets(50));
		right.setSpacing(10);
		
		aux.getChildren().addAll(left,right);
		
		// Opens a new display in case of double click showing info by street
		aux.setOnMouseClicked(new EventHandler<MouseEvent>() {

		    @Override
		    public void handle(MouseEvent click) {

		        if (click.getClickCount() == 2) {
		        	PrepareSecondaryDisplays.getInfoByStreet(table,"maxMin");
		        }
		    }
		});
		
		return aux;
	}

}
