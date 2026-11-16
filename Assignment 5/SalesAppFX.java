import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class SalesAppFX extends Application {

    private TextField tfInputFile;
    private TextField tfOutputFile;
    private TextArea taOutput;
    private double[][] currentData;

    @Override
    public void start(Stage primaryStage) {

        tfInputFile = new TextField("salesdata.txt");
        tfOutputFile = new TextField("sales_summary.txt");
        taOutput = new TextArea();
        taOutput.setEditable(false);

        Button btnLoad = new Button("Load & Process");
        Button btnSave = new Button("Save Summary");

        GridPane topPane = new GridPane();
        topPane.setHgap(10);
        topPane.setVgap(10);
        topPane.add(new Label("Input File:"), 0, 0);
        topPane.add(tfInputFile, 1, 0);
        topPane.add(new Label("Output File:"), 0, 1);
        topPane.add(tfOutputFile, 1, 1);

        HBox buttons = new HBox(10, btnLoad, btnSave);

        VBox root = new VBox(10, topPane, buttons, taOutput);
        root.setPadding(new Insets(10));

        btnLoad.setOnAction(e -> handleLoad());
        btnSave.setOnAction(e -> handleSave());

        primaryStage.setTitle("Store Sales Data Processor");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    private void handleLoad() {
        String inFile = tfInputFile.getText().trim();
        taOutput.clear();
        try {
            currentData = SalesFileIO.readSalesData(inFile);
            taOutput.appendText("Data loaded from " + inFile + "\n\n");

            double total = SalesDataUtility.getTotal(currentData);
            double avg = SalesDataUtility.getAverage(currentData);
            double highest = SalesDataUtility.getHighestInArray(currentData);
            double lowest = SalesDataUtility.getLowestInArray(currentData);

            taOutput.appendText(String.format("Total sales: %.2f%n", total));
            taOutput.appendText(String.format("Average sale: %.2f%n", avg));
            taOutput.appendText(String.format("Highest sale: %.2f%n", highest));
            taOutput.appendText(String.format("Lowest sale: %.2f%n%n", lowest));

            // Show row totals
            for (int r = 0; r < currentData.length; r++) {
                taOutput.appendText(
                        String.format("Row %d total: %.2f%n",
                                r, SalesDataUtility.getRowTotal(currentData, r))
                );
            }
        } catch (FileNotFoundException ex) {
            taOutput.appendText("ERROR: Input file not found.\n");
        } catch (NumberFormatException ex) {
            taOutput.appendText("ERROR: Invalid number in data file.\n");
        }
    }

    private void handleSave() {
        if (currentData == null) {
            taOutput.appendText("No data loaded. Load data first.\n");
            return;
        }
        String outFile = tfOutputFile.getText().trim();
        try {
            SalesFileIO.writeSummary(outFile, currentData);
            taOutput.appendText("Summary written to " + outFile + "\n");
        } catch (IOException ex) {
            taOutput.appendText("ERROR writing summary: " + ex.getMessage() + "\n");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
