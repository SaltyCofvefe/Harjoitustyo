package com.example.demo4;


import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

/**
 * Paaluokka, joka kaynnistaa ostoslistan.
 * @author Jesse Puustinen
 * @version 1.0 2023/03/27
 */

public class OstoslistaKayttoliittyma extends Application {

    private TableView<Tuote> tableView;
    private ObservableList<Tuote> tuoteLista;
    private File file;

    /**
     * Metodi, joka kaynnistaa sovelluksen.
     *
     * @param args komentoriviparametrit
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Luodaan ostoslistan tuotteita sisältävä taulukko
        tableView = new TableView<>();
        tuoteLista = FXCollections.observableArrayList();
        tableView.setItems(tuoteLista);

        // Luodaan sarake jokaiselle tuotteen tiedolle
        TableColumn<Tuote, String> nimiCol = new TableColumn<>("Nimi");
        nimiCol.setCellValueFactory(new PropertyValueFactory<>("nimi"));
        TableColumn<Tuote, Integer> maaraCol = new TableColumn<>("Määrä");
        maaraCol.setCellValueFactory(new PropertyValueFactory<>("maara"));
        TableColumn<Tuote, Double> hintaCol = new TableColumn<>("Hinta");
        hintaCol.setCellValueFactory(new PropertyValueFactory<>("hinta"));
        tableView.getColumns().addAll(nimiCol, maaraCol, hintaCol);

        // Luodaan nappi tuotteen lisäämiseksi
        Button addButton = new Button("Lisää tuote");
        addButton.setOnAction(event -> lisaaTuote());

        // Luodaan nappi tuotteen poistamiseksi
        Button deleteButton = new Button("Poista tuote");
        deleteButton.setOnAction(event -> poistaTuote());

        // Yhteishinnan laskeminen
        Label totalPriceLabel = new Label();
        totalPriceLabel.textProperty().bind(Bindings.format("Yhteensä: %.2f €", yhteisHinta()));

        // Luodaan yläpalkki
        HBox topBox = new HBox(10);
        topBox.setPadding(new Insets(1));
        topBox.getChildren().addAll(addButton, deleteButton, totalPriceLabel);

        // Luodaan pääikkuna
        VBox mainBox = new VBox(10);
        mainBox.setPadding(new Insets(10));
        mainBox.getChildren().addAll(topBox, tableView);

        Scene scene = new Scene(mainBox);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Luodaan tiedosto, johon ostoslistan tuotteet tallennetaan
        file = new File("ostoslista.txt");
        if (!file.exists()) {
            file.createNewFile();
        }

        // Ladataan ostoslistan tuotteet tiedostosta
        lataaTiedostosta();
    }

    /**
     * Metodi, joka lisaa tuotteen ostoslistaan.
     */
    private void lisaaTuote() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Lisää tuote");
        dialog.setHeaderText(null);
        dialog.setContentText("Anna tuotteen nimi:");
        Optional<String> nimiResult = dialog.showAndWait();

        if (nimiResult.isPresent()) {
            TextInputDialog maaraDialog = new TextInputDialog();
            maaraDialog.setTitle("Lisää tuote:");
            maaraDialog.setHeaderText(null);
            maaraDialog.setContentText("Anna tuotteen määrä:");
            Optional<String> maaraResult = maaraDialog.showAndWait();

            if (maaraResult.isPresent()) {
                TextInputDialog hintaDialog = new TextInputDialog();
                hintaDialog.setTitle("Lisää tuote");
                hintaDialog.setHeaderText(null);
                hintaDialog.setContentText("Anna tuotteen hinta:");
                Optional<String> hintaResult = hintaDialog.showAndWait();

                if (hintaResult.isPresent()) {
                    String nimi = nimiResult.get();
                    int maara = Integer.parseInt(maaraResult.get());
                    double hinta = Double.parseDouble(hintaResult.get());

                    tuoteLista.add(new Tuote(nimi, maara, hinta));

                    // Tallennetaan ostoslistan tuotteet tiedostoon
                    tallennaTiedostoon();
                }
            }
        }
    }

    /**
     * Metodi, joka poistaa valitun tuotteen ostoslistasta.
     */
    private void poistaTuote() {
        Tuote tuote = tableView.getSelectionModel().getSelectedItem();
        tuoteLista.remove(tuote);

        // Tallennetaan ostoslistan tuotteet tiedostoon
        tallennaTiedostoon();
    }

    /**
     * Metodi, joka laskee ostoslistan yhteishinnan.
     *
     * @return ostoslistan yhteishinta
     */
    private DoubleBinding yhteisHinta() {
        return Bindings.createDoubleBinding(() -> {
            double kokonaisHinta = 0.0;
            for (Tuote tuote : tuoteLista) {
                kokonaisHinta += tuote.getHinta() * tuote.getMaara();
            }
            return kokonaisHinta;
        }, tuoteLista);
    }

    /**
     * Metodi, joka tallentaa ostoslistan tuotteet tiedostoon.
     */
    private void tallennaTiedostoon() {
        try (FileWriter writer = new FileWriter(file)) {
            for (Tuote tuote : tuoteLista) {
                writer.write(tuote.getNimi() + ";" + tuote.getMaara() + ";" + tuote.getHinta() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodi, joka lataa ostoslistan tuotteet tiedostosta.
     */
    private void lataaTiedostosta() {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(";");
                String nimi = parts[0];
                int maara = Integer.parseInt(parts[1]);
                double hinta = Double.parseDouble(parts[2]);
                tuoteLista.add(new Tuote(nimi, maara, hinta));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}