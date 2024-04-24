package view;

import javax.swing.JPanel;

import model.MusicPlayer;
import model.Song;

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

import controller.Controller;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

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
	Image pause = pauseimg1.getImage().getScaledInstance(30, -1, Image.SCALE_SMOOTH);
	ImageIcon pauseimg = new ImageIcon(pause);
	
	
	JButton playSong, btnShuffle, btnBack, btnPlay, btnForward, btnLoop;
	JTextArea songReproducing;
	JPanel PlaylistPanel, CommandPanel, mainPanel;
	JLabel logo, welcome;
	
	MusicPlayer m = new MusicPlayer();
	
	HashMap<String, Song> allSongs = m.loadAllSongs(m.getStandardPath());
	
	public Pannello() {
        setBackground(new Color(51, 51, 51));
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 0.2, 0.8, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.9, 0.1};
        setLayout(gridBagLayout);

        logo = new JLabel();
        logo.setIcon(iconimg);
        GridBagConstraints gbc_logo = new GridBagConstraints();
        gbc_logo.insets = new Insets(0, 0, 5, 5);
        gbc_logo.gridx = 1;
        gbc_logo.gridy = 1;
        add(logo, gbc_logo);
        
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.DARK_GRAY);
        GridBagConstraints gbc_mainPanel = new GridBagConstraints();
        gbc_mainPanel.gridheight = 9; // set gridheight to 9 to span 9 rows
        gbc_mainPanel.gridwidth = 3; // set gridwidth to 3 to span 3 columns
        gbc_mainPanel.weightx = 0.7; // set weightx to 0.8 to take up 80% of the horizontal space
        gbc_mainPanel.weighty = 55; // set weighty to 0.9 to take up 90% of the vertical space
        gbc_mainPanel.fill = GridBagConstraints.BOTH; // set fill to BOTH to fill both horizontal and vertical space
        gbc_mainPanel.gridx = 1; // set gridx to 1
        gbc_mainPanel.gridy = 0; // set gridy to 0
        add(mainPanel, gbc_mainPanel);

        CommandPanel = new JPanel();
        CommandPanel.setBackground(Color.DARK_GRAY);
        GridBagConstraints gbc_CommandPanel = new GridBagConstraints();
        gbc_CommandPanel.gridwidth = GridBagConstraints.REMAINDER; // set gridwidth to REMAINDER to span all remaining columns
        gbc_CommandPanel.gridheight = 1; // set gridheight to 1 to span only 1 row
        gbc_CommandPanel.weightx = 0.8; // set weightx to 0.8 to take up 80% of the horizontal space
        gbc_CommandPanel.weighty = 0.5; // set weighty to 0.0 to take up no vertical space
        gbc_CommandPanel.fill = GridBagConstraints.BOTH; // set fill to BOTH to fill both horizontal and vertical space
        gbc_CommandPanel.insets = new Insets(0, 0, 5, 0); // set insets to add some padding
        gbc_CommandPanel.gridx = 1; // set gridx to 1
        gbc_CommandPanel.gridy = 10; // set gridy to 10 to position it in the 10th row
        add(CommandPanel, gbc_CommandPanel);
        CommandPanel.setPreferredSize(new Dimension(800, 80)); // set preferred size to 800x80 pixels
        
        PlaylistPanel = new JPanel();
        PlaylistPanel.setBackground(Color.DARK_GRAY);
        GridBagConstraints gbc_PlaylistPanel = new GridBagConstraints();
        gbc_PlaylistPanel.gridheight = GridBagConstraints.REMAINDER; // set gridheight to REMAINDER to span all remaining rows
        gbc_PlaylistPanel.gridwidth = 1; // set gridwidth to 1
        gbc_PlaylistPanel.weightx = 1; // set weightx to 0.2 to take up 20% of the horizontal space
        gbc_PlaylistPanel.weighty = 1.0; // set weighty to 1.0 to take up all remaining vertical space
        gbc_PlaylistPanel.fill = GridBagConstraints.BOTH; // set fill to BOTH to fill both horizontal and vertical space
        gbc_PlaylistPanel.insets = new Insets(0, 0, 0, 5); // set insets to add some padding
        gbc_PlaylistPanel.gridx = 0; // set gridx to 0
        gbc_PlaylistPanel.gridy = 0; // set gridy to 0
        add(PlaylistPanel, gbc_PlaylistPanel);


        songReproducing = new JTextArea();
        songReproducing.setForeground(Color.WHITE);
        songReproducing.setBackground(Color.DARK_GRAY);
        GridBagConstraints gbc_textArea = new GridBagConstraints();
        gbc_textArea.gridheight = 3;
        gbc_textArea.gridwidth = 3;
        gbc_textArea.fill = GridBagConstraints.BOTH;
        gbc_textArea.gridx = 1;
        gbc_textArea.gridy = 8;
        CommandPanel.add(songReproducing, gbc_textArea);

        btnShuffle = new JButton();
        btnShuffle.setBackground(Color.DARK_GRAY);
        btnShuffle.setName("shuffle");
        btnShuffle.setBorderPainted(false);
        btnShuffle.setFocusPainted(false);
        btnShuffle.setIcon(shuffleimg);
        GridBagConstraints gbc_btnShuffle = new GridBagConstraints();
        gbc_btnShuffle.gridheight = 5;
        gbc_btnShuffle.insets = new Insets(0, 0, 0, 5);
        gbc_btnShuffle.gridx = 1;
        gbc_btnShuffle.gridy = 6;
        CommandPanel.add(btnShuffle, gbc_btnShuffle);

        btnBack = new JButton();
        btnBack.setBackground(Color.DARK_GRAY);
        btnBack.setName("back");
        btnBack.setBorderPainted(false);
        btnBack.setIcon(backimg);
        btnBack.setFocusPainted(false);
        GridBagConstraints gbc_btnBack = new GridBagConstraints();
        gbc_btnBack.gridheight = 5;
        gbc_btnBack.insets = new Insets(0, 0, 0, 5);
        gbc_btnBack.gridx = 2;
        gbc_btnBack.gridy = 6;
        CommandPanel.add(btnBack, gbc_btnBack);

        btnPlay = new JButton();
        btnPlay.setName("play");
        btnPlay.setBorderPainted(false);
        btnPlay.setIcon(playimg);
        btnPlay.setFocusPainted(false);
        GridBagConstraints gbc_btnPlay = new GridBagConstraints();
        gbc_btnPlay.gridheight = 5;
        gbc_btnPlay.insets = new Insets(0, 0, 0, 5);
        gbc_btnPlay.gridx = 3;
        gbc_btnPlay.gridy = 6;
        CommandPanel.add(btnPlay, gbc_btnPlay);

        btnForward = new JButton();
        btnForward.setBackground(Color.DARK_GRAY);
        btnForward.setName("forward");
        btnForward.setBorderPainted(false);
        btnForward.setFocusPainted(false);
        btnForward.setIcon(forwardimg);
        GridBagConstraints gbc_btnForward = new GridBagConstraints();
        gbc_btnForward.gridheight = 5;
        gbc_btnForward.insets = new Insets(0, 0, 0, 5);
        gbc_btnForward.gridx = 4;
        gbc_btnForward.gridy = 6;
        CommandPanel.add(btnForward, gbc_btnForward);

        btnLoop = new JButton();
        btnLoop.setBackground(Color.DARK_GRAY);
        btnLoop.setName("loop");
        btnLoop.setBorderPainted(false);
        btnLoop.setFocusPainted(false);
        btnLoop.setIcon(loopimg);
        GridBagConstraints gbc_btnLoop = new GridBagConstraints();
        gbc_btnLoop.gridheight = 5;
        gbc_btnLoop.gridx = 5;
        gbc_btnLoop.gridy = 6;
        CommandPanel.add(btnLoop, gbc_btnLoop);
        
        generatePlaylistButton();

    }

	
	public void generateSongsButton(String playlistName) {
		ArrayList<Song> songs = new ArrayList<Song>();
		for(String song : m.allSongs.keySet()) {
			if(song.equals(playlistName)) {
				songs.add(m.allSongs.get(song));
			}
		}
		int posYPlay=5, posYSongAttributes=5;
		for(int i=1; i <= songs.size(); i++) {
			final int index = i;
		    playSong = new JButton("" + index, playimg);
		    playSong.addActionListener(e -> {
		        Song toPlay = songs.get(index-1);
		        m.play(toPlay);
		        setText(toPlay.getTitle()+ "\n" + toPlay.getAuthor());
		    });
		    playSong.setVisible(true);
			GridBagConstraints gbc_play = new GridBagConstraints();
			gbc_play.insets = new Insets(0, 0, 0, 5);
			gbc_play.gridx = 2;
			gbc_play.gridy = posYPlay;
			mainPanel.add(playSong, gbc_play);
				
			JLabel songAttributes = new JLabel(songs.get(i).getTitle() + "\n" + songs.get(i).getAuthor());
			GridBagConstraints gbc_songAttributes = new GridBagConstraints();
			gbc_songAttributes.gridwidth = 10;
			gbc_songAttributes.gridx = 3;
			gbc_songAttributes.gridy = posYSongAttributes;
			add(songAttributes, gbc_songAttributes);
			songAttributes.setVisible(true);
			
			posYPlay++;
			posYSongAttributes++;
		}
	}
	
	public void generatePlaylistButton() {
	    int y = 1;
	    ArrayList<String> playlist = new ArrayList<String>();
	    for(String name : m.allSongs.keySet()) {
	        if(!playlist.contains(name)) {
	            playlist.add(name);
	        }
	    }
	    for(String name : playlist) {
	        JButton btnPlaylist = new JButton(name);
	        btnPlaylist.addActionListener(e -> {
	            clearSongButtons();
	            generateSongsButton(name);
	        });
	        
	        GridBagConstraints gbc_btnPlaylist = new GridBagConstraints();
	        gbc_btnPlaylist.insets = new Insets(0, 0, 5, 0);
	        gbc_btnPlaylist.gridwidth = 4;
	        gbc_btnPlaylist.gridx = 1;
	        gbc_btnPlaylist.gridy = y;
	        
	        System.out.println("Aggiungo il pulsante per la playlist: " + name);
	        
	        PlaylistPanel.add(btnPlaylist, gbc_btnPlaylist);
	        btnPlaylist.setVisible(true);
	        y++;
	    }

	}

	
	
	public void registraEventi(Controller c) {
		btnShuffle.addActionListener(c);
		btnBack.addActionListener(c);
		btnPlay.addActionListener(c);
		btnForward.addActionListener(c);
		btnLoop.addActionListener(c);
		
	}
	
	public void clearSongButtons() {
	    Component[] components = getComponents();
	    for (Component component : components) {
	        if (component instanceof JButton || (component instanceof JLabel && (component != logo && component != welcome))) {
	            remove(component);
	        }
	    }
	    revalidate();
	    repaint();
	}

	
	public void setText(String text) {
		songReproducing.setText(text);
	}
	
	public void popup(String text) {
		JOptionPane.showMessageDialog(this, text);
	}
	
}
