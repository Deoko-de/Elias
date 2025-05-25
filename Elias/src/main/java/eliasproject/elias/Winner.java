package eliasproject.elias;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

public class Winner extends Application {
    static Stage curstage;

    @Override
    public void start(Stage primaryStage) {
        curstage = primaryStage;

        int maxTeam = (int) Setting_word.slider6.getValue();
        int[] teamScores =  Arrays.copyOfRange(Result.records, 0, maxTeam);


        Integer[] sortedIndices = IntStream.range(0, teamScores.length)
                .boxed()
                .sorted(Comparator.comparingInt(i -> -teamScores[i]))
                .toArray(Integer[]::new);


        VBox resultsBox = new VBox(10);
        resultsBox.setAlignment(Pos.CENTER);
        resultsBox.setPadding(new Insets(20));


        Label winnerLabel = new Label(
                "Команда " + (sortedIndices[0] + 1) + " победила!"
        );
        winnerLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2E8B57;");
        resultsBox.getChildren().add(winnerLabel);


        String[] placeTexts = {"второе", "третье", "четвертое"};
        for (int i = 1; i < sortedIndices.length; i++) {
            Label placeLabel = new Label(
                    "Команда " + (sortedIndices[i] + 1) + " заняла " + placeTexts[i-1] + " место"
            );
            placeLabel.setStyle("-fx-font-size: 16px;");
            resultsBox.getChildren().add(placeLabel);
        }


        Button actionButton = new Button("Новая игра");
        actionButton.setOnAction(e -> {
            Restart();
        });


        VBox root = new VBox(20, resultsBox, actionButton);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));


        primaryStage.setScene(new Scene(root, 400, 500));
        primaryStage.setTitle("Результаты команд");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    public  void Restart () {
        new Setting_word().start(new Stage());
        curstage.close();

    }
}