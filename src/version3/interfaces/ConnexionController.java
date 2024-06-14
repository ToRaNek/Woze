package version3.interfaces;

import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class ConnexionController {
    ObservableSet<String> villes = FXCollections.observableSet("Lille", "Lens", "Paris", "Lyon", "Nice", "Nimes", "Toulouse", "Tours");


    @FXML
    ComboBox<String> villesCB;

    public void initialize() {
        System.out.println("Initialisation...");

        villesCB.setEditable(true);
        villesCB.setItems(FXCollections.observableArrayList(villes));
        villesCB.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            villesCB.show();
            filterCities(newValue);
            System.out.println("Nouvelle valeur : " + newValue);
        });
    }

    private void filterCities(String filter) {
        villesCB.getItems().clear();
        for (String city : villes) {
            if (city.toLowerCase().contains(filter.toLowerCase())) {
                villesCB.getItems().add(city);
            }
        }
    }

    @FXML
    public void CBVilles(ActionEvent event) {
        System.out.println("test...");
    }

}