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

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import objetos.Jugador;
import personaje.Leyenda;

public class VenFusiones extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final String TITULO = "MLW: Fusiones";
	private static final Dimension MIN_DIM = new Dimension(500, 400);
	private static final Dimension PREF_DIM = new Dimension(600, 600);

	private Jugador usuario;

	private JButton btFusion = new JButton("FUSION");
	private JButton btLey1 = new JButton("Seleccione personaje 1");
	private JButton btLey2 = new JButton("Seleccione personaje 2");

	private JLabel lbSuma = new JLabel(" + ");

	private DefaultListModel<String> mdLista = new DefaultListModel<>();
	private JList<String> lsListaLey = new JList<>();

	private JPanel pnPrincipal = new JPanel(new GridLayout(2, 1));
	private JPanel pnArriba = new JPanel();
	private JPanel pnAbajo = new JPanel();
	private JPanel pnSuma = new JPanel();
	private JPanel pnBotones = new JPanel();

	private Leyenda ley1 = null;
	private int ind1 = -1;
	private Leyenda ley2 = null;
	private int ind2 = -1;

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
		pnAbajo.setLayout(new BoxLayout(pnAbajo, BoxLayout.X_AXIS));
		pnAbajo.add(pnSuma);
		pnAbajo.add(pnBotones);
		pnSuma.setLayout(new BoxLayout(pnSuma, BoxLayout.X_AXIS));
		pnSuma.add(btLey1);
		pnSuma.add(lbSuma);
		pnSuma.add(btLey2);
		pnBotones.add(btFusion);

		pnArriba.add(new JScrollPane(lsListaLey));
		actualizaLista();
		lsListaLey.setModel(mdLista);

		lbSuma.setFont(new Font("suma", 1, 40));

		// Listeners
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				VenGestorEquipos v = new VenGestorEquipos(jugador);
				v.setVisible(true);
			}
		});

		btFusion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (ind1 != -1 && ind2 != -1) {
					Leyenda fusion = Leyenda.fusion(ley1, ley2);
					JOptionPane.showMessageDialog(VenFusiones.this,
							"La fusion de: " + ley1.getNombre() + " y " + ley2.getNombre() + " salio: " + fusion,
							"FUSION EXISTOSA", JOptionPane.INFORMATION_MESSAGE);
					usuario.anyadirNuevaLeyenda(fusion);
					actualizaLista();
					ind1 = -1;
					ind2 = -1;
				} else {
					JOptionPane.showMessageDialog(VenFusiones.this, "Selecciona 2 personajes", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		btLey1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int i = lsListaLey.getSelectedIndex();
				if (ind2 != i) {
					ind1 = i;
					if (i < usuario.getNumLeyendasEnEquipo()) {
						// Esta en el equipo
						ley1 = usuario.getLeyendaEquipo(i);
						btLey1.setText(ley1.getNombre());
					} else {
						// Esta en la eternidad
						i -= usuario.getNumLeyendasEnEquipo();
						ley1 = usuario.getLeyendaEternidad(i);
						btLey1.setText(ley1.getNombre());
					}

				} else {
					JOptionPane.showMessageDialog(VenFusiones.this, "Personaje ya seleccionado", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btLey2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int i = lsListaLey.getSelectedIndex();
				if (ind1 != i) {
					ind2 = i;
					if (i < usuario.getNumLeyendasEnEquipo()) {
						// Esta en el equipo
						ley2 = usuario.getLeyendaEquipo(i);
						btLey2.setText(ley2.getNombre());
					} else {
						// Esta en la eternidad
						i -= usuario.getNumLeyendasEnEquipo();
						ley2 = usuario.getLeyendaEternidad(i);
						btLey2.setText(ley2.getNombre());
					}

				} else {
					JOptionPane.showMessageDialog(VenFusiones.this, "Personaje ya seleccionado", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		});

	}

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

	public Jugador getUsuario() {
		return usuario;
	}

	public void setUsuario(Jugador usuario) {
		this.usuario = usuario;
	}

}
