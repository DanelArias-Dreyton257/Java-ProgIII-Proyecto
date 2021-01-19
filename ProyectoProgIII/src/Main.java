
import java.io.File;

import audio.Cancion.SongException;
import gestion.GestorCanciones;
import gestion.GestorCanciones.FileNotDirectoryException;
import visuales.VenGestorJugadores;

/**
 * 
 * @author danel y jon ander
 *
 */
public class Main {
	/**
	 * Main de prueba
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			GestorCanciones.initCanciones(new File("src/audio/files"));
		} catch (FileNotDirectoryException | SongException e) {
			e.printStackTrace();
		}
		VenGestorJugadores v = new VenGestorJugadores();
		v.setVisible(true);
		
	}
}
