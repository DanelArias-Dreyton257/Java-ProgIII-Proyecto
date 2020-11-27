import javax.swing.JOptionPane;

import gestion.GestorDeDatos;
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
		int opcRandom = JOptionPane.showConfirmDialog(null, "Crear usuario random? *solo Desarrollo*", "Usuario Nuevo",
				JOptionPane.INFORMATION_MESSAGE);

		if (opcRandom == JOptionPane.YES_OPTION) {

			iniciar();

		} else if (opcRandom == JOptionPane.NO_OPTION) {

			Jugador cargado = GestorDeDatos.cargarJugadorFichero();

			if (cargado != null) {
				int opcCargar = JOptionPane.showConfirmDialog(null,
						"Usuario: " + cargado.getNombre() + " con " + cargado.getNumLeyendas()
								+ " leyendas, encontrado\n¿Quieres cargarlo?",
						"Usuario encontrado", JOptionPane.INFORMATION_MESSAGE);

				if (opcCargar == JOptionPane.NO_OPTION) {
					nuevoJugador();
				} else if (opcCargar == JOptionPane.YES_OPTION) {
					iniciar(cargado);
				} else if (opcCargar == JOptionPane.CANCEL_OPTION || opcCargar == JOptionPane.CLOSED_OPTION) {
					System.exit(0); //CIERRA LA MAQUINA VIRTUAL DE JAVA
				}
			}

			else {
				JOptionPane.showMessageDialog(null, "No se ha encontrado ningun usuario guardado", "No encontrado",
						JOptionPane.INFORMATION_MESSAGE);
				nuevoJugador();
			}

		} else if (opcRandom == JOptionPane.CANCEL_OPTION || opcRandom == JOptionPane.CLOSED_OPTION) {
			System.exit(0); //CIERRA LA MAQUINA VIRTUAL DE JAVA
		}
	}

	private static void iniciar(Jugador j) {
		VenMenuPrincipal m = new VenMenuPrincipal(j);
		m.setVisible(true);
	}

	private static void iniciar(String nombre) {
		iniciar(new Jugador(nombre));
	}

	private static void iniciar() {
		Jugador usuario = new Jugador("Simple alumno");
		usuario.anyadirLeyendasRandom(15);
		iniciar(usuario);
	}

	private static void nuevoJugador() {
		String s = JOptionPane.showInputDialog(null, "Introduce tu nombre de jugador", "Usuario Nuevo",
				JOptionPane.INFORMATION_MESSAGE);
		iniciar(s);
	}
}
