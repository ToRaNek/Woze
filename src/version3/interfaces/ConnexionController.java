package version3.interfaces;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import version3.user.User;

public class ConnexionController {
    static Set<String> villes;    

    public ConnexionController(){
        villes = new HashSet<>();
        villes.addAll(FxmlWoze.plateforme.getVilles());
    }

    @FXML
    ComboBox<String> villesCB;

    @FXML
    TextField prenom;

    @FXML
    TextField nom;

    public void initialize() {
        System.out.println("Initialisation...");

        villesCB.setEditable(true);
        villesCB.setItems(FXCollections.observableArrayList(villes));
        villesCB.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            villesCB.show();
            if (newValue != null && !villes.contains(newValue)) {
                filterCities(newValue);
            }
            System.out.println("Nouvelle valeur : " + newValue);
        });
    }

    public void filterCities(String filter) {
        villesCB.getItems().clear();
        for (String city : villes) {
            if (city.toLowerCase().contains(filter.toLowerCase())) {
                villesCB.getItems().add(city);
            }
        }
    }

    @FXML
    public void CBVilles(ActionEvent event) {
    }

    @FXML
    public void buttonSeConnecter(ActionEvent e) throws IOException {
        FxmlWoze.plateforme.setCurrentUser(new User(prenom.getText(), nom.getText(), villesCB.getSelectionModel().getSelectedItem(), null));

        // Get the stage from the current scene
        Stage currentStage = (Stage) ((Button) e.getSource()).getScene().getWindow();

        // Load the new scene (replace "accueil.fxml" with your desired FXML file)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("accueil.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root);

        // Set the new scene on the stage and show it
        currentStage.setScene(newScene);
        currentStage.show();
    }

}