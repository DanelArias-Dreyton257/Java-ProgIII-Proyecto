package soloDesarrollo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
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

	//private TreeSet<String> listaHabs;

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
		//listaHabs = GestorDeDatos.readListaHabilidades();

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

				if (txNombre.getText().length() >= 1) {
					try {
						Habilidad h = new Habilidad(txNombre.getText(), (Tipo) mdTipos.getSelectedItem(), Integer.parseInt(txPot.getText()), Double.parseDouble(txPrec.getText()) / 100);
						GestorDeDatos.insertHabilidadBD(h);
					}
					catch(IllegalArgumentException e1){
						JOptionPane.showMessageDialog(CreacionHabVen.this,
								"Error en el tipo de datos introducido. Asegure que 0<=potencia<=99 y 0<=precision<=100",
								"Error", JOptionPane.ERROR_MESSAGE);
						logger.log(Level.INFO, "Datos introducidos no son correctos");
					}
					
					logger.log(Level.INFO, "Añadido " + txNombre.getText());
					reDoList();
					clear();
				} else {
					JOptionPane.showMessageDialog(CreacionHabVen.this,
							"Error en el tipo de datos introducido. Asegure que el nombre no esta vacio",
							"Error", JOptionPane.ERROR_MESSAGE);
					logger.log(Level.INFO, "Datos introducidos no son correctos");
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
				String nombreEleg = lsNombres.getSelectedValue();
				Habilidad h = GestorDeDatos.getInfoHabilidad(nombreEleg);
				fillWith(h);

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

	private void fillWith(Habilidad h) {

		txNombre.setText(h.getNombre());

		mdTipos.setSelectedItem(h.getTipo());

		txPot.setText(h.getPotencia() + "");

		txPrec.setText((h.getPrecision() * 100) + "");

		taDescr.setText(h.getDescripcion());
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
		for (String l : GestorDeDatos.getNombresHabilidades()) {
			mdLista.addElement(l);
		}

	}

	public static void main(String[] args) {
		CreacionHabVen v = new CreacionHabVen();
		v.setVisible(true);
	}

}
