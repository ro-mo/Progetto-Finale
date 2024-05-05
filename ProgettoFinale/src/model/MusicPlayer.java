package model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import view.Pannello;

/**
 * La classe MusicPlayer implementa Runnable per gestire la riproduzione delle canzoni.
 */
public class MusicPlayer implements Runnable {
    
    private Semaphore songSemaphore = new Semaphore(1);
    private MediaPlayer mediaPlayer;
    private int currentIndex = -1;
    private Song currentSong;
    private boolean reproducing = false;
    private boolean looping = false;
    private boolean shuffling = false;
    private Pannello pannello;
    
    public static String standardPath = "C:\\Users\\Edoardo Menegazzi\\eclipse-workspace\\ProgettoFinale\\ProgettoFinale\\src\\progetto_finale_songs";
    //public static String standardPath = "/Users/matteo/Documents/progettoFinale/src/progetto_finale_songs";
    public HashMap<String, ArrayList<Song>> allSongs;
    public ArrayList<Song> playlist;
    
    /**
     * Costruttore della classe MusicPlayer.
     * @param pannello Il pannello dell'interfaccia grafica.
     */
    public MusicPlayer(Pannello pannello) {
    	this.pannello=pannello;
    }
    
    @Override
    public void run() {
    	
    }

    /**
     * Avvia la riproduzione di una canzone.
     * @param song La canzone da riprodurre.
     */
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
                }
                
                pannello.setText(getCurrentSong().toString());
            });
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ferma la riproduzione della canzone corrente.
     */
    public void stop() {
        mediaPlayer.pause();
        setReproducing(false);
        songSemaphore.release();
    }

    /**
     * Salva la canzone corrente quando viene interrotta.
     */
    public void saveStoppedSong() {
        if (mediaPlayer != null) {
            double currentTime = mediaPlayer.getCurrentTime().toMillis();
            currentSong.setSavedTime(currentTime);
        }
    }
    
    /**
     * Imposta la modalità loop.
     * @param loop Se true, attiva la modalità loop.
     */
    public void setLooping(boolean loop) {
        looping = loop;
    }
    
    /**
     * Controlla se la modalità loop è attiva.
     * @return true se la modalità loop è attiva, altrimenti false.
     */
    public boolean isLooping() {
        return looping;
    }
    
    /**
     * Imposta la modalità shuffle.
     * @param shuffle Se true, attiva la modalità shuffle.
     */
    public void setShuffling(boolean shuffle) {
        shuffling = shuffle;
    }
    
    /**
     * Controlla se la modalità shuffle è attiva.
     * @return true se la modalità shuffle è attiva, altrimenti false.
     */
    public boolean isShuffling() {
        return shuffling;
    }
    
    /**
     * Torna alla canzone precedente nella playlist.
     * @param playlist La playlist corrente.
     */
    public void goBack(ArrayList<Song> playlist) {
        if (playlist.isEmpty()) {
            System.out.println("La playlist è vuota.");
            return;
        }

        currentIndex = (currentIndex - 1 + playlist.size()) % playlist.size();
        setCurrentSong(playlist.get(currentIndex));
    }

    /**
     * Passa alla canzone successiva nella playlist.
     * @param playlist La playlist corrente.
     */
    public void goForward(ArrayList<Song> playlist) {
        if (playlist.isEmpty()) {
            System.out.println("La playlist è vuota.");
            return;
        }

        currentIndex = (currentIndex + 1) % playlist.size();
        setCurrentSong(playlist.get(currentIndex));
    }
    
    /**
     * Ottiene una canzone casuale dalla playlist.
     * @return Una canzone casuale dalla playlist.
     */
    public Song getRandomSong() {
        if (!playlist.isEmpty()) {
            return playlist.get((int) (Math.random() * playlist.size()));
        } else {
            System.out.println("La playlist è vuota, non ci sono canzoni da riprodurre.");
            return null;
        }
    }
    
    /**
     * Carica una playlist.
     * @param title Il titolo della playlist da caricare.
     */
    public void loadPlaylist(String title) {
        for (String key : allSongs.keySet()) {
            if(key.equalsIgnoreCase(title)) {
                playlist = allSongs.get(key);
            }
        }
    }
    
    /**
     * Carica tutte le canzoni da una cartella.
     * @param parentFolderPath Il percorso della cartella da cui caricare le canzoni.
     * @return Un HashMap che mappa i titoli delle playlist alle loro rispettive canzoni.
     */
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

    /**
     * Carica una canzone da un file.
     * @param songFile Il file da cui caricare la canzone.
     * @return Un oggetto Song che rappresenta la canzone caricata.
     */
    public Song loadSong(File songFile) {
        Media media = new Media(songFile.toURI().toString());
        String name = songFile.getName();
        String parts[] = name.split("_");
        String songTitle = parts[0].trim();
        String songArtist = (parts.length > 1) ? parts[1].replace(".mp3", "").trim() : "Sconosciuto";
        int duration = (int) media.getDuration().toMillis();
        return new Song(songTitle, songArtist, songFile.getAbsolutePath(), duration);
    }
    
    /**
     * Ottiene la lista dei titoli delle playlist.
     * @return Una lista dei titoli delle playlist.
     */
    public ArrayList<String> getPlaylists(){
        ArrayList<String> playlists = new ArrayList<>();
        System.out.println("getPlaylists:\n" + allSongs.keySet());
        for (String string : allSongs.keySet()) {
            playlists.add(string);
        }
        return playlists;
    }

    /**
     * Controlla se il player musicale sta riproducendo una canzone.
     * @return true se il player musicale sta riproducendo una canzone, altrimenti false.
     */
    public boolean isReproducing() {
        return reproducing;
    }

    /**
     * Imposta lo stato di riproduzione del player musicale.
     * @param reproducing Se true, il player musicale è in riproduzione.
     */
    public void setReproducing(boolean reproducing) {
        this.reproducing = reproducing;
    }
    
    /**
     * Ottiene la canzone corrente.
     * @return La canzone corrente.
     */
    public Song getCurrentSong() {
        return currentSong;
    }

    /**
     * Imposta la canzone corrente.
     * @param currentSong La canzone da impostare come corrente.
     */
    public void setCurrentSong(Song currentSong) {
        this.currentSong = currentSong;
        currentIndex = playlist.indexOf(currentSong);
    }
    
    /**
     * Ottiene la dimensione della lista di tutte le canzoni.
     * @return La dimensione della lista di tutte le canzoni.
     */
    public int getSize() {
        return allSongs.size();
    }
    
    /**
     * Ottiene il percorso standard delle canzoni.
     * @return Il percorso standard delle canzoni.
     */
    public String getStandardPath() {
        return standardPath;
    }

    /**
     * Ottiene l'indice della canzone corrente.
     * @return L'indice della canzone corrente.
     */
	public int getCurrentIndex() {
		return currentIndex;
	}

	/**
     * Imposta l'indice della canzone corrente.
     * @param currentIndex L'indice da impostare come indice corrente.
     */
	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	/**
     * Ottiene la playlist corrente.
     * @return La playlist corrente.
     */
	public ArrayList<Song> getPlaylist() {
		return playlist;
	}

	/**
     * Imposta la playlist corrente.
     * @param playlist La playlist da impostare come corrente.
     */
	public void setPlaylist(ArrayList<Song> playlist) {
		this.playlist = playlist;
	}
}

