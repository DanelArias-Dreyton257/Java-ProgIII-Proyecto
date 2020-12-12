package visuales;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import gestion.GestorDeDatos;
import objetosCombate.Jugador;
import personaje.Leyenda;

/**
 * 
 * @author danel y jon ander
 *
 */
public class VenPrueba extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final String TITULO = "MLW: Prueba";
	private static final Dimension MIN_DIM = new Dimension(400, 400);
	private static final Dimension PREF_DIM = new Dimension(600, 600);
	private static final int COSTE_CONTRATO = 500;

	private Jugador usuario;

	private JPanel pnPrincipal = new JPanel();

	// Fuentes
	private static final Font FUENTE_MENSAJE = new Font(GestorDeDatos.NOMBRE_PERPETUA_BOLD, Font.PLAIN, 20);
	private static final Font FUENTE_BOTON = new Font(GestorDeDatos.NOMBRE_PERPETUA_BOLD_ITALIC, Font.ITALIC, 15);

	/**
	 * Constructor de la ventana de Valhalla
	 * 
	 * @param jugador
	 */
	public VenPrueba(Jugador jugador) {

		setUsuario(jugador);

		// Colocar ventana
		setMinimumSize(MIN_DIM);
		setPreferredSize(PREF_DIM);
		setSize(PREF_DIM);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle(TITULO);

		// Colocacion de paneles
		getContentPane().add(pnPrincipal);
		
		pnPrincipal.add(usuario.getLeyendaEquipo(0).getBotonVentana(FUENTE_BOTON,200));
		

	}

	private void setUsuario(Jugador jugador) {
		usuario = jugador;
	}
	
	public static void main(String[] args) {
		Jugador j = new Jugador("PEPE");
		j.anyadirLeyendasRandom(3);
		VenPrueba v = new VenPrueba(j);
		v.setVisible(true);
	}
}
