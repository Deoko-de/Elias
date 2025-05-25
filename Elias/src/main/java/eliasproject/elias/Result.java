package eliasproject.elias;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Result extends Application {
public int maxTurn;
public int maxRound;
public static int curTurn = 0;
public static int curRound = 0;
public static int[] records = {0,0,0,0};

    Label turn = new Label();
    Label roundl = new Label();

    @Override
    public void start(Stage primaryStage) {
        int maxTeam = (int) Setting_word.slider6.getValue();
        int round = (int) Setting_word.slider5.getValue();

        maxTurn = maxTeam;
        maxRound = round;

        turn.setText("Ход: " +String.valueOf(curTurn+1));

        roundl.setText("Раунд: " +String.valueOf(curRound+1));
        VBox numbersBox = new VBox(10,turn,roundl);
        numbersBox.setAlignment(Pos.CENTER);
        numbersBox.setPadding(new Insets(20));

        records[curTurn] += Word_get.curRecord;

        for (int i = 0; i < maxTeam; i++) {
            Label numberLabel = new Label("Команда " + (int)(i+1) + ": " + String.valueOf(records[i]));

            numberLabel.setStyle("-fx-font-size: 24px;");
            numbersBox.getChildren().add(numberLabel);
        }

        Button actionButton = new Button("Следующий раунд");
        actionButton.setOnAction(e -> {
            nextTurn();
            primaryStage.close();
        });

        VBox root = new VBox(20, numbersBox, actionButton);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        primaryStage.setScene(new Scene(root, 400, 500));
        primaryStage.setTitle("Отображение чисел");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    private void nextTurn() {
        curTurn +=1;
        turn.setText("Ход: " + String.valueOf(curTurn+1));
        if (curTurn == maxTurn){
            curTurn = 0;
            turn.setText("Ход: " + String.valueOf(curTurn+1));
            curRound+=1;
            roundl.setText("Раунд: " + String.valueOf(curRound+1));
            if (curRound == maxRound){
                curRound = 0;
                roundl.setText("Раунд: " +String.valueOf(curRound+1));
                new Winner().start(new Stage());

            }
            else{
                new Word_get().start(new Stage());
            }
        }
        else{
            new Word_get().start(new Stage());
        }

    }
}