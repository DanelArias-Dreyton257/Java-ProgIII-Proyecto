package soloDesarrollo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.TreeSet;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import gestion.GestorDeDatos;
import personaje.atributos.Habilidad;
import personaje.atributos.Tipo;

public class CreacionHabVen extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(CreacionHabVen.class.getName());
	private static final boolean ANYADIR_A_FIC_LOG = false; // poner true para no sobreescribir
	static {
		try {
			logger.addHandler(new FileHandler("src/logs/"+CreacionHabVen.class.getName() + ".log.xml", ANYADIR_A_FIC_LOG));
		} catch (SecurityException | IOException e) {
			logger.log(Level.SEVERE, "Error en creacion fichero log");
		}
	}
	
	private static final int HEIGHT = 300;
	private static final int WIDTH = 900;

	private JTextField txNombre = new JTextField(15);
	private DefaultComboBoxModel<Tipo> mdTipos = new DefaultComboBoxModel<>(Tipo.values());

	private JComboBox<Tipo> cbTipo = new JComboBox<>(mdTipos);

	private JTextArea taDescr = new JTextArea(10, 50);
	private JButton btOk = new JButton("OK");
	private JButton btClear = new JButton("Clear");
	private DefaultListModel<String> mdLista = new DefaultListModel<>();
	private JList<String> lsNombres = new JList<>();
	private JScrollPane spnHabs;
	private JTextField txPot = new JTextField("99", 3);
	private JTextField txPrec = new JTextField("100", 3);

	private TreeSet<String> listaHabs;

	private JLabel lbNombre = new JLabel("Nombre:");
	private JLabel lbTipo = new JLabel("Tipo:");
	private JLabel lbDescr = new JLabel("Descripcion:");
	private JLabel lbPot = new JLabel("Potencia (0 <=x<= 99):");
	private JLabel lbPrec = new JLabel("Precisión (0 <=x<= 100)");
	private JLabel lbPrecPor = new JLabel("%");

	private JPanel pnCentral = new JPanel();
	private JPanel pnCenUp = new JPanel();
	private JPanel pnBot = new JPanel();

	public CreacionHabVen() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		setMinimumSize(new Dimension(850, 200));
		
		logger.log(Level.INFO, "Empieza proceso de lectura de datos");
		listaHabs = GestorDeDatos.readListaHabilidades();

		pnCentral.setLayout(new BoxLayout(pnCentral, BoxLayout.Y_AXIS));

		reDoList();
		lsNombres.setModel(mdLista);
		spnHabs = new JScrollPane(lsNombres);

		this.getContentPane().add(pnCentral, BorderLayout.CENTER);
		this.getContentPane().add(pnBot, BorderLayout.SOUTH);
		this.getContentPane().add(spnHabs, BorderLayout.WEST);

		pnCenUp.add(lbNombre);
		pnCenUp.add(txNombre);
		pnCenUp.add(lbTipo);
		pnCenUp.add(cbTipo);
		pnCenUp.add(lbPot);
		pnCenUp.add(txPot);
		pnCenUp.add(lbPrec);
		pnCenUp.add(txPrec);
		pnCenUp.add(lbPrecPor);

		pnCentral.add(pnCenUp);
		pnCentral.add(lbDescr);
		pnCentral.add(taDescr);

		pnBot.add(btOk);
		pnBot.add(btClear);

		taDescr.setLineWrap(true);
		taDescr.setWrapStyleWord(true);

		btOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String t = "\t";

				if (txNombre.getText().length() >= 1 && checkPotAndPrec(txPot.getText(), txPrec.getText())) {

					double prec = Double.parseDouble(txPrec.getText()) / 100;

					listaHabs.add(txNombre.getText() + t + cbTipo.getSelectedItem() + t + txPot.getText() + t + prec + t
							+ taDescr.getText());
					
					logger.log(Level.INFO, "Añadido " + txNombre.getText());
					reDoList();
					clear();
				} else {
					JOptionPane.showMessageDialog(CreacionHabVen.this,
							"Error en el tipo de datos introducido. Asegure que el nombre no esta vacio, 0<=potencia<=99 y 0<=precision<=100",
							"Error", JOptionPane.ERROR_MESSAGE);
					logger.log(Level.INFO, "Datos introducidos no son correctos");
				}

			}

			private boolean checkPotAndPrec(String strPot, String strPrec) {
				try {
					new Habilidad("", Tipo.AGUA, Integer.parseInt(strPot), Double.parseDouble(strPrec) / 100);
					return true;
				} catch (IllegalArgumentException e) {
					return false;
				}
			}

		});

		btClear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setEditableAll(true);
				clear();
			}

		});

		lsNombres.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int index = lsNombres.getSelectedIndex();
				int i = 0;
				String l = null;
				for (String s : listaHabs) {
					if (i == index) {
						l = s;
						break;
					}
					i++;
				}
				if (l != null)
					fillWith(l);

			}
		});

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosed(WindowEvent e) {
				new Thread() {
					@Override
					public void run() {
						logger.log(Level.INFO, "Empieza proceso de guardado de datos");
						GestorDeDatos.writeListaHabilidades(listaHabs);
					};
				}.start();

			}

		});
		
		logger.log(Level.FINE, "Ventana creada correctamente");

	}

	private void clear() {
		txNombre.setText("");
		taDescr.setText("");
		cbTipo.setSelectedIndex(0);
		txPot.setText("99");
		txPrec.setText("100");
		lsNombres.clearSelection();

	}

	private void fillWith(String l) {
		String t = "\t";

		int a = l.indexOf(t);
		String nombre = l.substring(0, a);

		int b = l.indexOf(t, a + 1);
		String tipo1 = l.substring(a + 1, b);

		int c = l.indexOf(t, b + 1);
		String pot = l.substring(b + 1, c);

		int d = l.indexOf(t, c + 1);
		String prec = l.substring(c + 1, d);

		String desc = l.substring(d + 1);

		txNombre.setText(nombre);

		Tipo t1 = Tipo.FUEGO;

		for (Tipo tipo : Tipo.values()) {
			if (tipo.toString().equals(tipo1)) {
				t1 = tipo;
			}
		}

		txPot.setText(pot);

		double p = Double.parseDouble(prec) * 100;
		txPrec.setText(p + "");

		cbTipo.setSelectedIndex(mdTipos.getIndexOf(t1));

		taDescr.setText(desc);
		setEditableAll(false);
	}

	private void setEditableAll(boolean b) {
		// txNombre.setEnabled(b);
		txNombre.setEditable(b);
		cbTipo.setEnabled(b);
		txPot.setEditable(b);
		txPrec.setEditable(b);
		// taDescr.setEnabled(b);
		taDescr.setEditable(b);
		btOk.setEnabled(b);

	}

	private void reDoList() {
		mdLista.clear();
		for (String l : listaHabs) {
			int i = l.indexOf("\t");
			String p = l.substring(0, i);
			mdLista.addElement(p);
		}

	}

	public static void main(String[] args) {
		CreacionHabVen v = new CreacionHabVen();
		v.setVisible(true);
	}

}
