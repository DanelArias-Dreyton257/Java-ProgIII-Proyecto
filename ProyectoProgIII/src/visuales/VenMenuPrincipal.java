package visuales;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import audio.ReproductorCanciones;
import audio.Cancion.SongException;
import gestion.GestorDeDatos;
import gestion.GestorJugadores;
import objetosCombate.Combate;
import objetosCombate.Jugador;

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

	private static final Font FUENTE_BOTONES = new Font(GestorDeDatos.NOMBRE_PERPETUA_BOLD_ITALIC, Font.ITALIC, 15);
	private static final Font FUENTE_G_BOTONES = new Font(GestorDeDatos.NOMBRE_PERPETUA_BOLD_ITALIC, Font.ITALIC, 25);
	private static final Font FUENTE_J_BOTONES = new Font(GestorDeDatos.NOMBRE_PERPETUA_BOLD_ITALIC, Font.ITALIC, 35);

	private JPanel pnCentral = new JPanel(new GridLayout(2, 1));
	private JPanel pnCenArriba = new JPanel();
	private JPanel pnCenAbajo = new JPanel();
	private JPanel pnCenAb1 = new JPanel();
	private JPanel pnCenAb2 = new JPanel();
	private JPanel pnSur = new JPanel();
	private JPanel pnLogo = new JPanel(new GridLayout(2, 1));
	private JPanel pnLogoArriba = new JPanel(new BorderLayout());
	private JPanel pnLogoArribaDer = new JPanel(new GridLayout(2, 1));

	private JLabel lbLogo[] = { new JLabel("MYTHS"), new JLabel("of"), new JLabel("the"), new JLabel("Legendary War") };

	private JButton btEquipo = new JButton("EQUIPO");
	private JButton btValhalla = new JButton("VALHALLA");
	private JButton btJugar = new JButton("JUGAR");
	private JButton btUsuario = new JButton("Cambio de Usuario");
	private JButton btCreditos = new JButton("Creditos");
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

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setTitle(TITULO);

		// Organizar paneles
		getContentPane().add(pnCentral, BorderLayout.CENTER);

		getContentPane().add(pnSur, BorderLayout.SOUTH);

		pnCentral.add(pnCenArriba);

		pnCentral.add(pnCenAbajo);
		pnCenAbajo.setLayout(new BoxLayout(pnCenAbajo, BoxLayout.Y_AXIS));

		pnCenAbajo.add(pnCenAb1);
		pnCenAbajo.add(pnCenAb2);

		// Anyadir logo
		pnCenArriba.add(pnLogo);
		pnLogo.add(pnLogoArriba);
		pnLogoArriba.add(lbLogo[0]);
		pnLogoArriba.add(pnLogoArribaDer, BorderLayout.EAST);
		pnLogoArribaDer.add(lbLogo[1]);
		pnLogoArribaDer.add(lbLogo[2]);
		pnLogo.add(lbLogo[3]);
		lbLogo[0].setFont(new Font(GestorDeDatos.NOMBRE_PERPETUA_TITLING_MT_BOLD, Font.BOLD, 55));
		lbLogo[1].setFont(new Font(GestorDeDatos.NOMBRE_PERPETUA_BOLD_ITALIC, Font.ITALIC, 30));
		lbLogo[2].setFont(new Font(GestorDeDatos.NOMBRE_PERPETUA_BOLD_ITALIC, Font.ITALIC, 30));
		lbLogo[3].setFont(new Font(GestorDeDatos.NOMBRE_PERPETUA_BOLD, Font.BOLD, 45));

		// Anyadir los botones a los paneles
		pnCenAb1.add(btEquipo);
		btEquipo.setFont(FUENTE_G_BOTONES);
		pnCenAb1.add(btValhalla);
		btValhalla.setFont(FUENTE_G_BOTONES);
		pnCenAb2.add(btJugar);
		btJugar.setFont(FUENTE_J_BOTONES);
		pnSur.add(btUsuario);
		btUsuario.setFont(FUENTE_BOTONES);
		pnSur.add(btOpciones);
		btOpciones.setFont(FUENTE_BOTONES);
		pnSur.add(btCreditos);
		btCreditos.setFont(FUENTE_BOTONES);
		pnSur.add(btSalir);
		btSalir.setFont(FUENTE_BOTONES);

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
				try {
					ReproductorCanciones.reproducirES(ReproductorCanciones.esClick);
				} catch (SongException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
				GestorJugadores gj = GestorDeDatos.cargarJugadoresFichero();
				gj.replaceJugador(usuario);
				GestorDeDatos.guardarJugadoresFichero(gj);
				logger.log(Level.INFO, "Abrir ventana de gestion de usuario");
				setVisible(false);
				VenGestorJugadores vj = new VenGestorJugadores();
				vj.setVisible(true);
				dispose();
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
				setVisible(false);
				int salirseguro = JOptionPane.showConfirmDialog(VenMenuPrincipal.this,
						"¿Estas seguro de que quieres cerrar el juego?", "Salir", JOptionPane.YES_NO_OPTION);
				if (salirseguro == JOptionPane.YES_OPTION) {
					logger.log(Level.INFO, "Ventana Cerrada");
					dispose();
				} else {
					setVisible(true);
				}
				// TODO hacer ventana de confirmacion?
			}
		});
		btCreditos.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				logger.log(Level.INFO, "Ventana de creditos");
				dispose();
				VenCreditos v = new VenCreditos(jugador);
				v.setVisible(true);
			}
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				if (ReproductorCanciones.getPosActual() != ReproductorCanciones.cancionMenuP) {
					ReproductorCanciones.pausar();

					try {
						Thread.sleep(100);
						ReproductorCanciones.reproducir(ReproductorCanciones.cancionMenuP);
					} catch (SongException | InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				int salirseguro = JOptionPane.showConfirmDialog(VenMenuPrincipal.this,
						"¿Estas seguro de que quieres cerrar el juego?", "Salir", JOptionPane.YES_NO_OPTION);
				if (salirseguro == JOptionPane.YES_OPTION) {
					logger.log(Level.INFO, "Ventana Cerrada");
					dispose();
					ReproductorCanciones.pausar();
				} else {
					setVisible(true);
				}
			}

			@Override
			public void windowClosed(WindowEvent e) {
				new Thread() {
					@Override
					public void run() {
						GestorJugadores gj = GestorDeDatos.cargarJugadoresFichero();
						gj.replaceJugador(usuario);
						GestorDeDatos.guardarJugadoresFichero(gj);
					};
				}.start();

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
