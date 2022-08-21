package unah.proyectofinal.otherClass;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import unah.proyectofinal.WordleApplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML
    private Button backButton;
    @FXML
    private Label labelWordsPlayed;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelWordsPlayed.setText((Words.getWordsPlayedSize())+"/"+Words.getWordsSize());
    }

    @FXML
    public void clickBackButton() {
        WordleApplication.changeScene("mainGame.fxml");
    }

    @FXML
    public void clicklightButton() {
        changeTheme("2");
    }

    @FXML
    public void clickDarkButton() {
        changeTheme("1");
    }

    public void changeTheme(String theme){
        try {
            FileWriter settings = new FileWriter(new File("src/main/resources/files/settings.txt"));
            settings.append(theme);
            settings.close();
            WordleApplication.changeScene("menu.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
