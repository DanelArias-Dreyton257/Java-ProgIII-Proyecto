package visuales;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import gestion.GestorDeDatos;
import gestion.GestorJugadores;
import objetosCombate.Jugador;

/**
 * 
 * @author danel y jon ander
 *
 */
public class VenRankings extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final String TITULO = "MLW: Rankings";
	private static final Dimension MIN_DIM = new Dimension(500, 100);
	private static final Dimension PREF_DIM = new Dimension(750, 200);

	private String[] cabeceras = { "Nombre", "Nv. Dificultad", "Num. Leyendas", "% Victorias", "Num. Victorias",
			"Num. Derrotas", "Racha" };

	private GestorJugadores gJugadores = new GestorJugadores();

	private DefaultTableModel mdTabla = new DefaultTableModel() {
		private static final long serialVersionUID = 1L;

		public java.lang.Class<?> getColumnClass(int columnIndex) {
			return String.class;
		};

		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};

	private JTable tbRankings = new JTable(mdTabla) {

		private static final long serialVersionUID = 1L;

		@Override
		public TableCellRenderer getCellRenderer(int arg0, int arg1) {
			DefaultTableCellRenderer df = new DefaultTableCellRenderer();
			df.setHorizontalAlignment(JLabel.CENTER);

			if (arg1 == 4)
				df.setForeground(Color.GREEN);
			else if (arg1 == 5)
				df.setForeground(Color.RED);
			else if (arg1 == 3) {
				String s = (String) getValueAt(arg0, arg1);
				int pos = s.indexOf("%");
				String numSolo = s.substring(0, pos);
				double porc = Double.parseDouble(numSolo);
				double g = porc * 2.55;
				double r = 255 - g;
				Color c = new Color((int) r, (int) g, 0);
				df.setForeground(c);
			} else if (arg1 == 6) {
				String s = (String) getValueAt(arg0, arg1);
				double racha = Double.parseDouble(s);
				if (racha > 0) {
					df.setForeground(Color.GREEN);
				} else {
					df.setForeground(Color.RED);
				}
			}

			return df;
		}

	};

	private static final Font FUENTE_DATOS = new Font(GestorDeDatos.NOMBRE_PERPETUA_BOLD, Font.PLAIN, 15);
	private static final Font FUENTE_CABECERA = new Font(GestorDeDatos.NOMBRE_PERPETUA_TITLING_MT_BOLD, Font.BOLD, 12);

	/**
	 * Constructor de la ventana de rankings, recibe un gestor de jugadores del cual
	 * expondra sus datos
	 * 
	 * @param gj
	 */
	public VenRankings(GestorJugadores gj) {

		setgJugadores(gj);

		// Colocar ventana
		setMinimumSize(MIN_DIM);
		setPreferredSize(PREF_DIM);
		setSize(PREF_DIM);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle(TITULO);

		getContentPane().add(new JScrollPane(tbRankings));
		tbRankings.getTableHeader().setFont(FUENTE_CABECERA);
		tbRankings.setFont(FUENTE_DATOS);

		datosATabla();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// Abrir menu principal
				setVisible(false);
				VenGestorJugadores v = new VenGestorJugadores(gJugadores);
				v.setVisible(true);
				dispose();

			}
		});

	}

	/**
	 * Mete los datos en la JTable
	 */
	private void datosATabla() {

		for (String c : cabeceras) {
			mdTabla.addColumn(c);
		}

		for (Jugador j : gJugadores.getJugadores()) {
			mdTabla.addRow(j.getDatosTabla());

		}

		tbRankings.revalidate();
	}

	/**
	 * Devuelve el gestor de jugadores
	 * 
	 * @return
	 */
	public GestorJugadores getgJugadores() {
		return gJugadores;
	}

	/**
	 * Establece el gestor de jugadores
	 * 
	 * @param gJugadores
	 */
	public void setgJugadores(GestorJugadores gJugadores) {
		this.gJugadores = gJugadores;
	}

}
