package gestion;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import audio.Cancion;
import audio.Cancion.NotViableFileException;
import audio.Cancion.SongException;

/**
 * 
 * @author danel
 *
 */
public class GestorCanciones {

	public static ArrayList<Cancion> canciones = new ArrayList<>();
	public static final String CLAVE_BUCLE = "-bucle-";
	public static final String[] nombres = { "final expense", "will power", "phantom", "matsushita", "button", "coins",
			"summon", "slap" };
	public static int CANCION_MENU_P = 0; // 0
	public static int CANCION_COMBATE = 0; // 1
	public static int CANCION_VALHALLA = 0; // 2
	public static int CANCION_MENU_USUARIOS = 0; // 3
	public static int ES_CLICK = 0; // 4
	public static int ES_TIRAR_MONEDAS = 0; // 5
	public static int ES_SUMMON = 0; // 6
	public static int ES_GOLPE = 0; // 7

	public static void initCanciones(File directorio) throws FileNotDirectoryException, SongException {
		if (directorio.isDirectory()) {
			File[] fcAudio = directorio.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					return name.toLowerCase().endsWith(".wav") || name.toLowerCase().endsWith(".WAV");
				}
			});

			try {
				addFiles(fcAudio);
			} catch (NotViableFileException e) {
				e.printStackTrace();
			}
		} else
			throw new FileNotDirectoryException("El objeto file debe ser un directorio donde esten las canciones");
	}

	public static void initCanciones(ArrayList<Cancion> canciones) {
		canciones.addAll(canciones);
		guardarIndicadores();
	}

	private static void addFiles(File... fs) throws SongException, NotViableFileException {
		for (File f : fs) {
			canciones.add(prepararSiBucle(f));
		}
		guardarIndicadores();
	}

	private static Cancion prepararSiBucle(File f) throws SongException, NotViableFileException {
		if (f.getName().toLowerCase().contains(CLAVE_BUCLE)) {
			return new Cancion(f, true);
		} else
			return new Cancion(f);
	}

	private static void guardarIndicadores() {
		for (int i = 0; i < canciones.size(); i++) {
			for (int j = 0; j < nombres.length; j++) {
				if (canciones.get(i).getNombre().contains(nombres[j])) {
					switch (j) {
					case 0:
						CANCION_MENU_P = i;
					case 1:
						CANCION_COMBATE = i;
					case 2:
						CANCION_VALHALLA = i;
					case 3:
						CANCION_MENU_USUARIOS = i;
					case 4:
						ES_CLICK = i;
					case 5:
						ES_TIRAR_MONEDAS = i;
					case 6:
						ES_SUMMON = i;
					case 7:
						ES_GOLPE = i;
					default:
						break;
					}
				}
			}
		}
	}

	public static ArrayList<Cancion> getCanciones() {
		return canciones;
	}

	/**
	 * 
	 * @author danel
	 *
	 */
	public static class FileNotDirectoryException extends Exception {

		private static final long serialVersionUID = 1L;

		public FileNotDirectoryException(String msg) {
			super(msg);
		}

		public FileNotDirectoryException(String msg, Throwable t) {
			super(msg, t);
		}

	}
}
