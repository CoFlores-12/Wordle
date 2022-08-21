package unah.proyectofinal.otherClass;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import unah.proyectofinal.WordleApplication;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class WinnerScreen implements Initializable {

    @FXML
    private Label textInfo;
    @FXML
    private Label wordLabel;
    @FXML
    private AnchorPane ROOTNODE2;
    @FXML
    private ImageView imageInfo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ROOTNODE2.setStyle("-fx-background-radius: 30;");
        Image img=null;
        if (WordleApplication.isWinner){
            textInfo.setText("GANASTE!");
            textInfo.setStyle("-fx-text-fill: #087500;");
            img = new Image(Objects.requireNonNull(WordleApplication.class.getResourceAsStream("ganar.png")));
        }else{
            textInfo.setText("PERDISTE!");
            textInfo.setStyle("-fx-text-fill: rgb(190, 24, 24);");

            img = new Image(Objects.requireNonNull(WordleApplication.class.getResourceAsStream("PERDER.png")));
        }
        wordLabel.setText(wordLabel.getText() + WordleApplication.getWordOfGame());
        imageInfo.setImage(img);
        imageInfo.setFitHeight(150);
        imageInfo.setFitWidth(200);

        FileWriter runningTXT = null;
        try {
            runningTXT = new FileWriter("src/main/resources/files/gameRunning.txt");
            runningTXT.append("");
            runningTXT.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    public void closeButtonClick(){
        Stage stage = (Stage) ROOTNODE2.getScene().getWindow();
        stage.close();
    }
}
