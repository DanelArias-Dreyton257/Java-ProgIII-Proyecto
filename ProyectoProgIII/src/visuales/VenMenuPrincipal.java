package visuales;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import objetos.Combate;
import objetos.Jugador;

/**
 * 
 * @author danel y jon ander
 *
 */
public class VenMenuPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(VenMenuPrincipal.class.getName());
	private static final boolean ANYADIR_A_FIC_LOG = false; // poner true para no sobreescribir
	static {
		try {
			logger.addHandler(
					new FileHandler("src/logs/" + VenMenuPrincipal.class.getName() + ".log.xml", ANYADIR_A_FIC_LOG));
		} catch (SecurityException | IOException e) {
			logger.log(Level.SEVERE, "Error en creacion fichero log");
		}
	}

	private static final String TITULO = "MYTHS of the LEGENDARY WAR";
	private static final Dimension MIN_DIM = new Dimension(400, 400);
	private static final Dimension PREF_DIM = new Dimension(600, 600);

	private JPanel pnCentral = new JPanel(new GridLayout(2, 1));
	private JPanel pnCenArriba = new JPanel();
	private JPanel pnCenAbajo = new JPanel();
	private JPanel pnCenAb1 = new JPanel();
	private JPanel pnCenAb2 = new JPanel();
	private JPanel pnSur = new JPanel();

	private JLabel lbLogo = new JLabel(TITULO);

	private JButton btEquipo = new JButton("EQUIPO");
	private JButton btValhalla = new JButton("VALHALLA");
	private JButton btJugar = new JButton("JUGAR");
	private JButton btUsuario = new JButton("Cambio de Usuario");
	private JButton btOpciones = new JButton("Opciones");
	private JButton btSalir = new JButton("Salir");

	private Jugador usuario;

	/**
	 * Constructor del menu principal
	 * 
	 * @param jugador
	 */
	public VenMenuPrincipal(Jugador jugador) {

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
		getContentPane().add(pnCentral, BorderLayout.CENTER);

		getContentPane().add(pnSur, BorderLayout.SOUTH);

		pnCentral.add(pnCenArriba);

		pnCentral.add(pnCenAbajo);
		pnCenAbajo.setLayout(new BoxLayout(pnCenAbajo, BoxLayout.Y_AXIS));

		pnCenAbajo.add(pnCenAb1);
		pnCenAbajo.add(pnCenAb2);

		// Anyadir los botones a los paneles
		pnCenArriba.add(lbLogo);
		pnCenAb1.add(btEquipo);
		pnCenAb1.add(btValhalla);
		pnCenAb2.add(btJugar);
		pnSur.add(btUsuario);
		pnSur.add(btOpciones);
		pnSur.add(btSalir);

		// Listeners
		btEquipo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				logger.log(Level.INFO, "Abrir ventana de Gestion de Equipo");
				setVisible(false);
				VenGestorEquipos v = new VenGestorEquipos(usuario);
				v.setVisible(true);
				dispose();
			}
		});
		btValhalla.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				logger.log(Level.INFO, "Abrir Valhalla");
				setVisible(false);
				VenValhalla v = new VenValhalla(usuario);
				v.setVisible(true);
				dispose();
			}
		});
		btJugar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				logger.log(Level.INFO, "Abrir ventana de batalla");
				setVisible(false);
				Combate c = new Combate(usuario); // FIXME
				VenCombate v = new VenCombate(c);
				v.setVisible(true);
				dispose();
			}
		});
		btUsuario.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				logger.log(Level.INFO, "Abrir loggeo de usuario");
			}
		});
		btOpciones.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				logger.log(Level.INFO, "Abrir ventana de opciones");
			}
		});
		btSalir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				logger.log(Level.INFO, "Ventana Cerrada");
				dispose();
				// TODO hacer ventana de confirmacion?
			}
		});

		comprobarNuevoUsuario();

	}

	/**
	 * Comprueba si el jugador es un nuevo usuario y si es asi no le permite jugar
	 * hasta que tenga un equipo
	 */
	private void comprobarNuevoUsuario() {
		boolean nuevo = usuario == null || (usuario != null && usuario.getNumLeyendas() <= 0);
		btJugar.setEnabled(!nuevo);
		btEquipo.setEnabled(!nuevo);

	}

	/**
	 * Devuelve el parametro usuario
	 * 
	 * @return
	 */
	public Jugador getUsuario() {
		return usuario;
	}

	/**
	 * introduce el parametro usuario
	 * 
	 * @param usuario
	 */
	public void setUsuario(Jugador usuario) {
		this.usuario = usuario;
	}

}
