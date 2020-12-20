package objetosCombate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import personaje.Leyenda;

/**
 * 
 * @author danel y jon ander
 *
 */
public class Jugador implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final int NUM_PER = 6;
	protected String nombre;
	protected Leyenda[] equipo = new Leyenda[NUM_PER]; // donde se guardan los personajes del equipo
	protected ArrayList<Leyenda> eternidad = new ArrayList<>(); // donde se guardan todos los personajes no usados en el
	protected int doblones = 0;
	protected double nvDificultad = 0;

	/**
	 * Crea un jugador con el equipo y la eternidad vacia
	 * 
	 * @param nombre
	 */
	public Jugador(String nombre) {
		setNombre(nombre);
		incDoblones(2000);
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
	 * Despues se reorganiza el equipo
	 * 
	 * @param id Indice
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public void delLeyendaEquipo(int id) throws ArrayIndexOutOfBoundsException {
		delLeyendaEquipo(id, true);
	}

	/**
	 * Elimina el personaje del equipo indicado con el indice como parametro,
	 * 
	 * @param id      Indice
	 * @param boolean que indica si se quiere reorganizar el equipo tras el borrado
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public void delLeyendaEquipo(int id, boolean reorganizar) throws ArrayIndexOutOfBoundsException {
		if (id >= 0 && id <= NUM_PER) {
			equipo[id] = null;
			if (reorganizar) {
				reorganizaEquipo();
			}
		} else
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
		if (leyenda != null) {
			eternidad.add(leyenda);
		}
	}

	/**
	 * Anyade un personaje al equipo del jugador y en caso de que el equipo este
	 * lleno lo anyade a la eternidad
	 * 
	 * @param Leyenda p
	 */
	public void anyadirNuevaLeyenda(Leyenda p) {
		int indHueco = -1;
		if (p != null) {
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
		delLeyendaEternidad(iEternidad);
		anyadirAEquipo(iEquipo, l2);
		anyadirAEternidad(l1);
	}

	/**
	 * Funcion que nos dara leyendas aleatoria de la base de datos
	 * 
	 * @param numLeyendas
	 */
	public void anyadirLeyendasRandom(int numLeyendas) {
		for (int i = 0; i < numLeyendas; i++) {
			anyadirNuevaLeyenda(Leyenda.getLeyendaRandom());
		}
	}

	public void anyadirLeyendasRandom(int numLeyendas, double dif) {
		for (int i = 0; i < numLeyendas; i++) {
			anyadirNuevaLeyenda(Leyenda.getLeyendaRandom(dif));
		}
	}

	/**
	 * Devuelve el numero de personajes no nulos del equipo
	 * 
	 * @return
	 */
	public int getNumLeyendasEnEquipo() {
		int cont = NUM_PER;
		for (Leyenda l : equipo) {
			if (l == null)
				cont--;
		}
		return cont;
	}

	/**
	 * Devuelve el numero de personajes en la eternidad
	 * 
	 * @return
	 */
	public int getNumLeyendasEnEternidad() {
		return eternidad.size();
	}

	/**
	 * Devuelve la ArrayList que representa la eternidad del jugador
	 * 
	 * @return
	 */
	public ArrayList<Leyenda> getEternidad() {
		return eternidad;
	}

	/**
	 * Funcion que reorganiza al equipo, para que los espacios vacios se queden al
	 * final del equipo y no hayas huecos intermedios
	 */
	public void reorganizaEquipo() {
		if (getNumLeyendasEnEquipo() == 0) {
			if (!eternidad.isEmpty()) {
				Leyenda l = eternidad.remove(eternidad.size() - 1);
				anyadirNuevaLeyenda(l);
			}
		}
		Leyenda[] copia = equipo;
		equipo = new Leyenda[NUM_PER];
		for (Leyenda ley : copia) {
			if (ley != null) {
				anyadirNuevaLeyenda(ley);
			}
		}
	}

	/**
	 * Devuelve los doblones del jugador
	 * 
	 * @return
	 */
	public int getDoblones() {
		return doblones;
	}

	/**
	 * Establece los doblones del jugador
	 * 
	 * @param doblones
	 */
	public void setDoblones(int doblones) throws IllegalArgumentException {
		if (doblones >= 0)
			this.doblones = doblones;
		else
			throw new IllegalArgumentException("Los doblones introducidos NO deben ser negativos");
	}

	/**
	 * Incrementa la cantidad de doblones del jugador segun lo indicado como
	 * parametro
	 * 
	 * @param incremento
	 */
	public void incDoblones(int incremento) {
		setDoblones(doblones + incremento);
	}

	/**
	 * Hace pagar al jugador el numero de doblones inidcado como parametro Estos se
	 * descuentan si el jugador los tiene disponibles. Si se descuentan se devolvera
	 * true. En caso contrario, o sea el jugador no tiene suficientes doblones como
	 * para pagar devuelve false
	 * 
	 * @param coste
	 * @return
	 */
	public boolean pagar(int coste) {
		try {
			setDoblones(doblones - coste);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}

	}

	/**
	 * Devuelve el numero de leyendas en posesion num de leyendas en equipo + num
	 * leyendas en eterenidad
	 * 
	 * @return
	 */
	public int getNumLeyendas() {
		return getNumLeyendasEnEquipo() + getNumLeyendasEnEternidad();
	}

	/**
	 * Cura la vida de todas las leyendas del jugador
	 */
	public void curarLeyendas() {
		for (Leyenda l : equipo)
			if (l != null)
				l.curar();
		for (Leyenda l : eternidad)
			if (l != null)
				l.curar();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + doblones;
		result = prime * result + Arrays.hashCode(equipo);
		result = prime * result + ((eternidad == null) ? 0 : eternidad.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jugador other = (Jugador) obj;
		if (doblones != other.doblones)
			return false;
		if (!Arrays.equals(equipo, other.equipo))
			return false;
		if (eternidad == null) {
			if (other.eternidad != null)
				return false;
		} else if (!eternidad.equals(other.eternidad))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	/**
	 * devuelve un nivel de dificultad
	 * 
	 * @return
	 */
	public double getNvDificultad() {
		return nvDificultad;
	}

	/**
	 * introduce un nivel de dificultad
	 * 
	 * @param nvDificultad
	 */
	public void setNvDificultad(double nvDificultad) {
		if (nvDificultad >= 0 && nvDificultad <= 1) {
			this.nvDificultad = nvDificultad;
		} else
			throw new IllegalArgumentException(
					"Introducido: " + nvDificultad + "El nvDificultad debe ser 0 =< nvDificultad <= 1");
	}

	public void incDificultad(double nvDificultadCont) {
		double nuevoNv = (this.nvDificultad + nvDificultadCont) / 2;
		try {
			setNvDificultad(nuevoNv);
		} catch (Exception e) {
			if (nuevoNv > 1) {
				setNvDificultad(1);
			} else {
				setNvDificultad(0);
			}
		}
	}

}
