package soloDesarrollo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import gestion.GestorDeDatos;

public class CreacionContrincantes extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(CreacionContrincantes.class.getName());
	private static final boolean ANYADIR_A_FIC_LOG = false; // poner true para no sobreescribir
	static {
		try {
			logger.addHandler(new FileHandler("src/logs/"+CreacionContrincantes.class.getName() + ".log.xml", ANYADIR_A_FIC_LOG));
		} catch (SecurityException | IOException e) {
			logger.log(Level.SEVERE, "Error en creacion fichero log");
		}
	}
	
	private static final int HEIGHT = 500;
	private static final int WIDTH = 400;

	private JTextField txNombre = new JTextField(15);

	private JButton btOk = new JButton("OK");
	private JButton btClear = new JButton("Clear");
	private DefaultListModel<String> mdLista = new DefaultListModel<>();
	private JList<String> lsNombres = new JList<>();
	private JScrollPane spnHabs;

	//private TreeSet<String> listaHabs;

	private JLabel lbNombre = new JLabel("Nombre:");

	private JPanel pnCentral = new JPanel();
	private JPanel pnCenUp = new JPanel();
	private JPanel pnBot = new JPanel();

	public CreacionContrincantes() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		setMinimumSize(new Dimension(WIDTH, 100));
		
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

		pnCentral.add(pnCenUp);


		pnBot.add(btOk);
		pnBot.add(btClear);


		btOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GestorDeDatos.insertCont(txNombre.getText());
				clear();
				reDoList();
			}

		});

		btClear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setEditableAll(true);
				clear();
			}

		});
		
		logger.log(Level.FINE, "Ventana creada correctamente");

	}

	private void clear() {
		txNombre.setText("");
		lsNombres.clearSelection();

	}

	private void setEditableAll(boolean b) {

		txNombre.setEditable(b);

		btOk.setEnabled(b);

	}

	private void reDoList() {
		mdLista.clear();
		for (String l : GestorDeDatos.getNombresContrincantes()) {
			mdLista.addElement(l);
		}

	}

	public static void main(String[] args) {
		CreacionContrincantes v = new CreacionContrincantes();
		v.setVisible(true);
	}

}
