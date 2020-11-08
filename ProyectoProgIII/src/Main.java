import javax.swing.JOptionPane;

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
		int opc = JOptionPane.showConfirmDialog(null, "Crear usuario random? *solo Desarrollo*", "Usuario Nuevo",
				JOptionPane.INFORMATION_MESSAGE);
		
		Jugador usuario = null;
		if (opc == JOptionPane.YES_OPTION) {
			usuario = new Jugador("Simple alumno");
			usuario.anyadirLeyendasRandom(15);
			VenMenuPrincipal m = new VenMenuPrincipal(usuario);
			m.setVisible(true);
		}
		else if (opc == JOptionPane.NO_OPTION) {
			String s = JOptionPane.showInputDialog(null,"Introduce tu nombre de jugador","Usuario Nuevo",JOptionPane.INFORMATION_MESSAGE);
			usuario = new Jugador(s);
			VenMenuPrincipal m = new VenMenuPrincipal(usuario);
			m.setVisible(true);
		}
		else if (opc == JOptionPane.CANCEL_OPTION || opc == JOptionPane.CLOSED_OPTION) {
			
		}
	}
}
