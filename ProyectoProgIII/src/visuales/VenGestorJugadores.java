package visuales;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import gestion.GestorDeDatos;
import gestion.GestorJugadores;
import objetosCombate.Jugador;

public class VenGestorJugadores extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final String TITULO = "MLW: Gestor de Jugadores";
	private static final Dimension MIN_DIM = new Dimension(350, 300);
	private static final Dimension PREF_DIM = new Dimension(400, 400);

	private DefaultListModel<String> mdLista = new DefaultListModel<>();
	private JList<String> lsJugadores = new JList<>();

	private GestorJugadores gJugadores = new GestorJugadores();

	private JLabel lbJugadores = new JLabel("Jugadores");

	private JButton btSeleccionar = new JButton("Seleccionar");
	private JButton btBorrar = new JButton("Borrar Jug.");
	private JButton btCrearNuevo = new JButton("Crear Nuevo");

	private JPanel pnCentral = new JPanel();
	private JPanel pnBotones = new JPanel();
	private JPanel pnUp = new JPanel();

	// Fuentes
	private static final Font FUENTE_LEYENDA = new Font(GestorDeDatos.NOMBRE_PERPETUA_BOLD, Font.PLAIN, 20);
	private static final Font FUENTE_TOCHA = new Font(GestorDeDatos.NOMBRE_PERPETUA_TITLING_MT_BOLD, Font.BOLD, 25);
	private static final Font FUENTE_BOTON = new Font(GestorDeDatos.NOMBRE_PERPETUA_BOLD_ITALIC, Font.ITALIC, 15);

	public VenGestorJugadores() {
		// Gestor
		GestorJugadores gj = GestorDeDatos.cargarJugadoresFichero();
		if (gj != null) {
			setgJugadores(gj);
		}

		// Colocar ventana
		setMinimumSize(MIN_DIM);
		setPreferredSize(PREF_DIM);
		setSize(PREF_DIM);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle(TITULO);

		// Organizar paneles
		getContentPane().setLayout(new BorderLayout());
		lsJugadores.setFont(FUENTE_LEYENDA);
		lsJugadores.setModel(mdLista);
		getContentPane().add(pnCentral, BorderLayout.CENTER);
		pnCentral.add(new JScrollPane(lsJugadores));
		lbJugadores.setFont(FUENTE_TOCHA);
		pnUp.add(lbJugadores);
		getContentPane().add(pnUp, BorderLayout.NORTH);
		btSeleccionar.setFont(FUENTE_BOTON);
		btCrearNuevo.setFont(FUENTE_BOTON);
		btBorrar.setFont(FUENTE_BOTON);
		pnBotones.add(btSeleccionar);
		pnBotones.add(btBorrar);
		pnBotones.add(btCrearNuevo);
		getContentPane().add(pnBotones, BorderLayout.SOUTH);

		actualizaLista();
		btSeleccionar.setEnabled(false);
		btBorrar.setEnabled(false);

		lsJugadores.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int sel = lsJugadores.getSelectedIndex();
				btSeleccionar.setEnabled(sel > -1);
				btBorrar.setEnabled(sel > -1);
			}
		});
		btSeleccionar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String seleccionado = lsJugadores.getSelectedValue();
				String contr = JOptionPane.showInputDialog(VenGestorJugadores.this,
						"Introduzca la contrasena de " + seleccionado, "Contrasena", JOptionPane.QUESTION_MESSAGE);

				Jugador j = gJugadores.getJugador(seleccionado, contr);
				if (j != null) {
					// Abrir menu principal
					setVisible(false);
					VenMenuPrincipal v = new VenMenuPrincipal(j);
					v.setVisible(true);
					dispose();
				} else {
					JOptionPane.showMessageDialog(VenGestorJugadores.this, "Contrasena incorrecta", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btCrearNuevo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int opcRandom = JOptionPane.showConfirmDialog(null, "Crear usuario random? *solo Desarrollo*",
						"Usuario Nuevo", JOptionPane.INFORMATION_MESSAGE);
				if (opcRandom == JOptionPane.YES_OPTION) {
					Jugador usuario = new Jugador("Simple alumno");
					usuario.anyadirLeyendasRandom(15);
					gJugadores.anyadirJugador(usuario, "a");
					actualizaLista();
				} else if (opcRandom == JOptionPane.NO_OPTION) {
					String s = JOptionPane.showInputDialog(null, "Introduce tu nombre de jugador", "Usuario Nuevo",
							JOptionPane.INFORMATION_MESSAGE);
					String contra = JOptionPane.showInputDialog(null, "Introduce tu contrasena para el jugador",
							"Usuario Nuevo", JOptionPane.INFORMATION_MESSAGE);
					Jugador us = new Jugador(s);
					gJugadores.anyadirJugador(us, contra);
					actualizaLista();
				}

			}
		});

		btBorrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String seleccionado = lsJugadores.getSelectedValue();
				int opt = JOptionPane.showConfirmDialog(VenGestorJugadores.this, "¿Seguro que quiere borrar:"+ seleccionado+"?", "Borrado",
							JOptionPane.YES_NO_OPTION);
				if (opt == JOptionPane.YES_OPTION) {
					gJugadores.deleteJugador(seleccionado);
					actualizaLista();
				}
			}
		});

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosed(WindowEvent e) {
				new Thread() {
					@Override
					public void run() {
						GestorDeDatos.guardarJugadoresFichero(gJugadores);
					};
				}.start();
			}

		});
	}

	public GestorJugadores getgJugadores() {
		return gJugadores;
	}

	public void setgJugadores(GestorJugadores gJugadores) {
		this.gJugadores = gJugadores;
	}

	/**
	 * Actualiza el modelo de la JList con los nombres de los jugadores
	 */
	private void actualizaLista() {
		mdLista.clear();
		String[] ss = gJugadores.getNombresJugadores();
		if (ss != null) {
			for (String s : ss) {
				mdLista.addElement(s);
			}
		}
		revalidate();
	}

}
