import objetos.Jugador;
import visuales.MenuPrincipal;

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
		MenuPrincipal m = new MenuPrincipal(usuario);
		m.setVisible(true);
	}
}
