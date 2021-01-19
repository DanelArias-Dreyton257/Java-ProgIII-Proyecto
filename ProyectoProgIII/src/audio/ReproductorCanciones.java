package audio;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import audio.Cancion.NotViableFileException;
import audio.Cancion.SongException;

/**
 * 
 * @author danel
 *
 */
public class ReproductorCanciones {

	public static ArrayList<File> canciones = new ArrayList<>();
	public static ArrayList<Boolean> siBucle = new ArrayList<>();
	public static final String CLAVE_BUCLE = "-bucle-";
	public static final String[] nombres = { "house of hades", "will power", "phantom", "matsushita", "button", "coins",
			"summon", "slap", "final expense" };
	public static int cancionMenuP = -1;
	public static int cancionCombate = -1;
	public static int cancionValhalla = -1;
	public static int cancionMenuUsuarios = -1;
	public static int esClick = -1;
	public static int esTirarMonedas = -1;
	public static int esSummon = -1;
	public static int esGolpe = -1;
	public static int cancionCreditos = -1;

	private static Cancion cancionActual;
	private static int posActual = -1;

	public static void initCanciones(File directorio) throws FileNotDirectoryException {
		if (directorio.isDirectory()) {
			File[] fcAudio = directorio.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					return name.toLowerCase().endsWith(".wav") || name.toLowerCase().endsWith(".WAV");
				}
			});

			addFiles(fcAudio);

		} else
			throw new FileNotDirectoryException("El objeto file debe ser un directorio donde esten las canciones");
	}

	public static void initCanciones(ArrayList<File> canciones) {
		canciones.addAll(canciones);
		guardarIndicadores();
	}

	private static void addFiles(File... fs) {
		for (File f : fs) {
			canciones.add(f);
			siBucle.add(f.getName().toLowerCase().contains(CLAVE_BUCLE));
		}
		guardarIndicadores();
	}

	private static void guardarIndicadores() {
		for (int i = 0; i < canciones.size(); i++) {
			for (int j = 0; j < nombres.length; j++) {
				if (canciones.get(i).getName().toLowerCase().contains(nombres[j])) {
//					System.out.println(canciones.get(i).getName().toLowerCase() + " n:" + nombres[j] + " en " + j);

					if (j == 0)
						cancionMenuP = i;
					else if (j == 1)
						cancionCombate = i;
					else if (j == 2)
						cancionValhalla = i;
					else if (j == 3)
						cancionMenuUsuarios = i;
					else if (j == 4)
						esClick = i;
					else if (j == 5)
						esTirarMonedas = i;
					else if (j == 6)
						esSummon = i;
					else if (j == 7)
						esGolpe = i;
					else if (j == 8)
						cancionCreditos = i;

				}
			}
		}
//		System.out.println("menup" + cancionMenuP);
//		System.out.println("com" + cancionCombate);
//		System.out.println("val" + cancionValhalla);
//		System.out.println("menuUsu" + cancionMenuUsuarios);
//		System.out.println("click" + esClick);
//		System.out.println("tirar" + esTirarMonedas);
//		System.out.println("sum" + esSummon);
//		System.out.println("golpe" + es_golpe);
	}

	public static void reproducir(int posicion) throws SongException {
		File f;
		boolean b;
		try {
			f = canciones.get(posicion);
			b = siBucle.get(posicion);
			cancionActual = new Cancion(f, b);
			cancionActual.reproducir();
			posActual = posicion;
		} catch (IndexOutOfBoundsException | NotViableFileException e) {
			e.printStackTrace();
		}

	}

	public static void pausar() {
		cancionActual.pausar();
		cancionActual.finalizar();
	}
	
	public static void reproducirES(int posicion) throws SongException {
		File f;
		boolean b;
		try {
			f = canciones.get(posicion);
			b = siBucle.get(posicion);
			Cancion c = new Cancion(f, b);
			c.reproducir();
		} catch (IndexOutOfBoundsException | NotViableFileException e) {
			e.printStackTrace();
		}

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


	public static Cancion getCancionActual() {
		return cancionActual;
	}

	public static int getPosActual() {
		return posActual;
	} 
	

}
