package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Playlist {

	ArrayList<Song> playlist;
	
	public Playlist() {
		playlist = new ArrayList<>();
	}

	public String addSong(String title, String author, String path) {
		if(title!=null && author!=null && path!=null) {
			Song nuova = new Song(title, author, path);
			if (playlist != null) {
				playlist.add(nuova);
				return "Song added";
			}
		}
		return "Parameter missing";
	}
	
	public boolean removeSong(String title, String author) {
		boolean trovato = false;
		for (Song song : playlist) {
			if(song.getTitle().equalsIgnoreCase(title) && song.getAuthor().equalsIgnoreCase(author)) {
				playlist.remove(song);
				trovato = true;
			}
		}
		return trovato;
	}
	
	
	
}
