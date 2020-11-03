import objetos.Jugador;
import visuales.VenMenuPrincipal;

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
		Jugador usuario = new Jugador("Simple alumno");
		usuario.anyadirLeyendasRandom(30);
		VenMenuPrincipal m = new VenMenuPrincipal(usuario);
		m.setVisible(true);
	}
}
