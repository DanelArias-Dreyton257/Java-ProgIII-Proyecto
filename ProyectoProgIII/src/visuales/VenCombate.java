package visuales;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.util.TreeSet;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import gestion.GestorDeDatos;
import objetos.Combate;
import personaje.Leyenda;
import personaje.atributos.Habilidad;

public class VenCombate extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final String TITULO = "MLW: Combate";
	private static final Dimension MIN_DIM = new Dimension(1100, 400);
	private static final Dimension PREF_DIM = new Dimension(1200, 600);

	private Combate combate;

	private JPanel pnPrincipal = new JPanel(new GridLayout(1, 5));
	private JPanel pn1 = new JPanel(new BorderLayout());
	private JPanel pnGrid2 = new JPanel(new GridLayout(3, 2));
	private JPanel pn3Norte = new JPanel();
	private JPanel pn3 = new JPanel(new BorderLayout());
	private JPanel pnGrid4 = new JPanel(new GridLayout(3, 2));
	private JPanel pn5 = new JPanel(new BorderLayout());
	private JPanel pnBanquilloJ1 = new JPanel();
	private JPanel pnBanquilloJ2 = new JPanel();
	private JPanel pnSur = new JPanel();

	private JPanel pnHabilidades = new JPanel(new GridLayout(2, 2));

	private DefaultListModel<String> mdJ1Banquillo = new DefaultListModel<String>();
	private DefaultListModel<String> mdJ2Banquillo = new DefaultListModel<String>();
	private JList<String> lsJ1Banquillo = new JList<>(mdJ1Banquillo);
	private JList<String> lsJ2Banquillo = new JList<>(mdJ2Banquillo);

	private JLabel lbJugador1 = new JLabel("Jugador 1");
	private JLabel lbJugador2 = new JLabel("Jugador 2");

	private JLabel[] lbLeyEnBatalla = { new JLabel("Leyenda"), new JLabel("Leyenda"), new JLabel("Leyenda"),
			new JLabel("Leyenda"), new JLabel("Leyenda"), new JLabel("Leyenda") };

	private JLabel lbTurno = new JLabel("Turno 0");

	private JLabel lbMensaje = new JLabel("");

	private JButton btOpciones = new JButton("Opciones");
	private JButton btSigTurno = new JButton("Siguiente turno");

	private JButton[] btHabilidades = { new JButton("H0"), new JButton("H1"), new JButton("H2"), new JButton("H3") };

	private int contTurno = 0;

	private TreeSet<Leyenda> leyendasEnCombate = new TreeSet<>();
	private Leyenda leyendaEnCurso = null;
	private int indiceLeyEnCurso = -1;

	/**
	 * Contructor de la ventana de combate
	 * 
	 * @param combate
	 */
	public VenCombate(Combate combate) {

		setCombate(combate);

		// Colocar ventana
		setMinimumSize(MIN_DIM);
		setPreferredSize(PREF_DIM);
		setSize(PREF_DIM);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle(TITULO);

		// Anyadir a principal
		getContentPane().add(pnPrincipal, BorderLayout.CENTER);
		getContentPane().add(pnSur, BorderLayout.SOUTH);

		pnSur.add(lbMensaje);

		pnPrincipal.add(pn1);
		pnPrincipal.add(pnGrid2);
		pnPrincipal.add(pn3);
		pnPrincipal.add(pnGrid4);
		pnPrincipal.add(pn5);
		// Paneles del grid principal
		pn1.add(lbJugador1, BorderLayout.NORTH);
		pn1.add(pnBanquilloJ1, BorderLayout.CENTER);
		pn5.add(lbJugador2, BorderLayout.NORTH);
		pn5.add(pnBanquilloJ2, BorderLayout.CENTER);
		pn5.add(btOpciones, BorderLayout.SOUTH);

		pnBanquilloJ1.add(lsJ1Banquillo);
		pnBanquilloJ2.add(lsJ2Banquillo);

		// Anyadir leyendas
		pnGrid2.add(lbLeyEnBatalla[0]);
		pnGrid2.add(new JLabel(""));
		pnGrid2.add(new JLabel(""));
		pnGrid2.add(lbLeyEnBatalla[1]);
		pnGrid2.add(lbLeyEnBatalla[2]);
		pnGrid2.add(new JLabel(""));

		pnGrid4.add(new JLabel(""));
		pnGrid4.add(lbLeyEnBatalla[3]);
		pnGrid4.add(lbLeyEnBatalla[4]);
		pnGrid4.add(new JLabel(""));
		pnGrid4.add(new JLabel(""));
		pnGrid4.add(lbLeyEnBatalla[5]);

		pn3.add(pn3Norte, BorderLayout.NORTH);
		pn3Norte.add(lbTurno);
		pn3Norte.add(btSigTurno);

		pn3.add(pnHabilidades, BorderLayout.SOUTH);
		pnHabilidades.setVisible(false);

		for (JButton b : btHabilidades) {
			pnHabilidades.add(b);
		}

		// Cambiar textos
		lbJugador1.setText(combate.getJ1().getNombre());
		lbJugador2.setText(combate.getJ2().getNombre());

		actualizaNombresLeys();
		// Listeners
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				VenMenuPrincipal v = new VenMenuPrincipal(combate.getJ1());
				v.setUsuario(combate.getJ1());
				v.setVisible(true);
			}
		});

		btSigTurno.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				contTurno++;
				lbTurno.setText("Turno " + contTurno);
				btSigTurno.setVisible(false);
				revalidate();

				leyendasEnCombate = combate.ordenVelocidad();
				siguienteLeyenda();
			}
		});
		// Listeners para cada boton de las habilidades
		for (int j = 0; j < btHabilidades.length; j++) {
			JButton boton = btHabilidades[j];
			int h = j;

			boton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// Si el boton no tiene ninguna habilidad asociada
					if (boton.getText().equals(GestorDeDatos.NULL_STR)) {
						return;
					}
					Random r = new Random();
					boolean ataqueExitoso = false;
					// Ataca el j1 al j2
					if (indiceLeyEnCurso < 3) {
						ataqueExitoso = combate.leyendaAtacaLeyenda(true, indiceLeyEnCurso, r.nextInt(3), h);
					}
					// Ataca el j2 al j1
					else {
						ataqueExitoso = combate.leyendaAtacaLeyenda(false, r.nextInt(3), indiceLeyEnCurso - 3, h);
					}

					if (ataqueExitoso) {
						lbMensaje.setText("Ataque exitoso");
					} else {
						lbMensaje.setText("Ataque fallado");
					}

					// Actualizar nombres
					actualizaNombresLeys();
					JLabel lbleyEnCurso = lbLeyEnBatalla[indiceLeyEnCurso];
					lbleyEnCurso.setForeground(Color.BLACK);

					pnHabilidades.setVisible(false);

					// Si ya han atacado todos se termina el turno
					if (!leyendasEnCombate.isEmpty()) {
						siguienteLeyenda();
					} else {
						btSigTurno.setVisible(true);
					}

				}
			});
		}

	}

	/**
	 * Selecciona la siguiente leyenda a atacar
	 * 
	 * @param combate
	 */
	private void siguienteLeyenda() {

		leyendaEnCurso = leyendasEnCombate.pollFirst();

		indiceLeyEnCurso = combate.indiceEnBatalla(leyendaEnCurso);

		// Cambia el color de quien ataca
		JLabel lbleyEnCurso = lbLeyEnBatalla[indiceLeyEnCurso];
		lbleyEnCurso.setForeground(Color.RED);

		// Hacer aparecer panel con movs
		Habilidad[] hs = leyendaEnCurso.getHabilidades();
		for (int i = 0; i < hs.length; i++) {
			String nombre = GestorDeDatos.NULL_STR;
			if (hs[i] != null) {
				nombre = hs[i].getNombre();
			}
			btHabilidades[i].setText(nombre);
		}

		pnHabilidades.setVisible(true);

	}

	/**
	 * Actualiza los nombres de las leyendas en el combate
	 * 
	 * @param combate
	 */
	private void actualizaNombresLeys() {

		combate.getJ1().reorganizaEquipo();
		combate.getJ2().reorganizaEquipo();

		for (int i = 0; i < lbLeyEnBatalla.length; i++) {
			int j = i - 3;
			Leyenda correspondiente = null;

			if (i >= 3)
				correspondiente = combate.getJ2().getLeyendaEquipo(j);
			else
				correspondiente = combate.getJ1().getLeyendaEquipo(i);

			if (correspondiente != null)
				lbLeyEnBatalla[i].setText(correspondiente.getNombreCombate());
			else
				lbLeyEnBatalla[i].setText(GestorDeDatos.NULL_STR);
		}

		mdJ1Banquillo.clear();
		mdJ2Banquillo.clear();

		for (int x = 3; x < 6; x++) {
			Leyenda banquillo1 = combate.getJ1().getLeyendaEquipo(x);
			if (banquillo1 != null)
				mdJ1Banquillo.addElement(banquillo1.getNombreCombate());

			Leyenda banquillo2 = combate.getJ2().getLeyendaEquipo(x);
			if (banquillo2 != null)
				mdJ2Banquillo.addElement(banquillo2.getNombreCombate());
		}
	}

	/**
	 * Metodo que devuelve el combate
	 * 
	 * @return
	 */
	public Combate getCombate() {
		return combate;
	}

	/**
	 * Metodo que introduce el combate
	 * 
	 * @param combate
	 */
	public void setCombate(Combate combate) {
		this.combate = combate;
	}

}
