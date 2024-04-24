package main;

import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import model.MusicPlayer;
import view.Finestra;

public class Main extends Application{

	public static void main(String[] args) {
		
		launch(args);	
		
	}

	@Override
	public void start(Stage primaryStage) {
		Finestra frame = new Finestra();
		MusicPlayer model = new MusicPlayer();
		Controller controller = new Controller(model, frame.getPannello());
	}

}
