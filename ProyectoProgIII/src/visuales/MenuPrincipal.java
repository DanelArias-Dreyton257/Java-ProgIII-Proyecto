package visuales;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author danel y jon ander
 *
 */
public class MenuPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final String TITULO = "MYTHS of the LEGENDARY WAR";
	private static final Dimension MIN_DIM = new Dimension(400, 400);
	private static final Dimension PREF_DIM = new Dimension(800, 800);

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

	public MenuPrincipal() {

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
				// TODO
				System.out.println("Abrir ventana de Gestion de Equipo");
			}
		});
		btValhalla.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO NO PROYECTO MINIMO VIABLE
				System.out.println("No disponible de momento el Valhalla");
			}
		});
		btJugar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				System.out.println("Abrir ventana de batalla");
			}
		});
		btUsuario.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				System.out.println("Abrir logger");
			}
		});
		btOpciones.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				System.out.println("Abrir ventana de opciones");
			}
		});
		btSalir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				// TODO hacer ventana de confirmacion?
			}
		});

	}

	/**
	 * Main de prueba
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MenuPrincipal m = new MenuPrincipal();
		m.setVisible(true);
	}

}
