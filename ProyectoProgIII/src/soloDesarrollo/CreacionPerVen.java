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
import java.util.TreeSet;

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
import personaje.atributos.Tipo;

public class CreacionPerVen extends JFrame {

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

	private TreeSet<String> listaPer;

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

		listaPer = GestorDeDatos.readListaLeyendas();

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
				String t = "\t";
				if (txNombre.getText().length() >= 1) {
					String tipo2 = "NULL";
					if (ch2tipos.isSelected() && cbTipo1.getSelectedItem() != cbTipo2.getSelectedItem())
						tipo2 = cbTipo2.getSelectedItem().toString();
					listaPer.add(
							txNombre.getText() + t + cbTipo1.getSelectedItem() + t + tipo2 + t + taDescr.getText());
					System.out.println("Añadido " + txNombre.getText());
					reDoList();
					clear();
				} else {
					JOptionPane.showMessageDialog(CreacionPerVen.this, "El nombre no puede estar vacio", "Error",
							JOptionPane.ERROR_MESSAGE);
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
				for (String s : listaPer) {
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
						GestorDeDatos.writeListaLeyendas(listaPer);
					};
				}.start();

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

	private void fillWith(String l) {
		String t = "\t";

		int a = l.indexOf(t);
		String nombre = l.substring(0, a);

		int b = l.indexOf(t, a + 1);
		String tipo1 = l.substring(a + 1, b);

		int c = l.indexOf(t, b + 1);
		String tipo2 = l.substring(b + 1, c);

		String desc = l.substring(c + 1);

		txNombre.setText(nombre);

		Tipo t1 = Tipo.FUEGO;
		Tipo t2 = Tipo.FUEGO;

		boolean unTipo = tipo2.equals("NULL");

		for (Tipo tipo : Tipo.values()) {
			if (tipo.toString().equals(tipo1)) {
				t1 = tipo;

			}
			if (!unTipo && tipo.toString().equals(tipo2)) {
				t2 = tipo;

			}
		}

		cbTipo1.setSelectedIndex(mdTipos.getIndexOf(t1));
		cbTipo2.setSelectedIndex(mdTipos2.getIndexOf(t2));

		ch2tipos.setSelected(!unTipo);
		cbTipo2.setEnabled(!unTipo);

		taDescr.setText(desc);
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
		for (String l : listaPer) {
			int i = l.indexOf("\t");
			String p = l.substring(0, i);
			mdLista.addElement(p);
		}

	}

	public static void main(String[] args) {
		CreacionPerVen v = new CreacionPerVen();
		v.setVisible(true);
	}

}
