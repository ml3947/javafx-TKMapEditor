package com.wing.game.mapeditor;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("MainLayout.fxml"));
			Scene scene = new Scene(root,1024,640);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("WiTKMapEditor V0.7.0.2015.2.4");
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop() throws Exception {
		MainLayoutController.isRunning = false;
		super.stop();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
