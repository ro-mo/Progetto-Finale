package model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import view.Pannello;

public class MusicPlayer implements Runnable {
    
    private Semaphore songSemaphore = new Semaphore(1);
    private MediaPlayer mediaPlayer;
    private int currentIndex = -1;
    private Song currentSong;
    private boolean reproducing = false;
    private boolean looping = false;
    private boolean shuffling = false;
    
    //public static String standardPath = "C:\\Users\\Edoardo Menegazzi\\eclipse-workspace\\ProgettoFinale\\ProgettoFinale\\src\\progetto_finale_songs";
    public static String standardPath = "/Users/matteo/Documents/progettoFinale/src/progetto_finale_songs";
    public HashMap<String, ArrayList<Song>> allSongs;
    public ArrayList<Song> playlist;
    Pannello pannello = new Pannello();
    
    public MusicPlayer() {
    	
    }
    
    @Override
    public void run() {
    	
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
            System.out.println("current song: " + getCurrentSong());
            System.out.println("current index: " + getCurrentIndex());
            currentIndex = playlist.indexOf(song);
            
            mediaPlayer.setOnEndOfMedia(() -> {
                mediaPlayer.stop();
                if(isLooping()) {
                	mediaPlayer.setStartTime(Duration.millis(0));
                	mediaPlayer.play();
                } else if (isShuffling()) {
                	Song newSong = getRandomSong();
                	Media mediaNewSong = new Media(new File(newSong.getPath()).toURI().toString());
                	mediaPlayer = new MediaPlayer(mediaNewSong);
                	mediaPlayer.setStartTime(Duration.millis(0));
                	mediaPlayer.play();
                    setCurrentSong(newSong);
                } else if(isLooping() && isShuffling()){
                	mediaPlayer.setStartTime(Duration.millis(0));
                	mediaPlayer.play();
                } else {
                	goForward(playlist);
                	Media mediaNewSong = new Media(new File(currentSong.getPath()).toURI().toString());
                	mediaPlayer = new MediaPlayer(mediaNewSong);
                	mediaPlayer.setStartTime(Duration.millis(0));
                	mediaPlayer.play();
                	pannello.setText(currentSong.toString()); 
                }
            });
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        mediaPlayer.pause();
        setReproducing(false);
        songSemaphore.release();
    }

    public void saveStoppedSong() {
        if (mediaPlayer != null) {
            double currentTime = mediaPlayer.getCurrentTime().toMillis();
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
    
    public void goBack(ArrayList<Song> playlist) {
        if (playlist.isEmpty()) {
            System.out.println("La playlist è vuota.");
            return;
        }

        currentIndex = (currentIndex - 1 + playlist.size()) % playlist.size();
        setCurrentSong(playlist.get(currentIndex));
    }

    public void goForward(ArrayList<Song> playlist) {
        if (playlist.isEmpty()) {
            System.out.println("La playlist è vuota.");
            return;
        }

        currentIndex = (currentIndex + 1) % playlist.size();
        setCurrentSong(playlist.get(currentIndex));
    }
    
    public Song getRandomSong() {
        if (!playlist.isEmpty()) {
            return playlist.get((int) (Math.random() * playlist.size()));
        } else {
            System.out.println("La playlist è vuota, non ci sono canzoni da riprodurre.");
            return null;
        }
    }
    
    public void loadPlaylist(String title) {
        for (String key : allSongs.keySet()) {
            if(key.equalsIgnoreCase(title)) {
                playlist = allSongs.get(key);
            }
        }
    }
    
    public HashMap<String, ArrayList<Song>> loadAllSongs(String parentFolderPath) {
        allSongs = new HashMap<>();
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
        currentIndex = playlist.indexOf(currentSong);
    }
    
    public int getSize() {
        return allSongs.size();
    }
    
    public String getStandardPath() {
        return standardPath;
    }

	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	public ArrayList<Song> getPlaylist() {
		return playlist;
	}

	public void setPlaylist(ArrayList<Song> playlist) {
		this.playlist = playlist;
	}
}

