package visuales.objetos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gestion.GestorDeDatos;
import personaje.Especie;
import personaje.Leyenda;

public class BotonEsp extends JPanel {

	private static final long serialVersionUID = 1L;

	private Especie esp = null;
	private JLabel lbNombre = new JLabel(GestorDeDatos.NULL_STR);
	private JLabel lbImg = new JLabel();;
	private String imagen = "src/visuales/img/user-icon.png";
	private int scale = 100;
	private Font fuente = new Font(GestorDeDatos.NOMBRE_PERPETUA_BOLD, Font.PLAIN, 12);

	public Especie getEsp() {
		return esp;
	}

	public BotonEsp(Especie esp, int scale, Font fuente) {
		super(new BorderLayout());
		this.setEspecie(esp);
		prepararBoton();
		setFuente(fuente);
		setScale(scale);
	}

	public BotonEsp(int scale, Font fuente) {
		this(null, scale, fuente);
	}

	private void prepararBoton() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				BotonEsp.this.setBorder(BorderFactory.createLoweredBevelBorder());
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				BotonEsp.this.setBorder(BorderFactory.createRaisedBevelBorder());
			}
		});
		setBorder(BorderFactory.createRaisedBevelBorder());
		BufferedImage bimg = null;
		try {
			bimg = ImageIO.read(new File(imagen)); // TODO
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Redimensiona la imagen para que concuerd con las dimensiones especificadas
		Image dimg = bimg.getScaledInstance(scale, scale, Image.SCALE_SMOOTH);

		ImageIcon imgIcon = new ImageIcon(dimg);

		lbImg = new JLabel(imgIcon);

		this.add(lbImg, BorderLayout.CENTER);
		lbNombre.setFont(new Font(GestorDeDatos.NOMBRE_PERPETUA_BOLD, Font.PLAIN, 12));
		JPanel pn = new JPanel();
		pn.add(lbNombre);
		add(pn, BorderLayout.SOUTH);
		revalidate();
	}

	public void setEspecie(Especie esp) {
		this.esp = esp;
	}

	public String getNombre() {
		return lbNombre.getText();
	}

	public void setNombre(String nombre) {
		lbNombre.setText(nombre);
	}

	public Color getColorLb() {
		return lbNombre.getForeground();
	}

	public void setColorLb(Color colorlb) {
		lbNombre.setForeground(colorlb);
	}

	public Color getColorFondo() {
		return this.getBackground();
	}

	public void setColorFondo(Color colorfondo) {
		this.setBackground(colorfondo);
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
		reCalculaTamanyo();
		revalidate();
	}

	private void reCalculaTamanyo() {
		BufferedImage bimg = null;
		try {
			bimg = ImageIO.read(new File(imagen)); // TODO
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Redimensiona la imagen para que concuerd con las dimensiones especificadas
		Image dimg = bimg.getScaledInstance(scale, scale, Image.SCALE_SMOOTH);

		ImageIcon imgIcon = new ImageIcon(dimg);

		lbImg = new JLabel(imgIcon);
		revalidate();
	}

	public Font getFuente() {
		return lbNombre.getFont();
	}

	public void setFuente(Font fuente) {
		lbNombre.setFont(fuente);
		revalidate();
	}

	@Override
	protected void paintComponent(Graphics g) {
		actualizaDatos();
		super.paintComponent(g);
	}

	private void actualizaDatos() {

		if (esp != null) {
			lbNombre.setText(esp.getNombre());
			if (esp instanceof Leyenda) {
				Leyenda l = (Leyenda) esp;
				BotonEsp.this.setToolTipText(l.getToolTipInfo());
				lbNombre.setText(l.getNombreCombate()); // Apanyo
				
				if (isEnabled()) {
					if (l.estaMuerto()) {
						BotonEsp.this.setColorFondo(Color.DARK_GRAY);
					} else {
						BotonEsp.this.setColorFondo(Color.LIGHT_GRAY);
					}
				}

			}
		}
		if (!isEnabled()) {
			BotonEsp.this.setColorFondo(Color.BLACK);
		}
	}

}
