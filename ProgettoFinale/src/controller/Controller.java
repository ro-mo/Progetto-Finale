package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.MusicPlayer;
import view.Pannello;

public class Controller implements ActionListener {
    
    private MusicPlayer musicPlayer;
    private Pannello pannello;

    public Controller(MusicPlayer musicPlayer, Pannello pannello) {
        this.musicPlayer = musicPlayer;
        this.pannello = pannello;
        this.pannello.registraEventi(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand().toLowerCase()) {
            case "play":
                if (!musicPlayer.isReproducing()) {
                    pannello.popup("Nessuna canzone in riproduzione");
                } else {
                    musicPlayer.play(musicPlayer.getCurrentSong());
                    Thread musicThread = new Thread(musicPlayer);
                    musicThread.start();
                }
                break;
            case "stop":
                if (musicPlayer.isReproducing()) {
                    musicPlayer.stop();
                }
                break;
            case "back":
                musicPlayer.stop();
                musicPlayer.goBack();
                pannello.setText(musicPlayer.getCurrentSong().toString());
                break;
            case "forward":
                musicPlayer.stop();
                musicPlayer.goForward();
                pannello.setText(musicPlayer.getCurrentSong().toString());
                break;
            case "loop":
                musicPlayer.setLooping(!musicPlayer.isLooping());
                break;
            case "shuffle":
                musicPlayer.setShuffling(!musicPlayer.isShuffling());
                break;
            default:
                break;
        }
    }
}

