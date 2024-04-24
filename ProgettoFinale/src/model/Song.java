package model;

public class Song {
	
	private String title;
	private String author;
	private String path;
	private int durata;
	
	public Song(String title, String author, String path, int durata) {
		this.title = title;
		this.author = author;
		this.path = path;
		this.durata = durata;
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

	public int getDurata() {
		return durata;
	}

	public void setDurata(int durata) {
		this.durata = durata;
	}

	@Override
	public String toString() {
		return title + "\n" + author;
	}
	

}
