package pain;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
//import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainPain extends Application {

	@Override
	public void start(Stage fenetre) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Pain.fxml"));  //Permet de lier le document fxml a la fenetre principale
		Scene scene = new Scene(root); //definit la scene graphique principale contenant le ficihier fxml
		fenetre.centerOnScreen(); //centrer la fenetre
		fenetre.initStyle(StageStyle.UNDECORATED); //enleve la décoration sur la fenetre
		fenetre.setResizable(false); //empêche de modifier la taille de l'application
		fenetre.setScene(scene); //lie la fenetre et la scene
		fenetre.show(); //affiche la fenetre

	}

	public static void main(String[] args) {
		launch(args); //lance la fenetre  
	}

}
