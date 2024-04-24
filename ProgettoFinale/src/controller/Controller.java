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
                	int size = musicPlayer.getSize();
                	musicPlayer.play(musicPlayer.allSongs.get(Math.random() * size + 0));
                	pannello.setIsReproducing();
                }
                break;
            case "stop":
                if (musicPlayer.isReproducing()) {
                    musicPlayer.stop();
                    pannello.setIsPause();
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

