package unah.proyectofinal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import unah.proyectofinal.otherClass.Words;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class WordleApplication extends Application {
    private static Stage stage;
    private static String wordOfGame;
    //guarda los intentos del jugador
    public static String[][] lettersIntents = new String[5][6];
    //nos permiten saber en que posicion se encuentra el juador en el tablero
    public static int indexRow = 0;
    public static int indexColumn = 0;
    //cambia a true solo si la palabra es adivinada por el jugador
    public static boolean isWinner=false;

    @Override
    public void start(Stage stage) {
        WordleApplication.stage = stage;
        WordleApplication.stage.initStyle(StageStyle.TRANSPARENT);
        WordleApplication.stage.setTitle("Wordle");

        if (wordOfGame.equals(" ")){
            changeScene("noWords.fxml");
        }else{
            changeScene("mainGame.fxml");
        }

        File tempFile = new File("src/main/resources/unah/proyectofinal/logos-UNAH-11.png");
        if(tempFile.exists()){
            WordleApplication.stage.getIcons().add(new Image(Objects.requireNonNull(WordleApplication.class.getResourceAsStream("logos-UNAH-11.png"))));
        }
        WordleApplication.stage.show();
    }

    public static String getWordOfGame() {
        return wordOfGame;
    }

    public static void changeScene(String nameFileFXML)  {

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(WordleApplication.class.getResource(nameFileFXML));
            Scene scene = new Scene(fxmlLoader.load(), 320, 500);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);

            Scanner scan = new Scanner(new FileReader("src/main/resources/files/settings.txt"));

            if (scan.hasNext()){
                //configuracion de tema de la aplicacion
                if (scan.nextInt() != 1){
                    stage.getScene().getStylesheets().add(Objects.requireNonNull(WordleApplication.class.getResource("estilo1.css")).toString());
                }else{
                    stage.getScene().getStylesheets().add(Objects.requireNonNull(WordleApplication.class.getResource("estilo2.css")).toString());
                }
            }else{
                //tema por defecto si el archivo settings.txt esta vacio
                stage.getScene().getStylesheets().add(WordleApplication.class.getResource("estilo1.css").toString());
            }

            scan.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }

    public static void chnageSceneToWinner(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(WordleApplication.class.getResource("winnerScreen.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 320, 300);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);

            Scanner scan = new Scanner(new FileReader("src/main/resources/files/settings.txt"));

            if (scan.hasNext()){
                //configuracion de tema de la aplicacion
                if (scan.nextInt() != 1){
                    stage.getScene().getStylesheets().add(WordleApplication.class.getResource("estilo1.css").toString());
                }else{
                    stage.getScene().getStylesheets().add(WordleApplication.class.getResource("estilo2.css").toString());
                }
            }else{
                //tema por defecto si el archivo settings.txt esta vacio
                stage.getScene().getStylesheets().add(WordleApplication.class.getResource("estilo1.css").toString());
            }

            scan.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static void loadWords(){

        //carga de palabras no jugadas del archivo palabras.txt a memoria
        Words.LoadWords();

        Scanner running = null;
        try {
            running = new Scanner(new FileReader("src/main/resources/files/gameRunning.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //se busca si hay una partida sin terminar
        if (running.hasNext()){
            wordOfGame = running.next();
        }else{
            //obtener una palabra aleatoria
            wordOfGame = Words.getWordRandom();
            try {
                //guardamos la palabra aleatoria como jugada y como partida del juego actual
                FileWriter file = new FileWriter("src/main/resources/files/palabrasJugadas.txt",true);
                file.append(" ").append(wordOfGame);
                file.close();
                FileWriter runningTXT = new FileWriter("src/main/resources/files/gameRunning.txt");
                runningTXT.append(wordOfGame);
                runningTXT.close();
                //volvemos a cargar las palabras, descartando la palabra aleatoria
                Words.LoadWords();
            }catch (IOException e){
                System.out.println("No se encontro archivo para guardar la palabra jugada");
            }
        }

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                //si hay intentos previos, los recupera
                if (running.hasNext()){
                    lettersIntents[j][i] = running.next();

                    //aumenta index de la columna jugada
                    indexColumn++;
                    //si la columna llega al maximo, la reinicia y aumenta la fila
                    if (indexColumn==5){
                        indexColumn=0;
                        indexRow++;
                    }
                }else{
                    lettersIntents[j][i] = " ";
                }
            }
        }

    }

    public static void main(String[] args) {
        loadWords();
        launch();
    }
}