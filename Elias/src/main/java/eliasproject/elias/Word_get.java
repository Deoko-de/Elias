package eliasproject.elias;

import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

public class Word_get extends Application {
    private final Label resultLabel = new Label();
    private final Label typeLabel = new Label();

    private final Random random = new Random();

    private int totalSeconds;
    private Label timerLabel = new Label();
    private Timeline timeline;
    public static int curRecord;
    Label curRecords = new Label();


    Stage curstage;
    public void start(Stage primaryStage) {

        curRecord = 0;
        int easy = (int) Setting_word.slider1.getValue();
        int medium = (int) Setting_word.slider2.getValue();
        int hard = (int) Setting_word.slider3.getValue();
        int time = (int) Setting_word.slider4.getValue();

        totalSeconds= time;

        Button selectButton = new Button("Верно");
        selectButton.setOnAction(event -> {
            selectRandomWord(easy,  medium,  hard, time);
            plusRecord();
        });
        Button skipButton = new Button("Пропустить");
        skipButton.setOnAction(event -> {
            totalSeconds -= 3;
            selectRandomWord(easy,  medium,  hard, time);
        });


        timerLabel.setStyle("-fx-font-size: 48px;");
        updateTimerDisplay();

        resultLabel.setStyle("-fx-font-size: 30px;");
        typeLabel.setStyle("-fx-font-size: 20px;");
        VBox root = new VBox(10,timerLabel, selectButton,skipButton, resultLabel,typeLabel,curRecords);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(root, 400, 500);
        primaryStage.setTitle("Генератор случайных слов");
        primaryStage.setScene(scene);
        primaryStage.show();
        curstage = primaryStage;
        startCountdown();
        selectRandomWord(easy,  medium,  hard, time);
    }

    private void selectRandomWord(int easy, int medium, int hard, int time) {
        int listIndex = random.nextInt((int) (easy+medium+hard));
        List<String> selectedList;
        String word_type;

        if (listIndex<easy) {
            selectedList = createWordArrayFromFile("Easy_word.txt");
            word_type = "Простое слово";
        }
        else if(listIndex>easy && listIndex<easy+medium) {
            selectedList = createWordArrayFromFile("Medium_word.txt");
            word_type = "Среднее слово";
        }
        else {
            selectedList = createWordArrayFromFile("Hard_word.txt");
            word_type = "Сложное слово";

        }

        String randomWord = selectedList.get(random.nextInt(selectedList.size()));

        resultLabel.setText(randomWord);
        typeLabel.setText(word_type);

    }
    private void plusRecord() {
        curRecord += 1;

        curRecords.setText(String.valueOf(curRecord));
    }

    public static void main( String[] args) {
        launch(args);
    }

    public static List<String> createWordArrayFromFile(String filePath) {
        List<String> wordList = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            for (String line : lines) {
                String[] wordsInLine = line.split("\\s+");
                for (String word : wordsInLine) {
                    if (!word.isEmpty()) {
                        wordList.add(word.trim());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            return null;
        }

        return wordList;
    }


    private void startCountdown() {
        if (timeline != null) {
            timeline.play();
        } else {
            timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1), event -> {
                        totalSeconds--;
                        updateTimerDisplay();

                        if (totalSeconds <= 0) {
                            timerFinished();
                        }
                    })
            );
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }
    }
    private void updateTimerDisplay() {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));

        if (totalSeconds <= 10) {
            timerLabel.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: red;");
        }
    }

    private void timerFinished() {

        timeline.stop();

        timerLabel.setText("00:00");
        System.out.println("Таймер завершил работу!");
        new Result().start(new Stage());
        curstage.close();
    }

}

