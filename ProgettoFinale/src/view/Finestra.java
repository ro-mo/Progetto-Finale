package view;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * La classe Finestra rappresenta la finestra principale dell'applicazione.
 * Estende JFrame e implementa WindowListener per gestire gli eventi della finestra.
 */
public class Finestra extends JFrame implements WindowListener{
	
	private static final long serialVersionUID = 1L;
	private Pannello contentPane;
	
	/**
     * Costruttore della classe Finestra. Imposta le proprietà della finestra e
     * crea un oggetto Pannello come contenuto principale della finestra.
     */
	public Finestra() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);
        setLocationRelativeTo(null);
        setName("Javify");
        setIconImage(Toolkit.getDefaultToolkit().getImage("/images/logo.png"));

        contentPane = new Pannello();
        setContentPane(contentPane);
        setVisible(true);
    }

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/**
     * Restituisce il pannello principale associato a questa finestra.
     * 
     * @return Il pannello principale della finestra.
     */
	public Pannello getPannello() {
		return contentPane;
	}

}
