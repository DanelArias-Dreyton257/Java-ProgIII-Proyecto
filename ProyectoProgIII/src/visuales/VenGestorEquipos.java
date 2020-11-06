package visuales;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gestion.GestorDeDatos;
import objetos.Jugador;
import personaje.Leyenda;

/**
 * 
 * @author danel y jonander
 *
 */
public class VenGestorEquipos extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final String TITULO = "MLW: Gestor de Equipos";
	private static final Dimension MIN_DIM = new Dimension(500, 400);
	private static final Dimension PREF_DIM = new Dimension(600, 600);

	private Jugador usuario;

	private JButton btVenFusion = new JButton("Ventana de fusion");
	private JButton[] btEquipo = { new JButton("Equipo 1"), new JButton("Equipo 2"), new JButton("Equipo 3"),
			new JButton("Equipo 4"), new JButton("Equipo 5"), new JButton("Equipo 6") };

	private JLabel lbEquipo = new JLabel("Equipo");
	private JLabel lbEternidad = new JLabel("Eternidad");

	private JPanel pnPrincipal = new JPanel(new GridLayout(1, 2));
	private JPanel pnDer = new JPanel(new BorderLayout());
	private JPanel pnIzq = new JPanel(new BorderLayout());
	private JPanel pnEquipo = new JPanel(new GridLayout(3, 2));
	private JPanel pnIzqAbajo = new JPanel();

	private DefaultListModel<String> mdEternidad = new DefaultListModel<>();
	private JList<String> lsEternidad = new JList<>();

	private int indBotonSeleccionado = -1;

	/**
	 * Constructor de la ventana de gestion de equipos
	 * 
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
		pnIzq.add(lbEquipo, BorderLayout.NORTH);
		pnIzq.add(pnIzqAbajo, BorderLayout.SOUTH);
		pnIzq.add(pnEquipo, BorderLayout.CENTER);

		pnIzqAbajo.add(btVenFusion);

		pnDer.add(lbEternidad, BorderLayout.NORTH);
		lsEternidad.setModel(mdEternidad);
		pnDer.add(new JScrollPane(lsEternidad), BorderLayout.CENTER);

		for (JButton bt : btEquipo) {
			pnEquipo.add(bt);
		}

		// Listeners
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				VenMenuPrincipal v = new VenMenuPrincipal(usuario);
				v.setVisible(true);
				dispose();
			}
		});

		btVenFusion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				VenFusiones v = new VenFusiones(usuario);
				v.setVisible(true);
				dispose();
			}
		});

		for (int i = 0; i < btEquipo.length; i++) {
			int h = i;
			btEquipo[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int j = lsEternidad.getSelectedIndex();
					if (j >= 0) {
						usuario.intercambiarEquipoEternidad(h, j);
						if (indBotonSeleccionado != -1) {
							for (JButton bt : btEquipo) {
								bt.setEnabled(true);
							}
						}
						indBotonSeleccionado = -1;
					} else {
						if (indBotonSeleccionado != -1) {
							usuario.intercambiarEnEquipo(h, indBotonSeleccionado);
							btEquipo[indBotonSeleccionado].setEnabled(true);
							indBotonSeleccionado = -1;
						} else {
							btEquipo[h].setEnabled(false);
							indBotonSeleccionado = h;
						}
					}
					actualizaEquipo();
					actualizaLista();

				}
			});
		}

		actualizaEquipo();
		actualizaLista();

	}

	/**
	 * Actualiza los botones que representan las leyendas del equipo
	 */
	private void actualizaEquipo() {
		for (int i = 0; i < usuario.getEquipo().length; i++) {
			Leyenda l = usuario.getLeyendaEquipo(i);
			if (l != null) {
				btEquipo[i].setText(l.getNombre());
				btEquipo[i].setToolTipText(l.getToolTipInfo());
			} else {
				btEquipo[i].setText(GestorDeDatos.NULL_STR);
				btEquipo[i].setToolTipText("");
			}
		}
	}

	/**
	 * Actualiza el modelo de la JList con las leyendas de la eternidad
	 */
	private void actualizaLista() {
		mdEternidad.clear();
		for (Leyenda l : usuario.getEternidad()) {
			mdEternidad.addElement(l.getNombre());
		}
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
	 * Introduce el parametro usuario
	 * 
	 * @param usuario
	 */
	public void setUsuario(Jugador usuario) {
		this.usuario = usuario;
	}

}
