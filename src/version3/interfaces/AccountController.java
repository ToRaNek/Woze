package version3.interfaces;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AccountController {
    @FXML
    Text leNom;

    @FXML
    Text lePrenom;
    
    @FXML
    ListView<HBox> historiqueListV;

    public void initialize() {
        leNom.setText(FxmlWoze.plateforme.getCurrentUser().getNom());
        lePrenom.setText(FxmlWoze.plateforme.getCurrentUser().getPrenom());
        historiqueListV.getItems().addAll(FxmlWoze.plateforme.getCurrentUser().getHistorique());
    }

    @FXML
    public void boutonRetour(ActionEvent e) throws IOException{
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
