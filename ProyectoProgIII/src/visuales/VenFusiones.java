package visuales;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gestion.GestorDeDatos;
import objetosCombate.Jugador;
import personaje.Leyenda;

public class VenFusiones extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final String TITULO = "MLW: Fusiones";
	private static final Dimension MIN_DIM = new Dimension(500, 400);
	private static final Dimension PREF_DIM = new Dimension(600, 600);

	private Jugador usuario;

	private JButton btFusion = new JButton("FUSION");

	private JLabel lbSuma = new JLabel(" + ");

	private DefaultListModel<String> mdLista = new DefaultListModel<>();
	private JList<String> lsListaLey = new JList<>();

	private JPanel pnPrincipal = new JPanel(new GridLayout(2, 1));
	private JPanel pnArriba = new JPanel(new BorderLayout());
	private JPanel pnAbajo = new JPanel(new BorderLayout());
	private JPanel pnSuma = new JPanel();
	private JPanel pnBotones = new JPanel();

	private JLabel lbPersonajes = new JLabel("Personajes");

	private Leyenda ley1 = null;
	private int ind1 = -1;
	private Leyenda ley2 = null;
	private int ind2 = -1;

	// Fuentes
	private static final Font FUENTE_LEYENDA = new Font(GestorDeDatos.NOMBRE_PERPETUA_BOLD, Font.PLAIN, 15);
	private static final Font FUENTE_BOTON = new Font(GestorDeDatos.NOMBRE_PERPETUA_BOLD, Font.PLAIN, 25);
	private static final Font FUENTE_TOCHA = new Font(GestorDeDatos.NOMBRE_PERPETUA_TITLING_MT_BOLD, Font.BOLD, 20);
	private static final Font FUENTE_SUMA = new Font(GestorDeDatos.NOMBRE_PERPETUA_TITLING_MT_BOLD, 1, 40);
	
	private Component btLey1 = Leyenda.getBotonVentanaNULO(FUENTE_BOTON, 150);
	private Component btLey2 = Leyenda.getBotonVentanaNULO(FUENTE_BOTON, 150);
	
	/**
	 * Constructo de ventana fusiones
	 * 
	 * @param jugador
	 */
	public VenFusiones(Jugador jugador) {
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
		pnPrincipal.add(pnArriba);
		pnPrincipal.add(pnAbajo);
		pnAbajo.add(pnSuma, BorderLayout.CENTER);
		pnAbajo.add(pnBotones, BorderLayout.EAST);
		pnSuma.setLayout(new BoxLayout(pnSuma, BoxLayout.X_AXIS));
		pnSuma.add(btLey1);
		
		pnSuma.add(lbSuma);
		lbSuma.setFont(FUENTE_SUMA);
		pnSuma.add(btLey2);
		
		pnBotones.setLayout(new BoxLayout(pnBotones, BoxLayout.X_AXIS));
		pnBotones.add(btFusion);
		btFusion.setFont(FUENTE_TOCHA);

		pnArriba.add(lbPersonajes, BorderLayout.NORTH);
		lbPersonajes.setFont(FUENTE_TOCHA);
		pnArriba.add(new JScrollPane(lsListaLey), BorderLayout.CENTER);
		lsListaLey.setFont(FUENTE_LEYENDA);
		lsListaLey.setModel(mdLista);
		actualizaLista();
		actualizaBotones();

		// Listeners
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				VenGestorEquipos v = new VenGestorEquipos(usuario);
				v.setVisible(true);
				dispose();
			}
		});

		btFusion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (ind1 != -1 && ind2 != -1) {
					Leyenda fusion = Leyenda.fusion(ley1, ley2);
					JOptionPane.showMessageDialog(
							VenFusiones.this, "La fusion de: " + ley1.getNombre() + " y " + ley2.getNombre()
									+ " salio: " + fusion.getNombre(),
							"FUSION EXISTOSA", JOptionPane.INFORMATION_MESSAGE);
					eliminaPreFusiones();
					usuario.anyadirNuevaLeyenda(fusion);
					actualizaLista();
					ind1 = -1;
					ind2 = -1;
					ley1 = null;
					ley2 = null;
					btLey1=Leyenda.getBotonVentanaNULO(FUENTE_BOTON, 150);
					btLey2=Leyenda.getBotonVentanaNULO(FUENTE_BOTON, 150);
					actualizaBotones();
					VenFusiones.this.revalidate();
				} else {
					JOptionPane.showMessageDialog(VenFusiones.this, "Selecciona 2 personajes", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}

		});

		

	}
	private void actualizaBotones() {
		
		btLey1.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				int i = lsListaLey.getSelectedIndex();
				if (ind2 != i) {
					ind1 = i;
					if (i < usuario.getNumLeyendasEnEquipo()) {
						// Esta en el equipo
						ley1 = usuario.getLeyendaEquipo(i);
						btLey1 = ley1.getBotonVentana(FUENTE_BOTON, 150);
						actualizaBotones();
					} else {
						// Esta en la eternidad
						i -= usuario.getNumLeyendasEnEquipo();
						ley1 = usuario.getLeyendaEternidad(i);
						btLey1 = ley1.getBotonVentana(FUENTE_BOTON, 150);
						actualizaBotones();
					}
					VenFusiones.this.revalidate();

				} else {
					JOptionPane.showMessageDialog(VenFusiones.this, "Personaje ya seleccionado", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		btLey2.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				int i = lsListaLey.getSelectedIndex();
				if (ind1 != i) {
					ind2 = i;
					if (i < usuario.getNumLeyendasEnEquipo()) {
						// Esta en el equipo
						ley2 = usuario.getLeyendaEquipo(i);
						btLey2 = ley2.getBotonVentana(FUENTE_BOTON, 150);
						actualizaBotones();
					} else {
						// Esta en la eternidad
						i -= usuario.getNumLeyendasEnEquipo();
						ley2 = usuario.getLeyendaEternidad(i);
						btLey2 = ley2.getBotonVentana(FUENTE_BOTON, 150);
						actualizaBotones();
					}
					VenFusiones.this.revalidate();

				} else {
					JOptionPane.showMessageDialog(VenFusiones.this, "Personaje ya seleccionado", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		pnSuma.removeAll();
		pnSuma.add(btLey1);
		pnSuma.add(lbSuma);
		lbSuma.setFont(FUENTE_SUMA);
		pnSuma.add(btLey2);
		
	}

	/**
	 * Metodo que se usa para actualizar la lista de tus personajes Se usa a la hora
	 * de hacer fusiones, para añadir el nuevo que se haya obtenido
	 */
	private void actualizaLista() {
		mdLista.clear();
		for (Leyenda l : usuario.getEquipo()) {
			if (l != null) {
				mdLista.addElement(l.getNombre());
			}
		}
		for (Leyenda l1 : usuario.getEternidad()) {
			if (l1 != null) {
				mdLista.addElement(l1.getNombre());
			}
		}
	}

	/**
	 * Metodo que se usara para eliminar los personajes fusionados
	 */
	private void eliminaPreFusiones() {
		boolean i1enEtern = ind1 >= usuario.getNumLeyendasEnEquipo();
		boolean i2enEtern = ind2 >= usuario.getNumLeyendasEnEquipo();
		int i1 = ind1;
		int i2 = ind2;
		if (i1enEtern)
			i1 -= usuario.getNumLeyendasEnEquipo();
		if (i2enEtern)
			i2 -= usuario.getNumLeyendasEnEquipo();

		if (!i1enEtern) {
			usuario.delLeyendaEquipo(i1, false);
		} else {
			usuario.delLeyendaEternidad(i1);
		}
		if (!i2enEtern) {
			usuario.delLeyendaEquipo(i2);

		} else if (i2enEtern && !i1enEtern) {
			usuario.delLeyendaEternidad(i2);

		} else if (i2enEtern && i1enEtern) {
			if (i2 > i1) {
				usuario.delLeyendaEternidad(i2 - 1);
			} else {
				usuario.delLeyendaEternidad(i2);
			}
		}
	}

	/**
	 * Metodo que devuelve el usuario
	 * 
	 * @return
	 */
	public Jugador getUsuario() {
		return usuario;
	}

	/**
	 * Metodo que introduce el usuario
	 * 
	 * @param usuario
	 */
	public void setUsuario(Jugador usuario) {
		this.usuario = usuario;
	}

}
