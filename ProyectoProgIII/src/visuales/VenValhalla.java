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
import objetos.Jugador;
import personaje.Leyenda;

/**
 * 
 * @author danel y jon ander
 *
 */
public class VenValhalla extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final String TITULO = "MLW: Valhalla";
	private static final Dimension MIN_DIM = new Dimension(400, 400);
	private static final Dimension PREF_DIM = new Dimension(600, 600);
	private static final int COSTE_CONTRATO = 500;

	private Jugador usuario;

	private JLabel lbDoblones = new JLabel();

	private JButton btTirada = new JButton("Contratar una Leyenda: " + COSTE_CONTRATO + " doblones");

	private JPanel pnPrincipal = new JPanel();

	// Fuentes
	private static final Font FUENTE_MENSAJE = new Font(GestorDeDatos.NOMBRE_PERPETUA_BOLD, Font.PLAIN, 20);
	private static final Font FUENTE_BOTON = new Font(GestorDeDatos.NOMBRE_PERPETUA_BOLD_ITALIC, Font.ITALIC, 15);

	/**
	 * Constructor de la ventana de Valhalla
	 * 
	 * @param jugador
	 */
	public VenValhalla(Jugador jugador) {

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
		pnPrincipal.add(lbDoblones);
		lbDoblones.setFont(FUENTE_MENSAJE);
		pnPrincipal.add(btTirada);
		btTirada.setFont(FUENTE_BOTON);

		// Listeners
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				VenMenuPrincipal v = new VenMenuPrincipal(usuario);
				v.setVisible(true);
				dispose();
			}
		});

		btTirada.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (usuario.pagar(COSTE_CONTRATO)) {
					actualizaDoblones();
					Leyenda l = Leyenda.getLeyendaRandom();
					JOptionPane.showMessageDialog(VenValhalla.this, "Ha salido: " + l.getNombre(), "Leyenda contratada",
							JOptionPane.INFORMATION_MESSAGE);
					usuario.anyadirNuevaLeyenda(l);

				} else
					JOptionPane.showMessageDialog(VenValhalla.this,
							"No tienes suficientes doblones, si quieres mas gana algunas batallas", "Sin dinero",
							JOptionPane.ERROR_MESSAGE);
			}
		});

		actualizaDoblones();

	}

	/**
	 * Actualiza la JLabel que muestra cuantos doblones tiene el usuario
	 */
	private void actualizaDoblones() {
		lbDoblones.setText("Tus doblones: " + usuario.getDoblones());
	}

	/**
	 * Devuelve el usuario
	 * 
	 * @return
	 */
	public Jugador getUsuario() {
		return usuario;
	}

	/**
	 * Establece el usuario
	 * 
	 * @param usuario
	 */
	public void setUsuario(Jugador usuario) {
		this.usuario = usuario;
	}

}
