package visuales;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import gestion.GestorDeDatos;

public class VenCreditos extends JFrame{
	private static final long serialVersionUID = 1L;
	private static final int WIDTH=500;
	private static final int HEIGHT=750;
	private static final String TITLE= "Credits";
	private static final int BORDER_THICKNESS = 1;
	private static final Font FUENTE_BOTONES = new Font(GestorDeDatos.NOMBRE_PERPETUA_BOLD_ITALIC, Font.ITALIC, 15);
	private static final Font FUENTE_G_BOTONES = new Font(GestorDeDatos.NOMBRE_PERPETUA_BOLD_ITALIC, Font.ITALIC, 25);
	private static final Font FUENTE_J_BOTONES = new Font(GestorDeDatos.NOMBRE_PERPETUA_BOLD_ITALIC, Font.ITALIC, 35);
	//JPanel
	private JPanel pnTop = new JPanel();
	private JPanel pnCentral = new JPanel();
	private JPanel pnBot= new JPanel();
	//JLabel
	private JLabel message = new JLabel("");

	public void CreditsWindow() {
		//Colocación de paneles y componentes en paneles
		getContentPane().setLayout(new BorderLayout());
		//Paneles en su sitio
		getContentPane().add(pnCentral, BorderLayout.CENTER);
		getContentPane().add(pnBot, BorderLayout.SOUTH);
		getContentPane().add(pnTop,BorderLayout.NORTH);
		//Tamanyo y colocacion de la ventana
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		//Poner la ventana en el centro de la pantalla
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setTitle(TITLE);
		setResizable(false);
		//Titulo
		JLabel title = new JLabel(TITLE,SwingConstants.CENTER);
		title.setFont(FUENTE_BOTONES);
		pnTop.add(new JPanel().add(title));
		//Panel inferior
		pnBot.add(message);	
		//Panel Central
		pnCentral.setLayout(new BoxLayout(pnCentral, BoxLayout.Y_AXIS));

		pnCentral.add(getCreditsPart("Programador:", "Danel Arias, Jon Ander de la Puebla"));
		pnCentral.add(getCreditsPart("Disenyador:", "Danel Arias", "Jon Ander de la Puebla"));
		pnCentral.add(getCreditsPart("Musica:"));
		pnCentral.add(getCreditsPart("Agradecimientos:"));

	}
	/**
	 * Establece el mensaje
	 * @param msg
	 */
	public void setMessage(String msg) {
		message.setText(msg);
	}
	/**
	 * Devuelve un panel con las JLabel para los creditos
	 * @param category
	 * @param names
	 * @return
	 */
	private JPanel getCreditsPart(String category,String...names) {
		JPanel p = new JPanel(new GridLayout(names.length, 2));
		JLabel c = new JLabel(category);
		c.setFont(FUENTE_BOTONES);
		p.add(c);
		for (String s:names) {
			JLabel l = new JLabel(s);
			l.setFont(FUENTE_BOTONES);
			if (s!=names[0]) {
				p.add(new JLabel(""));
			}
			p.add(l);
		}
		p.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, BORDER_THICKNESS,false));
		return p;
	}
public static void main(String[] args) {
	VenCreditos ven= new VenCreditos();
	ven.setSize(500, 400);
	ven.setVisible(true);
	ven.CreditsWindow();

}

}
