package version3.interfaces;

import javafx.scene.control.TextField;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
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
import javafx.stage.Stage;
import version3.graphe.TypeCout;

public class AccueilController {

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
        ObservableList<HBox> hboxList = FXCollections.observableArrayList();
        hboxList.add(hboxTrajet("Lille", "Paris", "12", "14", "30", true, true, true));
        hboxList.add(hboxTrajet("Lyon", "Marseille", "2", "20", "28", true, false, true));
        listeTrajets.setItems(hboxList);
        villeDepart.setText(FxmlWoze.voyageur.getVille());

        villesArriveeCB.setEditable(true);
        villesArriveeCB.setItems(FXCollections.observableArrayList(ConnexionController.villes));
        villesArriveeCB.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            villesArriveeCB.show();
            if (newValue != null && !ConnexionController.villes.contains(newValue)) {
                filterCities(newValue);
            }
            System.out.println("Nouvelle valeur : " + newValue);
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
    
        hbCouts.getChildren().addAll(co2L, prixL, tempsL);
        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        hb.getChildren().addAll(spacer, hbCouts);
        return hb;
    }
    

    public void filterCities(String filter) {
        villesArriveeCB.getItems().clear();
        for (String city : ConnexionController.villes) {
            if (city.toLowerCase().contains(filter.toLowerCase())) {
                villesArriveeCB.getItems().add(city);
            }
        }
    }

    @FXML
    public void buttonBusAction(ActionEvent e){
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
    }

    @FXML
    public void buttonTrainAction(ActionEvent e){
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
    }

    @FXML
    public void buttonAvionAction(ActionEvent e){
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
    }

    @FXML
    ImageView imageAccount;

    @FXML
    Button account;

    @FXML
    public void openPopupCritere(ActionEvent e){
        if(parametreIsActivated){
            popupParametre.setVisible(false);
            parametreIsActivated = false;
            imageAccount.setVisible(true);
            account.setVisible(true);
        }else{
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
    }

    @FXML
    public void tempsPrio() {
        VBCouts.getChildren().remove(temps);
        VBCouts.getChildren().add(0, temps);
        FxmlWoze.ordreCout.remove(TypeCout.CO2);
        FxmlWoze.ordreCout.add(0, TypeCout.TEMPS);
    }

    @FXML
    public void prixPrio() {
        VBCouts.getChildren().remove(prix);
        VBCouts.getChildren().add(0, prix);
        FxmlWoze.ordreCout.remove(TypeCout.CO2);
        FxmlWoze.ordreCout.add(0, TypeCout.PRIX);
    }
}
