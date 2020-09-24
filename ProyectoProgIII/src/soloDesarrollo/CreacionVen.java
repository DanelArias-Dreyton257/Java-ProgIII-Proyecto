package soloDesarrollo;

import java.awt.BorderLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import objects.Tipo;

public class CreacionVen extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int HEIGHT = 250;
	private static final int WIDTH = 600;

	private JTextField txNombre = new JTextField(20);
	private DefaultComboBoxModel<Tipo> mdTipos = new DefaultComboBoxModel<>(Tipo.values());
	private JComboBox<Tipo> cbTipo1 = new JComboBox<>(mdTipos);
	private JComboBox<Tipo> cbTipo2 = new JComboBox<>(mdTipos);
	private JTextArea taDescr = new JTextArea(10, 50);
	private JButton btOk = new JButton("OK");

	private JLabel lbNombre = new JLabel("Nombre:");
	private JLabel lbTipo1 = new JLabel("Tipo1:");
	private JLabel lbTipo2 = new JLabel("Tipo2:");
	private JLabel lbDescr = new JLabel("Descripcion:");

	private JPanel pnCentral = new JPanel();
	private JPanel pnBot = new JPanel();

	public CreacionVen() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(WIDTH, HEIGHT);

		this.getContentPane().add(pnCentral, BorderLayout.CENTER);
		this.getContentPane().add(pnBot, BorderLayout.SOUTH);
		
		pnCentral.add(lbNombre);
		pnCentral.add(txNombre);
		pnCentral.add(lbTipo1);
		pnCentral.add(cbTipo1);
		pnCentral.add(lbTipo2);
		pnCentral.add(cbTipo2);
		pnCentral.add(lbDescr);
		pnCentral.add(taDescr);
		
		pnBot.add(btOk);
		
		taDescr.setLineWrap(true);

	}

}
