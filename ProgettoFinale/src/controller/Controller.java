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
    
    public void changePlaylist(ActionEvent e) {
    	String playlist = e.getActionCommand();
    	if(playlists.contains(playlist)) {
        	System.out.println("Generating songs button for playlist: " + playlist);
            pannello.clearSongButtons();
            pannello.generateSongsButton(musicPlayer.allSongs, this, playlist);
            PlaylistName = playlist;
    	}
    }
    
    public void displaySongs(ActionEvent e, ArrayList<Song> songs) {
    	String StrIndex = e.getActionCommand();
    	int index = Integer.valueOf(StrIndex);
    	if(index < songs.size()) {
    		if(!musicPlayer.isReproducing()) {
				musicPlayer.start(songs.get(index));
				pannello.setText(songs.get(index).toString());
				pannello.setIsReproducing();
				return;
			}else {
				musicPlayer.stop();
				musicPlayer.start(songs.get(index));
				pannello.setText(songs.get(index).toString());
				pannello.setIsReproducing();
			}
       	}
    }
    
    public void stopSong(ActionEvent e) {
    	String command = e.getActionCommand();
    	if(command.equalsIgnoreCase("pause")) {
    		if (musicPlayer.isReproducing()) {
            	musicPlayer.saveStoppedSong();
                musicPlayer.stop();
                pannello.setIsPause();
            }
    	}	
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action Command: " + e.getActionCommand());
        
        changePlaylist(e);
        
        ArrayList<Song> songs = musicPlayer.getPlaylistSongs(PlaylistName);
        displaySongs(e, songs);
        
        stopSong(e);
        
        switch (e.getActionCommand().toLowerCase()) {
            case "play":
                System.out.println("Play button pressed");
                if (musicPlayer.getCurrentSong().getSavedTime() == 0 && !musicPlayer.isReproducing()) {
                    musicPlayer.start(musicPlayer.getRandomSong());
                    pannello.setIsReproducing();
                } else if (musicPlayer.getCurrentSong().getSavedTime() != 0 && !musicPlayer.isReproducing()) {
                	musicPlayer.start(musicPlayer.getCurrentSong());
                	pannello.setIsReproducing();
                }
                break;
            case "back":
                System.out.println("Back button pressed");
                musicPlayer.stop();
                musicPlayer.goBack();
                musicPlayer.start(musicPlayer.getCurrentSong());
                pannello.setText(musicPlayer.getCurrentSong().toString());
                break;
            case "forward":
                System.out.println("Forward button pressed");
                musicPlayer.stop();
                musicPlayer.goForward();
                musicPlayer.start(musicPlayer.getCurrentSong());
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

