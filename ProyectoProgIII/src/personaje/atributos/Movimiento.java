package personaje.atributos;

public class Movimiento {

	protected Tipo tipo;
	protected int potencia;
	protected double precision;

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

	public double getPrecision() {
		return precision;
	}

	/**
	 * Establece la potencia del movimiento
	 * 
	 * @param potencia
	 */

	public void setPotencia(int potencia) throws IllegalArgumentException {
		if (potencia >= 0 && potencia <= 99) {
			this.potencia = potencia;
		} else
			throw new IllegalArgumentException("Introducido: " + potencia + "La potencia debe ser 0 <= potencia <= 99");
	}

	/**
	 * Establece la precision del movimiento
	 * 
	 * @param precision
	 */

	public void setPrecision(double precision) throws IllegalArgumentException {
		if (precision >= 0 && precision <= 1) {
			this.precision = precision;
		} else
			throw new IllegalArgumentException(
					"Introducido: " + precision + "La precision debe ser 0 <= precision <= 1");
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
