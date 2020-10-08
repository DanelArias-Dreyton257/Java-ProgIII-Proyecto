package personaje;

import java.lang.annotation.Retention;

import personaje.atributos.Estado;
import personaje.atributos.Movimiento;
import personaje.atributos.Tipo;

public class Personaje {
	
//	String nombre;
//	String descripcion;
	Estado estado;
	Tipo[] tipos = new Tipo[2];
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
		 * devuelve los tipos del personaje
		 * 
		 * @return
		 */
	public Tipo[] getTipos() {
		return tipos;
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
		this.ataque = ataque;
	}
	/**
	 * Establece la defensa del personaje
	 * 
	 * @param defensa
	 */
	public void setDefensa(int defensa) {
		this.defensa = defensa;
	}
	/**
	 * Establece la velocidad del personaje
	 * 
	 * @param velocidad
	 */	
	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}
	/**
	 * Establece la vida del personaje
	 * 
	 * @param vida
	 */
	public void setVida(int vida) {
		this.vida = vida;
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
		 * Establece los tipos del personaje
		 * 
		 * @param tipos
		 */
	public void setTipos(Tipo[] tipos) {
		this.tipos = tipos;
	}
}
