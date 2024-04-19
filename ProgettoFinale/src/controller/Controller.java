package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.MusicPlayer;
import view.Pannello;

public class Controller implements ActionListener {
	
	MusicPlayer m = new MusicPlayer();
	Pannello p = new Pannello();

	public Controller(MusicPlayer m, Pannello p) {
		this.m = m;
		this.p = p;
		p.registraEventi(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equalsIgnoreCase("play")) {
			if(!m.isReproducing()) {
				p.popup("Nessuna canzone in riproduzione");
			}else{
				m.play(m.getCurrentSong());
			}
		}else if(e.getActionCommand().equalsIgnoreCase("back")) {
			m.stop();
			m.goBack();
			p.setText(m.getCurrentSong().getTitle()+ "\n" + m.getCurrentSong().getAuthor());
		}else if(e.getActionCommand().equalsIgnoreCase("foward")) {
			m.stop();
			m.goForward();
			p.setText(m.getCurrentSong().getTitle()+ "\n" + m.getCurrentSong().getAuthor());
		}else if(e.getActionCommand().equalsIgnoreCase("loop")) {
			if(!m.isLooping()) {
				m.setLooping(true);
			}else {
				m.setLooping(false);
			}
		}else if(e.getActionCommand().equalsIgnoreCase("shuffle")) {
			if(!m.isShuffling()) {
				m.setShuffling(true);
			}else {
				m.setShuffling(false);
			}
		}
		
	}

}
