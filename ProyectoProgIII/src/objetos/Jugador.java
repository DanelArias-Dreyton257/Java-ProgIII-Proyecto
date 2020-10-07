package objetos;

import java.util.ArrayList;

import javax.jws.soap.InitParam;

import personaje.Personaje;

/**
 * 
 * @author danel y jon ander
 *
 */
public class Jugador {
	private static final int NUM_PER = 6;
	private String nombre;
	private Personaje[] equipo = new Personaje[NUM_PER]; // donde se guardan los personajes del equipo
	private ArrayList<Personaje> eternidad = new ArrayList<>(); // donde se guardan todos los personajes no usados en el
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
	public Personaje[] getEquipo() {
		return equipo;
	}

	/**
	 * Devuelve el personaje del equipo indicado con el indice como parametro, si no
	 * se encuentra personaje devuelve null
	 * 
	 * @param id Indice
	 * @return
	 */
	public Personaje getPersonajeEquipo(int id) {
		if (id >= 0 && id <= NUM_PER) // TODO Excepcion?
			return equipo[id];
		else
			return null;
	}

	/**
	 * Devuelve el personaje de la eternidad indicado con el indice como parametro,
	 * si no se encuentra personaje devuelve null
	 * 
	 * @param id Indice
	 * @return
	 */
	public Personaje getPersonajeEternidad(int id) {
		if (id >= 0 && id <= eternidad.size()) // TODO Excepcion?
			return eternidad.get(id);
		else
			return null;
	}

	/**
	 * Elimina el personaje del equipo indicado con el indice como parametro,
	 * 
	 * @param id Indice
	 */
	public void delPersonajeEquipo(int id) {
		if (id >= 0 && id <= NUM_PER) // TODO Excepcion?
			equipo[id] = null;
	}

	/**
	 * Elimina el personaje de la eternidad indicado con el indice como parametro,
	 * 
	 * @param id Indice
	 */
	public void delPersonajeEternidad(int id) {
		if (id >= 0 && id <= eternidad.size()) // TODO Excepcion?
			eternidad.remove(id);
	}

	/**
	 * Anyade un personaje al equipo del jugador y en caso de que el equipo este
	 * lleno lo anyade a la eternidad
	 * 
	 * @param Personaje p
	 */
	public void anyadirNuevoPersonaje(Personaje p) {
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

		// TODO FALTARIA COMPROBAR SI ESE PERSONAJE YA ESTABA O YA ESTA ANYADIDO
	}

	/**
	 * Fusiona dos personajes y devuelve el resultado como parametro
	 * 
	 * @param p1 personaje a fusionar 1
	 * @param p2 personaje a fusionar 2
	 * @return personaje fusionado
	 */
	public Personaje fusionar(Personaje p1, Personaje p2) {
		// TODO
		// Propongo moverlo a la clase personaje como metodo estatico de esa clase
		return null;
	}
}
