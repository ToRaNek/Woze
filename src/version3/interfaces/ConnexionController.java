package version3.interfaces;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        Stage currentStage = (Stage) ((ListView<String>) e.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("accueil.fxml"));
            Parent root = loader.load();
            currentStage.setScene(new Scene(root));
            currentStage.show();
    }
}
