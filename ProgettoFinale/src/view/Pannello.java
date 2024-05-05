package view;

import javax.swing.JPanel;

import model.MusicPlayer;
import model.Song;
import controller.Controller;

import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JTextArea;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class Pannello extends JPanel {

	private static final long serialVersionUID = 1L;
	
	ImageIcon iconimg1 = new ImageIcon(getClass().getResource("/images/logo.png"));
	Image icon = iconimg1.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
	ImageIcon iconimg = new ImageIcon(icon);
	ImageIcon shuffleimg1 = new ImageIcon(getClass().getResource("/images/shuffle.png"));
	Image shuffle = shuffleimg1.getImage().getScaledInstance(30, -1, Image.SCALE_SMOOTH);
	ImageIcon shuffleimg = new ImageIcon(shuffle);
	ImageIcon loopimg1 = new ImageIcon(getClass().getResource("/images/loop.png"));
	Image loop = loopimg1.getImage().getScaledInstance(30, -1, Image.SCALE_SMOOTH);
	ImageIcon loopimg = new ImageIcon(loop);
	ImageIcon playimg1 = new ImageIcon(getClass().getResource("/images/play.png"));
	Image play = playimg1.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	ImageIcon playimg = new ImageIcon(play);
	ImageIcon backimg1 = new ImageIcon(getClass().getResource("/images/back.png"));
	Image back = backimg1.getImage().getScaledInstance(30, -1, Image.SCALE_SMOOTH);
	ImageIcon backimg = new ImageIcon(back);
	ImageIcon fowardimg1 = new ImageIcon(getClass().getResource("/images/forward.png"));
	Image foward = fowardimg1.getImage().getScaledInstance(30, -1, Image.SCALE_SMOOTH);
	ImageIcon forwardimg = new ImageIcon(foward);
	ImageIcon pauseimg1 = new ImageIcon(getClass().getResource("/images/pause.png"));
	Image pause = pauseimg1.getImage().getScaledInstance(40, -1, Image.SCALE_SMOOTH);
	ImageIcon pauseimg = new ImageIcon(pause);
	
	JButton playSong, btnShuffle, btnBack, btnPlay, btnForward, btnLoop, btnPause;
	JTextArea songReproducing;
	JPanel PlaylistPanel, CommandPanel, mainPanel;
	JLabel logo, welcome;
	
	public Pannello() {
		setBackground(new Color(51, 51, 51));
	    setLayout(new GridBagLayout());

	    logo = new JLabel();
	    logo.setIcon(iconimg);
	    GridBagConstraints gbc_logo = new GridBagConstraints();
	    gbc_logo.insets = new Insets(0, 0, 5, 5);
	    gbc_logo.gridx = 1;
	    gbc_logo.gridy = 1;
	    add(logo, gbc_logo);

	    mainPanel = new JPanel();
	    mainPanel.setLayout(new GridBagLayout()); // Aggiungiamo un GridBagLayout al mainPanel
	    mainPanel.setBackground(Color.DARK_GRAY);
	    JScrollPane mainScrollPane = new JScrollPane(mainPanel);
	    mainScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    GridBagConstraints gbc_mainPanel = new GridBagConstraints();
	    gbc_mainPanel.gridheight = 9;
	    gbc_mainPanel.gridwidth = 3;
	    gbc_mainPanel.weightx = 0.8;
	    gbc_mainPanel.weighty = 0.9;
	    gbc_mainPanel.fill = GridBagConstraints.BOTH;
	    gbc_mainPanel.gridx = 1;
	    gbc_mainPanel.gridy = 0;
	    mainScrollPane.setBorder(null);
	    add(mainScrollPane, gbc_mainPanel);

	    PlaylistPanel = new JPanel();
	    PlaylistPanel.setBackground(Color.DARK_GRAY);
	    PlaylistPanel.setLayout(new GridBagLayout());
	    JScrollPane playlistScrollPane = new JScrollPane(PlaylistPanel);
	    playlistScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

	    GridBagConstraints gbc_PlaylistPanel = new GridBagConstraints();
	    gbc_PlaylistPanel.gridheight = GridBagConstraints.REMAINDER;
	    gbc_PlaylistPanel.gridwidth = 1;
	    gbc_PlaylistPanel.weightx = 0.2;
	    gbc_PlaylistPanel.weighty = 1.0;
	    gbc_PlaylistPanel.fill = GridBagConstraints.BOTH;
	    gbc_PlaylistPanel.insets = new Insets(0, 0, 0, 5);
	    gbc_PlaylistPanel.gridx = 0;
	    gbc_PlaylistPanel.gridy = 0;
	    playlistScrollPane.setBorder(null);
	    add(playlistScrollPane, gbc_PlaylistPanel);

	    CommandPanel = new JPanel();
	    CommandPanel.setBackground(Color.DARK_GRAY);
	    GridBagConstraints gbc_CommandPanel = new GridBagConstraints();
	    gbc_CommandPanel.gridwidth = GridBagConstraints.REMAINDER;
	    gbc_CommandPanel.gridheight = 1;
	    gbc_CommandPanel.weightx = 0.8;
	    gbc_CommandPanel.weighty = 0.0;
	    gbc_CommandPanel.fill = GridBagConstraints.BOTH;
	    gbc_CommandPanel.insets = new Insets(0, 0, 5, 0);
	    gbc_CommandPanel.gridx = 1;
	    gbc_CommandPanel.gridy = 10;
	    add(CommandPanel, gbc_CommandPanel);
	    CommandPanel.setPreferredSize(new Dimension(800, 80));
          
        btnShuffle = new JButton();
        btnShuffle.setBackground(Color.DARK_GRAY);
        btnShuffle.setName("shuffle");
        btnShuffle.setActionCommand("shuffle");
        btnShuffle.setBorderPainted(false);
        btnShuffle.setFocusPainted(false);
        btnShuffle.setIcon(shuffleimg);
        GridBagConstraints gbc_btnShuffle = new GridBagConstraints();
        gbc_btnShuffle.anchor = GridBagConstraints.WEST;
        gbc_btnShuffle.insets = new Insets(0, 0, 0, 5);
        gbc_btnShuffle.gridx = 1;
        gbc_btnShuffle.gridy = 0;
        CommandPanel.add(btnShuffle);
                
        btnBack = new JButton();
        btnBack.setBackground(Color.DARK_GRAY);
        btnBack.setName("back");
        btnBack.setActionCommand("back");
        btnBack.setBorderPainted(false);
        btnBack.setIcon(backimg);
        btnBack.setFocusPainted(false);
        GridBagConstraints gbc_btnBack = new GridBagConstraints();
        gbc_btnBack.anchor = GridBagConstraints.WEST;
        gbc_btnBack.insets = new Insets(0, 0, 0, 5);
        gbc_btnBack.gridx = 2;
        gbc_btnBack.gridy = 0;
        CommandPanel.add(btnBack);
                
        btnPlay = new JButton();
        btnPlay.setName("play");
        btnPlay.setActionCommand("play");
        btnPlay.setBorderPainted(false);
        btnPlay.setIcon(playimg);
        btnPlay.setFocusPainted(false);
        GridBagConstraints gbc_btnPlay = new GridBagConstraints();
        gbc_btnPlay.anchor = GridBagConstraints.NORTHWEST;
        gbc_btnPlay.insets = new Insets(0, 0, 0, 5);
        gbc_btnPlay.gridx = 2;
        gbc_btnPlay.gridy = 0;
        CommandPanel.add(btnPlay, gbc_btnPlay);
                        
        btnPause = new JButton();
        btnPause.setName("pause");
        btnPause.setActionCommand("pause");
        btnPause.setBorderPainted(false);
        btnPause.setIcon(pauseimg);
        btnPause.setFocusPainted(false);
        btnPause.setVisible(false);
        GridBagConstraints gbc_btnPause = new GridBagConstraints();
        gbc_btnPause.anchor = GridBagConstraints.NORTHWEST;
        gbc_btnPause.insets = new Insets(0, 0, 0, 5);
        gbc_btnPause.gridx = 2;
        gbc_btnPause.gridy = 0;
        CommandPanel.add(btnPause, gbc_btnPause);
        
        btnForward = new JButton();
        btnForward.setBackground(Color.DARK_GRAY);
        btnForward.setName("forward");
        btnForward.setActionCommand("forward");
        btnForward.setBorderPainted(false);
        btnForward.setFocusPainted(false);
        btnForward.setIcon(forwardimg);
        GridBagConstraints gbc_btnForward = new GridBagConstraints();
        gbc_btnForward.anchor = GridBagConstraints.WEST;
        gbc_btnForward.insets = new Insets(0, 0, 0, 5);
        gbc_btnForward.gridx = 4;
        gbc_btnForward.gridy = 0;
        CommandPanel.add(btnForward);
        
        btnLoop = new JButton();
        btnLoop.setBackground(Color.DARK_GRAY);
        btnLoop.setName("loop");
        btnLoop.setActionCommand("loop");
        btnLoop.setBorderPainted(false);
        btnLoop.setFocusPainted(false);
        btnLoop.setIcon(loopimg);
        GridBagConstraints gbc_btnLoop = new GridBagConstraints();
        gbc_btnLoop.anchor = GridBagConstraints.WEST;
        gbc_btnLoop.insets = new Insets(0, 0, 0, 5);
        gbc_btnLoop.gridx = 5;
        gbc_btnLoop.gridy = 0;
        CommandPanel.add(btnLoop);
      
        
        songReproducing = new JTextArea();
        songReproducing.setForeground(Color.BLACK);
        songReproducing.setBackground(Color.LIGHT_GRAY);
        songReproducing.setAlignmentX(CENTER_ALIGNMENT);
        songReproducing.setEditable(false);
        songReproducing.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc_textArea = new GridBagConstraints();
        gbc_textArea.gridwidth = 7;
        gbc_textArea.anchor = GridBagConstraints.WEST;
        gbc_textArea.fill = GridBagConstraints.BOTH;
        gbc_textArea.gridx = 5;
        gbc_textArea.gridy = 0;
        CommandPanel.add(songReproducing);

    }

	public void generateSongsButton(HashMap<String, ArrayList<Song>> allSongs, Controller c, String playlistName) {
		mainPanel.removeAll();
	    ArrayList<Song> songs = allSongs.get(playlistName);
	    System.out.println(songs.toString());
	    
	    JLabel playlistTitle = new JLabel(playlistName);
	    playlistTitle.setForeground(Color.WHITE);
	    GridBagConstraints gbc_playlistTitle = new GridBagConstraints();
	    gbc_playlistTitle.anchor = GridBagConstraints.CENTER;
	    gbc_playlistTitle.gridwidth = 2; // Imposta la larghezza della cella del titolo della playlist su 2
	    gbc_playlistTitle.gridx = 0;
	    gbc_playlistTitle.gridy = 0; // Imposta la riga a 0 per far iniziare il titolo della playlist dalla prima riga
	    mainPanel.add(playlistTitle, gbc_playlistTitle);

	    // Aggiungi i pulsanti delle canzoni
	    for (int i = 0; i < songs.size(); i++) {
	        JButton playSong = new JButton(playimg);
	        playSong.addActionListener(c);
	        playSong.setActionCommand("" + i);
	        playSong.setName("" + i);
	        playSong.setBorderPainted(false);
	        playSong.setIcon(playimg);
	        playSong.setFocusPainted(false);

	        GridBagConstraints gbc_play = new GridBagConstraints();
	        gbc_play.anchor = GridBagConstraints.WEST;
	        gbc_play.insets = new Insets(5, 0, 5, 5); // Aggiungi spazio sopra e sotto ai pulsanti delle canzoni
	        gbc_play.gridx = 0; // Imposta la colonna a 0 per far iniziare il pulsante della canzone dalla prima colonna
	        gbc_play.gridy = i + 1; // Imposta la riga al numero della canzone più 1 per posizionarla sotto il titolo della playlist
	        mainPanel.add(playSong, gbc_play);

	        JLabel songAttributes = new JLabel(songs.get(i).getTitle() + " - " + songs.get(i).getAuthor());
	        GridBagConstraints gbc_songAttributes = new GridBagConstraints();
	        gbc_songAttributes.anchor = GridBagConstraints.WEST;
	        gbc_songAttributes.gridwidth = 1;
	        gbc_songAttributes.insets = new Insets(5, 0, 5, 5); // Aggiungi spazio sopra e sotto al nome della canzone
	        gbc_songAttributes.gridx = 1; // Imposta la colonna a 1 per far iniziare il nome della canzone dalla seconda colonna
	        gbc_songAttributes.gridy = i + 1; // Imposta la riga al numero della canzone più 1 per posizionarla sotto il titolo della playlist
	        mainPanel.add(songAttributes, gbc_songAttributes);
	    }

	    // Ricalcola le dimensioni del mainPanel
	    mainPanel.revalidate();
	    mainPanel.repaint();

	}

	public void generatePlaylistButton(ArrayList<String> playlist, Controller c) {
	    PlaylistPanel.removeAll();
	    
	    // Configura i vincoli del layout a griglia
	    GridBagConstraints gbc_btnPlaylist = new GridBagConstraints();
	    gbc_btnPlaylist.anchor = GridBagConstraints.WEST;
	    gbc_btnPlaylist.insets = new Insets(10, 10, 10, 10);

	    // Aggiungi i pulsanti delle playlist al pannello
	    for (int i = 0; i < playlist.size(); i++) {
	        JButton btnPlaylist = new JButton(playlist.get(i));
	        btnPlaylist.setName(playlist.get(i));
	        btnPlaylist.addActionListener(c);

	        // Imposta i vincoli per posizionare il pulsante nella riga corretta e all'inizio dell'asse verticale
	        gbc_btnPlaylist.gridx = 0;
	        gbc_btnPlaylist.gridy = i;
	        gbc_btnPlaylist.anchor = GridBagConstraints.NORTHWEST; // Imposta l'ancoraggio all'inizio (nord-ovest) del pannello

	        PlaylistPanel.add(btnPlaylist, gbc_btnPlaylist);
	    }

	    // Ricalcola le dimensioni del pannello
	    PlaylistPanel.revalidate();
	}

	public void registraEventi(Controller c) {
		btnShuffle.addActionListener(c);
		btnBack.addActionListener(c);
		btnPlay.addActionListener(c);
		btnForward.addActionListener(c);
		btnLoop.addActionListener(c);
		btnPause.addActionListener(c);
	}
	
	public void clearSongButtons() {
	    Component[] components = mainPanel.getComponents();
	    for (Component component : components) {
	    	System.out.println(component.toString());
			if(component != logo) {
				mainPanel.remove(component);
			}
		}
	    mainPanel.revalidate();
	    mainPanel.repaint();
	}

	public void setIsReproducing() {
	    btnPlay.setVisible(false);
	    btnPause.setVisible(true);
	}

	public void setIsPause() {
	    btnPlay.setVisible(true);
	    btnPause.setVisible(false);
	}
	public void setText(String text) {
		songReproducing.setText(text);
	}
	
	public void setTextAreaVisible(boolean isReproducing) {
		songReproducing.setVisible(isReproducing);
	}
	
	public void setBtnLoop(boolean isLooping) {
		btnLoop.setBorderPainted(isLooping);
	}
	
	public void setBtnShuffle(boolean isShuffling) {
		btnShuffle.setBorderPainted(isShuffling);
	}
	
	public void popup(String text) {
		JOptionPane.showMessageDialog(this, text);
	}
	
}
