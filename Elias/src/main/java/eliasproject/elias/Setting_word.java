package eliasproject.elias;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Setting_word extends Application {
    static Stage curstage;
    public static final Slider slider1 = createSlider(0, 70, 35);
    public static final Slider slider2 = createSlider(0, 50, 25);
    public static final Slider slider3 = createSlider(0, 40, 20);
    public static final Slider slider4 = createSlider(1, 120, 10);
    public static final Slider slider5 = createSlider(1, 10, 2);
    public static final Slider slider6 = createSlider(2, 4, 1);
    @Override
    public void start(Stage primaryStage) {
        curstage = primaryStage;
        Label label1 = new Label("Простые слова:");
        Label label2 = new Label("Средние слова:");
        Label label3 = new Label("Тяжелые слова:");
        Label label4 = new Label("Время раунда:");
        Label label5 = new Label("Кол-во раундов:");
        Label label6 = new Label("Кол-во команд:");

        Button processButton = new Button("Начать игру");
        processButton.setOnAction(e -> {
            processValues();
        });

        GridPane sliderGrid = new GridPane();
        sliderGrid.setHgap(10);
        sliderGrid.setVgap(10);
        sliderGrid.setPadding(new Insets(10));

        sliderGrid.addRow(0, label1, createSliderBox(slider1));
        sliderGrid.addRow(1, label2, createSliderBox(slider2));
        sliderGrid.addRow(2, label3, createSliderBox(slider3));
        sliderGrid.addRow(3, label4, createSliderBox(slider4));
        sliderGrid.addRow(4, label5, createSliderBox(slider5));
        sliderGrid.addRow(5, label6, createSliderBox(slider6));

        VBox root = new VBox(20, sliderGrid, processButton);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 400, 500);
        primaryStage.setTitle("Elias");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static Slider createSlider(int min, int max, int sr) {
        Slider slider = new Slider(min, max, sr);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(2);
        slider.setMinorTickCount(1);
        slider.setSnapToTicks(true);
        return slider;
    }

    private HBox createSliderBox(Slider slider) {
        Label valueLabel = new Label();
        valueLabel.setMinWidth(40);

        valueLabel.textProperty().bind(slider.valueProperty().asString("%.0f"));

        HBox box = new HBox(10, slider, valueLabel);
        box.setAlignment(Pos.CENTER_LEFT);
        return box;
    }

    public static void processValues() {
        if ((int)slider1.getValue() + (int)slider2.getValue() + (int)slider3.getValue() != 0){
            new Word_get().start(new Stage());
            curstage.close();
        }

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}