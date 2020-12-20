package visuales;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import gestion.GestorDeDatos;
import objetosCombate.Jugador;
import personaje.Especie;
import personaje.Leyenda;
import personaje.atributos.Habilidad;
import personaje.atributos.Tipo;

/**
 * 
 * @author danel y jon ander
 *
 */
public class VenValhalla extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final String TITULO = "MLW: Valhalla";
	private static final Dimension MIN_DIM = new Dimension(400, 400);
	private static final Dimension PREF_DIM = new Dimension(600, 650);
	public static final int COSTE_CONTRATO = 500;

	private Jugador usuario;

	private JLabel lbDoblones = new JLabel();

	private JButton btTirada = new JButton("Contratar una Leyenda aleatoria: " + COSTE_CONTRATO + " doblones");
	private JLabel lbTipo1 = new JLabel("Tipo1:");
	private JLabel lbTipo2 = new JLabel("Tipo2:");
	private JLabel lbTipo1R = new JLabel(GestorDeDatos.NULL_STR);
	private JLabel lbTipo2R = new JLabel(GestorDeDatos.NULL_STR);

	private JLabel lbNomHab = new JLabel("Habilidad");
	private JLabel lbTipoHab = new JLabel("Tipo:");
	private JLabel lbTipoRHab = new JLabel(GestorDeDatos.NULL_STR);

	private JButton btDerEsp = new JButton(">");
	private JButton btIzqEsp = new JButton("<");
	private JButton btDerHab = new JButton(">");
	private JButton btIzqHab = new JButton("<");

	private JTextArea taDescrEsp = new JTextArea(8, 50);
	private JScrollPane spDescrEsp = new JScrollPane(taDescrEsp);

	private JTextArea taDescrHab = new JTextArea(4, 50);
	private JScrollPane spDescrHab = new JScrollPane(taDescrHab);

	private JPanel pnNorte = new JPanel();
	private JPanel pnCentral = new JPanel();
	private JPanel pnSelEspecie = new JPanel(new BorderLayout());
	private JPanel pnEsp = new JPanel(new BorderLayout());
	private JPanel pnDatosEsp = new JPanel(new BorderLayout());
	private JPanel pnDatosEspCen = new JPanel();
	private JPanel pnDatosEspNorte = new JPanel();
	private JPanel pnHab = new JPanel(new BorderLayout());
	private JPanel pnSelHab = new JPanel(new BorderLayout());
	private JPanel pnDatosHab = new JPanel(new BorderLayout());
	private JPanel pnSelCentro = new JPanel();

	private ArrayList<String> nombresEsp;
	private int posArrEsp = 0;

	private ArrayList<String> nombresHab;
	private int posArrHab = 0;

	// Fuentes
	private static final Font FUENTE_MENSAJE = new Font(GestorDeDatos.NOMBRE_PERPETUA_BOLD, Font.PLAIN, 20);
	private static final Font FUENTE_BOTON = new Font(GestorDeDatos.NOMBRE_PERPETUA_BOLD_ITALIC, Font.ITALIC, 15);
	private static final Font FUENTE_DESCR = new Font(GestorDeDatos.NOMBRE_PERPETUA_BOLD_ITALIC, Font.ITALIC, 15);
	private static final Font FUENTE_FLECHAS = new Font(GestorDeDatos.NOMBRE_PERPETUA_TITLING_MT_BOLD, Font.BOLD, 30);

	private Component btEspecie = Especie.getBotonVentanaNULO(FUENTE_MENSAJE, 150);

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

		nombresEsp = GestorDeDatos.getNombresEspecies();
		nombresHab = GestorDeDatos.getNombresHabilidades();

		// Colocacion de paneles
		getContentPane().add(pnNorte, BorderLayout.NORTH);
		getContentPane().add(pnCentral, BorderLayout.CENTER);
		pnNorte.add(lbDoblones);
		lbDoblones.setFont(FUENTE_MENSAJE);
		pnNorte.add(btTirada);
		btTirada.setFont(FUENTE_BOTON);

		pnCentral.add(pnEsp);

		pnEsp.add(pnSelEspecie, BorderLayout.NORTH);

		btIzqEsp.setFont(FUENTE_FLECHAS);
		btDerEsp.setFont(FUENTE_FLECHAS);

		pnEsp.add(pnDatosEsp, BorderLayout.CENTER);

		pnDatosEsp.add(pnDatosEspNorte, BorderLayout.NORTH);
		pnDatosEsp.add(pnDatosEspCen, BorderLayout.CENTER);
		pnDatosEspCen.add(spDescrEsp);
		taDescrEsp.setFont(FUENTE_DESCR);
		taDescrEsp.setEditable(false);
		taDescrEsp.setLineWrap(true);
		taDescrEsp.setWrapStyleWord(true);

		pnDatosEspNorte.add(lbTipo1);
		pnDatosEspNorte.add(lbTipo1R);
		pnDatosEspNorte.add(lbTipo2);
		pnDatosEspNorte.add(lbTipo2R);
		lbTipo1.setFont(FUENTE_BOTON);
		lbTipo1R.setFont(FUENTE_BOTON);
		lbTipo2.setFont(FUENTE_BOTON);
		lbTipo2R.setFont(FUENTE_BOTON);

		pnCentral.add(pnHab);

		pnHab.add(pnSelHab, BorderLayout.NORTH);
		pnSelHab.add(btIzqHab, BorderLayout.WEST);
		pnSelHab.add(btDerHab, BorderLayout.EAST);
		pnSelHab.add(pnSelCentro, BorderLayout.CENTER);

		btIzqHab.setFont(FUENTE_FLECHAS);
		btDerHab.setFont(FUENTE_FLECHAS);

		pnSelCentro.add(lbNomHab);
		lbNomHab.setFont(FUENTE_MENSAJE);
		pnSelCentro.add(lbTipoHab);
		lbTipoHab.setFont(FUENTE_BOTON);
		pnSelCentro.add(lbTipoRHab);
		lbTipoRHab.setFont(FUENTE_BOTON);

		pnHab.add(pnDatosHab, BorderLayout.CENTER);

		pnDatosHab.add(spDescrHab);
		taDescrHab.setFont(FUENTE_DESCR);
		taDescrHab.setEditable(false);
		taDescrHab.setLineWrap(true);
		taDescrHab.setWrapStyleWord(true);

		// Listeners
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				VenMenuPrincipal v = new VenMenuPrincipal(usuario);
				v.setVisible(true);
				dispose();
			}
		});

		btDerEsp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				posArrEsp++;
				if (posArrEsp >= nombresEsp.size()) {
					posArrEsp = 0;
				}
				actualizaDatos();
			}
		});
		btIzqEsp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				posArrEsp--;
				if (posArrEsp < 0) {
					posArrEsp = nombresEsp.size() - 1;
				}
				actualizaDatos();
			}
		});
		btDerHab.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				posArrHab++;
				if (posArrHab >= nombresHab.size()) {
					posArrHab = 0;
				}
				actualizaDatos();
			}
		});
		btIzqHab.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				posArrHab--;
				if (posArrHab < 0) {
					posArrHab = nombresHab.size() - 1;
				}
				actualizaDatos();
			}
		});

		btTirada.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (usuario.pagar(COSTE_CONTRATO)) {
					actualizaDatos();
					Leyenda l = Leyenda.getLeyendaRandom(usuario.getNvDificultad()/2);
					JOptionPane.showMessageDialog(VenValhalla.this, "Ha salido: " + l.getNombre(), "Leyenda contratada",
							JOptionPane.INFORMATION_MESSAGE);
					usuario.anyadirNuevaLeyenda(l);

				} else
					JOptionPane.showMessageDialog(VenValhalla.this,
							"No tienes suficientes doblones, si quieres mas gana algunas batallas", "Sin dinero",
							JOptionPane.ERROR_MESSAGE);
			}
		});

		actualizaDatos();

	}

	/**
	 * Actualiza los datos
	 */
	private void actualizaDatos() {
		lbDoblones.setText("Tus doblones: " + usuario.getDoblones());
		Especie act = GestorDeDatos.getInfoEspecie(nombresEsp.get(posArrEsp));
		btEspecie = act.getBotonVentana(FUENTE_MENSAJE, 150);

		pnSelEspecie.removeAll();

		pnSelEspecie.add(btIzqEsp, BorderLayout.WEST);
		pnSelEspecie.add(btDerEsp, BorderLayout.EAST);
		JPanel p = new JPanel();
		p.add(btEspecie);
		pnSelEspecie.add(p, BorderLayout.CENTER);

		taDescrEsp.setText(act.getDescripcion());

		Tipo[] tp = act.getTipos();
		Tipo t1 = tp[0];
		Tipo t2 = tp[1];
		if (t1 != null) {
			lbTipo1R.setText(t1.toString());
			lbTipo1R.setForeground(t1.getColor());
		} else {
			lbTipo1R.setText(GestorDeDatos.NULL_STR);
			lbTipo1R.setForeground(Color.BLACK);
		}
		if (t2 != null) {
			lbTipo2R.setText(t2.toString());
			lbTipo2R.setForeground(t2.getColor());
		} else {
			lbTipo2R.setText(GestorDeDatos.NULL_STR);
			lbTipo2R.setForeground(Color.BLACK);
		}

		Habilidad hab = GestorDeDatos.getInfoHabilidad(nombresHab.get(posArrHab));
		lbNomHab.setText(hab.getNombre());
		Tipo th = hab.getTipo();
		if (th != null) {
			lbTipoRHab.setText(th.toString());
			lbTipoRHab.setForeground(th.getColor());
		} else {
			lbTipoRHab.setText(GestorDeDatos.NULL_STR);
			lbTipoRHab.setForeground(Color.BLACK);
		}
		taDescrHab.setText(hab.getDescripcion());

		revalidate();

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
