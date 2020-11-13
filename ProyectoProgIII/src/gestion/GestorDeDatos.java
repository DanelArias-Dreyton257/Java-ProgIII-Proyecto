package gestion;

import java.sql.*;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import personaje.Especie;
import personaje.atributos.Habilidad;
import personaje.atributos.Tipo;

/**
 * 
 * @author danel y jon ander
 *
 */
public class GestorDeDatos {
	/**
	 * Agregamos un logger
	 */
	private static Logger logger = Logger.getLogger(GestorDeDatos.class.getName());
	private static final boolean ANYADIR_A_FIC_LOG = false; // poner true para no sobreescribir
	static {
		try {
			logger.addHandler(
					new FileHandler("src/logs/" + GestorDeDatos.class.getName() + ".log.xml", ANYADIR_A_FIC_LOG));
		} catch (SecurityException | IOException e) {
			logger.log(Level.SEVERE, "Error en creacion fichero log");
		}
	}

	private static final File FIC_HABS = new File("src/soloDesarrollo/ficheros/habilidades.txt");
	private static final File FIC_LEYS = new File("src/soloDesarrollo/ficheros/personajes.txt");
	public static final String STR_SEPARATOR = "\t";
	public static final String NULL_STR = "NULL";

	private static final File TTF_PERPETUA_BOLD = new File("src/fonts/Perpetua-Bold.ttf");
	private static final File TTF_PERPETUA_BOLD_ITALIC = new File("src/fonts/Perpetua-Bold-Italic.ttf");
	private static final File TTF_PERPETUA_TITLING_MT_BOLD = new File("src/fonts/Perpetua-Titling-MT-Bold.ttf");

	public static final String NOMBRE_PERPETUA_BOLD = "Perpetua Negrita";
	public static final String NOMBRE_PERPETUA_BOLD_ITALIC = "Perpetua Negrita Cursiva";
	public static final String NOMBRE_PERPETUA_TITLING_MT_BOLD = "Perpetua Titling MT Negrita";

	private static final String PATH_BD = "BaseDatos.db";

	// ---------------------------------------------------------------------------------------------------------------------
	static {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, TTF_PERPETUA_BOLD).deriveFont(Font.BOLD));

			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, TTF_PERPETUA_BOLD_ITALIC).deriveFont(Font.ITALIC));

			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, TTF_PERPETUA_TITLING_MT_BOLD).deriveFont(Font.BOLD));
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	// -------------------------------------------------------------------------------------------------------------------------

	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	public static void haceCosas() {
//		Connection conn = null;
//
//		try {
//
//			conn = DriverManager.getConnection("jdbc:sqlite:" + PATH_BD);
//			Statement stmt = conn.createStatement();
//
//			conn.setAutoCommit(false);
//
//			TreeSet<String> lista = readListaLeyendas();
//
//			for (String lineaLey : lista) {
//				int finNombre = lineaLey.indexOf(STR_SEPARATOR, 0);
//				String nombre = lineaLey.substring(0, finNombre);
//
//				int finTipo1 = lineaLey.indexOf(STR_SEPARATOR, finNombre + 1);
//				String tipo1 = lineaLey.substring(finNombre + 1, finTipo1);
//
//				int finTipo2 = lineaLey.indexOf(STR_SEPARATOR, finTipo1 + 1);
//				String tipo2 = lineaLey.substring(finTipo1 + 1, finTipo2);
//
//				String query1 = "SELECT * FROM TIPO WHERE NOMBRE = '" + tipo1 + "'";
//				ResultSet rs1 = stmt.executeQuery(query1);
//				int codTipo1 = rs1.getInt("CODIGO");
//				rs1.close();
//				
//				
//				int codTipo2 = -1;
//				if (!NULL_STR.equals(tipo2)) {
//					String query2 = "SELECT * FROM TIPO WHERE NOMBRE = '" + tipo2 + "'";
//					ResultSet rs2 = stmt.executeQuery(query2);
//					codTipo2 = rs2.getInt("CODIGO");
//					rs2.close();
//				}
//
//				String query3 = "SELECT * FROM ESPECIE WHERE NOMBRE = '" + nombre + "'";
//				ResultSet rs3 = stmt.executeQuery(query3);
//				int codEsp = rs3.getInt("CODIGO");
//				rs3.close();
//
//				String sql = "INSERT INTO ESPTIPO(COD_TIPO,COD_ESP) VALUES('" + codTipo1 + "','" + codEsp + "')";
//				stmt.executeUpdate(sql);
//				if (codTipo2 != -1) {
//					String sql2 = "INSERT INTO ESPTIPO(COD_TIPO,COD_ESP) VALUES('" + codTipo2 + "','" + codEsp + "')";
//					stmt.executeUpdate(sql2);
//				}
//
//			}
//
//			conn.commit();
//
//			stmt.close();
//		} catch (SQLException e) {
//			try {
//
//				conn.rollback(); // HECHA PA TRAS
//			} catch (SQLException excep) {
//			}
//		} finally {
//			try {
//				conn.setAutoCommit(true);
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//
//		try {
//			conn.close();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}

	/**
	 * Funcion que se encargara de leer la lista
	 * 
	 * @param f
	 * @return
	 */
	public static TreeSet<String> readLista(File f) {
		logger.log(Level.INFO, "inicio de lectura de fichero: " + f.getName());
		TreeSet<String> lista = new TreeSet<>();
		try {
			Scanner sc = new Scanner(f);
			while (sc.hasNextLine()) {
				String l = sc.nextLine();
				lista.add(l);
			}
			sc.close();
			logger.log(Level.FINE, "lectura de fichero: " + f.getName() + " exitosa");
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error en lectura de fichero: " + f.getName());
			e.printStackTrace();
		}

		return lista;
	}

	/**
	 * Funcion que se encargara de escribir en la lista
	 * 
	 * @param lista
	 * @param f
	 */
	public static void writeLista(TreeSet<String> lista, File f) {
		logger.log(Level.INFO, "inicio de escritura de fichero: " + f.getName());
		try {
			PrintWriter fs = new PrintWriter(new FileWriter(f));
			for (String s : lista)
				fs.println(s);
			fs.close();
			logger.log(Level.FINE, "escritura de fichero: " + f.getName() + " exitosa");
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error en escritura de fichero: " + f.getName());
			e.printStackTrace();
		}
	}

	/**
	 * Leera la lista de leyendas y te dara las leyendas
	 * 
	 * @return
	 */
	public static TreeSet<String> readListaLeyendas() {
		logger.log(Level.INFO, "inicio de lectura de fichero: " + FIC_LEYS.getName());
		TreeSet<String> lista = new TreeSet<>();

		Connection conn = null;

		try {

			conn = DriverManager.getConnection("jdbc:sqlite:" + PATH_BD);

			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM ESPECIE";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String nombre = rs.getString("NOMBRE");
				String desc = rs.getString("DESCRIPCION");
				int cod = rs.getInt("CODIGO");

				String sqlTipo = "SELECT * FROM ESPTIPO WHERE COD_ESP=" + cod + ";";

				Statement stmt2 = conn.createStatement();
				ResultSet rsTipo = stmt2.executeQuery(sqlTipo);

				int codTipo1 = -1;
				int codTipo2 = -1;
				while (rsTipo.next()) {
					if (codTipo1 == -1) {
						codTipo1 = rsTipo.getInt("COD_TIPO");
					}
					else {
						int codigito = rsTipo.getInt("COD_TIPO");
						if (codigito!=codTipo1) {
							codTipo2=codigito;
						}
					}
				}

				rsTipo.close();
				stmt2.close();

				Statement stmt3 = conn.createStatement();
				ResultSet rsTipo1 = stmt3.executeQuery("SELECT * FROM TIPO WHERE CODIGO=" + codTipo1);
				String tipo1 = rsTipo1.getString("NOMBRE");
				rsTipo1.close();
				stmt3.close();

				String tipo2 = NULL_STR;
				if (codTipo2 != -1) {
					Statement stmt4 = conn.createStatement();
					ResultSet rsTipo2 = stmt4.executeQuery("SELECT * FROM TIPO WHERE CODIGO=" + codTipo2);
					tipo2 = rsTipo2.getString("NOMBRE");
					rsTipo2.close();
					stmt4.close();
				}

				lista.add(nombre + STR_SEPARATOR + tipo1 + STR_SEPARATOR + tipo2 + STR_SEPARATOR + desc);

			}
			rs.close();

			stmt.close();
		} catch (SQLException e) {
		}

		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(lista);

		return lista;
	}

	/**
	 * Leera la lista de habilidades y te dara las habilidades
	 * 
	 * @return
	 */
	public static TreeSet<String> readListaHabilidades() {
		return readLista(FIC_HABS);
	}

	/**
	 * Escribiras en la lista de leyendas
	 * 
	 * @param listaLeyendas
	 */
	public static void writeListaLeyendas(TreeSet<String> listaLeyendas) {
		writeLista(listaLeyendas, FIC_LEYS);
	}

	/**
	 * Escribiras en la lista de habilidades
	 * 
	 * @param listaHabs
	 */
	public static void writeListaHabilidades(TreeSet<String> listaHabs) {
		writeLista(listaHabs, FIC_HABS);
	}

	/**
	 * --METODO PROVISIONAL--Dejarlo para que compile pero falta hacer los
	 * constructores de verdad
	 * 
	 * @param tipo1
	 * @param tipo2
	 * @return
	 */
	public static Especie buscarEspecieEnBD(Tipo tipo1, Tipo tipo2) {
		// Se transforma el treeset en una arraylist y se mezcla para mejorar la
		// eficiencia y ya que no siempre se tardara lo mismo en caso de que se necesite
		// una especie muy al final del fichero, ademas da la opcionde que si hay dos
		// especies con los mismos tipos no siempre salga la primera que tenga esos
		// tipos
		ArrayList<String> lista = new ArrayList<>(readListaLeyendas());
		Collections.shuffle(lista);

		String t1 = tipo1.toString();
		// si el segundo tipo es nulo
		String t2 = NULL_STR;
		if (tipo2 != null)
			t2 = tipo2.toString();
		// Se inicializa la especie como nula
		Especie esp = null;
		for (String l : lista) {

			int a = l.indexOf(STR_SEPARATOR);
			String nombre = l.substring(0, a);

			int b = l.indexOf(STR_SEPARATOR, a + 1);
			String readt1 = l.substring(a + 1, b);

			int c = l.indexOf(STR_SEPARATOR, b + 1);
			String readt2 = l.substring(b + 1, c);

			String desc = l.substring(c + 1);

			if (t1.equals(readt1) && t2.equals(readt2)) {
				if (t2.equals(NULL_STR)) {
					tipo2 = null;
				}
				Tipo[] tipos = { tipo1, tipo2 };
				esp = new Especie(nombre, desc, tipos);
				break;
			}
		}
		return esp;

	}

	/**
	 * Metodo que buscara la habilidad en la base de datos
	 * 
	 * @param tipo
	 * @return
	 */
	public static Habilidad buscarHabilidadEnBD(Tipo tipo) {
		// Se transforma el treeset en una arraylist y se mezcla para mejorar la
		// eficiencia y ya que no siempre se tardara lo mismo en caso de que se necesite
		// una habilidad muy al final del fichero, ademas da la opcion de que si hay dos
		// habilidades con el mismo tipo no siempre salga la primera que tenga ese
		// tipo
		ArrayList<String> listaHabs = new ArrayList<String>(readListaHabilidades());
		Habilidad h = null;
		if (tipo != null) {
			String tipoStr = tipo.toString();
			for (String l : listaHabs) {

				int a = l.indexOf(STR_SEPARATOR);
				String nombre = l.substring(0, a);

				int b = l.indexOf(STR_SEPARATOR, a + 1);
				String tipoRead = l.substring(a + 1, b);

				int c = l.indexOf(STR_SEPARATOR, b + 1);
				String pot = l.substring(b + 1, c);

				int d = l.indexOf(STR_SEPARATOR, c + 1);
				String prec = l.substring(c + 1, d);

				String desc = l.substring(d + 1);

				if (tipoStr.equals(tipoRead)) {
					h = new Habilidad(nombre, desc, tipo, Integer.parseInt(pot), Double.parseDouble(prec));
				}

			}
		}
		return h;
	}

}
