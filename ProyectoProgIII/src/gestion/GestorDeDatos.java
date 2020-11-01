package gestion;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

	public static TreeSet<String> readListaLeyendas() {
		return readLista(FIC_LEYS);
	}

	public static TreeSet<String> readListaHabilidades() {
		return readLista(FIC_HABS);
	}

	public static void writeListaLeyendas(TreeSet<String> listaLeyendas) {
		writeLista(listaLeyendas, FIC_LEYS);
	}

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
		TreeSet<String> listaPer = readListaLeyendas();
		String t1 = tipo1.toString();
		String t2 = "NULL";
		if (tipo2 != null)
			t2 = tipo2.toString();
		Especie esp = null;
		for (String l : listaPer) {
			String t = "\t";

			int a = l.indexOf(t);
			String nombre = l.substring(0, a);

			int b = l.indexOf(t, a + 1);
			String readt1 = l.substring(a + 1, b);

			int c = l.indexOf(t, b + 1);
			String readt2 = l.substring(b + 1, c);

			String desc = l.substring(c + 1);

			if (t1.equals(readt1) && t2.equals(readt2)) {
				if (t2.equals("NULL")) {
					tipo2 = null;
				}
				Tipo[] tipos = { tipo1, tipo2 };
				esp = new Especie(nombre, desc, tipos);
				break;
			}
		}
		return esp;

	}

	public static Habilidad buscarHabilidadEnBD(Tipo tipo) {
		TreeSet<String> listaHabs = readListaHabilidades();
		Habilidad h = null;
		if (tipo!=null) {
			String tipoStr = tipo.toString();
			for (String l : listaHabs) {
				String t = "\t";

				int a = l.indexOf(t);
				String nombre = l.substring(0, a);

				int b = l.indexOf(t, a + 1);
				String tipoRead = l.substring(a + 1, b);

				int c = l.indexOf(t, b + 1);
				String pot = l.substring(b + 1, c);

				int d = l.indexOf(t, c + 1);
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
