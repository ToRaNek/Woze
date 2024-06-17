package version3.interfaces;

import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

import fr.ulille.but.sae_s2_2024.ModaliteTransport;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

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

    public HBox hboxTrajet(String dep, String arr, String co2, String prix, String temps, boolean bus, boolean train, boolean avion){
        HBox hb = new HBox();
        Label depL = new Label(dep + " -> ");
        Label arrL = new Label(arr);
        Label co2L = new Label(co2 + " kg      ");
        Label prixL = new Label(prix + " €    ");
        Label tempsL = new Label(temps + " min    ");
        hb.getChildren().addAll(depL, arrL);
        if (bus) {
            ImageView busImgV = new ImageView();
            busImgV.setImage(new Image(("/version3/interfaces/images/bus_noir.png")));
            hb.getChildren().add(busImgV);            
        }
        if (train) {
            ImageView trainImgV = new ImageView();
            trainImgV.setImage(new Image(("/version3/interfaces/images/Avion_noir.png")));
            hb.getChildren().add(trainImgV);            
        }
        if (avion) {
            ImageView avionImgV = new ImageView();
            avionImgV.setImage(new Image(("/version3/interfaces/images/Train_noir.png")));
            hb.getChildren().add(avionImgV);            
        }
        hb.getChildren().addAll(co2L, prixL, tempsL);
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
    public void openPopupCritere(ActionEvent e){
        if(parametreIsActivated){
            popupParametre.setVisible(false);
            parametreIsActivated = false;
        }else{
            popupParametre.setVisible(true);
            parametreIsActivated = true;
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
}