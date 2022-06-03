package com.ricochet;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        VBox box = new VBox();

        MainGame mainGame = new MainGame();

        Canvas canvas = new Canvas(1280, 720);

        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mainGame.onMouseCLick(mouseEvent);
            }
        });


        GraphicsContext graphics_context =
                canvas.getGraphicsContext2D();

        mainGame.draw(graphics_context);

        box.getChildren().add(canvas);

        Scene scene = new Scene(box, 1280, 720);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}