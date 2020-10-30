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
			logger.addHandler(new FileHandler("src/logs/"+GestorDeDatos.class.getName() + ".log.xml", ANYADIR_A_FIC_LOG));
		} catch (SecurityException | IOException e) {
			logger.log(Level.SEVERE, "Error en creacion fichero log");
		}
	}
	
	private static final File FIC_HABS = new File("src/soloDesarrollo/ficheros/habilidades.txt");
	private static final File FIC_LEYS = new File("src/soloDesarrollo/ficheros/personajes.txt");

	public static TreeSet<String> readLista(File f) {
		logger.log(Level.INFO, "inicio de lectura de fichero: "+f.getName());
		TreeSet<String> lista = new TreeSet<>();
		try {
			Scanner sc = new Scanner(f);
			while (sc.hasNextLine()) {
				String l = sc.nextLine();
				lista.add(l);
			}
			sc.close();
			logger.log(Level.FINE, "lectura de fichero: "+f.getName()+ " exitosa");
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error en lectura de fichero: "+f.getName());
			e.printStackTrace();
		}
		return lista;
	}

	public static void writeLista(TreeSet<String> lista, File f) {
		logger.log(Level.INFO, "inicio de escritura de fichero: "+f.getName());
		try {
			PrintWriter fs = new PrintWriter(new FileWriter(f));
			for (String s : lista)
				fs.println(s);
			fs.close();
			logger.log(Level.FINE, "escritura de fichero: "+f.getName()+ " exitosa");
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error en escritura de fichero: "+f.getName());
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

}
