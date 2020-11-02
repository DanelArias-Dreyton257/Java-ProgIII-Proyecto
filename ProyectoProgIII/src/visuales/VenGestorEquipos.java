package visuales;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import objetos.Jugador;

public class VenGestorEquipos extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final String TITULO = "MLW: Gestor de Equipos";
	private static final Dimension MIN_DIM = new Dimension(500, 400);
	private static final Dimension PREF_DIM = new Dimension(600, 600);
	
	private Jugador usuario;

	private JButton btVenFusion = new JButton("Ventana de fusion");

	private JPanel pnPrincipal = new JPanel(new GridLayout(1, 2));
	private JPanel pnDer = new JPanel();
	private JPanel pnIzq = new JPanel(new BorderLayout());
	private JPanel pnIzqAbajo = new JPanel();

	/**
	 * Constructor de la ventana de gestion de equipos
	 * @param jugador
	 */
	public VenGestorEquipos(Jugador jugador) {
		setUsuario(jugador);
		// Colocar ventana
		setMinimumSize(MIN_DIM);
		setPreferredSize(PREF_DIM);
		setSize(PREF_DIM);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle(TITULO);

		// Organizar paneles
		getContentPane().add(pnPrincipal, BorderLayout.CENTER);
		pnPrincipal.add(pnIzq);
		pnPrincipal.add(pnDer);
		pnIzq.add(pnIzqAbajo, BorderLayout.SOUTH);

		pnIzqAbajo.add(btVenFusion);

		// Listeners
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				MenuPrincipal v = new MenuPrincipal(jugador);
				v.setVisible(true);
			}
		});

		btVenFusion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				VenFusiones v = new VenFusiones(jugador);
				v.setVisible(true);
			}
		});

	}

	/**
	 *  Devuelve el parametro usuario
	 * @return
	 */
	public Jugador getUsuario() {
		return usuario;
	}

	/**
	 *  Introduce el parametro usuario
	 * @param usuario
	 */
	public void setUsuario(Jugador usuario) {
		this.usuario = usuario;
	}
	

}
