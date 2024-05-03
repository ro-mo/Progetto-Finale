package model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MusicPlayer implements Runnable {
    
    private Semaphore songSemaphore = new Semaphore(1);
    private MediaPlayer mediaPlayer;
    private ArrayList<Song> playlist;
    private int currentIndex = -1;
    private Song currentSong;
    private boolean reproducing = false;
    private boolean looping = false;
    private boolean shuffling = false;
    
    public static String standardPath = "/Users/matteo/Documents/progettoFinale/src/progetto_finale_songs";
    public HashMap<String, ArrayList<Song>> allSongs;
    
    public MusicPlayer() {
        playlist = new ArrayList<>();
        allSongs = new HashMap<>();
    }
    
    @Override
    public void run() {
        while (true) {
            if (!reproducing && !playlist.isEmpty()) {
                start(playlist.get((currentIndex + 1) % playlist.size()));
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void start(Song song) {
        try {
            songSemaphore.acquire();
            
            Media media = new Media(new File(song.getPath()).toURI().toString());
            
            mediaPlayer = new MediaPlayer(media);
            
            double startTime = song.getSavedTime();
            
            mediaPlayer.setStartTime(Duration.millis(startTime));
            
            mediaPlayer.play();
            setReproducing(true);
            setCurrentSong(song);
            currentIndex = playlist.indexOf(song); // Imposta l'indice alla nuova canzone corrente
            
            mediaPlayer.setOnEndOfMedia(() -> {
                mediaPlayer.stop();
                if(isLooping()) {
                    start(currentSong);
                } else if (isShuffling()) {
                    start(getRandomSong());
                } else {
                    goForward(); // Cambia alla prossima canzone
                }
            });
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        mediaPlayer.stop();
        setReproducing(false);
        songSemaphore.release();
    }

    public void saveStoppedSong() {
        // Verifica se c'è una canzone attualmente in riproduzione
        if (mediaPlayer != null) {
            // Ottieni il tempo corrente della canzone in millisecondi
            double currentTime = mediaPlayer.getCurrentTime().toMillis();
            // Imposta il tempo corrente della canzone nel MusicPlayer
            currentSong.setSavedTime(currentTime);
        }
    }
    
    public void setLooping(boolean loop) {
        looping = loop;
    }
    
    public boolean isLooping() {
    	return looping;
    }
    
    public void setShuffling(boolean shuffle) {
        shuffling = shuffle;
    }
    
    public boolean isShuffling() {
    	return shuffling;
    }
    
    public void addToPlaylist(Song song) {
        playlist.add(song);
    }
    
    public void goBack() {
        if (currentIndex > 0) {
            currentIndex--; // Decrementa l'indice solo se è maggiore di zero
        } else {
            currentIndex = playlist.size() - 1; // Imposta l'indice alla fine della playlist se l'indice è zero
        }
        setCurrentSong(playlist.get(currentIndex)); // Imposta la nuova canzone corrente
    }

    public void goForward() {
        if (!playlist.isEmpty()) { // Verifica se la playlist non è vuota
            currentIndex = (currentIndex + 1) % playlist.size(); // Incrementa l'indice in modo circolare
            setCurrentSong(playlist.get(currentIndex)); // Imposta la nuova canzone corrente
        } else {
        	System.out.println("frocio");
        }
    }

    public Song getRandomSong() {
        return playlist.get((int) (Math.random()*playlist.size()+0));
    }
    
    public void loadPlaylist(String title) {
    	
    	for (String key : allSongs.keySet()) {
			if(key.equalsIgnoreCase(title)) {
				playlist = allSongs.get(key);
			}
		}
    	
    }
    
    public HashMap<String, ArrayList<Song>> loadAllSongs(String parentFolderPath) {

        File parentFolder = new File(parentFolderPath);

        if (parentFolder.exists() && parentFolder.isDirectory()) {
            File[] playlistFolders = parentFolder.listFiles(File::isDirectory);

            if (playlistFolders != null) {
                for (File playlistFolder : playlistFolders) {
                    ArrayList<Song> songsInPlaylist = new ArrayList<>();

                    File[] songFiles = playlistFolder.listFiles();
                    if (songFiles != null) {
                        for (File songFile : songFiles) {
                            Song song = loadSong(songFile);
                            if (song != null) {
                                songsInPlaylist.add(song);
                            }
                        }
                    } else {
                    	System.out.println("Canzoni non esistenti");
                    }
                    
                    String playlistName = playlistFolder.getName();
                    allSongs.put(playlistName, songsInPlaylist);
                    System.out.println("LoadAllSongs:\n" + allSongs.keySet());
                }
            } else {
            	System.out.println("Playlist non esistenti");
            }
        } else {
            System.out.println("La cartella genitore non esiste o non è una directory.");
        }
        
        return allSongs;
    }


    public Song loadSong(File songFile) {
        Media media = new Media(songFile.toURI().toString());

        String name = songFile.getName();
        String parts[] = name.split("_");
        String songTitle = parts[0].trim();
        String songArtist = (parts.length > 1) ? parts[1].replace(".mp3", "").trim() : "Sconosciuto";

        int duration = (int) media.getDuration().toMillis();

        return new Song(songTitle, songArtist, songFile.getAbsolutePath(), duration);
    }

    public ArrayList<Song> getPlaylistSongs(String playlistName) {
        ArrayList<Song> songs = allSongs.get(playlistName);
        return songs;
    }
	
	public void addPlaylist(String playlistName) {
        String folderPath = standardPath + File.separator + playlistName;
        File newFolder = new File(folderPath);
        boolean success = newFolder.mkdir();
        if (success) {
            System.out.println("Cartella della playlist creata con successo: " + folderPath);
        } else {
            System.out.println("Impossibile creare la cartella della playlist: " + folderPath);
        }
    }
	
	public void removePlaylistFolder(String playlistName) {
        String folderPath = standardPath + File.separator + playlistName;
        File playlistFolder = new File(folderPath);
        boolean success = playlistFolder.delete();
        if (success) {
            System.out.println("Playlist rimossa con successo");
        } else {
            System.out.println("Impossibile rimuovere la playlist");
        }
    }
	
	public ArrayList<String> getPlaylists(){
		ArrayList<String> playlists = new ArrayList<>();
		System.out.println("getPlaylists:\n" + allSongs.keySet());
		for (String string : allSongs.keySet()) {
			playlists.add(string);
		}
		return playlists;
	}

	public boolean isReproducing() {
		return reproducing;
	}

	public void setReproducing(boolean reproducing) {
		this.reproducing = reproducing;
	}

	public Song getCurrentSong() {
		return currentSong;
	}

	public void setCurrentSong(Song currentSong) {
		this.currentSong = currentSong;
	}
	
	public int getSize() {
		return allSongs.size();
	}
	
	public String getStandardPath() {
		return standardPath;
	}
	
}
