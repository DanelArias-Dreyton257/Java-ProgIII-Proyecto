
import java.io.File;

import audio.ReproductorCanciones;
import audio.ReproductorCanciones.FileNotDirectoryException;
import gestion.GestorConfiguracion;
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
			ReproductorCanciones.initCanciones(new File(GestorConfiguracion.getValue(GestorConfiguracion.PATH_DIR_CANCIONES)));
		} catch (FileNotDirectoryException e) {
			e.printStackTrace();
		}
		VenGestorJugadores v = new VenGestorJugadores();
		v.setVisible(true);
		
	}
}
