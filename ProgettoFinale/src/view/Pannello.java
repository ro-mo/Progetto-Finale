package view;

import javax.swing.JPanel;

import model.MusicPlayer;
import model.Song;

import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JButton;

public class Pannello extends JPanel {

	private static final long serialVersionUID = 1L;
	MusicPlayer m = new MusicPlayer();
	ImageIcon iconimg = new ImageIcon("Documents/progetto_finale_images/icon.png");
	ImageIcon playimg = new ImageIcon("Documents/progetto_finale_images/play.png");
	ImageIcon backimg = new ImageIcon("Documents/progetto_finale_images/back.png");
	ImageIcon fowardimg = new ImageIcon("Documents/progetto_finale_images/foward.png");
	ImageIcon pauseimg = new ImageIcon("Documents/progetto_finale_images/pause.png");
	
	JButton playSong;
	JPanel PlaylistPanel, CommandPanel;
	
	MusicPlayer musicPlayer = new MusicPlayer();

	public Pannello() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel logo = new JLabel(iconimg);
		GridBagConstraints gbc_logo = new GridBagConstraints();
		gbc_logo.insets = new Insets(0, 0, 5, 5);
		gbc_logo.gridx = 6;
		gbc_logo.gridy = 2;
		add(logo, gbc_logo);
		
		JLabel welcome = new JLabel("Benvenuto!");
		GridBagConstraints gbc_welcome = new GridBagConstraints();
		gbc_welcome.insets = new Insets(0, 0, 5, 5);
		gbc_welcome.anchor = GridBagConstraints.BELOW_BASELINE;
		gbc_welcome.gridx = 20;
		gbc_welcome.gridy = 2;
		add(welcome, gbc_welcome);
		
		PlaylistPanel = new JPanel();
		GridBagConstraints gbc_PlaylistPanel = new GridBagConstraints();
		gbc_PlaylistPanel.gridheight = 15;
		gbc_PlaylistPanel.gridwidth = 6;
		gbc_PlaylistPanel.insets = new Insets(0, 0, 0, 5);
		gbc_PlaylistPanel.fill = GridBagConstraints.BOTH;
		gbc_PlaylistPanel.gridx = 0;
		gbc_PlaylistPanel.gridy = 0;
		add(PlaylistPanel, gbc_PlaylistPanel);
		GridBagLayout gbl_PlaylistPanel = new GridBagLayout();
		gbl_PlaylistPanel.columnWidths = new int[]{0, 0, 0};
		gbl_PlaylistPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_PlaylistPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_PlaylistPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		PlaylistPanel.setLayout(gbl_PlaylistPanel);
		
		CommandPanel = new JPanel();
		GridBagConstraints gbc_CommandPanel = new GridBagConstraints();
		gbc_CommandPanel.gridwidth = 16;
		gbc_CommandPanel.gridheight = 3;
		gbc_CommandPanel.insets = new Insets(0, 0, 5, 0);
		gbc_CommandPanel.fill = GridBagConstraints.BOTH;
		gbc_CommandPanel.gridx = 6;
		gbc_CommandPanel.gridy = 11;
		add(CommandPanel, gbc_CommandPanel);
		GridBagLayout gbl_CommandPanel = new GridBagLayout();
		gbl_CommandPanel.columnWidths = new int[]{0};
		gbl_CommandPanel.rowHeights = new int[]{0};
		gbl_CommandPanel.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_CommandPanel.rowWeights = new double[]{Double.MIN_VALUE};
		CommandPanel.setLayout(gbl_CommandPanel);
		
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
		    playSong = new JButton("" + (index + 1), playimg);
		    playSong.addActionListener(e -> {
		        Song toPlay = songs.get(index);
		        musicPlayer.play(toPlay);
		    });
			GridBagConstraints gbc_play = new GridBagConstraints();
			gbc_play.insets = new Insets(0, 0, 0, 5);
			gbc_play.gridx = 2;
			gbc_play.gridy = posYPlay;
			add(playSong, gbc_play);
				
			JLabel songAttributes = new JLabel(songs.get(i).getTitle() + "\n" + songs.get(i).getAuthor());
			GridBagConstraints gbc_songAttributes = new GridBagConstraints();
			gbc_songAttributes.gridwidth = 10;
			gbc_songAttributes.gridx = 3;
			gbc_songAttributes.gridy = posYSongAttributes;
			add(songAttributes, gbc_songAttributes);
			
			posYPlay++;
			posYSongAttributes++;
		}
	}
	
	public void generatePlaylistButton() {
		int y = 1;
		GridBagConstraints gbc_btnPlaylist = new GridBagConstraints();
		ArrayList<String> playlist = new ArrayList<String>();
		for(String name : m.allSongs.keySet()) {
			if(!playlist.contains(name)) {
				playlist.add(name);
			}
		}
		for(String name : playlist) {
			JButton btnPlaylist = new JButton(name);
			btnPlaylist.addActionListener(e -> {
				generateSongsButton(name);
			});
			gbc_btnPlaylist.insets = new Insets(0, 0, 5, 0);
			gbc_btnPlaylist.gridwidth = 4;
			gbc_btnPlaylist.gridx = 1;
			gbc_btnPlaylist.gridy = y;
			PlaylistPanel.add(btnPlaylist, gbc_btnPlaylist);
			y++;
		}
	}

}
