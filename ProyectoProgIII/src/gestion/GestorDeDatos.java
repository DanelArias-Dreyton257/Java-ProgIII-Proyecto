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
					} else {
						int codigito = rsTipo.getInt("COD_TIPO");
						if (codigito != codTipo1) {
							codTipo2 = codigito;
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
		logger.log(Level.INFO, "inicio de lectura de fichero: " + FIC_HABS.getName());
		TreeSet<String> listaHabs = new TreeSet<>();

		Connection conn = null;

		try {

			conn = DriverManager.getConnection("jdbc:sqlite:" + PATH_BD);

			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM HABILIDAD";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String nombre = rs.getString("NOMBRE");
				int potencia = rs.getInt("POTENCIA");
				double precision = rs.getDouble("PRECISION");
				String descripcion = rs.getString("DESCRIPCION");
				int cod = rs.getInt("CODIGO");

				String sqlTipo = "SELECT * FROM HABTIPO WHERE COD_HAB=" + cod + ";";

				Statement stmt2 = conn.createStatement();
				ResultSet rsTipo = stmt2.executeQuery(sqlTipo);

				int codTipo1 = -1;
				
				while (rsTipo.next()) {
					if (codTipo1 == -1) {
						codTipo1 = rsTipo.getInt("COD_TIPO");
					} else {
						codTipo1 = rsTipo.getInt("COD_TIPO");
						
					}
				}

				rsTipo.close();
				stmt2.close();

				Statement stmt3 = conn.createStatement();
				ResultSet rsTipo1 = stmt3.executeQuery("SELECT * FROM TIPO WHERE CODIGO=" + codTipo1);
				String tipo1 = rsTipo1.getString("NOMBRE");
				rsTipo1.close();
				stmt3.close();

				listaHabs.add(nombre + STR_SEPARATOR + tipo1 + STR_SEPARATOR + potencia + STR_SEPARATOR + precision + STR_SEPARATOR + descripcion);

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

		System.out.println(listaHabs);

		return listaHabs;
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

//	/**
//	 * FALTA COMENTAR Y SIN TERMINAR
//	 * 
//	 * @param tipo1
//	 * @param tipo2
//	 * @return
//	 */
//	public static Especie buscarEspecieEnBD(Tipo tipo1, Tipo tipo2) {
//
//		String t1Str = tipo1.toString();
//		// si el segundo tipo es nulo
//		String t2Str = NULL_STR;
//		if (tipo2 != null)
//			t2Str = tipo2.toString();
//		// Se inicializa la especie como nula
//		Especie esp = null;
//
//		Connection conn = null;
//
//		try {
//
//			conn = DriverManager.getConnection("jdbc:sqlite:" + PATH_BD);
//			
//			//Buscar los codigos de los tipos
//			Statement stmt = conn.createStatement();
//			ResultSet rs = stmt.executeQuery("SELECT CODIGO FROM TIPO WHERE NOMBRE ='"+t1Str+"'");
//			int codTipo1 = rs.getInt("CODIGO");
//			rs.close();
//			stmt.close();
//			
//			int codTipo2=-1;
//			if (!NULL_STR.equals(t2Str)) {
//				Statement stmt1 = conn.createStatement();
//				ResultSet rs1 = stmt.executeQuery("SELECT CODIGO FROM TIPO WHERE NOMBRE ='"+t2Str+"'");
//				codTipo2 = rs1.getInt("CODIGO");
//				rs1.close();
//				stmt1.close();
//			}
//			
//			//Seleccionar el WHERE
//			String where = "WHERE COD_TIPO IN("+codTipo1;
//			if (!NULL_STR.equals(t2Str)) {
//				where +=","+codTipo2; 
//			}
//			where+=");";
//			
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			conn.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return esp;
//
//	}

	/**
	 * Metodo que buscara la habilidad en la base de datos
	 * 
	 * @param tipo
	 * @return
	 */
	public static Habilidad buscarHabilidadEnBD(Tipo tipo) { // FIXME implementarlo con BD
		// Se transforma el treeset en una arraylist y se mezcla para mejorar la
		// eficiencia y ya que no siempre se tardara lo mismo en caso de que se necesite
		// una habilidad muy al final del fichero, ademas da la opcion de que si hay dos
		// habilidades con el mismo tipo no siempre salga la primera que tenga ese
		// tipo
		ArrayList<String> listaHabs = new ArrayList<String>(readListaHabilidades());
		Collections.shuffle(listaHabs);
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

	public static ArrayList<String> getNombresEspecies() { // FUNCIONA GUAY :)
		Connection conn = null;
		ArrayList<String> lista = new ArrayList<>();

		try {

			conn = DriverManager.getConnection("jdbc:sqlite:" + PATH_BD);

			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT NOMBRE FROM ESPECIE ORDER BY NOMBRE");
			while (rs.next()) {
				lista.add(rs.getString("NOMBRE"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
		}

		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public static Especie getInfoEspecie(String nombre) { // FUNCIONA :)
		Connection conn = null;
		Especie esp = null;

		try {

			conn = DriverManager.getConnection("jdbc:sqlite:" + PATH_BD);

			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM ESPECIE WHERE NOMBRE = '" + nombre + "'");
			int cod = -1;
			String desc = "";

			while (rs.next()) {
				desc = rs.getString("DESCRIPCION");
				cod = rs.getInt("CODIGO");
			}
			rs.close();
			stmt.close();

			Statement stmt3 = conn.createStatement();
			ResultSet rs3 = stmt3.executeQuery(
					"SELECT COUNT() FROM ESPTIPO,TIPO WHERE COD_ESP=" + cod + " AND ESPTIPO.COD_TIPO = TIPO.CODIGO");
			int numTipos = rs3.getInt("COUNT()");

			rs3.close();
			stmt3.close();

			Statement stmt2 = conn.createStatement();
			ResultSet rs2 = stmt2.executeQuery(
					"SELECT * FROM ESPTIPO,TIPO WHERE COD_ESP=" + cod + " AND ESPTIPO.COD_TIPO = TIPO.CODIGO");

			Tipo[] tipos = new Tipo[2];
			for (int i = 0; i < numTipos; i++) {
				if (rs2.next()) {
					tipos[i] = Tipo.getTipoPorNombre(rs2.getString("NOMBRE"));
				}

			}

			rs2.close();
			stmt2.close();

			esp = new Especie(nombre, desc, tipos);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return esp;
	}

	public static void insertEspecieBD(Especie esp) { // FUNCIOOOOOOOONAAAAAAAA :D

		Connection conn = null;

		try {

			conn = DriverManager.getConnection("jdbc:sqlite:" + PATH_BD);
			conn.setAutoCommit(false);
			// Insertar especie
			Statement stmt = conn.createStatement();

			stmt.executeUpdate("INSERT INTO ESPECIE(NOMBRE,DESCRIPCION) VALUES('" + esp.getNombre() + "','"
					+ esp.getDescripcion() + "')");

			stmt.close();

			// Busqueda de cod de especie
			Statement stmt2 = conn.createStatement();

			ResultSet rs2 = stmt.executeQuery("SELECT CODIGO FROM ESPECIE WHERE NOMBRE = '" + esp.getNombre() + "'");

			int codigoEsp = rs2.getInt("CODIGO");

			rs2.close();
			stmt2.close();

			// Busqueda de cods de especie

			int codTipo1 = -1;
			int codTipo2 = -1;

			// Tipo1
			Statement stmt3 = conn.createStatement();

			ResultSet rs3 = stmt3
					.executeQuery("SELECT CODIGO FROM TIPO WHERE NOMBRE = '" + esp.getTipos()[0].toString() + "'");

			codTipo1 = rs3.getInt("CODIGO");

			rs3.close();
			stmt3.close();

			// Tipo 2
			if (esp.getTipos()[1] != null) {
				Statement stmt4 = conn.createStatement();

				ResultSet rs4 = stmt4
						.executeQuery("SELECT CODIGO FROM TIPO WHERE NOMBRE = '" + esp.getTipos()[1].toString() + "'");

				codTipo2 = rs4.getInt("CODIGO");

				rs4.close();
				stmt4.close();
			}

			// INSERTS en ESPTIPO
			Statement stmt5 = conn.createStatement();

			stmt5.executeUpdate("INSERT INTO ESPTIPO(COD_ESP,COD_TIPO) VALUES(" + codigoEsp + "," + codTipo1 + ")");
			stmt5.close();

			if (codTipo2 != -1) {
				Statement stmt6 = conn.createStatement();

				stmt6.executeUpdate("INSERT INTO ESPTIPO(COD_ESP,COD_TIPO) VALUES(" + codigoEsp + "," + codTipo2 + ")");
				stmt6.close();

			}

			conn.commit();

		} catch (SQLException e) {
			try {

				conn.rollback(); // HECHA PA TRAS
			} catch (SQLException excep) {
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static ArrayList<String> getNombresHabilidades() {
		Connection conn = null;
		ArrayList<String> listaHabs = new ArrayList<>();

		try {

			conn = DriverManager.getConnection("jdbc:sqlite:" + PATH_BD);

			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT NOMBRE FROM HABILIDAD ORDER BY NOMBRE");
			while (rs.next()) {
				listaHabs.add(rs.getString("NOMBRE"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
		}

		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listaHabs;
	
	}

	public static Habilidad getInfoHabilidad(String nombre) {
		Connection conn = null;
		Habilidad hab = null;

		try {

			conn = DriverManager.getConnection("jdbc:sqlite:" + PATH_BD);

			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM HABILIDAD WHERE NOMBRE = '" + nombre + "'");
			int cod = -1;
			int pot =0;
			double prec=0;
			String desc = "";

			while (rs.next()) {
				desc = rs.getString("DESCRIPCION");
				cod = rs.getInt("CODIGO");
				pot = rs.getInt("POTENCIA");
				prec = rs.getDouble("PRECISION");
			}
			rs.close();
			stmt.close();

			Statement stmt3 = conn.createStatement();
			ResultSet rs3 = stmt3.executeQuery(
					"SELECT COUNT() FROM HABTIPO,TIPO WHERE COD_HAB=" + cod + " AND HABTIPO.COD_TIPO = TIPO.CODIGO");
			int numTipos = rs3.getInt("COUNT()");

			rs3.close();
			stmt3.close();

			Statement stmt2 = conn.createStatement();
			ResultSet rs2 = stmt2.executeQuery(
					"SELECT * FROM HABTIPO,TIPO WHERE COD_HAB=" + cod + " AND HABTIPO.COD_TIPO = TIPO.CODIGO");

		
			Tipo tipos = null;
				if (rs2.next()) {
					
					 tipos = Tipo.getTipoPorNombre(rs2.getString("NOMBRE"));
				

			}

			rs2.close();
			stmt2.close();

			
			hab = new Habilidad(nombre, desc, tipos, pot, prec);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return hab;
	
	}

	public static void insertHabilidadBD(Habilidad hab) {

		Connection conn = null;

		try {

			conn = DriverManager.getConnection("jdbc:sqlite:" + PATH_BD);
			conn.setAutoCommit(false);
			// Insertar habilidad
			Statement stmt = conn.createStatement();

			stmt.executeUpdate("INSERT INTO HABILIDAD(NOMBRE,POTENCIA,PRECISION,DESCRIPCION) VALUES('" + hab.getNombre() + "','"
					+ hab.getPotencia() + "','"+ hab.getPrecision() + "','"+ hab.getDescripcion() + "')");

			stmt.close();

			// Busqueda de cod de habilidad
			Statement stmt2 = conn.createStatement();

			ResultSet rs2 = stmt.executeQuery("SELECT CODIGO FROM HABILIDAD WHERE NOMBRE = '" + hab.getNombre() + "'");

			int codigoHab = rs2.getInt("CODIGO");

			rs2.close();
			stmt2.close();

			// Busqueda de cods de especie

			int codTipo1 = -1;
			

			// Tipo1
			Statement stmt3 = conn.createStatement();

			ResultSet rs3 = stmt3
					.executeQuery("SELECT CODIGO FROM TIPO WHERE NOMBRE = '" + hab.getTipo().toString() + "'");

			codTipo1 = rs3.getInt("CODIGO");

			rs3.close();
			stmt3.close();

			

			// INSERTS en ESPTIPO
			Statement stmt5 = conn.createStatement();

			stmt5.executeUpdate("INSERT INTO ESPTIPO(COD_ESP,COD_TIPO) VALUES(" + codigoHab + "," + codTipo1 + ")");
			stmt5.close();


			conn.commit();

		} catch (SQLException e) {
			try {

				conn.rollback(); // HECHA PA TRAS
			} catch (SQLException excep) {
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	
	}

	/**
	 * COMENTADO PARA COMPROBAR
	 */
	/*
	 * public static Habilidad getInfoHabilidad(String nombre) { Connection conn =
	 * null; Habilidad hab = null;
	 * 
	 * try {
	 * 
	 * conn = DriverManager.getConnection("jdbc:sqlite:" + PATH_BD);
	 * 
	 * Statement stmt = conn.createStatement();
	 * 
	 * ResultSet rs = stmt.executeQuery("SELECT * FROM HABILIDAD WHERE NOMBRE = '" +
	 * hab.getNombre() + "'"); int cod = -1; int pot = 0; double pre = 0;
	 * 
	 * while (rs.next()) { pot = rs.getInt("POTENCIA"); pre =
	 * rs.getDouble("PRECISION"); cod = rs.getInt("CODIGO"); } rs.close();
	 * stmt.close();
	 * 
	 * Statement stmt2 = conn.createStatement(); ResultSet rs2 =
	 * stmt2.executeQuery("SELECT * FROM ESPTIPO WHERE COD_HAB=" + cod);
	 * 
	 * // Busqueda de codigos de tipo int codTipo = -1;
	 * 
	 * while (rs2.next()) { if (codTipo == -1) { codTipo = rs2.getInt("COD_TIPO"); }
	 * else { int codigito = rs2.getInt("COD_TIPO");
	 * 
	 * } } rs2.close(); stmt2.close();
	 * 
	 * // Busqueda de tipo 1 Statement stmt3 = conn.createStatement(); ResultSet
	 * rsTipo1 = stmt3.executeQuery("SELECT * FROM TIPO WHERE CODIGO=" + codTipo);
	 * String tipo1Str = rsTipo1.getString("NOMBRE"); rsTipo1.close();
	 * stmt3.close();
	 * 
	 * 
	 * 
	 * Tipo tipo1 = Tipo.getTipoPorNombre(tipo1Str);
	 * 
	 * 
	 * Tipo tipos = tipo1;
	 * 
	 * hab = new Habilidad(nombre, tipos, pot, pre);
	 * 
	 * } catch (SQLException e) { e.printStackTrace(); } try { conn.close(); } catch
	 * (SQLException e) { e.printStackTrace(); }
	 * 
	 * return hab; }
	 */

	/**
	 * COMENTADO PARA REVISAR
	 */
	/*
	 * public static void insertHabilidadBD(Habilidad hab) {
	 * 
	 * Connection conn = null;
	 * 
	 * try {
	 * 
	 * conn = DriverManager.getConnection("jdbc:sqlite:" + PATH_BD);
	 * conn.setAutoCommit(false); // Insertar especie Statement stmt =
	 * conn.createStatement();
	 * 
	 * stmt.
	 * executeUpdate("INSERT INTO HABILIDAD(NOMBRE,POTENCIA,PRECISION) VALUES('" +
	 * hab.getNombre() + "','" + hab.getPotencia() + "','" + hab.getPrecision() +
	 * "')");
	 * 
	 * stmt.close();
	 * 
	 * // Busqueda de cod de especie Statement stmt2 = conn.createStatement();
	 * 
	 * ResultSet rs2 =
	 * stmt.executeQuery("SELECT CODIGO FROM HABILIDAD WHERE NOMBRE = '" +
	 * hab.getNombre() + "'");
	 * 
	 * int codigoHab = rs2.getInt("CODIGO");
	 * 
	 * rs2.close(); stmt2.close();
	 * 
	 * // Busqueda de cods de especie
	 * 
	 * int codTipo = -1;
	 * 
	 * 
	 * //Tipo1 Statement stmt3 = conn.createStatement();
	 * 
	 * ResultSet rs3 = stmt3
	 * .executeQuery("SELECT CODIGO FROM TIPO WHERE NOMBRE = '" +
	 * hab.getTipo().toString() + "'");
	 * 
	 * codTipo = rs3.getInt("CODIGO");
	 * 
	 * rs3.close(); stmt3.close();
	 * 
	 * //INSERTS en ESPTIPO Statement stmt5 = conn.createStatement();
	 * 
	 * stmt5.executeUpdate("INSERT INTO HABTIPO(COD_HAB,COD_TIPO) VALUES("+codigoHab
	 * +","+codTipo+")"); stmt5.close(); conn.commit();
	 * 
	 * } catch (SQLException e) { try {
	 * 
	 * conn.rollback(); // HECHA PA TRAS } catch (SQLException excep) { } } finally
	 * { try { conn.setAutoCommit(true); } catch (SQLException e) {
	 * e.printStackTrace(); } }
	 * 
	 * try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
	 * 
	 * }
	 */

}
