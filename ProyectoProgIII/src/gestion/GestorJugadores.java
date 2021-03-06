package gestion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import objetosCombate.Jugador;

public class GestorJugadores implements Serializable {

	private static final long serialVersionUID = 2L;
	private ArrayList<Jugador> jugadores = new ArrayList<>();
	private HashMap<String, String> credenciales = new HashMap<>();

	public GestorJugadores() {
	}

	/**
	 * se usa para añadir un jugador con su contraseña
	 * 
	 * @param j
	 * @param contrasena
	 */
	public void anyadirJugador(Jugador j, String contrasena) {
		while (credenciales.containsKey(j.getNombre())) {
			j.setNombre(nextNombre(j.getNombre()));
		}
		credenciales.put(j.getNombre(), contrasena);
		jugadores.add(j);

	}

	/**
	 * Esta funcion se usa para que al crear un nuebo usuario este como se llama
	 * simple alumno, el siguiente se llamara simple alumno 1, y el siguiente simple
	 * alumno 2 etc
	 * 
	 * @param previous
	 * @return
	 */
	private String nextNombre(String previous) {
		String dev = "";
		try {
			String ult = previous.substring(previous.length() - 1, previous.length());
			int num = Integer.parseInt(ult);
			num++;
			String ant = previous.substring(0, previous.length() - 1);
			dev = ant + num;
		} catch (Exception e) {
			dev = previous + " 1";
		}
		return dev;
	}

	/**
	 * devuelve todos los nombres de los jugadores
	 * 
	 * @return
	 */
	public String[] getNombresJugadores() {
		try {
			String[] strs = new String[jugadores.size()];
			int i = 0;
			for (Jugador j : jugadores) {
				strs[i] = j.getNombre();
				i++;
			}
			return strs;
		} catch (NegativeArraySizeException e) {
			return null;
		}
	}

	/**
	 * funcion que hace que puedas obtener un jugador de el mapa introduciendo el
	 * nombre y la contraseña
	 * 
	 * @param nombre
	 * @param contrasena
	 * @return
	 */
	public Jugador getJugador(String nombre, String contrasena) {
		Jugador j = null;
		if (credenciales.containsKey(nombre)) {

			if (credenciales.get(nombre).equals(contrasena)) {

				j = getJugadorPorNombre(nombre);
			}
		}
		return j;
	}

	/**
	 * Obtiene el jugador de el mapa introduciendo el nombre
	 * 
	 * @param nombre
	 * @return
	 */
	private Jugador getJugadorPorNombre(String nombre) {
		return getJugadorPorNombre(nombre, 0);
	}

	/**
	 * esta funcion debuelve el jugador por nombre
	 * 
	 * @param nombre
	 * @param pos
	 * @return
	 */
	private Jugador getJugadorPorNombre(String nombre, int pos) {
		if (pos >= jugadores.size() || pos < 0) {
			return null;
		} else if (jugadores.get(pos).getNombre().equals(nombre)) {
			return jugadores.get(pos);
		} else {
			return getJugadorPorNombre(nombre, pos + 1);
		}
	}

	/**
	 * esta funcion permite cambiar de jugador llamando a otra funcion
	 * 
	 * @param j
	 */
	public void replaceJugador(Jugador j) {
		replaceJugador(j, 0);
	}

	/**
	 * Esta funcion permite cambiar de jugador
	 * 
	 * @param j
	 * @param pos
	 */
	private void replaceJugador(Jugador j, int pos) {
		if (pos >= jugadores.size() || pos < 0) {
			return;
		} else if (jugadores.get(pos).getNombre().equals(j.getNombre())) {
			jugadores.remove(pos);
			jugadores.add(pos, j);
			return;
		} else {
			replaceJugador(j, pos + 1);
			return;
		}

	}

	/**
	 * Funcion que sirve para borrar el jugador de el arraylist
	 * 
	 * @param nombre
	 */
	public void deleteJugador(String nombre) {
		credenciales.remove(nombre);
		for (int i = 0; i < jugadores.size(); i++) {
			if (jugadores.get(i).getNombre().equalsIgnoreCase(nombre)) {
				jugadores.remove(i);
			}
		}
	}

	/**
	 * devuelve el arraylist de los jugadores
	 * 
	 * @return
	 */
	public ArrayList<Jugador> getJugadores() {
		return jugadores;
	}

}
