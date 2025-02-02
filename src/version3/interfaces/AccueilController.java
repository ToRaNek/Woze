package version3.interfaces;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import fr.ulille.but.sae_s2_2024.ModaliteTransport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import version3.graphe.Trajet;
import version3.graphe.TypeCout;
import version3.utils.algorithm.Algorithme;

public class AccueilController {
    Map<HBox, Trajet> trajetsMapHbox = new HashMap<HBox, Trajet>();

    boolean buttonBusActionisActivated = false;
    boolean buttonTrainActionisActivated = false;
    boolean buttonAvionActionisActivated = false;

    boolean buttonCO2isActivated = false;
    @FXML
    private TextField critereCO2;

    boolean buttonPrixisActivated = false;
    @FXML
    private TextField criterePrix;

    boolean buttonTempsisActivated = false;
    @FXML
    private TextField critereTemps;

    boolean parametreIsActivated = false;

    @FXML
    Pane popupParametre;

    @FXML
    Button buttonBus;

    @FXML
    Button buttonTrain;

    @FXML
    Button buttonAvion;

    @FXML
    ImageView imageBusLogo;

    @FXML
    ImageView imageTrainLogo;

    @FXML
    ImageView imageAvionLogo;

    @FXML
    Text villeDepart;

    @FXML
    ComboBox<String> villesArriveeCB;

    @FXML
    ListView<HBox> listeTrajets;

    public void initialize() {
        villeDepart.setText(FxmlWoze.plateforme.getCurrentUser().getVille());

        villesArriveeCB.setEditable(true);
        villesArriveeCB.setItems(FXCollections.observableArrayList(InscriptionController.villes));
        villesArriveeCB.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            villesArriveeCB.show();
            if (newValue != null && !InscriptionController.villes.contains(newValue)) {
                filterCities(newValue);
            }
            validateCitySelection(); // Validate city selection
        });

        villesCB.setEditable(true);
        villesCB.setItems(FXCollections.observableArrayList(InscriptionController.villes));
        villesCB.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            villesCB.show();
            if (newValue != null && !InscriptionController.villes.contains(newValue)) {
                filterCities(newValue);
            }
        });
    }


    public HBox hboxTrajet(String dep, String arr, String co2, String prix, String temps, boolean bus, boolean train, boolean avion) {
        HBox hb = new HBox(5); 
        HBox hbCouts = new HBox(3); 
    
        Label depL = new Label(dep + " ->");
        Label arrL = new Label(arr);
        Label co2L = new Label(co2 + " kg");
        Label prixL = new Label(prix + " €");
        Label tempsL = new Label(temps + " min");
    
        hb.getChildren().addAll(depL, arrL);
    
        double iconSize = 15;
    
        if (bus) {
            ImageView busImgV = new ImageView("/version3/interfaces/images/bus_noir.png");
            busImgV.setFitWidth(iconSize);
            busImgV.setFitHeight(iconSize);
            hb.getChildren().add(busImgV);
        }
        if (train) {
            ImageView trainImgV = new ImageView("/version3/interfaces/images/train_noir.png"); // Corrected image path
            trainImgV.setFitWidth(iconSize);
            trainImgV.setFitHeight(iconSize);
            hb.getChildren().add(trainImgV);
        }
        if (avion) {
            ImageView avionImgV = new ImageView("/version3/interfaces/images/avion_noir.png"); // Corrected image path
            avionImgV.setFitWidth(iconSize);
            avionImgV.setFitHeight(iconSize);
            hb.getChildren().add(avionImgV);
        }
    
        Button reserverButton = new Button("Réserver");
        reserverButton.setStyle("-fx-background-color: transparent; -fx-border-color: black; -fx-border-radius: 5px;");
        reserverButton.setOnMousePressed(e -> showPopupAndReserver(reserverButton, hb));
    
        Pane spacer = new Pane();
        Pane spacer2 = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        hbCouts.getChildren().addAll(co2L, prixL, tempsL);
        hb.getChildren().addAll(spacer, reserverButton, spacer2, hbCouts);
        hb.setAlignment(Pos.CENTER);
    
        return hb;
    }

    private void showPopupAndReserver(Button button, HBox hb) {
        // 1. Show Confirmation Popup
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de réservation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Voulez-vous vraiment réserver ce trajet ?");

        // 2. Wait for User Response
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        // 3. Handle Response
        if (result.isPresent() && result.get() == ButtonType.OK) {
            reserver(hb, button); // Proceed with reservation
            showReservationSuccessPopup(button); // Show success popup
        } else {
            // Do nothing or handle cancellation if needed
        }
    }

    private void showReservationSuccessPopup(Button button) {
        Popup popup = new Popup();
        Label popupLabel = new Label("Réservé !");
        VBox popupContent = new VBox(popupLabel);
        popupContent.setStyle("-fx-background-color: white; -fx-padding: 10px;");
        popup.getContent().add(popupContent);
        popup.setAutoHide(true);

        // Get the scene directly from the button
        Scene scene = button.getScene();

        if (scene != null) {
            popup.show(scene.getWindow(),
                    button.localToScreen(button.getBoundsInLocal()).getMinX(),
                    button.localToScreen(button.getBoundsInLocal()).getMinY());
        }
    }
    
    

    public void filterCities(String filter) {
        villesArriveeCB.getItems().clear();
        for (String city : InscriptionController.villes) {
            if (city.toLowerCase().contains(filter.toLowerCase())) {
                villesArriveeCB.getItems().add(city);
            }
        }
    }

    @FXML
    public void buttonBusAction(ActionEvent e){
        if (!buttonBusActionisActivated && villesArriveeCB.getSelectionModel().isEmpty()) {
            showAlert("Ville d'arrivée manquante", "Veuillez sélectionner une ville d'arrivée.");
            return; // Exit the method if no arrival city is selected
        }

        if(buttonBusActionisActivated){
            buttonBus.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-radius: 10; -fx-background-radius: 10");
            imageBusLogo.setImage(new Image(("/version3/interfaces/images/bus_noir.png")));
            buttonBus.setGraphic(imageBusLogo);
            buttonBus.setTextFill(Paint.valueOf("black"));
            buttonBusActionisActivated = false;
        }else{
            buttonBus.setTextFill(Paint.valueOf("white"));
            buttonBus.setStyle("-fx-background-color: rgb(40, 42, 85); -fx-border-color: black; -fx-border-radius: 10; -fx-background-radius: 10;");
            imageBusLogo.setImage(new Image(("/version3/interfaces/images/Bus_blanc.png")));
            buttonBus.setGraphic(imageBusLogo);
            buttonBusActionisActivated = true;
        }
        validateCitySelection();
        constructionListeTrajets();
    }

    @FXML
    public void buttonTrainAction(ActionEvent e){
        if (!buttonTrainActionisActivated && villesArriveeCB.getSelectionModel().isEmpty()) {
            showAlert("Ville d'arrivée manquante", "Veuillez sélectionner une ville d'arrivée.");
            return; // Exit the method if no arrival city is selected
        }

        if(buttonTrainActionisActivated){
            buttonTrain.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-radius: 10; -fx-background-radius: 10");
            imageTrainLogo.setImage(new Image(("/version3/interfaces/images/Train_noir.png")));
            buttonTrain.setGraphic(imageTrainLogo);
            buttonTrain.setTextFill(Paint.valueOf("black"));
            buttonTrainActionisActivated = false;
        }else{
            buttonTrain.setTextFill(Paint.valueOf("white"));
            buttonTrain.setStyle("-fx-background-color: rgb(30, 90, 30); -fx-border-color: black; -fx-border-radius: 10; -fx-background-radius: 10;");
            imageTrainLogo.setImage(new Image(("/version3/interfaces/images/Train_blanc.png")));
            buttonTrain.setGraphic(imageTrainLogo);
            buttonTrainActionisActivated = true;
        }
        validateCitySelection();
        constructionListeTrajets();
    }

    @FXML
    public void buttonAvionAction(ActionEvent e){
        if (!buttonAvionActionisActivated && villesArriveeCB.getSelectionModel().isEmpty()) {
            showAlert("Ville d'arrivée manquante", "Veuillez sélectionner une ville d'arrivée.");
            return; // Exit the method if no arrival city is selected
        }

        if(buttonAvionActionisActivated){
            buttonAvion.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-radius: 10; -fx-background-radius: 10");
            imageAvionLogo.setImage(new Image(("/version3/interfaces/images/Avion_noir.png")));
            buttonAvion.setGraphic(imageAvionLogo);
            buttonAvion.setTextFill(Paint.valueOf("black"));
            buttonAvionActionisActivated = false;
        }else{
            buttonAvion.setTextFill(Paint.valueOf("white"));
            buttonAvion.setStyle("-fx-background-color: rgb(113, 8, 8); -fx-border-color: black; -fx-border-radius: 10; -fx-background-radius: 10;");
            imageAvionLogo.setImage(new Image(("/version3/interfaces/images/Avion_blanc.png")));
            buttonAvion.setGraphic(imageAvionLogo);
            buttonAvionActionisActivated = true;
        }
        validateCitySelection();
        constructionListeTrajets();
    }

    @FXML
    ImageView imageAccount;

    @FXML
    Button account;

    @FXML
    public void openPopupCritere(ActionEvent e) {
        if (villesArriveeCB.getSelectionModel().isEmpty()) {
            showAlert("Ville d'arrivée manquante", "Veuillez sélectionner une ville d'arrivée avant d'accéder aux paramètres.");
            return; // Exit the method if no arrival city is selected
        }

        if (parametreIsActivated) {
            popupParametre.setVisible(false);
            parametreIsActivated = false;
            imageAccount.setVisible(true);
            account.setVisible(true);
        } else {
            popupParametre.setVisible(true);
            parametreIsActivated = true;
            imageAccount.setVisible(false);
            account.setVisible(false);
        }
    }

    @FXML
    ImageView buttonCO2;

    @FXML
    public void toggleButtonCO2(){
        if(buttonCO2isActivated){
            buttonCO2.setImage(new Image(("/version3/interfaces/images/untoggle_button.png")));
            buttonCO2isActivated = false;
            critereCO2.setVisible(false);
            ((Control) critereCO2).setDisable(true);
        }else{
            buttonCO2.setImage(new Image(("/version3/interfaces/images/toggle_button.png")));
            buttonCO2isActivated = true;
            ((Control) critereCO2).setDisable(false);
            critereCO2.setVisible(true);
        }
        constructionListeTrajets();
    }

    @FXML
    ImageView buttonPrix;

    @FXML
    public void toggleButtonPrix(){
        if(buttonPrixisActivated){
            buttonPrix.setImage(new Image(("/version3/interfaces/images/untoggle_button.png")));
            buttonPrixisActivated = false;
            criterePrix.setVisible(false);
            ((Control) criterePrix).setDisable(true);
        }else{
            buttonPrix.setImage(new Image(("/version3/interfaces/images/toggle_button.png")));
            buttonPrixisActivated = true;
            ((Control) criterePrix).setDisable(false);
            criterePrix.setVisible(true);
        }
        constructionListeTrajets();
    }

    @FXML
    ImageView buttonTemps;

    @FXML
    public void toggleButtonTemps(){
        if(buttonTempsisActivated){
            buttonTemps.setImage(new Image(("/version3/interfaces/images/untoggle_button.png")));
            buttonTempsisActivated = false;
            critereTemps.setVisible(false);
            ((Control) critereTemps).setDisable(true);
        }else{
            buttonTemps.setImage(new Image(("/version3/interfaces/images/toggle_button.png")));
            buttonTempsisActivated = true;
            ((Control) critereTemps).setDisable(false);
            critereTemps.setVisible(true);
        }
        constructionListeTrajets();
    }

    @FXML
    public void accountClicked(ActionEvent e) throws IOException{
         Stage currentStage = (Stage) ((Button) e.getSource()).getScene().getWindow();

        // Load the new scene (replace "accueil.fxml" with your desired FXML file)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("account.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root);

        // Set the new scene on the stage and show it
        currentStage.setScene(newScene);
        currentStage.show();
    }

    @FXML
    VBox VBCouts;

    @FXML
    HBox co2;

    @FXML
    HBox temps;

    @FXML
    HBox prix;

    @FXML
    public void co2Prio() {
        VBCouts.getChildren().remove(co2);
        VBCouts.getChildren().add(0, co2);
        FxmlWoze.ordreCout.remove(TypeCout.CO2);
        FxmlWoze.ordreCout.add(0, TypeCout.CO2);
        FxmlWoze.plateforme.setCurrentCrit(TypeCout.CO2);
        constructionListeTrajets();

    }

    @FXML
    public void tempsPrio() {
        VBCouts.getChildren().remove(temps);
        VBCouts.getChildren().add(0, temps);
        FxmlWoze.ordreCout.remove(TypeCout.CO2);
        FxmlWoze.ordreCout.add(0, TypeCout.TEMPS);
        FxmlWoze.plateforme.setCurrentCrit(TypeCout.TEMPS);
        constructionListeTrajets();


    }

    @FXML
    public void prixPrio() {
        VBCouts.getChildren().remove(prix);
        VBCouts.getChildren().add(0, prix);
        FxmlWoze.ordreCout.remove(TypeCout.CO2);
        FxmlWoze.ordreCout.add(0, TypeCout.PRIX);
        FxmlWoze.plateforme.setCurrentCrit(TypeCout.PRIX);
        constructionListeTrajets();


    }

    @FXML
    Pane poppupChangementVille;

    boolean poppupChangeVilleIsActivated = false;

    @FXML
    public void boutonChangementValider(ActionEvent e) {
        if (poppupChangeVilleIsActivated) {
            String selectedCity = villesCB.getSelectionModel().getSelectedItem();

            // Check if the selected city is different from the arrival city
            if (selectedCity != null && !selectedCity.isBlank() && InscriptionController.villes.contains(selectedCity) 
                    && !selectedCity.equals(villesArriveeCB.getValue())) { // Added condition
                villeDepart.setText(selectedCity);
                poppupChangementVille.setVisible(false);
                poppupChangeVilleIsActivated = false;
                constructionListeTrajets();
            } else {
                if (selectedCity.equals(villesArriveeCB.getValue())) {
                    showAlert("Erreur", "La ville de départ et d'arrivée doivent être différentes.");
                } else {
                    showAlert("Ville invalide", "Veuillez sélectionner une ville valide pour le départ.");
                }
                // Reset the departure city if the change is invalid
                villeDepart.setText(FxmlWoze.plateforme.getCurrentUser().getVille()); 
            }
        }
    }

    @FXML
    ComboBox<String> villesCB;

    @FXML
    public void boutonChangement(ActionEvent e){
        if(!poppupChangeVilleIsActivated){
            poppupChangementVille.setVisible(true);
            poppupChangeVilleIsActivated = true;
        }
        constructionListeTrajets();

    }

    public void textFeildCritereAction(){
        constructionListeTrajets();
    }

    public void constructionListeTrajets() {
        Map<TypeCout, Double> map = new HashMap<TypeCout, Double>();
        constructionMapCouts(map, buttonCO2isActivated, critereCO2, TypeCout.CO2);
        constructionMapCouts(map, buttonPrixisActivated, criterePrix, TypeCout.PRIX);
        constructionMapCouts(map, buttonTempsisActivated, critereTemps, TypeCout.TEMPS);
        List<ModaliteTransport> modalites = new ArrayList<ModaliteTransport>();
        if (buttonAvionActionisActivated) {
            modalites.add(ModaliteTransport.AVION);
        }
        if (buttonBusActionisActivated) {
            modalites.add(ModaliteTransport.BUS);
        }
        if (buttonTrainActionisActivated) {
            modalites.add(ModaliteTransport.TRAIN);
        }
        System.out.println(villeDepart.getText() + villesArriveeCB.getSelectionModel().getSelectedItem().toString() + map.toString() + modalites.toString() + 50);
        List<Trajet> trajets = Algorithme.kpccUltime(FxmlWoze.plateforme, villeDepart.getText(), villesArriveeCB.getSelectionModel().getSelectedItem(), map, modalites, 50);        
        System.out.println(trajets.toString());
        ObservableList<HBox> hboxList = FXCollections.observableArrayList();
        for (Trajet trajet : trajets) {
            HBox hBox = hboxTrajet(trajet.getDepart(), trajet.getArrivee(), trajet.getPoids(TypeCout.CO2)+"", trajet.getPoids(TypeCout.PRIX)+"", trajet.getPoids(TypeCout.TEMPS)+"", trajet.contains(ModaliteTransport.BUS), trajet.contains(ModaliteTransport.TRAIN), trajet.contains(ModaliteTransport.AVION));
            hboxList.add(hBox);
            trajetsMapHbox.put(hBox, trajet);
        }
        listeTrajets.setItems(hboxList);
    }

    public void reserver(HBox hb, Button b){
        hb.getChildren().remove(b);
        FxmlWoze.plateforme.getCurrentUser().addHistorique(trajetsMapHbox.get(hb));
    }

    public void constructionMapCouts(Map<TypeCout, Double> map, Boolean button, TextField critere, TypeCout type) {
        if (button) {
            if (critere == null || critere.getText().isBlank()) {
                map.put(type, Double.MAX_VALUE);
            } else {
                try {
                    double value = Double.parseDouble(critere.getText());
                    if (value >= 0) {
                        map.put(type, value);
                    } else {
                        showAlert("Erreur", "Veuillez saisir une valeur positive ou nulle pour " + type.toString());
                    }
                } catch (NumberFormatException e) {
                    showAlert("Erreur", "Veuillez saisir un nombre valide pour " + type.toString());
                }
            }
        }
    }

    private void validateCitySelection() {
        boolean departureValid = !villeDepart.getText().isBlank();
        boolean arrivalValid = !villesArriveeCB.getSelectionModel().isEmpty();

        // Additional check for same departure and arrival cities
        boolean differentCities = !villeDepart.getText().equals(villesArriveeCB.getValue());

        buttonBus.setDisable(!departureValid || !arrivalValid || !differentCities);
        buttonTrain.setDisable(!departureValid || !arrivalValid || !differentCities);
        buttonAvion.setDisable(!departureValid || !arrivalValid || !differentCities);

        if (departureValid && arrivalValid && differentCities) {
            constructionListeTrajets();
        } else {
            listeTrajets.getItems().clear();
            if (!differentCities) {
                showAlert("Erreur", "La ville de départ et d'arrivée doivent être différentes.");
            }
        }
    }


    private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);

    // Corrected stylesheet loading (if you have a custom CSS file)
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
}
