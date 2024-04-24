package model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicPlayer implements Runnable {
    
    private Semaphore songSemaphore = new Semaphore(1);
    private MediaPlayer mediaPlayer;
    private ArrayList<Song> playlist;
    private int currentIndex = -1;
    private Song currentSong;
    private boolean reproducing = false;
    private boolean looping = false;
    private boolean shuffling = false;
    
    public static String standardPath = "/Users/matteo/Documents/progettoFinale2/src/progetto_finale_songs";
    public HashMap<String, Song> allSongs = new HashMap<>();
    
    public MusicPlayer() {
        playlist = new ArrayList<>();
    }
    
    @Override
    public void run() {
        while (true) {
            if (!reproducing && !playlist.isEmpty()) {
                play(playlist.get((currentIndex + 1) % playlist.size()));
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void play(Song song) {
        try {
            songSemaphore.acquire();
            
            Media media = new Media(new File(song.getPath()).toURI().toString());
            
            mediaPlayer = new MediaPlayer(media);
            
            mediaPlayer.play();
            setReproducing(true);
            setCurrentSong(song);
            
            mediaPlayer.setOnEndOfMedia(() -> {
                mediaPlayer.stop();
                songSemaphore.release();
                if (looping) {
                    play(playlist.get((currentIndex + 1) % playlist.size()));
                } else if (shuffling) {
                    play(getRandomSong());
                }
            });
            
            currentIndex = playlist.indexOf(song);
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    
    
    public void stop() {
        mediaPlayer.stop();
        setReproducing(false);
        songSemaphore.release();
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
            play(playlist.get(currentIndex - 1));
        }
    }
    
    public void goForward() {
        if (currentIndex < playlist.size() - 1) {
            play(playlist.get(currentIndex + 1));
        } else {
        	play(playlist.get(0));
        }
    }
    
    private Song getRandomSong() {
        return playlist.get((int) (Math.random()*playlist.size()+0));
    }
    
    public void loadPlaylist(String title) {
    	
    	for (String key : allSongs.keySet()) {
			if(key.equalsIgnoreCase(title)) {
				Song song = allSongs.get(key);
				playlist.add(song);
			}
		}
    	
    }
    
    public HashMap<String, Song> loadAllSongs(String parentFolderPath) {
    	
        File parentFolder = new File(parentFolderPath);

        if (parentFolder.exists() && parentFolder.isDirectory()) {
            File[] playlistFolders = parentFolder.listFiles(File::isDirectory);

            if (playlistFolders != null) {
                for (File playlistFolder : playlistFolders) {
                    Song song = loadSongs(playlistFolder);
                    if (song != null) {
                        allSongs.put(playlistFolder.getName(), song);
                    }
                }
            }
        } else {
            System.out.println("La cartella genitore non esiste o non è una directory.");
        }

        return allSongs;
    }

    public Song loadSongs(File playlistFolder) {
        File[] songFiles = playlistFolder.listFiles();

        if (songFiles != null) {
            for (File songFile : songFiles) {
                Media media = new Media(songFile.toURI().toString());

                String name = songFile.getName();
                String parts[] = name.split("_");
                String songTitle = parts[0].trim();
                String songArtist = (parts.length > 1) ? parts[1].replace(".mp3", "").trim() : "Sconosciuto";

                int duration = (int) media.getDuration().toMillis();

                return new Song(songTitle, songArtist, songFile.getAbsolutePath(), duration);
            }
        }

        return null;
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
