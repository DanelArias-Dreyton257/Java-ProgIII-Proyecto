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
import java.util.HashMap;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import gestion.GestorDeDatos;
import personaje.Especie;
import personaje.Leyenda;

/**
 * 
 * @author danel y jon ander
 *
 */
public class BotonEsp extends JPanel {

	private static final long serialVersionUID = 1L;

	private Especie esp = null;
	private JLabel lbNombre = new JLabel(GestorDeDatos.NULL_STR);
	private JLabel lbImg = new JLabel();;
	private String imagen = "src/visuales/img/user-icon.png";
	private int scale = 100;
	private JProgressBar prBarraVida = new JProgressBar(JProgressBar.HORIZONTAL);
	private Font fuente = new Font(GestorDeDatos.NOMBRE_PERPETUA_BOLD, Font.PLAIN, 12);
	private JPanel pnLabel = new JPanel();

	/**
	 * Crea un boton para la especie especificada
	 * 
	 * @param esp
	 * @param scale
	 * @param fuente usada para el texto
	 */
	public BotonEsp(Especie esp, int scale, Font fuente) {
		super(new BorderLayout());
		this.setEspecie(esp);
		prepararBoton();
		setFuente(fuente);
		setScale(scale);
	}

	/**
	 * Crea un boton que representa una especie nula
	 * 
	 * @param scale
	 * @param fuente
	 */
	public BotonEsp(int scale, Font fuente) {
		this(null, scale, fuente);
	}

	/**
	 * Prepara el boton con todas sus caracteristicas
	 */
	private void prepararBoton() {
		// Movimientos de los bordes para que simule un boton
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

		// Imagen
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

		// Texto
		lbNombre.setFont(new Font(GestorDeDatos.NOMBRE_PERPETUA_BOLD, Font.PLAIN, 13));
		pnLabel.add(lbNombre);
		add(pnLabel, BorderLayout.SOUTH);

		prBarraVida.setStringPainted(true);
		prBarraVida.setForeground(Color.BLACK);
		prBarraVida.setFont(fuente);
		
//		add(prBarraVida, BorderLayout.NORTH);

		revalidate();
	}

	/**
	 * Establece la especie del boton
	 * 
	 * @param esp
	 */
	public void setEspecie(Especie esp) {
		this.esp = esp;
	}

	/**
	 * Devuelve la especie del boton
	 * 
	 * @return
	 */
	public Especie getEsp() {
		return esp;
	}

	/**
	 * Devuelve el nombre, o sea, el texto que aparece en el boton
	 * 
	 * @return
	 */
	public String getNombre() {
		return lbNombre.getText();
	}

	/**
	 * Establece el nombre, o sea, el texto que aparece en el boton
	 * 
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		lbNombre.setText(nombre);
	}

	/**
	 * Devuelve el color del texto del boton
	 * 
	 * @return
	 */
	public Color getColorLb() {
		return lbNombre.getForeground();
	}

	/**
	 * Establece el color del texto del boton
	 * 
	 * @param colorlb
	 */
	public void setColorLb(Color colorlb) {
		lbNombre.setForeground(colorlb);
	}

	/**
	 * Devuelve el color del fondo del boton
	 * 
	 * @return
	 */
	public Color getColorFondo() {
		return this.getBackground();
	}

	/**
	 * Establece el color de fondo del boton
	 * 
	 * @param colorfondo
	 */
	public void setColorFondo(Color colorfondo) {
		this.setBackground(colorfondo);
		BotonEsp.this.pnLabel.setBackground(colorfondo);
	}

	/**
	 * Devuelve la escala del boton
	 * 
	 * @return
	 */
	public int getScale() {
		return scale;
	}

	/**
	 * Establece la escala del boton
	 * 
	 * @param scale
	 */
	public void setScale(int scale) {
		this.scale = scale;
		reCalculaTamanyo();
		revalidate();
	}

	/**
	 * Recalcula el tamanyo de la imagen, y por lo tanto el boton, segun la escala
	 * dada
	 */
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

	/**
	 * Devuelve la fuente del boton
	 * 
	 * @return
	 */
	public Font getFuente() {
		return lbNombre.getFont();
	}

	/**
	 * Establece la fuente que usara el boton
	 * 
	 * @param fuente
	 */
	public void setFuente(Font fuente) {
		lbNombre.setFont(fuente);
		this.fuente = fuente;
		revalidate();
	}

	@Override
	protected void paintComponent(Graphics g) {
		actualizaDatos();
		super.paintComponent(g);
	}

	/**
	 * Actualiza los datos que muestra el boton
	 */
	private void actualizaDatos() {

		if (esp != null) {

			lbNombre.setText(esp.getNombre());
			this.remove(prBarraVida);
//			prBarraVida.setValue(prBarraVida.getMaximum());
//			prBarraVida.setString(GestorDeDatos.NO_STR);
//			prBarraVida.setForeground(Color.BLACK);

			if (esp instanceof Leyenda) {
				Leyenda l = (Leyenda) esp;
				BotonEsp.this.setToolTipText(l.getToolTipInfo());
				lbNombre.setText(l.getNombre()); // Apanyo
				
				this.add(prBarraVida, BorderLayout.NORTH);
				prBarraVida.setMaximum(l.getVidaMax());
				prBarraVida.setValue(l.getVida());
				prBarraVida.setString(l.getVida() + " / " + l.getVidaMax());
				prBarraVida.setStringPainted(true);
				
				prBarraVida.setForeground(getColorBarraVida(l.getVida(), l.getVidaMax()));

				if (isEnabled()) {
					if (l.estaMuerto()) {
						BotonEsp.this.setColorFondo(Color.DARK_GRAY);
						this.remove(prBarraVida);
					} else {
						BotonEsp.this.setColorFondo(Color.LIGHT_GRAY);
					}
				}

			}
		} else {
			this.remove(prBarraVida);
//			prBarraVida.setValue(prBarraVida.getMaximum());
//			prBarraVida.setString(GestorDeDatos.NO_STR);
//			prBarraVida.setForeground(Color.BLACK);
		}
		if (!isEnabled()) {
			BotonEsp.this.setColorFondo(Color.BLACK);
		}
	}
	
	private Color getColorBarraVida(int vida, int vidaMax) {
		double ratio = vida/vidaMax;
		if (ratio>=0.7) return Color.GREEN;
		else if (ratio<0.7 && ratio>=0.5) return Color.YELLOW;
		else if (ratio<0.5 && ratio>=0.2) return Color.ORANGE;
		else if (ratio<0.2 && ratio>0) return Color.RED;
		else if (ratio<=0) return Color.BLACK;
		else return Color.PINK;// NO deberia ocurrir nunca //FIXME
	}

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setSize(500, 500);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		BotonEsp bt = Leyenda.getLeyendaRandom().getBotonVentana(new Font("P", Font.BOLD, 25), 100);
		f.getContentPane().add(bt);
		f.setVisible(true);
	}

}
