package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import model.MusicPlayer;
import model.Song;
import view.Pannello;

public class Controller implements ActionListener {
    
    private MusicPlayer musicPlayer;
    private Pannello pannello;
    private String PlaylistName;
    private ArrayList<String> playlists;

    public Controller(MusicPlayer musicPlayer, Pannello pannello) {
        this.musicPlayer = musicPlayer;
        this.pannello = pannello;
        this.pannello.registraEventi(this);
        this.musicPlayer.loadAllSongs(musicPlayer.getStandardPath());
        this.playlists = musicPlayer.getPlaylists();
        this.pannello.generatePlaylistButton(playlists, this);
        this.musicPlayer.setLooping(false);
        this.musicPlayer.setShuffling(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action Command: " + e.getActionCommand());
        for (String string : playlists) {
            System.out.println("Playlist: " + string);
            if (e.getActionCommand().equalsIgnoreCase(string)) {
                System.out.println("Generating songs button for playlist: " + string);
                pannello.clearSongButtons();
                pannello.generateSongsButton(musicPlayer.allSongs, this, string);
                PlaylistName = string;
                return;
            }
        }
        ArrayList<Song> songs = musicPlayer.getPlaylistSongs(PlaylistName);
        int i = 0;
        for (Song song : songs) {
			if (e.getActionCommand().equalsIgnoreCase("" + i)) {
				if(!musicPlayer.isReproducing()) {
					musicPlayer.start(song);
					pannello.setText(song.toString());
					pannello.setIsReproducing();
					return;
				}else {
					musicPlayer.stop();
					musicPlayer.start(song);
					pannello.setText(song.toString());
					pannello.setIsReproducing();
				}
			}else {
				i++;
			}
		}
        
        switch (e.getActionCommand().toLowerCase()) {
            case "play":
                System.out.println("Play button pressed");
                if (musicPlayer.getCurrentSong().getSavedTime() == 0 && !musicPlayer.isReproducing()) {
                    musicPlayer.start(musicPlayer.getRandomSong());
                    pannello.setIsReproducing();
                } else if (musicPlayer.getCurrentSong().getSavedTime() != 0 && !musicPlayer.isReproducing()) {
                	musicPlayer.resumeStoppedSong();
                	pannello.setIsReproducing();
                }
                break;
            case "pause":
                System.out.println("Stop button pressed");
                if (musicPlayer.isReproducing()) {
                	musicPlayer.saveStoppedSong();
                    musicPlayer.stop();
                    pannello.setIsPause();
                }
                break;
            case "back":
                System.out.println("Back button pressed");
                musicPlayer.stop();
                musicPlayer.goBack();
                pannello.setText(musicPlayer.getCurrentSong().toString());
                break;
            case "forward":
                System.out.println("Forward button pressed");
                musicPlayer.stop();
                musicPlayer.goForward();
                pannello.setText(musicPlayer.getCurrentSong().toString());
                break;
            case "loop":
                System.out.println("Loop " + musicPlayer.isLooping());
                musicPlayer.setLooping(!musicPlayer.isLooping());
                break;
            case "shuffle":
                System.out.println("Shuffle button pressed");
                musicPlayer.setShuffling(!musicPlayer.isShuffling());
                break;
        }
    }

}

