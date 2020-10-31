package personaje;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import gestion.GestorDeDatos;
import personaje.atributos.Habilidad;
import personaje.atributos.Tipo;

/**
 * 
 * @author danel y jon ander
 *
 */
public class Leyenda extends Especie {
	// Estado estado;
	Habilidad[] habilidades = new Habilidad[4];
	int ataque;
	int defensa;
	int velocidad;
	int vida;
	int vidaMax;
	/**
	 * Constructor que te recibe todos los datos de la leyenda
	 * 
	 * @param nombre
	 * @param descripcion
	 * @param tipos
	 * @param habilidades
	 * @param ataque
	 * @param defensa
	 * @param velocidad
	 * @param vida
	 */
	public Leyenda(String nombre, String descripcion, Tipo[] tipos, Habilidad[] habilidades, int ataque, int defensa,
			int velocidad, int vida) {
		super(nombre, descripcion, tipos);
		setHabilidades(habilidades);
		setAtaque(ataque);
		setDefensa(defensa);
		setVelocidad(velocidad);
		setVida(vida);
		setVidaMax(vida);
		
	}

	/**
	 * Constructor para crear personaje desde cero
	 * 
	 * @param nombre
	 * @param descripcion
	 * @param tipos
	 */
	public Leyenda(String nombre, String descripcion, Tipo[] tipos) {
		super(nombre, descripcion, tipos);
		generarStatsRandom();

	}

	/**
	 * Constructor que te recibe todos los datos de la leyenda menos las habilidades
	 * 
	 * @param nombre
	 * @param descripcion
	 * @param tipos
	 * @param ataque
	 * @param defensa
	 * @param velocidad
	 * @param vida
	 */
	public Leyenda(String nombre, String descripcion, Tipo[] tipos, int ataque, int defensa, int velocidad, int vida) {
		super(nombre, descripcion, tipos);
		setAtaque(ataque);
		setDefensa(defensa);
		setVelocidad(velocidad);
		setVida(vida);
		setVidaMax(vida);
	}
/**
 *  devuelve la vidaMax del personaje
 * @return
 */
	public int getVidaMax() {
		return vidaMax;
	}
/**
 *  establece la VidaMax del personaje
 * @param vidaMax
 */
	public void setVidaMax(int vidaMax) {
		if (vidaMax <= 999) {
			this.vidaMax = vidaMax;
		} else
			throw new IllegalArgumentException("Introducido: " + vidaMax + "La vida debe ser vida <= 999");
	
	}

	/**
	 * genera estadisticas aleatorias
	 */
	private void generarStatsRandom() {
		Random r = new Random();
		int vidaN= r.nextInt(100+1)+200;
		setVida(vidaN);
		setVidaMax(vidaN);
		int ataqueN= r.nextInt(25+1)+25;
		setAtaque(ataqueN);
		int defensaN= r.nextInt(25+1)+25;
		setDefensa(defensaN);
		int velocidadN= r.nextInt(25+1)+25;
		setVelocidad(velocidadN);
	}

	/**
	 * devuelve el ataque del personaje
	 * 
	 * @return
	 */
	public int getAtaque() {
		return ataque;
	}

	/**
	 * devuelve la defensa del personaje
	 * 
	 * @return
	 */
	public int getDefensa() {
		return defensa;
	}

	/**
	 * devuelve la velocidad del personaje
	 * 
	 * @return
	 */
	public int getVelocidad() {
		return velocidad;
	}

	/**
	 * devuelve la vida del personaje
	 * 
	 * @return
	 */
	public int getVida() {
		return vida;
	}

	/**
	 * devuelve las habilidades del personaje
	 * 
	 * @return
	 */
	public Habilidad[] getHabilidades() {
		return habilidades;
	}

	/**
	 * Establece el ataque del personaje
	 * 
	 * @throws IllegalArgumentException
	 * @param ataque
	 */
	public void setAtaque(int ataque) throws IllegalArgumentException {
		if (ataque >= 1 && ataque <= 99) {
			this.ataque = ataque;
		} else
			throw new IllegalArgumentException("Introducido: " + ataque + "El ataque debe ser 1 <= ataque <= 99");

	}

	/**
	 * Establece la defensa del personaje
	 * 
	 * @throws IllegalArgumentException
	 * @param defensa
	 */
	public void setDefensa(int defensa) throws IllegalArgumentException {
		if (defensa >= 1 && defensa <= 99) {
			this.defensa = defensa;
		} else
			throw new IllegalArgumentException("Introducido: " + defensa + "La defensa debe ser 1 <= defensa <= 99");
	}

	/**
	 * Establece la velocidad del personaje
	 * 
	 * @throws IllegalArgumentException
	 * @param velocidad
	 */
	public void setVelocidad(int velocidad) throws IllegalArgumentException {
		if (velocidad >= 1 && velocidad <= 99) {
			this.velocidad = velocidad;
		} else
			throw new IllegalArgumentException(
					"Introducido: " + velocidad + "La velocidad debe ser 1 <= velocidad <= 99");
	}

	/**
	 * Establece la vida del personaje
	 * 
	 * @throws IllegalArgumentException
	 * @param vida
	 */
	public void setVida(int vida) throws IllegalArgumentException {
		if (vida <= 999) {
			this.vida = vida;
		} else
			throw new IllegalArgumentException("Introducido: " + vida + "La vida debe ser vida <= 999");
	}

	/**
	 * Establece los movimientos del personaje
	 * 
	 * @param movimientos
	 */
	public void setHabilidades(Habilidad[] habilidades) {
		this.habilidades = habilidades;
	}

	/**
	 * Fusiona dos personajes y devuelve el resultado como parametro
	 * 
	 * @param p1 personaje a fusionar 1
	 * @param p2 personaje a fusionar 2
	 * @return personaje fusionado
	 */
	public static Leyenda fusion(Leyenda p1, Leyenda p2) {
		// Calculo de los atributos numericos
		int extraPts = 25;
		int nAtk = ((p1.getAtaque() + p2.getAtaque()) / 2) + extraPts;
		int nDef = ((p1.getDefensa() + p2.getDefensa()) / 2) + extraPts;
		int nVel = ((p1.getVelocidad() + p2.getVelocidad()) / 2) + extraPts;
		int nVida = ((p1.getVida() + p2.getVida()) / 2) + extraPts;

		// Seleccion de los movimientos
		ArrayList<Habilidad> posHabs = new ArrayList<>();
		posHabs.addAll(Arrays.asList(p1.getHabilidades()));
		posHabs.addAll(Arrays.asList(p2.getHabilidades()));
		int numHabs = 4;
		Habilidad[] habsElegidas = new Habilidad[numHabs];
		for (int x = 0; x < numHabs; x++) {
			Random r = new Random();
			int id = r.nextInt(posHabs.size());
			habsElegidas[x] = posHabs.get(id);
			posHabs.remove(id);
		}
		Especie esp = null;
		while (esp == null) {
			// Seleccion de los tipos
			ArrayList<Tipo> posTipos = new ArrayList<>();
			posTipos.addAll(Arrays.asList(p1.getTipos()));
			posTipos.addAll(Arrays.asList(p2.getTipos()));
			Tipo tipo1 = null;
			int id = 0;
			while (tipo1 == null) {
				Random r = new Random();
				id = r.nextInt(posTipos.size());
				tipo1 = posTipos.get(id);
			}

			Tipo tipo2 = tipo1;
			while (tipo2.equals(tipo1)) {
				posTipos.remove(id); // primero elimina el tipo elegido en el primer while y luego va borrando el
										// anterior seleccionado ya que ha vuelto a ejecutar el while por lo que
										// significa que es un tipo no valido
				Random r = new Random();
				id = r.nextInt(posTipos.size());
				tipo2 = posTipos.get(id);
			}

			esp = GestorDeDatos.buscarEspecieEnBD(tipo1, tipo2);
		}

		Leyenda l = new Leyenda(esp.nombre, esp.descripcion, esp.tipos, habsElegidas, nDef, nVel, nVida, nAtk);

		return l;
	}

	

	public void danyar(double danyo) {
		danyo -= this.defensa;
		if (danyo >= 0) {
			this.vida -= (int) danyo;
		}
	}
}
