package personaje;

import personaje.atributos.Habilidad;

/**
 * 
 * @author danel y jon ander
 *
 */
public class Leyenda extends Especie {
	// Estado estado;
	Habilidad[] movimientos = new Habilidad[4];
	int ataque;
	int defensa;
	int velocidad;
	int vida;

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
	 * devuelve los movimientos del personaje
	 * 
	 * @return
	 */
	public Habilidad[] getMovimientos() {
		return movimientos;
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
		this.movimientos = movimientos;
	}

	/**
	 * Fusiona dos personajes y devuelve el resultado como parametro
	 * 
	 * @param p1 personaje a fusionar 1
	 * @param p2 personaje a fusionar 2
	 * @return personaje fusionado
	 */
	public static Leyenda fusion(Leyenda p1, Leyenda p2) {
		// TODO
		return null;
	}

}
