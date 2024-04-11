package model;

import javax.sound.sampled.AudioInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Song {
	
	private String title;
	private String author;
	private String path;
	private AudioInputStream audioStream;
	
	public Song(String title, String author, String path) {
		this.title = title;
		this.author = author;
		try {
            this.audioStream = javax.sound.sampled.AudioSystem.getAudioInputStream(new File(path));
        } catch (javax.sound.sampled.UnsupportedAudioFileException | java.io.IOException e) {
            e.printStackTrace();
        }
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return title + "\t" + author;
	}
	
	
	
	

}
