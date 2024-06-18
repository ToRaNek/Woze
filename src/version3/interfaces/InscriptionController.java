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
import javafx.scene.control.*;
import javafx.stage.Stage;
import version3.user.User;

public class InscriptionController {
    static Set<String> villes;

    public InscriptionController() {
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
        villesCB.setEditable(true);
        villesCB.setItems(FXCollections.observableArrayList(villes));
        villesCB.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            villesCB.show();
            if (newValue != null && !villes.contains(newValue)) {
                filterCities(newValue);
            }
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
    public void buttonSeConnecter(ActionEvent e) throws IOException {
        if (isValidInput()) {
            FxmlWoze.plateforme.setCurrentUser(new User(prenom.getText(), nom.getText(), 
                                                      villesCB.getSelectionModel().getSelectedItem(), null));

            Stage currentStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("accueil.fxml"));
            Parent root = loader.load();
            currentStage.setScene(new Scene(root));
            currentStage.show();
        }
    }

    private boolean isValidInput() {
        if (prenom.getText().isBlank()) {
            showAlert("Prénom manquant", "Veuillez entrer votre prénom.");
            return false;
        }

        if (nom.getText().isBlank()) {
            showAlert("Nom manquant", "Veuillez entrer votre nom.");
            return false;
        }

        String selectedCity = villesCB.getSelectionModel().getSelectedItem();
        if (selectedCity == null || selectedCity.isBlank() || !villes.contains(selectedCity)) {
            showAlert("Ville invalide", "Veuillez sélectionner une ville valide.");
            return false;
        }

        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Corrected stylesheet loading
        java.net.URL styleSheetUrl = getClass().getResource("/version3/interfaces/alert-style.css");
        if (styleSheetUrl != null) {
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(styleSheetUrl.toExternalForm());
            dialogPane.getStyleClass().add("myDialog");
        } else {
            System.err.println("Error loading stylesheet: alert-style.css not found.");
        }

        alert.showAndWait();
    }

    @FXML
    public void buttonJaiDejaUnCompte(ActionEvent e) throws IOException {
        Stage currentStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("accueil.fxml"));
            Parent root = loader.load();
            currentStage.setScene(new Scene(root));
            currentStage.show();
    }
}
