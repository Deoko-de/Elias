package eliasproject.elias;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Timer extends Application {

    private int totalSeconds = 1; // 2 минуты (120 секунд)
    private Label timerLabel = new Label();
    private Timeline timeline;
    @Override
    public void start(Stage primaryStage) {
        // Настраиваем отображение таймера
        timerLabel.setStyle("-fx-font-size: 48px; -fx-font-weight: bold;");
        updateTimerDisplay();

        StackPane root = new StackPane(timerLabel);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 300, 200);
        primaryStage.setTitle("Таймер обратного отсчета");
        primaryStage.setScene(scene);
        primaryStage.show();

        startCountdown();
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

        // Меняем цвет при приближении к нулю
        if (totalSeconds <= 10) {
            timerLabel.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: red;");
        }
    }

    private void timerFinished() {
        timerLabel.setText("00:00");
        System.out.println("Таймер завершил работу!");
        // Здесь можно добавить дополнительные действия по завершении
    }

    public static void main(String[] args) {
        launch(args);
    }
}