package unah.proyectofinal.otherClass;


import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Words {
    private static ArrayList<String> words;
    private static ArrayList<String> wordsPlayed;
    private static int wordsSize=0;
    private static int wordsPlayedSize=0;

    public static void LoadWords(){
        words= new ArrayList<String>();
        wordsPlayed = new ArrayList<String>();
        wordsSize=0;
        wordsPlayedSize=0;
        try {
            //leemos el archivo con las palabras previamente jugadas
            File tempFile = new File("src/main/resources/files/palabrasJugadas.txt");
            if(!tempFile.exists()){
                tempFile.createNewFile();
            }
            Scanner scan = new Scanner(new FileReader("src/main/resources/files/palabrasJugadas.txt"));
            while (scan.hasNext()){
                wordsPlayed.add(scan.next());
                wordsPlayedSize++;
            }

            //leemos el archivo con las palabras del juego
            tempFile = new File("src/main/resources/files/palabras.txt");
            if(!tempFile.exists()){
                tempFile.createNewFile();
            }
            scan = new Scanner(new FileReader("src/main/resources/files/palabras.txt"));

            while (scan.hasNext()){
                String wordRead=scan.next();
                wordsSize++;
                // si la palabra no ha sido jugada, se agrega a la partida
                if (!wordsPlayed.contains(wordRead))
                    words.add(wordRead);
            }
            scan.close();

        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getWordRandom(){
        //si las palabras no han sido cargadas, las carga
        if (getWordsSize()==0 && getWordsPlayedSize() == 0){
            LoadWords();
        }
        //devolver una palabra random de las palabras en la partida
        if (words.size() == 0) {
            System.out.println("Todos las palabras han sido jugadas");
            return " ";
        }
        return words.get( (int) (Math.random()*(words.size())));
    }

    public static int getWordsSize() {
        return wordsSize;
    }

    public static int getWordsPlayedSize() {
        return wordsPlayedSize;
    }
}
