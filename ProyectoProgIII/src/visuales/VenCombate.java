package visuales;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import objetos.Combate;

public class VenCombate extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private static final String TITULO = "MYTHS of the LEGENDARY WAR";
	private static final Dimension MIN_DIM = new Dimension(400, 400);
	private static final Dimension PREF_DIM = new Dimension(900, 700);
	
	private JPanel pnPrincipal = new JPanel(new GridLayout(1, 5));
	private JPanel pn1 = new JPanel(new BorderLayout());
	private JPanel pnGrid2 = new JPanel(new GridLayout(3, 2));
	private JPanel pn3 = new JPanel();
	private JPanel pnGrid4 = new JPanel(new GridLayout(3,2));
	private JPanel pn5 = new JPanel(new BorderLayout());
	private JPanel pnBanquilloJ1 = new JPanel();
	private JPanel pnBanquilloJ2 = new JPanel();
	
	private DefaultListModel<String> mdJ1Banquillo = new DefaultListModel<String>();
	private DefaultListModel<String> mdJ2Banquillo = new DefaultListModel<String>();
	private JList<String> lsJ1Banquillo = new JList<>(mdJ1Banquillo);
	private JList<String> lsJ2Banquillo = new JList<>(mdJ2Banquillo);
	
	private JLabel lbJugador1 = new JLabel("Jugador 1");
	private JLabel lbJugador2 = new JLabel("Jugador 2");
	
	private JLabel lbJ1Ley1 = new JLabel("Leyenda 1");
	private JLabel lbJ1Ley2 = new JLabel("Leyenda 2");
	private JLabel lbJ1Ley3 = new JLabel("Leyenda 3");
	private JLabel lbJ2Ley1 = new JLabel("Leyenda 1");
	private JLabel lbJ2Ley2 = new JLabel("Leyenda 2");
	private JLabel lbJ2Ley3 = new JLabel("Leyenda 3");
	
	private JButton btOpciones = new JButton("Opciones");

	public VenCombate(Combate combate) {
		// Colocar ventana
		setMinimumSize(MIN_DIM);
		setPreferredSize(PREF_DIM);
		setSize(PREF_DIM);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle(TITULO);
		
		//Anyadir a principal
		getContentPane().add(pnPrincipal);
		pnPrincipal.add(pn1);
		pnPrincipal.add(pnGrid2);
		pnPrincipal.add(pn3);
		pnPrincipal.add(pnGrid4);
		pnPrincipal.add(pn5);
		
		pn1.add(lbJugador1,BorderLayout.NORTH);
		pn1.add(pnBanquilloJ1,BorderLayout.CENTER);
		pn5.add(lbJugador2,BorderLayout.NORTH);
		pn5.add(pnBanquilloJ2,BorderLayout.CENTER);
		pn5.add(btOpciones,BorderLayout.SOUTH);
		
		pnBanquilloJ1.add(lsJ1Banquillo);
		pnBanquilloJ2.add(lsJ2Banquillo);
		
		//Anyadir leyendas
		pnGrid2.add(lbJ1Ley1);
		pnGrid2.add(new JLabel(""));
		pnGrid2.add(new JLabel(""));
		pnGrid2.add(lbJ1Ley2);
		pnGrid2.add(lbJ1Ley3);
		pnGrid2.add(new JLabel(""));
		
		pnGrid4.add(new JLabel(""));
		pnGrid4.add(lbJ2Ley1);
		pnGrid4.add(lbJ2Ley2);
		pnGrid4.add(new JLabel(""));
		pnGrid4.add(new JLabel(""));
		pnGrid4.add(lbJ2Ley3);
		
		//Cambiar textos
		lbJugador1.setText(combate.getJ1().getNombre());
		lbJugador2.setText(combate.getJ2().getNombre());
		
		lbJ1Ley1.setText(combate.getJ1().getLeyendaEquipo(0).getNombre());
		lbJ1Ley2.setText(combate.getJ1().getLeyendaEquipo(1).getNombre());
		lbJ1Ley3.setText(combate.getJ1().getLeyendaEquipo(2).getNombre());
		lbJ2Ley1.setText(combate.getJ2().getLeyendaEquipo(0).getNombre());
		lbJ2Ley2.setText(combate.getJ2().getLeyendaEquipo(1).getNombre());
		lbJ2Ley3.setText(combate.getJ2().getLeyendaEquipo(2).getNombre());
		
		mdJ1Banquillo.addElement(combate.getJ1().getLeyendaEquipo(3).getNombre());
		mdJ1Banquillo.addElement(combate.getJ1().getLeyendaEquipo(4).getNombre());
		mdJ1Banquillo.addElement(combate.getJ1().getLeyendaEquipo(5).getNombre());
		mdJ2Banquillo.addElement(combate.getJ2().getLeyendaEquipo(3).getNombre());
		mdJ2Banquillo.addElement(combate.getJ2().getLeyendaEquipo(4).getNombre());
		mdJ2Banquillo.addElement(combate.getJ2().getLeyendaEquipo(5).getNombre());
		
		
	}
	public static void main(String[] args) {
		Combate c = new Combate();
		VenCombate v = new VenCombate(c);
		v.setVisible(true);
	}

}
