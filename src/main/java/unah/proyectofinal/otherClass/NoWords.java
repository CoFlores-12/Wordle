package unah.proyectofinal.otherClass;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import unah.proyectofinal.WordleApplication;

import java.io.FileWriter;
import java.io.IOException;

public class NoWords {
    @FXML
    private AnchorPane ROOTNODE2;

    @FXML
    public void exitButton(){
        try {
            FileWriter wordPlayed = new FileWriter("src/main/resources/files/palabrasJugadas.txt");
            wordPlayed.append("");
            wordPlayed.close();
            WordleApplication.loadWords();
            WordleApplication.changeScene("mainGame.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
