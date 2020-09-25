package soloDesarrollo;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import objects.Tipo;

public class CreacionVen extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int HEIGHT = 250;
	private static final int WIDTH = 700;

	private JTextField txNombre = new JTextField(15);
	private DefaultComboBoxModel<Tipo> mdTipos = new DefaultComboBoxModel<>(Tipo.values());
	private DefaultComboBoxModel<Tipo> mdTipos2 = new DefaultComboBoxModel<>(Tipo.values());
	private JComboBox<Tipo> cbTipo1 = new JComboBox<>(mdTipos);
	private JComboBox<Tipo> cbTipo2 = new JComboBox<>(mdTipos2);
	private JCheckBox ch2tipos = new JCheckBox("2 tipos");
	private JTextArea taDescr = new JTextArea(10, 50);
	private JButton btOk = new JButton("OK");
	private DefaultListModel<String> mdLista = new DefaultListModel<>();
	private JList<String> lsNombres = new JList<>();

	private ArrayList<String> listaPer = new ArrayList<>();

	private JLabel lbNombre = new JLabel("Nombre:");
	private JLabel lbTipo1 = new JLabel("Tipo1:");
	private JLabel lbTipo2 = new JLabel("Tipo2:");
	private JLabel lbDescr = new JLabel("Descripcion:");

	private JPanel pnCentral = new JPanel();
	private JPanel pnBot = new JPanel();
	private JPanel pnIzq = new JPanel();

	public CreacionVen() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		
		try {
			Scanner sc = new Scanner(new File("src/soloDesarrollo/ficheros/personajes.txt"));
			while (sc.hasNextLine()) {
				String l = sc.nextLine();
				int i = l.indexOf("\t");
				String p = l.substring(0, i);
				mdLista.addElement(p);
			}
			sc.close();
			lsNombres.setModel(mdLista);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.getContentPane().add(pnCentral, BorderLayout.CENTER);
		this.getContentPane().add(pnBot, BorderLayout.SOUTH);
		this.getContentPane().add(pnIzq, BorderLayout.WEST);

		pnCentral.add(lbNombre);
		pnCentral.add(txNombre);
		pnCentral.add(lbTipo1);
		pnCentral.add(cbTipo1);
		pnCentral.add(ch2tipos);
		pnCentral.add(lbTipo2);
		pnCentral.add(cbTipo2);
		pnCentral.add(lbDescr);
		pnCentral.add(taDescr);

		pnBot.add(btOk);
		
		pnIzq.add(lsNombres);

		taDescr.setLineWrap(true);
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
				String tipo2 = "NULL";
				if (ch2tipos.isSelected() && cbTipo1.getSelectedItem() != cbTipo2.getSelectedItem())
					tipo2 = cbTipo2.getSelectedItem().toString();
				listaPer.add(txNombre.getText() + t + cbTipo1.getSelectedItem() + t + tipo2 + t + taDescr.getText());
				mdLista.addElement(txNombre.getText());
				System.out.println("Añadido " + txNombre.getText());

				clear();

			}

		});

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosed(WindowEvent e) {
				new Thread() {
					@Override
					public void run() {

						try {
							PrintWriter fs = new PrintWriter(
									new FileWriter("src/soloDesarrollo/ficheros/personajes.txt", true));

							for (String s : listaPer) {
								fs.println(s);
							}

							fs.close();

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					};
				}.start();

			}

		});

	}

	private void clear() {
		txNombre.setText("");
		taDescr.setText("");

	}

}
