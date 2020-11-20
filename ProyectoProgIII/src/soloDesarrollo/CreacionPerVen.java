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
import javax.swing.JCheckBox;
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
import personaje.Especie;
import personaje.atributos.Tipo;

public class CreacionPerVen extends JFrame {
	
	private static Logger logger = Logger.getLogger(CreacionPerVen.class.getName());
	private static final boolean ANYADIR_A_FIC_LOG = false; // poner true para no sobreescribir
	static {
		try {
			logger.addHandler(new FileHandler("src/logs/"+CreacionPerVen.class.getName() + ".log.xml", ANYADIR_A_FIC_LOG));
		} catch (SecurityException | IOException e) {
			logger.log(Level.SEVERE, "Error en creacion fichero log");
		}
	}

	private static final long serialVersionUID = 1L;
	private static final int HEIGHT = 500;
	private static final int WIDTH = 800;

	private JTextField txNombre = new JTextField(15);
	private DefaultComboBoxModel<Tipo> mdTipos = new DefaultComboBoxModel<>(Tipo.values());
	private DefaultComboBoxModel<Tipo> mdTipos2 = new DefaultComboBoxModel<>(Tipo.values());
	private JComboBox<Tipo> cbTipo1 = new JComboBox<>(mdTipos);
	private JComboBox<Tipo> cbTipo2 = new JComboBox<>(mdTipos2);
	private JCheckBox ch2tipos = new JCheckBox("2 tipos");
	private JTextArea taDescr = new JTextArea(10, 50);
	private JButton btOk = new JButton("OK");
	private JButton btClear = new JButton("Clear");
	private DefaultListModel<String> mdLista = new DefaultListModel<>();
	private JList<String> lsNombres = new JList<>();
	private JScrollPane spnPersonajes;

	//private TreeSet<String> listaPer;

	private JLabel lbNombre = new JLabel("Nombre:");
	private JLabel lbTipo1 = new JLabel("Tipo1:");
	private JLabel lbTipo2 = new JLabel("Tipo2:");
	private JLabel lbDescr = new JLabel("Descripcion:");

	private JPanel pnCentral = new JPanel();
	private JPanel pnCenUp = new JPanel();
	private JPanel pnBot = new JPanel();

	public CreacionPerVen() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		setMinimumSize(new Dimension(700, 250));
		
		logger.log(Level.INFO, "Empieza proceso de lectura de datos");
		
		//listaPer = GestorDeDatos.readListaLeyendas();

		pnCentral.setLayout(new BoxLayout(pnCentral, BoxLayout.Y_AXIS));

		reDoList();
		lsNombres.setModel(mdLista);
		spnPersonajes = new JScrollPane(lsNombres);

		this.getContentPane().add(pnCentral, BorderLayout.CENTER);
		this.getContentPane().add(pnBot, BorderLayout.SOUTH);
		this.getContentPane().add(spnPersonajes, BorderLayout.WEST);

		pnCenUp.add(lbNombre);
		pnCenUp.add(txNombre);
		pnCenUp.add(lbTipo1);
		pnCenUp.add(cbTipo1);
		pnCenUp.add(ch2tipos);
		pnCenUp.add(lbTipo2);
		pnCenUp.add(cbTipo2);

		pnCentral.add(pnCenUp);
		pnCentral.add(lbDescr);
		pnCentral.add(taDescr);

		pnBot.add(btOk);
		pnBot.add(btClear);

		taDescr.setLineWrap(true);
		taDescr.setWrapStyleWord(true);

		ch2tipos.setSelected(false);
		cbTipo2.setEnabled(false);

		ch2tipos.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cbTipo2.setEnabled(ch2tipos.isSelected());

			}
		});

		btOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (txNombre.getText().length() >= 1) {
					
					Tipo[] tipos = new Tipo[2];
					
					tipos[0] = (Tipo) mdTipos.getSelectedItem();
					
					Tipo t2 = (Tipo) mdTipos2.getSelectedItem();
					
					if (t2!=null && !tipos[0].equals(t2) && ch2tipos.isSelected()) {
						tipos[1] = t2;
					}
					
					Especie esp = new Especie(txNombre.getText(), taDescr.getText(), tipos);
					GestorDeDatos.insertEspecieBD(esp);
					
					
					
					logger.log(Level.INFO,"Añadido " + txNombre.getText());
					reDoList();
					clear();
				} else {
					JOptionPane.showMessageDialog(CreacionPerVen.this, "El nombre no puede estar vacio", "Error",
							JOptionPane.ERROR_MESSAGE);
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
				Especie esp = GestorDeDatos.getInfoEspecie(nombreEleg);
				if (esp != null)
					fillWith(esp);

			}
		});

	}

	private void clear() {
		txNombre.setText("");
		taDescr.setText("");
		cbTipo1.setSelectedIndex(0);
		cbTipo2.setSelectedIndex(0);
		ch2tipos.setSelected(false);
		cbTipo2.setEnabled(false);
		lsNombres.clearSelection();

	}

	private void fillWith(Especie esp) {

		txNombre.setText(esp.getNombre());

		
		cbTipo1.setSelectedIndex(mdTipos.getIndexOf(esp.getTipos()[0]));
		Tipo t2 = Tipo.FUEGO;
		boolean unTipo = esp.getTipos()[1]==null;
		if (!unTipo) {
			t2 = esp.getTipos()[1];
		}
		cbTipo2.setSelectedIndex(mdTipos2.getIndexOf(t2));

		ch2tipos.setSelected(!unTipo);
		cbTipo2.setEnabled(!unTipo);

		taDescr.setText(esp.getDescripcion());
		setEditableAll(false);
	}

	private void setEditableAll(boolean b) {
		// txNombre.setEnabled(b);
		txNombre.setEditable(b);
		cbTipo1.setEnabled(b);
		ch2tipos.setEnabled(b);
		cbTipo2.setEnabled(b && ch2tipos.isSelected());
		// taDescr.setEnabled(b);
		taDescr.setEditable(b);
		btOk.setEnabled(b);

	}

	private void reDoList() {
		mdLista.clear();
		for (String l : GestorDeDatos.getNombresEspecies()) {
			mdLista.addElement(l);
		}

	}

	public static void main(String[] args) {
		CreacionPerVen v = new CreacionPerVen();
		v.setVisible(true);
	}

}
