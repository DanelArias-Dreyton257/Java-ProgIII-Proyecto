package objetos;

import java.util.ArrayList;
import java.util.Random;


import personaje.Leyenda;
import personaje.atributos.Tipo;

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

	/**
	 * Crea un jugador con el equipo y la eternidad vacia
	 * 
	 * @param nombre
	 */
	public Jugador(String nombre) {
		setNombre(nombre);
	}

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
	 * @throws ArrayIndexOutOfBoundsException
	 * @return
	 */
	public Leyenda getLeyendaEquipo(int id) throws ArrayIndexOutOfBoundsException {
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
	 * @throws ArrayIndexOutOfBoundsException
	 * @return
	 */
	public Leyenda getLeyendaEternidad(int id) throws ArrayIndexOutOfBoundsException {
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
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public void delLeyendaEquipo(int id) throws ArrayIndexOutOfBoundsException {
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
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public void delLeyendaEternidad(int id) throws ArrayIndexOutOfBoundsException {
		if (id >= 0 && id <= eternidad.size())
			eternidad.remove(id);
		else
			throw new ArrayIndexOutOfBoundsException(
					"Indice introducido:" + id + "deberia encontrarse entre" + 0 + " y " + eternidad.size());
	}

	/**
	 * Anyade un personaje al equipo del jugador en la posicion indicada como
	 * parametro
	 * 
	 * @param id
	 * @param leyenda
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public void anyadirAEquipo(int id, Leyenda leyenda) throws ArrayIndexOutOfBoundsException {
		if (id >= 0 && id <= NUM_PER)
			equipo[id] = leyenda;
		else
			throw new ArrayIndexOutOfBoundsException(
					"Indice introducido:" + id + "deberia encontrarse entre" + 0 + " y " + NUM_PER);
	}

	/**
	 * Anyade un personaje a la eternidad del jugador
	 * 
	 * @param leyenda
	 */
	public void anyadirAEternidad(Leyenda leyenda) {
		eternidad.add(leyenda);
	}

	/**
	 * Anyade un personaje al equipo del jugador y en caso de que el equipo este
	 * lleno lo anyade a la eternidad
	 * 
	 * @param Leyenda p
	 */
	public void anyadirNuevaLeyenda(Leyenda p) {
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

	/**
	 * Anyade todos los personajes al equipo del jugador y si se llena el equipo
	 * sigue anyadiendolos en la eternidad los anyade a la eternidad
	 * 
	 * 
	 * @param Leyenda p
	 */
	public void anyadirNuevasLeyendas(Leyenda... pers) {
		for (Leyenda l : pers)
			anyadirNuevaLeyenda(l);
	}

//	Metodo no creo necesario ya que la eternidad no tiene porque llevar un orden especifico y si fuera asi usariamos algun otro tipo de estructura de datos
//	/**
//	 * Metodo que intercambia las posiciones de dos leyendas dentro de la eternidad
//	 * @param i1
//	 * @param i2
//	 */
//	public void intercambiarEnEternidad(int i1, int i2) {
//		//TODO
//		
//	}

	/**
	 * Intercambia las posiciones de dos leyendas del equipo
	 * 
	 * @param i1
	 * @param i2
	 */
	public void intercambiarEnEquipo(int i1, int i2) {
		Leyenda l1 = getLeyendaEquipo(i1);
		Leyenda l2 = getLeyendaEquipo(i2);
		anyadirAEquipo(i1, l2);
		anyadirAEquipo(i2, l1);

	}

	/**
	 * Intercambia las posiciones de una leyenda de la eternidad con una del equipo
	 * 
	 * @param iEquipo
	 * @param iEternidad
	 */
	public void intercambiarEquipoEternidad(int iEquipo, int iEternidad) {
		Leyenda l1 = getLeyendaEquipo(iEquipo);
		Leyenda l2 = getLeyendaEternidad(iEternidad);
		anyadirAEquipo(iEquipo, l2);
		anyadirAEternidad(l1);
	}

	public void anyadirLeyendasRandom(int numLeyendas) {
		for (int i = 0; i < numLeyendas; i++) {
			Random r = new Random();
			int it1 = r.nextInt(Tipo.values().length);
			Random r1 = new Random();
			int it2 = r1.nextInt(Tipo.values().length);
			Tipo[] tipos = { Tipo.values()[it1], Tipo.values()[it2] };
			anyadirNuevaLeyenda(new Leyenda("Leyenda " + i, "", tipos));
		}
	}

}
