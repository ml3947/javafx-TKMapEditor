package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,790,640);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			MainCanvas canvas = new MainCanvas(800,650);
			root.getChildren().add(canvas);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("TKMapEditor示例");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
