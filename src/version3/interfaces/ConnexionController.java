package version3.interfaces;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import version3.user.User;

public class ConnexionController {
    @FXML
    ListView<String> usersListV;

    public void initialize(){
        List<String> users = new ArrayList<String>();
        for (User user : FxmlWoze.plateforme.getUsers()) {
            users.add(user.toString());
        }
        usersListV.setItems(FXCollections.observableArrayList(users));
    }

    @FXML
    public void seConnecter(MouseEvent e) throws IOException {
        System.out.println("clicked");
        FxmlWoze.plateforme.setCurrentUser(FxmlWoze.plateforme.getUserById(usersListV.getSelectionModel().getSelectedIndex()+1));
        @SuppressWarnings("unchecked")
        Stage currentStage = (Stage) ((ListView<String>) e.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("accueil.fxml"));
            Parent root = loader.load();
            currentStage.setScene(new Scene(root));
            currentStage.show();
    }

    @FXML
    public void boutonRetour(ActionEvent e) throws IOException{
        Stage currentStage = (Stage) ((Button) e.getSource()).getScene().getWindow();

        // Load the new scene (replace "accueil.fxml" with your desired FXML file)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("inscription.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root);

        // Set the new scene on the stage and show it
        currentStage.setScene(newScene);
        currentStage.show();
    }

}
