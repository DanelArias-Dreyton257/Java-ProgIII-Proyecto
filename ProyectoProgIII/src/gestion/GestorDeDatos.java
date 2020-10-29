package gestion;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * 
 * @author danel y jon ander
 *
 */
public class GestorDeDatos {
	private static final File FIC_HABS = new File("src/soloDesarrollo/ficheros/habilidades.txt");
	private static final File FIC_LEYS = new File("src/soloDesarrollo/ficheros/personajes.txt");

	public static TreeSet<String> readLista(File f) {
		TreeSet<String> lista = new TreeSet<>();
		try {
			Scanner sc = new Scanner(f);
			while (sc.hasNextLine()) {
				String l = sc.nextLine();
				lista.add(l);
			}
			sc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	public static void writeLista(TreeSet<String> lista, File f) {
		try {
			PrintWriter fs = new PrintWriter(new FileWriter(f));
			for (String s : lista)
				fs.println(s);
			fs.close();
		} catch (IOException e) {
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
