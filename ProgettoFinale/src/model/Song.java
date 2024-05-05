package model;

/**
 * La classe Song rappresenta una singola canzone nel programma.
 * Ogni istanza di Song ha un titolo, un autore, un percorso del file,
 * una durata e il tempo salvato (per la riproduzione salvata).
 */
public class Song {
    
    private String title;
    private String author;
    private String path;
    private int durata;
    private double savedTime = -1;
    
    /**
     * Costruttore per creare un nuovo oggetto Song con i dettagli specificati.
     * @param title Il titolo della canzone.
     * @param author L'autore della canzone.
     * @param path Il percorso del file della canzone.
     * @param durata La durata della canzone in secondi.
     */
    public Song(String title, String author, String path, int durata) {
        this.title = title;
        this.author = author;
        this.path = path;
        this.durata = durata;
        this.savedTime = 0;
    }

    /**
     * Restituisce il titolo della canzone.
     * @return Il titolo della canzone.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Imposta il titolo della canzone.
     * @param title Il nuovo titolo della canzone.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Restituisce l'autore della canzone.
     * @return L'autore della canzone.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Imposta l'autore della canzone.
     * @param author Il nuovo autore della canzone.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Restituisce il percorso del file della canzone.
     * @return Il percorso del file della canzone.
     */
    public String getPath() {
        return path;
    }

    /**
     * Imposta il percorso del file della canzone.
     * @param path Il nuovo percorso del file della canzone.
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Restituisce la durata della canzone in secondi.
     * @return La durata della canzone in secondi.
     */
    public int getDurata() {
        return durata;
    }

    /**
     * Imposta la durata della canzone in secondi.
     * @param durata La nuova durata della canzone in secondi.
     */
    public void setDurata(int durata) {
        this.durata = durata;
    }
    
    /**
     * Restituisce il tempo salvato per la riproduzione salvata.
     * @return Il tempo salvato per la riproduzione salvata.
     */
    public double getSavedTime() {
        return savedTime;
    }

    /**
     * Imposta il tempo salvato per la riproduzione salvata.
     * @param savedTime Il nuovo tempo salvato per la riproduzione salvata.
     */
    public void setSavedTime(double savedTime) {
        this.savedTime = savedTime;
    }

    /**
     * Restituisce una rappresentazione in formato stringa della canzone,
     * che comprende il titolo seguito dall'autore.
     * @return Una stringa che rappresenta la canzone.
     */
    @Override
    public String toString() {
        return title + "\n" + author;
    }
}
