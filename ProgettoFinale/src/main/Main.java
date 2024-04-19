package main;

import model.MusicPlayer;
import view.Finestra;
import controller.Controller;

public class Main {

	public static void main(String[] args) {
		
		Finestra frame = new Finestra();
		MusicPlayer musicPlayer = new MusicPlayer();
		Controller controller = new Controller(musicPlayer,frame.getPannello());
	}

}
