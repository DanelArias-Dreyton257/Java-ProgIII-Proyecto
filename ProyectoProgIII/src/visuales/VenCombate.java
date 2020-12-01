package visuales;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.TreeSet;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import gestion.GestorDeDatos;
import objetos.Combate;
import personaje.Leyenda;
import personaje.atributos.Habilidad;

public class VenCombate extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final String TITULO = "MLW: Combate";
	private static final Dimension MIN_DIM = new Dimension(1300, 400);
	private static final Dimension PREF_DIM = new Dimension(1500, 600);

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

	private JButton[] btLeyEnBatalla = { new JButton("Leyenda"), new JButton("Leyenda"), new JButton("Leyenda"),
			new JButton("Leyenda"), new JButton("Leyenda"), new JButton("Leyenda") };

	private JLabel lbTurno = new JLabel("Turno 0");

	private JLabel lbMensaje = new JLabel("");

	private JButton btOpciones = new JButton("Opciones");
	private JButton btSigTurno = new JButton("Siguiente turno");

	private JButton[] btHabilidades = { new JButton("H0"), new JButton("H1"), new JButton("H2"), new JButton("H3") };

	private int contTurno = 0;

	private TreeSet<Leyenda> leyendasEnCombate = new TreeSet<>();
	private Leyenda leyendaEnCurso = null;
	private int indiceLeyEnCurso = -1;
	private int indiceHabElegida = -1;
	private boolean enTurno = false;

	// Fuentes
	private static final Font FUENTE_LEYENDA = new Font(GestorDeDatos.NOMBRE_PERPETUA_BOLD, Font.PLAIN, 12);
	private static final Font FUENTE_HABILIDAD = new Font(GestorDeDatos.NOMBRE_PERPETUA_BOLD_ITALIC, Font.ITALIC, 15);
	private static final Font FUENTE_JUGADOR = new Font(GestorDeDatos.NOMBRE_PERPETUA_TITLING_MT_BOLD, Font.BOLD, 15);
	private static final Font FUENTE_MENSAJE = new Font(GestorDeDatos.NOMBRE_PERPETUA_BOLD_ITALIC, Font.BOLD, 15);

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
		lbMensaje.setFont(FUENTE_MENSAJE);

		pnPrincipal.add(pn1);
		pnPrincipal.add(pnGrid2);
		pnPrincipal.add(pn3);
		pnPrincipal.add(pnGrid4);
		pnPrincipal.add(pn5);
		// Paneles del grid principal
		pn1.add(lbJugador1, BorderLayout.NORTH);
		lbJugador1.setFont(FUENTE_JUGADOR);
		pn1.add(pnBanquilloJ1, BorderLayout.CENTER);
		pn5.add(lbJugador2, BorderLayout.NORTH);
		lbJugador2.setFont(FUENTE_JUGADOR);
		pn5.add(pnBanquilloJ2, BorderLayout.CENTER);
		pn5.add(btOpciones, BorderLayout.SOUTH);
		btOpciones.setFont(FUENTE_MENSAJE);

		pnBanquilloJ1.add(lsJ1Banquillo);
		pnBanquilloJ2.add(lsJ2Banquillo);
		lsJ1Banquillo.setFont(FUENTE_LEYENDA);
		lsJ2Banquillo.setFont(FUENTE_LEYENDA);

		// Anyadir leyendas
		pnGrid2.add(panelBoxLayoutX(btLeyEnBatalla[0]));
		pnGrid2.add(panelBoxLayoutX(new JLabel("")));
		pnGrid2.add(panelBoxLayoutX(new JLabel("")));
		pnGrid2.add(panelBoxLayoutX(btLeyEnBatalla[1]));
		pnGrid2.add(panelBoxLayoutX(btLeyEnBatalla[2]));
		pnGrid2.add(panelBoxLayoutX(new JLabel("")));

		pnGrid4.add(panelBoxLayoutX(new JLabel("")));
		pnGrid4.add(panelBoxLayoutX(btLeyEnBatalla[3]));
		pnGrid4.add(panelBoxLayoutX(btLeyEnBatalla[4]));
		pnGrid4.add(panelBoxLayoutX(new JLabel("")));
		pnGrid4.add(panelBoxLayoutX(new JLabel("")));
		pnGrid4.add(panelBoxLayoutX(btLeyEnBatalla[5]));

		pn3.add(pn3Norte, BorderLayout.NORTH);
		pn3Norte.add(lbTurno);
		lbTurno.setFont(FUENTE_MENSAJE);
		pn3Norte.add(btSigTurno);
		btSigTurno.setFont(FUENTE_MENSAJE);

		pn3.add(pnHabilidades, BorderLayout.SOUTH);
		pnHabilidades.setVisible(false);

		for (JButton b : btHabilidades) {
			pnHabilidades.add(b);
			b.setFont(FUENTE_HABILIDAD);
		}

		// Cambiar textos
		lbJugador1.setText(combate.getJ1().getNombre());
		lbJugador2.setText(combate.getJ2().getNombre());

		combate.getJ1().curarLeyendas();
		combate.getJ2().curarLeyendas();
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

				enTurno = true;
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
					if (!boton.getText().equals(GestorDeDatos.NULL_STR)) {
						indiceHabElegida = h;
						pnHabilidades.setVisible(false);
						Habilidad ataque = leyendaEnCurso.getHabilidades()[h];
						lbMensaje.setText(ataque.getNombre() + " elegida");
					}
				}
			});
		}
		// Listeners para cada boton de los personajes
		for (int k = 0; k < btLeyEnBatalla.length; k++) {
			JButton boton = btLeyEnBatalla[k];

			boton.setFont(FUENTE_LEYENDA);// Fuente del boton, incluido aqui por comodidad del for loop

			int l = k;

			boton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					if (indiceHabElegida >= 0 && enTurno) {

						String ataqueStr = "";
						// Ataca el j1 al j2
						if (indiceLeyEnCurso < 3) {
							if (l - 3 < 0)
								return;
							ataqueStr = combate.leyendaAtacaLeyenda(true, indiceLeyEnCurso, l - 3, indiceHabElegida);
						}
						// Ataca el j2 al j1
						else {
							if (l > 2)
								return;
							ataqueStr = combate.leyendaAtacaLeyenda(false, l, indiceLeyEnCurso - 3, indiceHabElegida);
						}

						lbMensaje.setText(ataqueStr);

						// Actualizar nombres
						actualizaNombresLeys();
						JButton lbleyEnCurso = btLeyEnBatalla[indiceLeyEnCurso];
						lbleyEnCurso.setForeground(Color.BLACK);

						checkFinalTurno();

					} else if (!enTurno) {
						// Jugador 1
						if (l < 3) {
							int ind = lsJ1Banquillo.getSelectedIndex();
							if (ind >= 0) {
								// equipo = [0],[1],[2] [3],[4],[5]
								// l = [0],[1],[2] ind = [0],[1],[2]
								Leyenda aCambiar = combate.getJ1().getLeyendaEquipo(ind + 3);
								if (aCambiar != null && !aCambiar.estaMuerto()) {
									combate.getJ1().intercambiarEnEquipo(l, ind + 3);
									actualizaNombresLeys();
								} else {
									JOptionPane.showMessageDialog(VenCombate.this,
											"No puedes dejar un hueco vacio en tu frente o sacar a un muerto al campo de batalla DESALMADO",
											"Error en intercambio", JOptionPane.INFORMATION_MESSAGE);

								}

							} else {
								JOptionPane.showMessageDialog(VenCombate.this,
										"Si quieres cambiar, selecciona primero una leyenda del banquillo",
										"Error en intercambio", JOptionPane.INFORMATION_MESSAGE);
							}
						}
						// Jugador 2
						else {
							int ind = lsJ2Banquillo.getSelectedIndex();
							if (ind >= 0) {
								// equipo = [0],[1],[2] [3],[4],[5]
								// l = [3],[4],[5] ind = [0],[1],[2]
								Leyenda aCambiar = combate.getJ2().getLeyendaEquipo(ind + 3);
								if (aCambiar != null && !aCambiar.estaMuerto()) {
									combate.getJ2().intercambiarEnEquipo(l - 3, ind + 3);
									actualizaNombresLeys();
								} else {
									JOptionPane.showMessageDialog(VenCombate.this,
											"No puedes dejar un hueco vacio en tu frente o sacar a un muerto al campo de batalla DESALMADO",
											"Error en intercambio", JOptionPane.INFORMATION_MESSAGE);

								}

							} else {
								JOptionPane.showMessageDialog(VenCombate.this,
										"Si quieres cambiar, selecciona primero una leyenda del banquillo",
										"Error en intercambio", JOptionPane.INFORMATION_MESSAGE);
							}
						}

					}

				}
			});
		}

	}

	/**
	 * Comprueba si el turno deberia terminar y si no continua
	 */
	private void checkFinalTurno() {
		// Si ya han atacado todos se termina el turno
		if (!leyendasEnCombate.isEmpty()) {
			siguienteLeyenda();
		} else {
			enTurno = false;
			int g = combate.checkGanadorBatalla();
			if (g==0) {
				int dobsGanados= combate.getJ2().getDoblones();
				JOptionPane.showMessageDialog(VenCombate.this, combate.getJ1().getNombre()+" gano la partida!\nSe lleva "+dobsGanados+" doblones" , "FIN DE LA PARTIDA", JOptionPane.INFORMATION_MESSAGE);
				combate.getJ1().incDoblones(dobsGanados);;
				dispose();
				VenMenuPrincipal v = new VenMenuPrincipal(combate.getJ1());
				v.setUsuario(combate.getJ1());
				v.setVisible(true);
			}
			else if (g==1) {
				JOptionPane.showMessageDialog(VenCombate.this, combate.getJ2().getNombre()+" gano la partida!" , "FIN DE LA PARTIDA", JOptionPane.INFORMATION_MESSAGE);
				dispose();
				VenMenuPrincipal v = new VenMenuPrincipal(combate.getJ1());
				v.setUsuario(combate.getJ1());
				v.setVisible(true);
			}
			else {
			btSigTurno.setVisible(true);}
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
		indiceHabElegida = -1;

		// Cambia el color de quien ataca
		JButton lbleyEnCurso = btLeyEnBatalla[indiceLeyEnCurso];
		lbleyEnCurso.setForeground(Color.RED);

		// Hacer aparecer panel con movs
		Habilidad[] hs = leyendaEnCurso.getHabilidades();
		for (int i = 0; i < hs.length; i++) {
			if (hs[i] != null) {
				btHabilidades[i].setText(hs[i].getNombre());
				btHabilidades[i].setToolTipText(hs[i].getToolTipInfo());
			} else {
				btHabilidades[i].setText(GestorDeDatos.NULL_STR);
				btHabilidades[i].setToolTipText("");
			}

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

		for (int i = 0; i < btLeyEnBatalla.length; i++) {
			int j = i - 3;
			Leyenda correspondiente = null;

			if (i >= 3)
				correspondiente = combate.getJ2().getLeyendaEquipo(j);
			else
				correspondiente = combate.getJ1().getLeyendaEquipo(i);

			if (correspondiente != null) {
				btLeyEnBatalla[i].setText(correspondiente.getNombreCombate());
				btLeyEnBatalla[i].setToolTipText(correspondiente.getToolTipInfo());
			} else {
				btLeyEnBatalla[i].setText(GestorDeDatos.NULL_STR);
				btLeyEnBatalla[i].setToolTipText("");
			}
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

	/**
	 * Devuelve un JPanel con una BoxLayout de eje X con el componente anyadido
	 * 
	 * @param c
	 * @return
	 */
	private JPanel panelBoxLayoutX(Component c) {
		JPanel pn = new JPanel();
		pn.setLayout(new BoxLayout(pn, BoxLayout.X_AXIS));
		pn.add(c);
		return pn;
	}

}
