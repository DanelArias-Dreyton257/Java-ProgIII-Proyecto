package gestion;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
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

	public static final String STR_SEPARATOR = "\t";
	public static final String NULL_STR = "NULL";

	private static final File TTF_PERPETUA_BOLD = new File("src/fonts/Perpetua-Bold.ttf");
	private static final File TTF_PERPETUA_BOLD_ITALIC = new File("src/fonts/Perpetua-Bold-Italic.ttf");
	private static final File TTF_PERPETUA_TITLING_MT_BOLD = new File("src/fonts/Perpetua-Titling-MT-Bold.ttf");

	private static final File JUGADORES_FICH = new File("src/saves/Jugadores.dat");

	public static final String NOMBRE_PERPETUA_BOLD = "Perpetua Negrita";
	public static final String NOMBRE_PERPETUA_BOLD_ITALIC = "Perpetua Negrita Cursiva";
	public static final String NOMBRE_PERPETUA_TITLING_MT_BOLD = "Perpetua Titling MT Negrita";

	private static final String PATH_BD = "BaseDatos.db";

	// ---------------------------------------------------------------------------------------------------------------------
	// insertamos los tipos de letra
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
	// agregamos el sqlite
	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Busca en la base de datos y devuelve una especie que sea de los tipos
	 * indicados como parametros
	 * 
	 * @param tipo1
	 * @param tipo2
	 * @return
	 */
	public static Especie buscarEspecieEnBD(Tipo tipo1, Tipo tipo2) {

		String t1Str = tipo1.toString();
		// si el segundo tipo es nulo
		String t2Str = NULL_STR;
		if (tipo2 != null)
			t2Str = tipo2.toString();

		// Se inicializa la especie como nula
		Especie esp = null;

		Connection conn = null;

		try {

			conn = DriverManager.getConnection("jdbc:sqlite:" + PATH_BD);

			// Buscar los codigos de los tipos

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT COD_ESP FROM ESPTIPO,TIPO WHERE TIPO.NOMBRE='" + t1Str
					+ "' AND ESPTIPO.COD_TIPO = TIPO.CODIGO");
			ArrayList<Integer> codEsp1 = new ArrayList<>();
			while (rs.next()) {
				codEsp1.add(rs.getInt("COD_ESP"));
			}

			rs.close();
			stmt.close();

			ArrayList<Integer> codigosElegibles = new ArrayList<>();

			if (tipo2 != null) {
				Statement stmt2 = conn.createStatement();
				ResultSet rs2 = stmt2.executeQuery("SELECT COD_ESP FROM ESPTIPO,TIPO WHERE TIPO.NOMBRE='" + t2Str
						+ "' AND ESPTIPO.COD_TIPO = TIPO.CODIGO");

				ArrayList<Integer> codEsp2 = new ArrayList<>();
				while (rs2.next()) {
					codEsp2.add(rs2.getInt("COD_ESP"));
				}

				rs2.close();
				stmt2.close();

				// Solo coger los que coincidan
				for (int n : codEsp1) {
					for (int m : codEsp2) {
						if (n == m) {
							codigosElegibles.add(n);
						}
					}
				}
			} else {
				// comprobar cuales son de tipo unico
				for (int cod : codEsp1) {
					Statement s = conn.createStatement();
					ResultSet rst = s.executeQuery(
							"SELECT COUNT() FROM TIPO,ESPTIPO,ESPECIE WHERE TIPO.CODIGO=ESPTIPO.COD_TIPO AND ESPTIPO.COD_ESP=ESPECIE.CODIGO AND ESPECIE.CODIGO="
									+ cod + " AND TIPO.NOMBRE='" + t1Str + "'");
					if (rst.next()) {
						if (rst.getInt("COUNT()") < 2) {
							codigosElegibles.add(cod);
						}
					}
					rst.close();
					s.close();
				}
			}

			// Elegir un codigo random
			if (codigosElegibles.isEmpty())
				return null;
			else {
				Random r = new Random();
				int posEleg = r.nextInt(codigosElegibles.size());
				int codEleg = codigosElegibles.get(posEleg);

				Statement stmt3 = conn.createStatement();
				ResultSet rs3 = stmt3.executeQuery("SELECT * FROM ESPECIE WHERE CODIGO=" + codEleg);

				String nombre = rs3.getString("NOMBRE");
				String desc = rs3.getString("DESCRIPCION");
				Tipo[] tipos = { tipo1, tipo2 };

				rs3.close();
				stmt3.close();

				esp = new Especie(nombre, desc, tipos);
			}

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

	/**
	 * Busca en la base de datos y devuelve una habilidad que sea del tipo indicado
	 * como parametro
	 * 
	 * @param tipo
	 * @return
	 */
	public static Habilidad buscarHabilidadEnBD(Tipo tipo) {
		Habilidad h = null;
		Connection conn = null;

		try {

			conn = DriverManager.getConnection("jdbc:sqlite:" + PATH_BD);

			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT TIPO.NOMBRE AS TIPONOM, HABILIDAD.NOMBRE AS HABNOM, DESCRIPCION, POTENCIA, PRECISION FROM HABILIDAD,TIPO WHERE HABILIDAD.COD_TIPO=TIPO.CODIGO AND TIPONOM='"
							+ tipo.toString() + "'");

			ArrayList<Habilidad> habs = new ArrayList<>();

			while (rs.next()) {

				Tipo t = Tipo.getTipoPorNombre(rs.getString("TIPONOM"));

				habs.add(new Habilidad(rs.getString("HABNOM"), rs.getString("DESCRIPCION"), t, rs.getInt("POTENCIA"),
						rs.getDouble("PRECISION")));
			}

			rs.close();
			stmt.close();

			if (habs.isEmpty()) {
				return null;
			} else {
				Random r = new Random();
				int posEleg = r.nextInt(habs.size());
				return habs.get(posEleg);
			}

		} catch (SQLException e) {
		}

		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return h;
	}

	/**
	 * Busca en la base de datos todos los nombres de las especies
	 * 
	 * @return lista de nombres de las especies ordenada por orden alfabetico
	 */
	public static ArrayList<String> getNombresEspecies() {
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

	/**
	 * Busca en la base de datos y devuelve la especie que coincida con el nombre
	 * 
	 * @param nombre de la especie
	 * @return especie, si no se encuentra: null
	 */
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

	/**
	 * Introduce en la base de datos la especie pasada como parametro
	 * 
	 * @param especie a introducir
	 */
	public static void insertEspecieBD(Especie esp) {

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

	/**
	 * Busca en la base de datos todos los nombres de las habilidades
	 * 
	 * @return lista de nombres de las habilidades ordenada por orden alfabetico
	 */
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

	/**
	 * Busca en la base de datos y devuelve la habilidad que coincida con el nombre
	 * 
	 * @param nombre de la habilidad
	 * @return habilidad, si no se encuentra: null
	 */
	public static Habilidad getInfoHabilidad(String nombre) {
		Connection conn = null;
		Habilidad hab = null;

		try {

			conn = DriverManager.getConnection("jdbc:sqlite:" + PATH_BD);

			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT DESCRIPCION,POTENCIA,PRECISION,TIPO.NOMBRE AS TIPONOM FROM HABILIDAD,TIPO WHERE HABILIDAD.COD_TIPO = TIPO.CODIGO AND HABILIDAD.NOMBRE = '"
							+ nombre + "'");

			int pot = 0;
			double prec = 0;
			String desc = "";
			String tipoS = "";
			while (rs.next()) {
				desc = rs.getString("DESCRIPCION");

				pot = rs.getInt("POTENCIA");
				prec = rs.getDouble("PRECISION");
				tipoS = rs.getString("TIPONOM");
			}

			rs.close();
			stmt.close();

			Tipo tipoN = Tipo.getTipoPorNombre(tipoS);

			hab = new Habilidad(nombre, desc, tipoN, pot, prec);

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

	/**
	 * Introduce en la base de datos la habilidad pasada como parametro
	 * 
	 * @param habilidad a introducir
	 */
	public static void insertHabilidadBD(Habilidad hab) {

		Connection conn = null;

		try {

			conn = DriverManager.getConnection("jdbc:sqlite:" + PATH_BD);
			conn.setAutoCommit(false);
			// Insertar habilidad
			Statement stmt1 = conn.createStatement();

			ResultSet rs1 = stmt1
					.executeQuery("SELECT CODIGO FROM TIPO WHERE NOMBRE='" + hab.getTipo().toString() + "'");

			int codTipo = rs1.getInt("CODIGO");

			rs1.close();
			stmt1.close();

			Statement stmt = conn.createStatement();

			stmt.executeUpdate("INSERT INTO HABILIDAD(NOMBRE,POTENCIA,PRECISION,DESCRIPCION,COD_TIPO) VALUES('"
					+ hab.getNombre() + "','" + hab.getPotencia() + "','" + hab.getPrecision() + "','"
					+ hab.getDescripcion() + "'," + codTipo + ")");

			stmt.close();

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
 * Funcion que guarda un jugador en el fichero
 * @param jug
 */
	public static void guardarJugadoresFichero(GestorJugadores jug) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(JUGADORES_FICH));
			oos.writeObject(jug);
			oos.close();
			logger.log(Level.INFO, "Fichero de jugadores guardado");
		} catch (IOException e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, "Fallo al cargar");
		}
	}

	/**
	 * Clase que debuelve un jugador del fichero
	 * @return
	 */
	public static GestorJugadores cargarJugadoresFichero() {
		GestorJugadores leido = null;
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(JUGADORES_FICH));
			leido = (GestorJugadores) ois.readObject();
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return leido;
	}

	/**
	 * Funcion que te devuelve un contricante aleatorio
	 * @return
	 */
	public static String selectRandContrincante() { //TODO
		return "Andoni El Destructor de Mundos";
	}

}
