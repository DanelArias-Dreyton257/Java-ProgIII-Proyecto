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
public class GestorCanciones extends ArrayList<Cancion> {

	private static final long serialVersionUID = 1L;
	public static final String CLAVE_BUCLE = "-bucle-";
	public static final String[] nombres = {};
	/**
	 * Anyade las canciones que se encuentren en el directorio enviado como parametro
	 * @param directorio
	 * @throws FileNotDirectoryException
	 * @throws SongException
	 */
	public GestorCanciones(File directorio) throws FileNotDirectoryException, SongException {
		if (directorio.isDirectory()) {
			File[] fcAudio = directorio.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					return name.toLowerCase().endsWith(".wav") || name.toLowerCase().endsWith(".WAV");
				}
			});

			try {
				this.addFiles(fcAudio);
			} catch (NotViableFileException e) {
				e.printStackTrace();
			}
		} else
			throw new FileNotDirectoryException("El objeto file debe ser un directorio donde esten las canciones");
	}
	/**
	 * Anyade las canciones segun la ArrayList
	 * @param canciones
	 */
	public GestorCanciones(ArrayList<Cancion> canciones) {
		addAll(canciones);
		guardarIndicadores();
	}

	private void addFiles(File... fs) throws SongException, NotViableFileException {
		for (File f : fs) {
			add(prepararSiBucle(f));
		}
		guardarIndicadores();
	}

	private Cancion prepararSiBucle(File f) throws SongException, NotViableFileException {
		if (f.getName().toLowerCase().contains(CLAVE_BUCLE)) {
			return new Cancion(f, true);
		}
		else return new Cancion(f);
	}
	private void guardarIndicadores() {
		for (int i = 0; i<this.size(); i++) {
			for (int j = 0; j<nombres.length; j++) {}
		}
	}

	/**
	 * 
	 * @author danel
	 *
	 */
	public class FileNotDirectoryException extends Exception {

		private static final long serialVersionUID = 1L;

		public FileNotDirectoryException(String msg) {
			super(msg);
		}

		public FileNotDirectoryException(String msg, Throwable t) {
			super(msg, t);
		}

	}
}
