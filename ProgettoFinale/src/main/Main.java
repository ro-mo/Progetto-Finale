package main;

import controller.Controller;
import model.MusicPlayer;
import view.Finestra;

public class Main {

	public static void main(String[] args) {
		Finestra frame = new Finestra();
		MusicPlayer model = new MusicPlayer();
		Controller controller = new Controller(model, frame.getPannello());
		

	}

}
