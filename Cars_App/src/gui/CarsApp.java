package gui;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import model.Car;


/**
 * This class represents the GUI of the application
 * @author Khaled Badran <gym4programming@gmail.com>
 * @version 1.00 - 06/02/2022
 */
public class CarsApp extends Application {
	
//	creating the class variable that we will need
//	adding layouts/panes
    private HBox rootHBox = new HBox(); // HBox layout that would be used as the root/parent layout
    private VBox sideBarLeftVBox = new VBox(); // VBox layout to display some elements on the sidebar on the left side of the program
    private GridPane addOrEditCarGridPane = new GridPane(); // grid pane layout to display some elements/controls on the right side of the program
    private VBox listVBox = new VBox(); // VBox layout to display the list view and its label
//  adding components/widgets
    private Label carsListViewLabel = new Label("Cars List"); // label for the list view
    private ListView<Car> carsListView = new ListView<>(); // list view to display/view the cars
    private ArrayList<Car> carsArrayList = new ArrayList<>(); // array list of cars
    private FileChooser fc = new FileChooser(); // to enable the user to upload a file by browsing the computer // fc = fileChooser
    private DirectoryChooser dc = new DirectoryChooser(); // to enable the user to save/export the list of cars in a specific location by choosing the desired directory// dc = DirectoryChooser

    private Button[] sideBarButtons = {new Button("Add a Car"), new Button("Import CSV File"),  new Button("Export To CSV File")}; // creating buttons for the main root
    private Control[] selectedCarControls = { // adding all the required controls to display/view and edit a car.
		new Button("Edit Car"), new Button("Delete Car"),
		new Label("Car Model Name: "), new Label(),
		new Label("Car Model year: "), new Label(),
		new Label("Car Weight In Tons: "), new Label(),
		new Label("Is The Car New? "),  new Label(),
		new Button("Submit Changes")		
	}; 
    private Control[] addOrEditCarControls = { // adding all the required controls to add a car.
		new Label("Car Model Name: "), new TextField(),
		new Label("Car Model year: "), new TextField(),
		new Label("Car Weight In Tons (e.g. 2.3): "), new TextField(),
		new Label("Is The Car New? [y/n]: "), new TextField(), 
		new Button("Submit")		
	}; 
//  End creating the class variable that we will need
    
    /**
     * The is the Main method to start the application.
     * 
     * @author Khaled Badran <gym4programming@gmail.com>
     * @param args arguments giving while compiling the program
     */
	public static void main(String[] args) {		
		launch(args); // launch(args) calls start(Stage primaryStage) automatically
	}

	/**
	 * This the main loop of javaFX library which will always update the GUI.
     * @author Khaled Badran <gym4programming@gmail.com>
     * @param primaryStage is the main stage
     * @throws Exception
	 */
	@Override
	public void start(@SuppressWarnings("exports") Stage primaryStage) throws Exception{				
		initMainScene();    
        bindButtons();
//      new Scene(Node node, int width, int height);
        Scene mainScene = new Scene(rootHBox, 800,  500); // adding the rootHBox to the scene

        primaryStage.setTitle("  Cars Application");
        primaryStage.setScene(mainScene); // add the scene to the stage
        primaryStage.show();
	}
	
	/**
	 * initialize the required controls/layouts to display the main scene/stage
	 * @author Khaled Badran <gym4programming@gmail.com>
	 */
    private void initMainScene(){
//      adjusting the rootHBox layout/pane		
		rootHBox.getChildren().addAll(sideBarLeftVBox, listVBox, addOrEditCarGridPane); // adding the list of contacts    	
//		change the background color of rootHBox layout/pane
        rootHBox.setBackground(new Background(new BackgroundFill(Color.DARKGREY, CornerRadii.EMPTY, Insets.EMPTY)));

//      adjusting the listVBox layout/pane
		listVBox.getChildren().addAll(carsListViewLabel, carsListView); // adding the list view and its label to the listVBox pane/layout
    	listVBox.setPadding(new Insets(10, 5, 30, 5));
		carsListViewLabel.setPadding(new Insets(0, 100, 4, 100));

//      adjusting the sideBarLeftVBox layout/pane		
//      adding padding around the inside edges of the pane // new Insets(TOP, RIGHT, BOTTOM, LEFT)
    	sideBarLeftVBox.setPadding(new Insets(30, 5, 30, 5));
//    	adding spacing between elements within the pane
        sideBarLeftVBox.setSpacing(20);
		for (Button btn : sideBarButtons) { // adding all the sideBarButtons to the sideBarLeftVBox pane/layout
			btn.setPrefWidth(200);
//			new Insets(TOP, RIGHT, BOTTOM, LEFT)
			btn.setPadding(new Insets(8, 0, 8, 0));
			sideBarLeftVBox.getChildren().add(btn);	
		}
//		change the background color of sideBarLeftVBox layout/pane
		sideBarLeftVBox.setBackground(new Background(new BackgroundFill(Color.STEELBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		
//		hiding the addOrEditCarGridPane in the mainScene
		addOrEditCarGridPane.setVisible(false);

    }

	/**
	 * bind all the buttons with all the corresponding methods
	 * @author Khaled Badran <gym4programming@gmail.com>
	 */
    private void bindButtons() {
//		sideBarButtons
    	sideBarButtons[0].setOnAction(event -> initAddCarScene()); // bind "Add a Car" Button
    	sideBarButtons[1].setOnAction(event -> importFile()); // bind "Import CSV File" Button
    	sideBarButtons[2].setOnAction(event -> exportFile()); // bind "Export To a CSV File" Button
    	((Button)addOrEditCarControls[addOrEditCarControls.length-1]).setOnAction(event -> submitAddingOrEditingCar(null)); // bind "Submit" Button in case of adding a new car
    	    	
//    	for selecting a car from the list view
    	carsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Car>() {
            @Override
            public void changed(ObservableValue<? extends Car> arg0, Car arg1, Car selectedCar) {
                if (selectedCar != null) {
                	initSelectedCarScene(selectedCar);
                	((Button)selectedCarControls[0]).setOnAction(event -> initEditCarScene()); // bind "Edit Car" Button
                	((Button)selectedCarControls[1]).setOnAction(event -> deleteSelectedCar(selectedCar)); // bind "Delete Car" Button
                	((Button)selectedCarControls[selectedCarControls.length-1]).setOnAction(event -> submitAddingOrEditingCar(selectedCar)); // bind "Submit Changes" Button in case of editing a pre-existed car
                }
            }
        });
    }
    
	/**
	 * import a csv file to the list view
	 * @author Khaled Badran <gym4programming@gmail.com>
	 */
    private void importFile() {
    	File CSVFilesDir = new File("src\\CSV_files");
    	ExtensionFilter allowedExtension = new ExtensionFilter("CSV File", "*.csv");
    	fc.setInitialDirectory(CSVFilesDir); // to automatically open the wished directory
    	fc.getExtensionFilters().add(allowedExtension);
    	fc.setTitle("Choosing a CSV File For The Cars Application"); // setting a title for the file chooser window/stage
    	File importedFile = fc.showOpenDialog(null); // importedFilePath
    	String line = ""; // would be the parsed row/line from the *.csv file
    	if (importedFile != null) {
			try { // opening the *.csv file
				BufferedReader parser = new BufferedReader(new FileReader(importedFile));
				try { // parsing the file and manipulating the parsed data. Then storing the parsed data in cars objects and adding them to the list view
					int lineNumber = 0;
					while((line = parser.readLine()) != null) {
						if (lineNumber > 0) { // to ignore the first row which is the headers row
							String[] row = line.split(","); //e.g. String[] row = [modelName,modelYear,weight,isNew]
//					        storing the parsed data in a car object and adding it to the list view
					        String carModelName = row[0];
					        int carModelYear = Integer.valueOf(row[1]);
			        		double  carWeight  = Double.valueOf(row[2]);
	        				Boolean isNew = Boolean.valueOf(row[3]);
	        	        	Car carToAdd = new Car(carModelName, carModelYear, carWeight, isNew);
	        	            carsArrayList.add(carToAdd);	
						} else {
							lineNumber++;
						}
					}
		            updateListView();
					resetAddOrEditCarGridPane();
		            Alert errorAlert = new Alert(AlertType.CONFIRMATION);
		            errorAlert.setHeaderText("Import Status");
		            errorAlert.setContentText("Import Was Successful");
		            errorAlert.showAndWait();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
	            Alert errorAlert = new Alert(AlertType.ERROR);
	            errorAlert.setHeaderText("Import Status");
	            errorAlert.setContentText("Import Failed");
	            errorAlert.showAndWait();
				e.printStackTrace();
			}
    	}
    }    
    
	/**
	 * export all the cars of the list view to a csv file 
	 * @author Khaled Badran <gym4programming@gmail.com>
	 */
    private void exportFile() {
    	File CSVFilesDir = new File("src");
    	dc.setInitialDirectory(CSVFilesDir);
    	File desiredDir = dc.showDialog(null); // path of the desired directory 
    	if (desiredDir != null) { //write the data of the listview/carsArrayList to a *.csv file
    		try {
        		String dateAndTimeNow = String.valueOf(LocalDateTime.now()).replaceAll(":", "-"); //  changing the format of the data from e.g. "2015-02-20T06:30:00" to "2015-02-20T06-30-00"
				PrintWriter writer = new PrintWriter(desiredDir + "//" + "exported_" + dateAndTimeNow + ".csv"); // the created filename would be exported_<<current date and time>>
				
    			// the first row which is the headers row: modelName,modelYear,weight,isNew
    			writer.print("modelName"+",");
    			writer.print("modelYear"+",");
    			writer.print("weight"+",");
    			writer.print("isNew");
    			writer.println();
	    		for (Car car: carsArrayList) {
	    			writer.print(car.getModelName()+",");
	    			writer.print(car.getModelYear()+",");
	    			writer.print(car.getWeight()+",");
	    			writer.print(car.isNew());
	    			writer.println();
	    		}
				resetAddOrEditCarGridPane();
	            Alert errorAlert = new Alert(AlertType.CONFIRMATION);
	            errorAlert.setHeaderText("Export Status");
	            errorAlert.setContentText("Export Was Successful");
	            errorAlert.showAndWait();
				writer.close(); // close the PrintWriter
			} catch (FileNotFoundException e) {
	            Alert errorAlert = new Alert(AlertType.ERROR);
	            errorAlert.setHeaderText("Export Status");
	            errorAlert.setContentText("Export Failed");
	            errorAlert.showAndWait();
				e.printStackTrace();
			}
    	}
    }
    
	/**
	 * initialize the required controls/layouts to add a car
	 * @author Khaled Badran <gym4programming@gmail.com>
	 */
    private void initAddCarScene() {
        updateListView();
    	resetAddOrEditCarGridPane();
		addOrEditCarGridPane.setVisible(true);
        
        int row = 0;
        for(int i = 0; i < addOrEditCarControls.length-1; i++, row++) { 
//    		anyGridPane.add(Node child, int indexColumn, int indexRow)
        	addOrEditCarGridPane.add(addOrEditCarControls[i], 0, row);
        	i++;
        	addOrEditCarGridPane.add(addOrEditCarControls[i], 1, row);
        }
//		anyGridPane.add(Node child, int indexColumn, int indexRow, int colspan, int rowspan)
    	addOrEditCarGridPane.add(addOrEditCarControls[addOrEditCarControls.length-1], 0, row , 2, 1);

    }
    
	/**
	 * submit adding a new car or submit changes for editing a pre-existed car
	 * @author Khaled Badran <gym4programming@gmail.com>
	 * @param selectedCar is the selected car from the list view
	 */
    private void submitAddingOrEditingCar(Car selectedCar) {
    	String carModelName;
    	int carModelYear;
    	double  carWeight;
    	Boolean isNew;
    	// make sure that the data types of the input are accurate.
    	try { 
    		carModelName =  ((TextField)addOrEditCarControls[1]).getText();
    		if (carModelName.length() < 1)  throw new Exception();
		} catch(Exception e){  
	        Alert errorAlert = new Alert(AlertType.ERROR);
	        errorAlert.setHeaderText("Data Type Error");
	        errorAlert.setContentText("The \"Car Model Name\" TextField can't be empty");
	        errorAlert.showAndWait();
	        return;
    	}  
        try {carModelYear = Integer.valueOf(((TextField)addOrEditCarControls[3]).getText());}
        catch(NumberFormatException e){  
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setHeaderText("Data Type Error");
            errorAlert.setContentText("The \"Car Model Year\" TextField has to be an integer");
            errorAlert.showAndWait();
            return;
        }  
        try { carWeight = Double.valueOf(((TextField)addOrEditCarControls[5]).getText());}
        catch(NumberFormatException e){
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setHeaderText("Data Type Error");
            errorAlert.setContentText("The \"Car Weight\" TextField has to be an integer or a double");
            errorAlert.showAndWait();
            return;
        }
        try {
        	String isNewStr = ((TextField)addOrEditCarControls[7]).getText();
        	if (isNewStr.equalsIgnoreCase("y") || isNewStr.equalsIgnoreCase("true") || isNewStr.equalsIgnoreCase("yes")) isNew = true;
        	else isNew = false;
    	} catch(Exception e){
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setHeaderText("Data Type Error");
            errorAlert.setContentText("The \"Car Weight\" TextField has to be an integer or a double");
            errorAlert.showAndWait();
            return;
        }  
        
        if (selectedCar!=null){ // for editing a pre-existed car
        	int selectedCarIndex = carsArrayList.indexOf(selectedCar);
        	carsArrayList.set(selectedCarIndex, new Car(carModelName, carModelYear, carWeight, isNew));  
        } else { // for adding a new car
            Car carToAdd = new Car(carModelName, carModelYear, carWeight, isNew);
            carsArrayList.add(carToAdd);
        }
    	
        updateListView();
        resetAddOrEditCarGridPane();
        resetTextFields();
    }
    
	/**
	 * initialize the required controls/layouts to edit a car
	 * @author Khaled Badran <gym4programming@gmail.com>
	 */
    private void initEditCarScene() {
     	((Button)selectedCarControls[0]).setDisable(true); // disable "Edit Car" Button
		int row = 8;
        for(int i = 0; i < addOrEditCarControls.length-1; i++, row++) { 
//    		anyGridPane.add(Node child, int indexColumn, int indexRow)
        	addOrEditCarGridPane.add(addOrEditCarControls[i], 0, row);
        	i++;
        	addOrEditCarGridPane.add(addOrEditCarControls[i], 1, row);
        }
//		anyGridPane.add(Node child, int indexColumn, int indexRow, int colspan, int rowspan)
    	addOrEditCarGridPane.add(selectedCarControls[selectedCarControls.length-1], 0, row , 2, 1);
    }
    
    
	/**
	 * delete the selected car when the users clicks on the delete button
     * @author Khaled Badran <gym4programming@gmail.com>
	 * @param carToDelete is the car that the user is selecting and he wants to delete it.
	 */
    private void deleteSelectedCar(Car carToDelete) {
        carsArrayList.remove(carToDelete);
        updateListView(); 
        resetAddOrEditCarGridPane();
    }
    
	/**
	 * initialize the required controls/layouts to display the details of the selected car from the list view
	 * @author Khaled Badran <gym4programming@gmail.com>
	 * @param selectedCar is the selected car from the list view
	 */
    private void initSelectedCarScene(Car selectedCar) {
    	resetAddOrEditCarGridPane();
    	addOrEditCarGridPane.setVisible(true);
        for(int i = 0, row = 0; i < selectedCarControls.length-1; i++, row++) { 
//    		anyGridPane.add(Node child, int indexColumn, int indexRow)
        	addOrEditCarGridPane.add(selectedCarControls[i], 0, row);
        	i++;	
    		if (i==3) ((Label)selectedCarControls[i]).setText(selectedCar.getModelName());
    		else if (i==5) ((Label)selectedCarControls[i]).setText(String.valueOf(selectedCar.getModelYear()));
    		else if (i==7) ((Label)selectedCarControls[i]).setText(String.valueOf(selectedCar.getWeight()));
    		else if (i==9) ((Label)selectedCarControls[i]).setText(String.valueOf(selectedCar.isNew()));
        	addOrEditCarGridPane.add(selectedCarControls[i], 1, row);
        }
    }
    
	/**
	 * reset all the required controls/layout for displaying a selected car, editing a car or adding a new car. 
	 * @author Khaled Badran <gym4programming@gmail.com>
	 */
    private void resetAddOrEditCarGridPane() {
    	rootHBox.getChildren().remove(2);
    	addOrEditCarGridPane = new GridPane();
    	rootHBox.getChildren().add(2, addOrEditCarGridPane);
//      adjusting the addOrEditCarGridPane layout/pane
        addOrEditCarGridPane.setPadding(new Insets(30, 5, 30, 5));
//    	adding spacing between elements within the pane
        addOrEditCarGridPane.setHgap(10);
        addOrEditCarGridPane.setVgap(10);
		// making the "Submit", "Edit Car" and "Delete Car" buttons big and wide buttons
		selectedCarControls[0].setPrefWidth(150); // selectedCarControls[0] is the "Edit Car" Button
		selectedCarControls[0].setPadding(new Insets(6, 0, 6, 0));
		selectedCarControls[1].setPrefWidth(150);// selectedCarControls[1] is the "Delete Car" Button
		selectedCarControls[1].setPadding(new Insets(6, 0, 6, 0));
		addOrEditCarControls[addOrEditCarControls.length-1].setPrefWidth(315); // addOrEditCarControls[addOrEditCarControls.length-1] is the "Submit" Button
		addOrEditCarControls[addOrEditCarControls.length-1].setPadding(new Insets(6, 0, 6, 0));
		selectedCarControls[selectedCarControls.length-1].setPrefWidth(315);
		selectedCarControls[selectedCarControls.length-1].setPadding(new Insets(6, 0, 6, 0));
     	((Button)selectedCarControls[0]).setDisable(false); // enable "Edit Car" Button

		addOrEditCarGridPane.setVisible(false);
//		change the background color of addOrEditCarGridPane layout/pane
		addOrEditCarGridPane.setBackground(new Background(new BackgroundFill(Color.STEELBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		addOrEditCarGridPane.setVisible(false);
    }
    
	/**
	 * reset/clear all the textFields)
	 * @author Khaled Badran <gym4programming@gmail.com>
	 */
    private void resetTextFields(){
        for(int i = 1; i < addOrEditCarControls.length; i+=2)  
            ((TextField)addOrEditCarControls[i]).clear();
    }

	/**
	 * update the list view 
	 * by fetching data from the carsArrayList and then adding the data to the list view.
	 * @author Khaled Badran <gym4programming@gmail.com>
	 */
    private void updateListView(){ // adding the cars from the carsArrayList to the list view
        carsListView.getItems().clear(); // clear the list firstly and then update it.
        for (Car car: carsArrayList)
        	carsListView.getItems().add(car);
    }
    
}
