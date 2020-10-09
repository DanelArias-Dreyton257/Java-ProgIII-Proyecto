package objetos;

import java.util.ArrayList;

import personaje.Leyenda;

/**
 * 
 * @author danel y jon ander
 *
 */
public class Jugador {
	private static final int NUM_PER = 6;
	private String nombre;
	private Leyenda[] equipo = new Leyenda[NUM_PER]; // donde se guardan los personajes del equipo
	private ArrayList<Leyenda> eternidad = new ArrayList<>(); // donde se guardan todos los personajes no usados en el
																// equipo

	/**
	 * Devuelve el nombre del jugador
	 * 
	 * @return
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del jugador
	 * 
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Devuelve la array de personajes del equipo del equipo
	 * 
	 * @return
	 */
	public Leyenda[] getEquipo() {
		return equipo;
	}

	/**
	 * Devuelve el personaje del equipo indicado con el indice como parametro, si no
	 * se encuentra personaje devuelve null
	 * 
	 * @param id Indice
	 * @return
	 */
	public Leyenda getPersonajeEquipo(int id) throws ArrayIndexOutOfBoundsException {
		if (id >= 0 && id <= NUM_PER)
			return equipo[id];
		else
			throw new ArrayIndexOutOfBoundsException(
					"Indice introducido:" + id + "deberia encontrarse entre" + 0 + " y " + NUM_PER);
	}

	/**
	 * Devuelve el personaje de la eternidad indicado con el indice como parametro,
	 * si no se encuentra personaje devuelve null
	 * 
	 * @param id Indice
	 * @return
	 */
	public Leyenda getPersonajeEternidad(int id) throws ArrayIndexOutOfBoundsException {
		if (id >= 0 && id <= eternidad.size())
			return eternidad.get(id);
		else
			throw new ArrayIndexOutOfBoundsException(
					"Indice introducido:" + id + "deberia encontrarse entre" + 0 + " y " + eternidad.size());
	}

	/**
	 * Elimina el personaje del equipo indicado con el indice como parametro,
	 * 
	 * @param id Indice
	 */
	public void delPersonajeEquipo(int id) throws ArrayIndexOutOfBoundsException {
		if (id >= 0 && id <= NUM_PER)
			equipo[id] = null;
		else
			throw new ArrayIndexOutOfBoundsException(
					"Indice introducido:" + id + "deberia encontrarse entre" + 0 + " y " + NUM_PER);
	}

	/**
	 * Elimina el personaje de la eternidad indicado con el indice como parametro,
	 * 
	 * @param id Indice
	 */
	public void delPersonajeEternidad(int id) throws ArrayIndexOutOfBoundsException {
		if (id >= 0 && id <= eternidad.size())
			eternidad.remove(id);
		else
			throw new ArrayIndexOutOfBoundsException(
					"Indice introducido:" + id + "deberia encontrarse entre" + 0 + " y " + eternidad.size());
	}

	/**
	 * Anyade un personaje al equipo del jugador y en caso de que el equipo este
	 * lleno lo anyade a la eternidad
	 * 
	 * @param Leyenda p
	 */
	public void anyadirNuevoPersonaje(Leyenda p) {
		int indHueco = -1;
		// Compruebo si hay un hueco libre en el equipo
		for (int i = 0; i < NUM_PER; i++)
			if (equipo[i] == null) {
				indHueco = i;
				break;
			}
		// Si hay hueco libre lo anyade al equipo y sino a la eternidad
		if (indHueco != -1)
			equipo[indHueco] = p;
		else
			eternidad.add(p);

		// TODO FALTARIA COMPROBAR SI ESE PERSONAJE YA ESTABA O YA ESTA ANYADIDO ??
	}

}
