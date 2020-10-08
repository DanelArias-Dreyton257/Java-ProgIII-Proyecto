package personaje.atributos;

public class Movimiento {

	Tipo tipo;
	int potencia;
	int precision;

	/**
	 * Devuelve el tipo del movimiento
	 * 
	 * @return
	 */
	public Tipo getTipo() {
		return tipo;
	}

	/**
	 * Devuelve la potencia del movimiento
	 * 
	 * @return
	 */
	public int getPotencia() {
		return potencia;
	}

	/**
	 * Devuelve la precision del movimiento
	 * 
	 * @return
	 */

	public int getPrecision() {
		return precision;
	}

	/**
	 * Establece la potencia del movimiento
	 * 
	 * @param potencia
	 */

	public void setPotencia(int potencia) {
		this.potencia = potencia;
	}

	/**
	 * Establece la precision del movimiento
	 * 
	 * @param precision
	 */

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	/**
	 * Establece el tipo del movimiento
	 * 
	 * @param tipo
	 */
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

}
