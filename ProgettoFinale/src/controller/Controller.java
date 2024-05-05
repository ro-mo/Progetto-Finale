/**
 * Questa classe rappresenta il controller del programma, gestisce gli eventi dell'interfaccia grafica
 * e coordina le azioni tra il modello e la vista.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import model.MusicPlayer;
import model.Song;
import view.Pannello;

/**
 * La classe Controller implementa ActionListener per gestire gli eventi dell'interfaccia grafica.
 */
public class Controller implements ActionListener {
    
    private MusicPlayer musicPlayer;
    private Pannello pannello;
    private ArrayList<String> playlists;
    private ArrayList<Song> songs;

    /**
     * Costruttore della classe Controller.
     * @param musicPlayer Il player musicale.
     * @param pannello Il pannello dell'interfaccia grafica.
     */
    public Controller(MusicPlayer musicPlayer, Pannello pannello) {
        this.musicPlayer = musicPlayer;
        this.pannello = pannello;
        this.pannello.registraEventi(this);
        this.musicPlayer.loadAllSongs(musicPlayer.getStandardPath());
        this.playlists = musicPlayer.getPlaylists();
        this.pannello.generatePlaylistButton(playlists, this);
        this.musicPlayer.setLooping(false);
        this.musicPlayer.setShuffling(false);
        this.pannello.setTextAreaVisible(musicPlayer.isReproducing());
    }
    
    /**
     * Cambia la playlist corrente.
     * @param e L'evento di azione.
     */
    public void changePlaylist(ActionEvent e) {
        String playlist = e.getActionCommand();
        if(playlists.contains(playlist)) {
            System.out.println("Generating songs button for playlist: " + playlist);
            pannello.clearSongButtons();
            pannello.generateSongsButton(musicPlayer.allSongs, this, playlist);
            
            musicPlayer.loadPlaylist(playlist);
            songs = musicPlayer.playlist;
            musicPlayer.setCurrentIndex(0);
            
        }
    }

    /**
     * Visualizza le canzoni.
     * @param e L'evento di azione.
     * @param songs La lista delle canzoni.
     */
    public void displaySongs(ActionEvent e, ArrayList<Song> songs) {
        String strIndex = e.getActionCommand();
        try {
            int index = Integer.parseInt(strIndex);
            if (index < songs.size()) {
                if (!musicPlayer.isReproducing()) {
                    musicPlayer.start(songs.get(index));
                    pannello.setText(songs.get(index).toString());
                    pannello.setIsReproducing();
                    pannello.setTextAreaVisible(musicPlayer.isReproducing());
                    return;
                } else {
                    musicPlayer.stop();
                    musicPlayer.getCurrentSong().setSavedTime(0);
                    musicPlayer.start(songs.get(index));
                    pannello.setText(songs.get(index).toString());
                    pannello.setIsReproducing();
                    pannello.setTextAreaVisible(musicPlayer.isReproducing());
                }
            }
        } catch (NumberFormatException ex) {
        	
        }
    }

    /**
     * Ferma la canzone corrente.
     * @param e L'evento di azione.
     */
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
    
    /**
     * Riproduce la canzone.
     * @param e L'evento di azione.
     */
    public void playSong(ActionEvent e) {
    	String command = e.getActionCommand();
    	if(command.equalsIgnoreCase("play")) {
    		if(musicPlayer.getCurrentSong() != null) {
		    	if (musicPlayer.getCurrentSong().getSavedTime() == 0 && !musicPlayer.isReproducing()) {
		            musicPlayer.start(musicPlayer.getRandomSong());
		            pannello.setIsReproducing();
		            pannello.setTextAreaVisible(musicPlayer.isReproducing());
		        } else if (musicPlayer.getCurrentSong().getSavedTime() != 0 && !musicPlayer.isReproducing()) {
		        	musicPlayer.start(musicPlayer.getCurrentSong());
		        	pannello.setIsReproducing();
		        	pannello.setTextAreaVisible(musicPlayer.isReproducing());
		        }
    		}
    	}
    }
    
    /**
     * Torna alla canzone precedente.
     * @param e L'evento di azione.
     */
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

    /**
     * Passa alla canzone successiva.
     * @param e L'evento di azione.
     */
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

    /**
     * Attiva o disattiva la modalità loop.
     * @param e L'evento di azione.
     */
    public void loopSong(ActionEvent e) {
    	String command = e.getActionCommand();
    	if(command.equalsIgnoreCase("loop")) {
    		musicPlayer.setLooping(!musicPlayer.isLooping());
    		System.out.println("Loop - " + musicPlayer.isLooping());
    		pannello.setBtnLoop(musicPlayer.isLooping());
    	}
    }
    
    /**
     * Attiva o disattiva la modalità shuffle.
     * @param e L'evento di azione.
     */
    public void shuffleSong(ActionEvent e) {
    	String command = e.getActionCommand();
    	if(command.equalsIgnoreCase("shuffle")) {
    		musicPlayer.setShuffling(!musicPlayer.isShuffling());
    		pannello.setBtnShuffle(musicPlayer.isShuffling());
    	}
    }
    
    /**
     * Gestisce gli eventi di azione.
     * @param e L'evento di azione.
     */
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
