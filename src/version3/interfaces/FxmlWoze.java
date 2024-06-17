package version3.interfaces;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import version1.Plateforme;
import version1.Voyageur;
import version3.graphe.TypeCout;
import version3.utils.data.extract.VilleDataExtractor;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class FxmlWoze extends Application {
        
        static Plateforme plateforme = new Plateforme(VilleDataExtractor.data_villes);
        static Voyageur voyageur;
        static ArrayList<TypeCout> ordreCout;

        public void start(Stage stage) throws IOException {
                ordreCout =  new ArrayList<TypeCout>();
                ordreCout.add(TypeCout.CO2);
                ordreCout.add(TypeCout.PRIX);
                ordreCout.add(TypeCout.TEMPS);
                FXMLLoader loader = new FXMLLoader();
                URL fxmlFileUrl = getClass().getResource("connexion.fxml");
                if (fxmlFileUrl == null) {
                        System.out.println("Impossible de charger le fichier fxml");
                        System.exit(-1);
                }
                loader.setLocation(fxmlFileUrl);
                Parent root = loader.load();

                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("FXML demo");
                stage.show();
        }

        public static void main(String[] args) {
                Application.launch(args);
        }
}