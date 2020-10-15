package personaje;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

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

	/**
	 * Metodo provisional--JON ANDER DEBES REVISAR SI ESTO ES NECESARIO-- Dejarlo
	 * para que compile pero falta hacer los constructores de verdad
	 * 
	 * @param esp
	 * @param nAtk
	 * @param nDef
	 * @param nVel
	 * @param nVida
	 * @param habsElegidas
	 */
	public Leyenda(Especie esp, int nAtk, int nDef, int nVel, int nVida, Habilidad[] habsElegidas) {
		// TODO Auto-generated constructor stub
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
	public void setMovimientos(Habilidad[] movimientos) {
		this.habilidades = movimientos;
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

			esp = buscarEspecieEnBD(tipo1, tipo2);
		}

		Leyenda l = new Leyenda(esp, nAtk, nDef, nVel, nVida, habsElegidas);

		return l;
	}

	/**
	 * --METODO PROVISIONAL--Dejarlo para que compile pero falta hacer los
	 * constructores de verdad
	 * 
	 * @param tipo1
	 * @param tipo2
	 * @return
	 */
	private static Especie buscarEspecieEnBD(Tipo tipo1, Tipo tipo2) {
		// TODO Auto-generated method stub
		return null;
	}

}
