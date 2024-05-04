package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import model.MusicPlayer;
import model.Song;
import view.Pannello;

public class Controller implements ActionListener {
    
    private MusicPlayer musicPlayer;
    private Pannello pannello;
    private ArrayList<String> playlists;
    private ArrayList<Song> songs;

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
            
            songs = musicPlayer.allSongs.get(playlist);
            musicPlayer.setCurrentIndex(0);
            
        }
    }

    public void displaySongs(ActionEvent e, ArrayList<Song> songs) {
        String strIndex = e.getActionCommand();
        try {
            int index = Integer.parseInt(strIndex);
            if (index < songs.size()) {
                if (!musicPlayer.isReproducing()) {
                    musicPlayer.start(songs.get(index));
                    pannello.setText(songs.get(index).toString());
                    pannello.setIsReproducing();
                    return;
                } else {
                    musicPlayer.stop();
                    musicPlayer.getCurrentSong().setSavedTime(0);
                    musicPlayer.start(songs.get(index));
                    pannello.setText(songs.get(index).toString());
                    pannello.setIsReproducing();
                }
            }
        } catch (NumberFormatException ex) {
        	
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
    
    public void playSong(ActionEvent e) {
    	String command = e.getActionCommand();
    	if(command.equalsIgnoreCase("play")) {
    		if(musicPlayer.getCurrentSong() != null) {
		    	if (musicPlayer.getCurrentSong().getSavedTime() == 0 && !musicPlayer.isReproducing()) {
		            musicPlayer.start(musicPlayer.getRandomSong());
		            pannello.setIsReproducing();
		        } else if (musicPlayer.getCurrentSong().getSavedTime() != 0 && !musicPlayer.isReproducing()) {
		        	musicPlayer.start(musicPlayer.getCurrentSong());
		        	pannello.setIsReproducing();
		        }
    		}
    	}
    }
    
    public void backSong(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equalsIgnoreCase("back")) {
            musicPlayer.stop();
            musicPlayer.goBack(songs);
            musicPlayer.start(musicPlayer.getCurrentSong()); // Avvia la riproduzione della nuova canzone corrente
            System.out.println("current song: " + musicPlayer.getCurrentSong());
            pannello.setText(musicPlayer.getCurrentSong().toString());
        }
    }

    public void forwardSong(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equalsIgnoreCase("forward") && musicPlayer.getCurrentSong() != null) {
            musicPlayer.stop();
            musicPlayer.goForward(songs);
            musicPlayer.start(musicPlayer.getCurrentSong()); // Avvia la riproduzione della nuova canzone corrente
            System.out.println("current song: " + musicPlayer.getCurrentSong());
            System.out.println("current index: " + musicPlayer.getCurrentIndex());
            pannello.setText(musicPlayer.getCurrentSong().toString());
        }
    }

    
    public void loopSong(ActionEvent e) {
    	String command = e.getActionCommand();
    	if(command.equalsIgnoreCase("loop")) {
    		musicPlayer.setLooping(!musicPlayer.isLooping());
    		System.out.println("Loop - " + musicPlayer.isLooping());
    		pannello.setBtnLoop(musicPlayer.isLooping());
    	}
    }
    
    public void shuffleSong(ActionEvent e) {
    	String command = e.getActionCommand();
    	if(command.equalsIgnoreCase("shuffle")) {
    		musicPlayer.setShuffling(!musicPlayer.isShuffling());
    		pannello.setBtnShuffle(musicPlayer.isShuffling());
    	}
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action Command: " + e.getActionCommand());
        
        changePlaylist(e);
        
        displaySongs(e, songs);
        
        stopSong(e);
        playSong(e);
        backSong(e);
        forwardSong(e);
        loopSong(e);
        shuffleSong(e);
        
    }

}

