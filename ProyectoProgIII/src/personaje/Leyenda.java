package personaje;

import personaje.atributos.Estado;
import personaje.atributos.Movimiento;

public class Leyenda extends Especie{
	Estado estado;
	Movimiento[] movimientos = new Movimiento[4];
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
	public Movimiento[] getMovimientos() {
		return movimientos;
	}
	/**
	 * Establece el ataque del personaje
	 * 
	 * @param ataque
	 */
	public void setAtaque(int ataque) {
		if (ataque>=1) {
			this.ataque = ataque;	
		}
		
	}
	/**
	 * Establece la defensa del personaje
	 * 
	 * @param defensa
	 */
	public void setDefensa(int defensa) {
		if (defensa>=1) {
			this.defensa = defensa;	
		}
	}
	/**
	 * Establece la velocidad del personaje
	 * 
	 * @param velocidad
	 */	
	public void setVelocidad(int velocidad) {
		if (velocidad>=1) {
			this.velocidad = velocidad;
		}
	}
	/**
	 * Establece la vida del personaje
	 * 
	 * @param vida
	 */
	public void setVida(int vida) {
		if (vida>=1) {
		this.vida = vida;
		}
	}
	/**
	 * Establece los movimientos del personaje
	 * 
	 * @param movimientos
	 */
	public void setMovimientos(Movimiento[] movimientos) {
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
