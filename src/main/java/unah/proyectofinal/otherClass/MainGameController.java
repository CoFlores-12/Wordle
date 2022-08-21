package unah.proyectofinal.otherClass;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import unah.proyectofinal.WordleApplication;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainGameController implements Initializable {
    private final TextField[][] textFields = new TextField[5][6];
    @FXML
    private AnchorPane ROOTNODE;
    @FXML
    private GridPane wordsGrid;
    @FXML
    private GridPane keyboardGrid1;
    @FXML
    private GridPane keyboardGrid2;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Button btnTemp;
        String styleCOlor="";
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                //creamos un nuevo textfield para agregarlo
                TextField textField = new TextField(WordleApplication.lettersIntents[i][j]);
                textFields[i][j]=textField;
                textFields[i][j].setStyle("-fx-background-color: rgba(255, 255, 255,0.1); -fx-border-color:grey;");

                //solo se validan los intentos que esten completos (con 5 caracteres)
                if (j < WordleApplication.indexRow ){
                    if (WordleApplication.getWordOfGame().contains(WordleApplication.lettersIntents[i][j])) {
                        textFields[i][j].setStyle("-fx-background-color: rgba(240, 240, 24,0.9); -fx-text-fill: rgba(10, 10, 10,0.9);");
                        styleCOlor = "-fx-background-color: rgba(240, 240, 24,0.9); -fx-text-fill: rgba(10, 10, 10,0.9);";

                        if (WordleApplication.getWordOfGame().charAt(i) == WordleApplication.lettersIntents[i][j].charAt(0)) {
                            textFields[i][j].setStyle("-fx-background-color: rgba(24, 240, 24,0.9); -fx-text-fill: rgba(10, 10, 10,0.9);");
                            styleCOlor = "-fx-background-color: rgba(24, 240, 24,0.9); -fx-text-fill: rgba(10, 10, 10,0.9);";
                        }

                    } else {

                        if (WordleApplication.lettersIntents[i][j].charAt(0) != ' ') {
                            textFields[i][j].setStyle("-fx-background-color: rgba(150, 150, 150,0.9); -fx-text-fill: rgba(240, 240, 240,0.9);");
                            styleCOlor = "-fx-background-color: rgba(150, 150, 150,0.9); -fx-text-fill: rgba(240, 240, 240,0.9);";
                        }
                    }
                }

                textFields[i][j].setPrefWidth(35);
                textFields[i][j].setPrefHeight(35);
                textFields[i][j].setEditable(false);
                textFields[i][j].setAlignment(Pos.CENTER);
                wordsGrid.add(textFields[i][j],i,j);

                if (!styleCOlor.equals("")){
                    for (int i2 = 0; i2 < 20; i2++) {
                        btnTemp = (Button) keyboardGrid1.getChildren().get(i2);
                        if (btnTemp.getText().equals(WordleApplication.lettersIntents[i][j]))
                            keyboardGrid1.getChildren().get(i2).setStyle(styleCOlor);
                    }
                    for (int i2 = 1; i2 < 9; i2++) {
                        btnTemp = (Button) keyboardGrid2.getChildren().get(i2);
                        if (btnTemp.getText().equals(WordleApplication.lettersIntents[i][j]))
                            keyboardGrid2.getChildren().get(i2).setStyle(styleCOlor);
                    }
                }

            }
        }

    }

    @FXML
    private void closeButtonAction(){
        Stage stage = (Stage) ROOTNODE.getScene().getWindow();
        stage.close();
    }


    @FXML
    private void buttonClick(ActionEvent e) {
        Button numberButton = (Button) e.getTarget();
        if (WordleApplication.indexColumn < 5) {
            WordleApplication.lettersIntents[WordleApplication.indexColumn][WordleApplication.indexRow] = numberButton.getText();
            wordsGrid.getChildren().remove(textFields[WordleApplication.indexColumn][WordleApplication.indexRow]);
            textFields[WordleApplication.indexColumn][WordleApplication.indexRow].setText(WordleApplication.lettersIntents[WordleApplication.indexColumn][WordleApplication.indexRow]);
            wordsGrid.add(textFields[WordleApplication.indexColumn][WordleApplication.indexRow],WordleApplication.indexColumn,WordleApplication.indexRow);
            WordleApplication.indexColumn++;


        }
    }

    @FXML
    private void sentClickButton(){
        if (WordleApplication.indexColumn==5) {

            Button btnTemp;
            int index=0;
            String styleCOlor;
            String wordEntered="";

            //constante la fila : usar la variable global
            //iterar la columna con for

            for (int col = 0; col < 5; col++) {
                styleCOlor="";
                if (WordleApplication.getWordOfGame().contains(WordleApplication.lettersIntents[col][WordleApplication.indexRow])) {
                    //color amarillo
                    styleCOlor="-fx-background-color: rgba(240, 240, 24,0.9); -fx-text-fill: rgba(10, 10, 10,0.9);";
                    if (WordleApplication.getWordOfGame().charAt(col) == WordleApplication.lettersIntents[col][WordleApplication.indexRow].charAt(0)) {
                        //color verde
                        styleCOlor="-fx-background-color: rgba(24, 240, 24,0.9); -fx-text-fill: rgba(10, 10, 10,0.9);";
                    }
                }else{
                    //color gris
                    styleCOlor="-fx-background-color: rgba(150, 150, 150,0.9); -fx-text-fill: rgba(240, 240, 240,0.9);";
                }

                wordsGrid.getChildren().remove(textFields[col][WordleApplication.indexRow]);
                textFields[col][WordleApplication.indexRow].setText(WordleApplication.lettersIntents[col][WordleApplication.indexRow]);
                textFields[col][WordleApplication.indexRow].setStyle(styleCOlor);
                wordsGrid.add(textFields[col][WordleApplication.indexRow],col,WordleApplication.indexRow);
                wordEntered += WordleApplication.lettersIntents[col][WordleApplication.indexRow];

                try {
                    FileWriter running = new FileWriter("src/main/resources/files/gameRunning.txt",true);
                    running.append(" "+WordleApplication.lettersIntents[col][WordleApplication.indexRow]);
                    running.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                for (int i = 0; i < 20; i++) {
                    btnTemp = (Button) keyboardGrid1.getChildren().get(i);
                    if (btnTemp.getText().equals(WordleApplication.lettersIntents[col][WordleApplication.indexRow]))
                        keyboardGrid1.getChildren().get(i).setStyle(styleCOlor);
                }
                for (int i = 1; i < 9; i++) {
                    btnTemp = (Button) keyboardGrid2.getChildren().get(i);
                    if (btnTemp.getText().equals(WordleApplication.lettersIntents[col][WordleApplication.indexRow]))
                        keyboardGrid2.getChildren().get(i).setStyle(styleCOlor);
                }
            }
            if (wordEntered.equals(WordleApplication.getWordOfGame())){
                WordleApplication.isWinner=true;
                WordleApplication.chnageSceneToWinner();
            }

            if (WordleApplication.indexRow==5 && !WordleApplication.isWinner){
                WordleApplication.isWinner=false;
                WordleApplication.chnageSceneToWinner();
            }
            WordleApplication.indexRow++;
            WordleApplication.indexColumn=0;




        }
    }

    @FXML
    private void buttonDeleteClick(){
        if (WordleApplication.indexColumn>0){
            WordleApplication.indexColumn--;
            WordleApplication.lettersIntents[WordleApplication.indexColumn][WordleApplication.indexRow] = " ";
            wordsGrid.getChildren().remove(textFields[WordleApplication.indexColumn][WordleApplication.indexRow]);
            textFields[WordleApplication.indexColumn][WordleApplication.indexRow].setText(WordleApplication.lettersIntents[WordleApplication.indexColumn][WordleApplication.indexRow]);
            wordsGrid.add(textFields[WordleApplication.indexColumn][WordleApplication.indexRow],WordleApplication.indexColumn,WordleApplication.indexRow);
        }
    }

    @FXML
    private void buttonMenuClick(){
        WordleApplication.changeScene("menu.fxml");
    }
}